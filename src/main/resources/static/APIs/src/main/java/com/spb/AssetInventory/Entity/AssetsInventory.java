package com.spb.AssetInventory.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "assetsinventory")
public class AssetsInventory {
	@Id
	private String employee_id;
	private String employee_name;
	private String employee_department;
	private String asset_tag;
	private String asset_type;
	private String asset_model;
	private String asset_serialnumber;
	private String os_info;
	private String location;
	private byte[] handover_form;
	public AssetsInventory() {}
	public AssetsInventory(String employee_id, String employee_name, String employee_department, String asset_tag,
			String asset_type, String asset_model, String asset_serialnumber, String os_info, String location,
			byte[] handover_form) {
		super();
		this.employee_id = employee_id;
		this.employee_name = employee_name;
		this.employee_department = employee_department;
		this.asset_tag = asset_tag;
		this.asset_type = asset_type;
		this.asset_model = asset_model;
		this.asset_serialnumber = asset_serialnumber;
		this.os_info = os_info;
		this.location = location;
		this.handover_form = handover_form;
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
	public String getEmployee_department() {
		return employee_department;
	}
	public void setEmployee_department(String employee_department) {
		this.employee_department = employee_department;
	}
	public String getAsset_tag() {
		return asset_tag;
	}
	public void setAsset_tag(String asset_tag) {
		this.asset_tag = asset_tag;
	}
	public String getAsset_type() {
		return asset_type;
	}
	public void setAsset_type(String asset_type) {
		this.asset_type = asset_type;
	}
	public String getAsset_model() {
		return asset_model;
	}
	public void setAsset_model(String asset_model) {
		this.asset_model = asset_model;
	}
	public String getAsset_serialnumber() {
		return asset_serialnumber;
	}
	public void setAsset_serialnumber(String asset_serialnumber) {
		this.asset_serialnumber = asset_serialnumber;
	}
	public String getOs_info() {
		return os_info;
	}
	public void setOs_info(String os_info) {
		this.os_info = os_info;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public byte[] getHandover_form() {
		return handover_form;
	}
	public void setHandover_form(byte[] handover_form) {
		this.handover_form = handover_form;
	}
	
}
