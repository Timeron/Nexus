package com.nexus.apps.wallet.service.dto;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.WalletType;

public class RecordTypeDTO {

	private int id;
	private String name;
	private int defaultValue;
	private String color;
	private String icon;
	private Date timestamp;
	private Date updated;
	
	public RecordTypeDTO() {}
	
	public RecordTypeDTO(WalletType walletType) {
		id = walletType.getId();
		name = walletType.getName();
		defaultValue = walletType.getDefaultValue();
		color = walletType.getColor();
		icon = walletType.getIcon();
		timestamp = walletType.getTimestamp();
		updated = walletType.getUpdated();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(int defaultValue) {
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
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	
}
