package com.nexus.apps.jTask.dto.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.timeron.NexusDatabaseLibrary.Entity.JProject;

public class JProjectDTO {

	private int id;
	private String name;
	private String description;
	private Date created;
	private String prefix;
	private List<JTaskDTO> tasks = new ArrayList<JTaskDTO>();
	
	public JProjectDTO(JProject project) {
		this.id = project.getId();
		this.name = project.getName();
		this.description = project.getDescription();
		this.created = project.getCreated();
		this.prefix = project.getPrefix();
	}
	
	public JProjectDTO() {}

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

	public List<JTaskDTO> getTasks() {
		return tasks;
	}

	public void setTasks(List<JTaskDTO> tasks) {
		this.tasks = tasks;
	}

	public void addTasks(List<JTaskDTO> tasks) {
		this.tasks.addAll(tasks);
	}
	
	public void addTask(JTaskDTO task) {
		this.tasks.add(task);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setjProject(JProject jProject) {
		id = jProject.getId();
		name = jProject.getName();
		description = jProject.getDescription();
		created = jProject.getCreated();
		prefix = jProject.getPrefix();
	}
	
	
}
