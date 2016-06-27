package com.timeron.nexus.apps.jTask.dto.bean;

import com.timeron.NexusDatabaseLibrary.Entity.JStatus;


public class JStatusDTO {
	
	private int id;
	private String description;
	
	public JStatusDTO(JStatus jStatus) {
		this.id = jStatus.getId();
		this.description = jStatus.getDescription();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
