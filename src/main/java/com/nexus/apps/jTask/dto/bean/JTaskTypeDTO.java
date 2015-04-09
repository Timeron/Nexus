package com.nexus.apps.jTask.dto.bean;

import com.timeron.NexusDatabaseLibrary.Entity.JTaskType;

public class JTaskTypeDTO {
	
	private int id;
	private String description;
	
	public JTaskTypeDTO(JTaskType jTaskType) {
		this.id = jTaskType.getId();
		this.description = jTaskType.getDescription();
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
