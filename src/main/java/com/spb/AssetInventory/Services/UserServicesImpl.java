package com.spb.AssetInventory.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.spb.AssetInventory.Entity.Users;
import com.spb.AssetInventory.Repository.UsersRepository;
@Service
public class UserServicesImpl implements UserServices {
	@Autowired
	private UsersRepository usersRepository ;
	@Override
	public Optional<Users> findById(String id) {
		Optional<Users> user = usersRepository.findById(id);
		return user;
	}
	@Override
	public void save(Users user) {
		usersRepository.save(user);
		
	}
	@Override
	public List<Users> findAll(){
		List<Users> users = usersRepository.findAll();
		return users;
	}

}
