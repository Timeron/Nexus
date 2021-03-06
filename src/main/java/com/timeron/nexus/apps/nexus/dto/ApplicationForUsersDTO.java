package com.timeron.nexus.apps.nexus.dto;

import java.util.List;

import com.timeron.nexus.common.NexusApplicationsDTO;
import com.timeron.nexus.common.dto.NexusPersonDTO;

public class ApplicationForUsersDTO {

	private NexusApplicationsDTO application;
	private List<NexusPersonDTO> users;
	
	public NexusApplicationsDTO getApplication() {
		return application;
	}
	public void setApplication(NexusApplicationsDTO application) {
		this.application = application;
	}
	public List<NexusPersonDTO> getUsers() {
		return users;
	}
	public void setUsers(List<NexusPersonDTO> users) {
		this.users = users;
	}
	
	
	
}
