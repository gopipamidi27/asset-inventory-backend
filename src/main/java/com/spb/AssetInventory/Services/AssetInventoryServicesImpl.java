package com.spb.AssetInventory.Services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.SheetBuilder;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.spb.AssetInventory.Entity.AssetsInventory;
import com.spb.AssetInventory.Repository.AssetInventoryRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
@Service
public class AssetInventoryServicesImpl implements AssetInventoyServices {
	@Autowired
	private AssetInventoryRepository assetInventoryRepository;
	@Override
	public void saveDetail(AssetsInventory assetsInventory) {
		assetInventoryRepository.save(assetsInventory);

	}

	@Override
	public void uploadDetails(MultipartFile file) {
			AssetsInventory assetsInventory = new AssetsInventory();
			try {
				InputStream is = file.getInputStream();
				Workbook workbook = WorkbookFactory.create(is);
				Sheet sheet = workbook.getSheetAt(0);
				for ( Row row : sheet) {
					if(row.getRowNum() == 0) continue;
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
					assetInventoryRepository.save(assetsInventory);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	@Override
	public List<AssetsInventory> findAll() {
		List<AssetsInventory> assetsInventory = assetInventoryRepository.findAll();
		return assetsInventory;
	}

	@Override
	public AssetsInventory findById(String id) {
		AssetsInventory assetsInventory = null;
			assetsInventory = assetInventoryRepository.findById(id).get();
	
		return assetsInventory;
	}
	@Override
	public byte[] downloadAll() {
		List<AssetsInventory> assets = assetInventoryRepository.findAll();
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		Row hrow = sheet.createRow(0);
		hrow.createCell(0).setCellValue("Employee_id");
		hrow.createCell(1).setCellValue("Employee_name");
		hrow.createCell(2).setCellValue("Employee_department");
		hrow.createCell(3).setCellValue("Asset_Tag");
		hrow.createCell(4).setCellValue("Asset_Type");
		hrow.createCell(5).setCellValue("Asset_Model");
		hrow.createCell(6).setCellValue("Asset_serialnumber");
		hrow.createCell(7).setCellValue("Os_Info");
		hrow.createCell(8).setCellValue("Location");
		int rowindex = 1;
		for(AssetsInventory asset : assets) {
			Row row = sheet.createRow(rowindex);
			row.createCell(0).setCellValue(asset.getEmployeeid());
			row.createCell(1).setCellValue(asset.getEmployee_name());
			row.createCell(2).setCellValue(asset.getEmployee_department());
			row.createCell(3).setCellValue(asset.getAssettag());
			row.createCell(4).setCellValue(asset.getAsset_type());
			row.createCell(5).setCellValue(asset.getAsset_model());
			row.createCell(6).setCellValue(asset.getAssetserialnumber());
			row.createCell(7).setCellValue(asset.getOs_info());
			row.createCell(8).setCellValue(asset.getLocation());
			rowindex++;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			workbook.write(out);
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return out.toByteArray();
	}

	@Override
	public void updateDetails(String employee_id, MultipartFile file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] handover(String id) {
		AssetsInventory handover = assetInventoryRepository.findById(id).get();
		byte[] bytehandover = handover.getHandover_form();
		return bytehandover;
	}

	@Override
	public List<AssetsInventory> search(String kword) {
		Specification<AssetsInventory> spic= new Specification<AssetsInventory>() {

			@Override
			public Predicate toPredicate(Root<AssetsInventory> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				String pattern = "%"+kword+"%";
				return cb.or(cb.like(cb.lower(root.get("employee_id")), pattern),
							cb.like(cb.lower(root.get("employee_name")),pattern),
							cb.like(cb.lower(root.get("employee_department")),pattern),
							cb.like(cb.lower(root.get("asset_tag")),pattern),
							cb.like(cb.lower(root.get("os_info")),pattern),
							cb.like(cb.lower(root.get("location")),pattern),
							cb.like(cb.lower(root.get("asset_type")),pattern),
							cb.like(cb.lower(root.get("asset_serialnumber")),pattern));
			}
		};
		return assetInventoryRepository.findAll(spic);
	}

	@Override
	public void uploadDetails(List<AssetsInventory> assetsInventory) {
		//assetInventoryRepository.save(assetsInventory);
		assetInventoryRepository.saveAll(assetsInventory);
	}

	@Override
	public boolean existsByEmployeeId(String id) {
		
		return assetInventoryRepository.existsByEmployeeidIgnoreCase(id);
	}

	@Override
	public boolean existsByAssetTag(String assettag) {
		// TODO Auto-generated method stub
		return assetInventoryRepository.existsByAssettagIgnoreCase(assettag);
	}

	@Override
	public boolean existsByAssetSerialnumber(String assetserialnumber) {
		// TODO Auto-generated method stub
		return assetInventoryRepository.existsByAssetserialnumberIgnoreCase(assetserialnumber);
	}

	

	

}
