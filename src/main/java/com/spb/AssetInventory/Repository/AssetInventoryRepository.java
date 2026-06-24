package com.spb.AssetInventory.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.spb.AssetInventory.Entity.AssetsInventory;

public interface AssetInventoryRepository extends JpaRepository<AssetsInventory, String>,JpaSpecificationExecutor<AssetsInventory>{
	boolean existsByEmployeeidIgnoreCase(String employeeid);
	boolean existsByAssettagIgnoreCase(String assettag);
	boolean existsByAssetserialnumberIgnoreCase(String assetserialnumber);
}
