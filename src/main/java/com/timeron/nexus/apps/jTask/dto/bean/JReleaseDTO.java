package com.timeron.nexus.apps.jTask.dto.bean;

import com.timeron.NexusDatabaseLibrary.Entity.JRelease;

public class JReleaseDTO{

	private Integer id;
	private String version;
	private String comment;
	private Integer projectId;
	
	public JReleaseDTO(JRelease jRelease) {
		this.id = jRelease.getId();
		this.version = jRelease.getVersion();
		this.projectId = jRelease.getProject().getId();
	}
	
	
	
	public JReleaseDTO(Integer id, String version, String comment,
			Integer projectId) {
		this.id = id;
		this.version = version;
		this.comment = comment;
		this.projectId = projectId;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	
}
