package com.nexus.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "nexs_user")
public class NexusUser extends NexusPerson{

	private boolean admin;
	private boolean active;
	
	@OneToMany(mappedBy="userId")
	private List<NexusApplications> userApplications;
	
	@OneToMany(mappedBy="userId")
	private List<NexusRole> roles;
	
	/*
	 * Getters & setters
	 */
	
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

	public List<NexusApplications> getUserApplications() {
		return userApplications;
	}

	public void setUserApplications(List<NexusApplications> userApplications) {
		this.userApplications = userApplications;
	}

	public List<NexusRole> getRoles() {
		return roles;
	}

	public void setRoles(List<NexusRole> roles) {
		this.roles = roles;
	}
	
	
}
