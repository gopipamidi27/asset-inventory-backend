package com.spb.AssetInventory.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spb.AssetInventory.Entity.AssetsInventory;
import com.spb.AssetInventory.Entity.Users;
import com.spb.AssetInventory.Services.AssetInventoyServices;
import com.spb.AssetInventory.Services.UserServices;

@RestController
public class AssetInventoryController {
	@Autowired
	private AssetInventoyServices assetInventoryService;
	@Autowired
	private UserServices userServices ;
	//@CrossOrigin(origins = "http://127.0.0.1:5500")
	@PostMapping("/savedetails/")
	public ResponseEntity<?> Savedetails(@RequestParam String employee_id,
										 @RequestParam String employee_name,
										 @RequestParam String employee_department,
										 @RequestParam String asset_tag,
										 @RequestParam String asset_type,
										 @RequestParam String asset_model,
										 @RequestParam String asset_serialnumber,
										 @RequestParam String os_info,
										 @RequestParam String location,
										 @RequestParam("handover_form") MultipartFile file){
		byte[] handover_form = null;
		try {
			handover_form = file.getBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(assetInventoryService.existsByAssetTag(asset_tag)) {
			return ResponseEntity.ok("Asset tag already exists");
		}
		if(assetInventoryService.existsByAssetSerialnumber(asset_serialnumber)) {
			return ResponseEntity.ok("Asset serialnumber already exists");
		}
		AssetsInventory assetInventory = new AssetsInventory();
		assetInventory.setEmployeeid(employee_id);
		assetInventory.setEmployee_name(employee_name);
		assetInventory.setEmployee_department(employee_department);
		assetInventory.setAssettag(asset_tag);
		assetInventory.setAsset_type(asset_type);
		assetInventory.setAsset_model(asset_model);
		assetInventory.setAssetserialnumber(asset_serialnumber);
		assetInventory.setOs_info(os_info);
		assetInventory.setLocation(location);
		assetInventory.setHandover_form(handover_form);
		assetInventoryService.saveDetail(assetInventory);
		return ResponseEntity.ok("Details saved");
	}
//	@CrossOrigin(origins = "http://127.0.0.1:5500")
//	@PostMapping("/uploaddetails/")
//	public ResponseEntity<?> uploaddetails(@RequestParam("file") MultipartFile file){
//		if(!file.getOriginalFilename().endsWith(".xlsx")) {
//			return ResponseEntity.ok("please upload .Xlsx file only");
//		}
//		assetInventoryService.uploadDetails(file);
//		return ResponseEntity.ok("Details uploaded");
//	}
//	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@GetMapping("/inventorylist/")
	public ResponseEntity<?> inventorylist(){
		List<AssetsInventory> assetsInventory = assetInventoryService.findAll();
		return ResponseEntity.ok(assetsInventory);
	}
//	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@GetMapping("/inventory/{id}")
	public ResponseEntity<?> inventoryForID(@PathVariable String id){
		AssetsInventory asset = assetInventoryService.findById(id);
		if(asset == null ) {
			return ResponseEntity.ok("No data found with given id ");
		}
		
		return ResponseEntity.ok(asset);
	}
	@PostMapping("/saveuser/")
	public ResponseEntity<?> saveuser(@RequestBody Users user){
		userServices.save(user);
		return ResponseEntity.ok("user created successfully");
	}
//	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@GetMapping("/user/{id}")
	public ResponseEntity<?> username(@PathVariable String id){
		Optional<Users> user = userServices.findById(id);
		if(user.isEmpty() ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found with given id :" +id);
		}
		
		return ResponseEntity.ok(user);
	}
//	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@PutMapping("/updatedetails/{id}")
	public ResponseEntity<?> updatedetails(@PathVariable String id,@RequestParam String employee_name,
			@RequestParam String employee_department,
			@RequestParam String employee_id,
			@RequestParam String asset_type,
			@RequestParam String asset_model,
			@RequestParam String asset_serialnumber,
			@RequestParam String os_info,
			@RequestParam String location,
			@RequestParam("handover_form") MultipartFile file){
		byte[] handover_form = null;
		try {
			handover_form = file.getBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AssetsInventory assetInventory = new AssetsInventory();
		assetInventory.setEmployeeid(employee_id);
		assetInventory.setEmployee_name(employee_name);
		assetInventory.setEmployee_department(employee_department);
		assetInventory.setAssettag(id);
		assetInventory.setAsset_type(asset_type);
		assetInventory.setAsset_model(asset_model);
		assetInventory.setAssetserialnumber(asset_serialnumber);
		assetInventory.setOs_info(os_info);
		assetInventory.setLocation(location);
		assetInventory.setHandover_form(handover_form);
		assetInventoryService.saveDetail(assetInventory);
		return ResponseEntity.ok("Details Updated");
		
	}
//	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@PatchMapping("updateuserdetails/{id}")
	public ResponseEntity<?> updateuserdetails(@PathVariable String id, @RequestBody Users user){
		Optional<Users> useroptional = userServices.findById(id);
		if(useroptional.isEmpty() ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found with given id :" +id);
		}
		Users user1 = useroptional.get();
		if(user.getEmployee_name() != null)
			user1.setEmployee_name(user.getEmployee_name());
		if(user.getPassword() != null)
			user1.setPassword(user.getPassword());
		userServices.save(user1);
		return ResponseEntity.ok("user details updated");
	}
//	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@GetMapping("/downloadinventorylist/")
	public ResponseEntity<?> downloadinventorylist(){
		byte[] assetslist = assetInventoryService.downloadAll();
		if (assetslist == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Data Found");
		}
		String filename = "InventoryList.xlsx";
		return ResponseEntity.ok()
				.header("CONTENT-TYPE" ,"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
				.header("CONTENT-DISPOSITION","attachment;filename=\""+filename+"\"")
				.body(assetslist);
	}
	@GetMapping("downloadhandover/{id}")
	public ResponseEntity<?> downloadhandover(@PathVariable String id){
		byte[] handover = assetInventoryService.handover(id);
			String filename = id+".pdf" ;
			return ResponseEntity.ok()
					.header("Content-Type","application/pdf")
					.header("Content-Disposition","inline;filename=\""+filename+"\"")
					.body(handover);
		}
	@GetMapping("/search/{keyword}")
	public ResponseEntity<?> search(@PathVariable String keyword){
		List<AssetsInventory> assets = assetInventoryService.search(keyword);
		return ResponseEntity.ok(assets);
	}
//	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@PostMapping("/uploaddetails/")
	public ResponseEntity<?> uploaddetails(@RequestParam("file") MultipartFile file) throws IOException, Exception{
		if(!file.getOriginalFilename().endsWith(".xlsx")) {
			return ResponseEntity.ok("please upload .Xlsx file only");
		}
		List<AssetsInventory> assetsInventoryItems = assetInventoryService.findAll();
		List<AssetsInventory> assetsInventoryItems1 = new ArrayList<>();
		InputStream is = file.getInputStream();
		Workbook workbook = WorkbookFactory.create(is);
		Sheet sheet = workbook.getSheetAt(0);
		for ( Row row : sheet) {
			AssetsInventory assetsInventory = new AssetsInventory();
			if(row.getRowNum() == 0) {
				if(row.getCell(0).getStringCellValue().equalsIgnoreCase("Employee_id")&&
						row.getCell(1).getStringCellValue().equalsIgnoreCase("Employee_name")&&
						row.getCell(2).getStringCellValue().equalsIgnoreCase("Employee_department")&&
						row.getCell(3).getStringCellValue().equalsIgnoreCase("Asset_tag")&&
						row.getCell(4).getStringCellValue().equalsIgnoreCase("Asset_type")&&
						row.getCell(5).getStringCellValue().equalsIgnoreCase("Asset_model")&&
						row.getCell(6).getStringCellValue().equalsIgnoreCase("Asset_serialnumber")&&
						row.getCell(7).getStringCellValue().equalsIgnoreCase("Os_info")&&
						row.getCell(8).getStringCellValue().equalsIgnoreCase("Location"))
					continue ;
				else
					return ResponseEntity.ok("Uploaded File formate was not matched");
			}
			if(row.getCell(0) == null ||
					row.getCell(0).getStringCellValue().trim().isEmpty() || 
					row.getCell(3) == null ||
					row.getCell(3).getStringCellValue().trim().isEmpty() ||
					row.getCell(6) == null ||
					row.getCell(6).getStringCellValue().trim().isEmpty())
				return ResponseEntity.ok("null values not accepted");
			for(AssetsInventory asset : assetsInventoryItems) {
				if(row.getCell(0).getStringCellValue().equals(asset.getEmployeeid())
						|| row.getCell(3).getStringCellValue().equals(asset.getAssettag())
						|| row.getCell(6).getStringCellValue().equals(asset.getAssetserialnumber()))
					return ResponseEntity.ok("Duplicate values are not allowed");
			}
			
			assetsInventory.setEmployeeid(row.getCell(0).getStringCellValue());
			assetsInventory.setEmployee_name(row.getCell(1).getStringCellValue());
			assetsInventory.setEmployee_department(row.getCell(2).getStringCellValue());
			assetsInventory.setAssettag(row.getCell(3).getStringCellValue());
			assetsInventory.setAsset_type(row.getCell(4).getStringCellValue());
			assetsInventory.setAsset_model(row.getCell(5).getStringCellValue());
			assetsInventory.setAssetserialnumber(row.getCell(6).getStringCellValue());
			assetsInventory.setOs_info(row.getCell(7).getStringCellValue());
			assetsInventory.setLocation(row.getCell(8).getStringCellValue());
			assetsInventory.setHandover_form(null);
			assetsInventoryItems1.add(assetsInventory);
		}
		if(assetsInventoryItems1.isEmpty()) {
			return ResponseEntity.ok("Uploaded file is Empty");
		}
		assetInventoryService.uploadDetails(assetsInventoryItems1);
		return ResponseEntity.ok("Details uploaded successfully");
	}
	
	
}
