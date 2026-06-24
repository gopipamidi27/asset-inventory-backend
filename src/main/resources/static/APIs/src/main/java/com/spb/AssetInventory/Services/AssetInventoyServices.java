package com.spb.AssetInventory.Services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spb.AssetInventory.Entity.AssetsInventory;

public interface AssetInventoyServices {
	
	public void saveDetail(AssetsInventory assetsInventory);
	public void uploadDetails(MultipartFile file);
	public void updateDetails(String employee_id , MultipartFile file);
	public List<AssetsInventory> findAll();
	public AssetsInventory findById(String id);
	public byte[] downloadAll();
	public byte[] handover(String id);
	public List<AssetsInventory> search(String kword);

}
