package com.nexus.apps.jTask.dto.bean;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.JTask;

public class JTaskDTO {

	private int id;
	private String name;
	private String summary;
	private String description;
	private Date created;
	private Date updated;
	private Integer mainTaskId;
	private Integer taskTypeId;
	private Integer priority;
	private Integer projectId;
	private Integer status;
	
	public JTaskDTO(JTask jTask) {
		this.id = jTask.getId();
		this.name = jTask.getName();
		this.summary = jTask.getSummary();
		this.description = jTask.getDescription();
		this.created = jTask.getCreated();
		this.priority = jTask.getPriority();
		this.updated = jTask.getUpdated();
		this.projectId = jTask.getProject().getId();
		
		if(jTask.getMainTask() != null){
			this.mainTaskId = jTask.getMainTask().getId();
		}
		if(jTask.getTaskType() != null){
			this.taskTypeId = jTask.getTaskType().getId();
		}
		if(jTask.getStatus() != null){
			this.status = jTask.getStatus().getId();
		}
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
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public Integer getMainTaskId() {
		return mainTaskId;
	}
	public void setMainTaskId(Integer mainTaskId) {
		this.mainTaskId = mainTaskId;
	}
	public Integer getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
