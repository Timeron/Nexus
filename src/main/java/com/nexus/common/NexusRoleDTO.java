package com.nexus.common;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.NexusRole;

public class NexusRoleDTO {

	private Integer id;
	private String name;
	private String description;
	private Date updateTimestamp;
	private Date createTimestamp;
	
	public NexusRoleDTO(NexusRole nexusRole) {
		this.id = nexusRole.getId();
		this.name = nexusRole.getName();
		this.description = nexusRole.getDescription();
		this.updateTimestamp = nexusRole.getUpdateTimestamp();
		this.createTimestamp = nexusRole.getCreateTimestamp();
	}
	
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
	
	
}
