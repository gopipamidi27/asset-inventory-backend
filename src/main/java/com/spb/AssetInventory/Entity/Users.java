package com.spb.AssetInventory.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Users {
	@Id
	private String employee_id;
	private String employee_name;
	private String password;
	public Users() {}
	public Users(String employee_id, String employee_name, String password) {
		super();
		this.employee_id = employee_id;
		this.employee_name = employee_name;
		this.password = password;
	}
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
