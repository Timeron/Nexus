package com.nexus.common;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.NexusApplications;

public class NexusApplicationsDTO {

	private Integer id;
	private String applicationName;
	private String applicationDescription;
	private boolean deployed;
	private Date updateTimestamp;
	private Date createTimestamp;
	
	public NexusApplicationsDTO(NexusApplications nexusApplications) {
		this.id = nexusApplications.getId();
		this.applicationName = nexusApplications.getApplicationName();
		this.applicationDescription = nexusApplications.getApplicationDescription();
		this.deployed = nexusApplications.isDeployed();
		this.updateTimestamp = nexusApplications.getUpdateTimestamp();
		this.createTimestamp = nexusApplications.getCreateTimestamp();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getApplicationDescription() {
		return applicationDescription;
	}
	public void setApplicationDescription(String applicationDescription) {
		this.applicationDescription = applicationDescription;
	}
	public boolean isDeployed() {
		return deployed;
	}
	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}
	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}
	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	public Date getCreateTimestamp() {
		return createTimestamp;
	}
	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}
	
	
}
