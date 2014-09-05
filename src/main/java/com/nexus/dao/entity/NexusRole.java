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
@Table(name="nexus_role")
public class NexusRole {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length = 30)
	private String name;
	private String description;
	
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
	 * Getters & setters
	 */
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	@Override
	public String toString() {
		return "NexusRole [id=" + id + ", name=" + name + ", description="
				+ description + ", updateTimestamp=" + updateTimestamp
				+ ", createTimestamp=" + createTimestamp + ", userId=" + userId
				+ "]";
	}
	
	
	
	
}
