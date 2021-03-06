package com.timeron.nexus.apps.jTask.dto.bean;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.JTask;
import com.timeron.nexus.common.dto.NexusPersonDTO;

public class JTaskDTO {

	private int id;
	private String name;
	private String summary;
	private String description;
	private Date endDate;
	private long endDateLong = 0;
	private long workExpected = 0;
	private Date created;
	private Date updated;
	private Integer mainTaskId;
	private String mainTaskName;
	private Integer taskTypeId;
	private Integer priority;
	private Integer projectId;
	private Integer status;
	private String statusDescription;
	private String updateMessage;
	private Integer updateMessageStatus;
	private NexusPersonDTO user;
	
	public JTaskDTO() {
		
	}
	
	public JTaskDTO(JTask jTask) {
		this.id = jTask.getId();
		this.name = jTask.getName();
		this.summary = jTask.getSummary();
		this.description = jTask.getDescription();
		this.created = jTask.getCreated();
		this.priority = jTask.getPriority();
		this.updated = jTask.getUpdated();
		this.projectId = jTask.getProject().getId();
		this.statusDescription = jTask.getStatus().getDescription();

		if(jTask.getMainTask() != null){
			this.mainTaskId = jTask.getMainTask().getId();
			this.mainTaskName =jTask.getMainTask().getName();
		}
		if(jTask.getTaskType() != null){
			this.taskTypeId = jTask.getTaskType().getId();
		}
		if(jTask.getStatus() != null){
			this.status = jTask.getStatus().getId();
		}
		if(jTask.getEndDate() != null){
			this.endDate = jTask.getEndDate();
		}
		if(jTask.getEndDate() != null){
			this.endDate = jTask.getEndDate();
			this.endDateLong = jTask.getEndDate().getTime();
		}
		if(jTask.getWorkExpected() != 0){
			this.workExpected = jTask.getWorkExpected();
		}
		if(jTask.getUser() != null){
			this.user = new NexusPersonDTO(jTask.getUser());
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
	public String getUpdateMessage() {
		return updateMessage;
	}
	public Integer getUpdateMessageStatus() {
		return updateMessageStatus;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public long getEndDateLong() {
		return endDateLong;
	}
	public void setEndDateLong(long endDateLong) {
		this.endDateLong = endDateLong;
		this.endDate = new Date(endDateLong);
	}
	public long getWorkExpected() {
		return workExpected;
	}
	public void setWorkExpected(long workExpected) {
		this.workExpected = workExpected;
	}
	public NexusPersonDTO getUser() {
		return user;
	}
	public void setUser(NexusPersonDTO user) {
		this.user = user;
	}
	public String getMainTaskName() {
		return mainTaskName;
	}

	@Override
	public String toString() {
		return "JTaskDTO [id=" + id + ", name=" + name + ", summary=" + summary
				+ ", description=" + description + ", endDate=" + endDate
				+ ", endDateLong=" + endDateLong + ", workExpected="
				+ workExpected + ", created=" + created + ", updated="
				+ updated + ", mainTaskId=" + mainTaskId + ", mainTaskName="
				+ mainTaskName + ", taskTypeId=" + taskTypeId + ", priority="
				+ priority + ", projectId=" + projectId + ", status=" + status
				+ ", statusDescription=" + statusDescription
				+ ", updateMessage=" + updateMessage + ", updateMessageStatus="
				+ updateMessageStatus + ", user=" + user + "]";
	}

	
}
