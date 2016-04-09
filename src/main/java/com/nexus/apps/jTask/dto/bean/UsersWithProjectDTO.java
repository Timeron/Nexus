package com.nexus.apps.jTask.dto.bean;

import java.util.List;

import com.nexus.common.dto.NexusPersonDTO;

public class UsersWithProjectDTO {

	private Integer projectId;
	private List<NexusPersonDTO> users;
	
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public List<NexusPersonDTO> getUsers() {
		return users;
	}
	public void setUsers(List<NexusPersonDTO> users) {
		this.users = users;
	}
	
	
}
