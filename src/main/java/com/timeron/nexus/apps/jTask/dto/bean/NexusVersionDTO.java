package com.timeron.nexus.apps.jTask.dto.bean;

import com.timeron.NexusDatabaseLibrary.Entity.NexusVersion;

public class NexusVersionDTO {

	private int id;
	private String app;
	private String version;
	private String comment;
	
	public NexusVersionDTO(){}
	
	public NexusVersionDTO(NexusVersion nexusVersion){
		this.id = nexusVersion.getId();
		this.app = nexusVersion.getApp();
		this.version = nexusVersion.getVersion();
		this.comment = nexusVersion.getComment();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
