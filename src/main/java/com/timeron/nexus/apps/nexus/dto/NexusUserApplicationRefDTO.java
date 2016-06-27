package com.timeron.nexus.apps.nexus.dto;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.NexusUserApplicationRef;

public class NexusUserApplicationRefDTO {

	private Integer id;
	private Integer userId;
	private Integer applicationId;
	private Date timestamp;
	
	public NexusUserApplicationRefDTO(){}
	
	public NexusUserApplicationRefDTO(NexusUserApplicationRef nexusUserApplicationRef) {
		super();
		this.id = nexusUserApplicationRef.getId();
		this.userId = nexusUserApplicationRef.getUser().getId();
		this.applicationId = nexusUserApplicationRef.getApplication().getId();
		this.timestamp = nexusUserApplicationRef.getTimestamp();
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
