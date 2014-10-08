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
	
	@OneToMany(mappedBy="user")
	private List<JHistory> history;
	
	@OneToMany(mappedBy="user")
	private List<JTask> task;
	
	@OneToMany(mappedBy="user")
	private List<JNote> note;
	
	@OneToMany(mappedBy="user")
	private List<JProject> project;
	
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

	public List<JHistory> getHistory() {
		return history;
	}

	public void setHistory(List<JHistory> history) {
		this.history = history;
	}

	public List<JTask> getTask() {
		return task;
	}

	public void setTask(List<JTask> task) {
		this.task = task;
	}

	public List<JNote> getNote() {
		return note;
	}

	public void setNote(List<JNote> note) {
		this.note = note;
	}

	public List<JProject> getProject() {
		return project;
	}

	public void setProject(List<JProject> project) {
		this.project = project;
	}
	
	
	
	
}
