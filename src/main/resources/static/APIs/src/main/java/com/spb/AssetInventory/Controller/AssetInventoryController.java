package com.spb.AssetInventory.Controller;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spb.AssetInventory.Entity.AssetsInventory;
import com.spb.AssetInventory.Services.AssetInventoyServices;

@RestController
public class AssetInventoryController {
	@Autowired
	private AssetInventoyServices assetInventoryService;
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
		AssetsInventory assetInventory = new AssetsInventory();
		assetInventory.setEmployee_id(employee_id);
		assetInventory.setEmployee_name(employee_name);
		assetInventory.setEmployee_department(employee_department);
		assetInventory.setAsset_tag(asset_tag);
		assetInventory.setAsset_type(asset_type);
		assetInventory.setAsset_model(asset_model);
		assetInventory.setAsset_serialnumber(asset_serialnumber);
		assetInventory.setOs_info(os_info);
		assetInventory.setLocation(location);
		assetInventory.setHandover_form(handover_form);
		assetInventoryService.saveDetail(assetInventory);
		return ResponseEntity.ok("Details saved");
	}
	@PostMapping("/uploaddetails/")
	public ResponseEntity<?> uploaddetails(@RequestPart("file") MultipartFile file){
		if(!file.getOriginalFilename().endsWith(".xlsx")) {
			return ResponseEntity.ok("please upload .Xlsx file only");
		}
		assetInventoryService.uploadDetails(file);
		return ResponseEntity.ok("Details uploaded");
	}
	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@GetMapping("/inventorylist/")
	public ResponseEntity<?> inventorylist(){
		List<AssetsInventory> assetsInventory = assetInventoryService.findAll();
		return ResponseEntity.ok(assetsInventory);
	}
	@GetMapping("/inventory/{id}")
	public ResponseEntity<?> inventoryForID(@PathVariable String id){
		AssetsInventory asset = assetInventoryService.findById(id);
		if(asset == null ) {
			return ResponseEntity.ok("No data found with given id ");
		}
		
		return ResponseEntity.ok(asset);
	}
	@PutMapping("/updatedetails/{id}")
	public ResponseEntity<?> updatedetails(@PathVariable String id,@RequestParam String employee_name,
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
		AssetsInventory assetInventory = new AssetsInventory();
		assetInventory.setEmployee_id(id);
		assetInventory.setEmployee_name(employee_name);
		assetInventory.setEmployee_department(employee_department);
		assetInventory.setAsset_tag(asset_tag);
		assetInventory.setAsset_type(asset_type);
		assetInventory.setAsset_model(asset_model);
		assetInventory.setAsset_serialnumber(asset_serialnumber);
		assetInventory.setOs_info(os_info);
		assetInventory.setLocation(location);
		assetInventory.setHandover_form(handover_form);
		assetInventoryService.saveDetail(assetInventory);
		return ResponseEntity.ok("Details Updated");
		
	}
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
}
