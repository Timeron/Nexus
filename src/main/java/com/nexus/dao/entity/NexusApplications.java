package com.nexus.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="nexus_applications")
public class NexusApplications {

	@Id
	@GeneratedValue
	private int id;

	@Column(name="application_name", length = 30)
	private String applicationName;
	
	@Column(name="application_description")
	private String applicationDescription;
	
	private boolean deployed;
	
	@GeneratedValue
	@Column(name = "updat_timestamp")
	@Temporal (TemporalType.TIMESTAMP)
	private Date updateTimestamp;
	
	@GeneratedValue
	@Column(name = "timestamp")
	@Temporal (TemporalType.TIMESTAMP)
	private Date createTimestamp;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private NexusUser userId;
	
	/*
	 * getters & setters
	 */
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public NexusUser getUserId() {
		return userId;
	}

	public void setUserId(NexusUser userId) {
		this.userId = userId;
	}
	
}
