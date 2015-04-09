package com.nexus.apps.contact.dto;

import com.timeron.NexusDatabaseLibrary.Entity.NexusUser;


public class NexusUserDTO {

	private boolean admin;
	private boolean active;
	
	public NexusUserDTO(NexusUser nexusUser) {
		this.admin = nexusUser.isAdmin();
		this.active = nexusUser.isActive();
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
}
