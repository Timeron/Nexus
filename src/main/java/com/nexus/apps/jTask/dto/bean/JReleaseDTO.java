package com.nexus.apps.jTask.dto.bean;

import com.timeron.NexusDatabaseLibrary.Entity.JRelease;

public class JReleaseDTO {

	private int id;
	private String version;
	
	public JReleaseDTO(JRelease jRelease) {
		this.id = jRelease.getId();
		this.version = jRelease.getVersion();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
