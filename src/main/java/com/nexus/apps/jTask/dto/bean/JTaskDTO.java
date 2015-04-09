package com.nexus.apps.jTask.dto.bean;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.JTask;

public class JTaskDTO {

	private int id;
	private String name;
	private String summary;
	private String description;
	private Date created;
	
	public JTaskDTO(JTask jTask) {
		this.id = jTask.getId();
		this.name = jTask.getName();
		this.summary = jTask.getSummary();
		this.description = jTask.getDescription();
		this.created = jTask.getCreated();
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}
