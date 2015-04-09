package com.nexus.apps.jTask.dto.bean;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.JProject;

public class JProjectDTO {

	private int id;
	private String name;
	private String description;
	private Date created;
	
	public JProjectDTO(JProject project) {
		this.id = project.getId();
		this.name = project.getName();
		this.description = project.getDescription();
		this.created = project.getCreated();
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

	public void setjProject(JProject jProject) {
		id = jProject.getId();
		name = jProject.getName();
		description = jProject.getDescription();
		created = jProject.getCreated();	
	}
	
	
}
