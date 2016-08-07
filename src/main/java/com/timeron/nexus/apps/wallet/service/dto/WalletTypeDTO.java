package com.timeron.nexus.apps.wallet.service.dto;

import com.timeron.NexusDatabaseLibrary.Entity.WalletType;


public class WalletTypeDTO {

	private Integer id;
	private String name;
	private boolean defaultValue;
	private String color;
	private String icon;
	
	public WalletTypeDTO(WalletType type) {
		this.name = type.getName();
		this.defaultValue = type.getDefaultValue();
		this.color = type.getColor();
		this.icon = type.getIcon();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	
}
