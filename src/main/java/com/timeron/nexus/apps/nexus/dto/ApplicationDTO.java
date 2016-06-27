package com.timeron.nexus.apps.nexus.dto;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.NexusApplication;

public class ApplicationDTO {

	private Integer id;
	private String name;
	private String description;
	private String key;
	private Date created;
	
	public ApplicationDTO() {

	}
	
	public ApplicationDTO(NexusApplication app) {
		this.id = app.getId();
		this.name = app.getApplicationName();
		this.description = app.getApplicationDescription();
		this.key = app.getAppKey();
		this.created = app.getCreateTimestamp();
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}
