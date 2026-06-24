package com.spb.AssetInventory.Services;

import java.util.Optional;

import com.spb.AssetInventory.Entity.Users;

public interface UserServices {
	public Optional<Users> findById(String id);
	public void save(Users user);

}
