package com.nexus.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="j_status")
public class JStatus {

	@Id
	@GeneratedValue
	private int id;
	
	private String description;
	
	@OneToMany(mappedBy="status")
	private List<JTask> task;
	
	@OneToOne(mappedBy="status")
	private JHistory history;
	
	@OneToMany(mappedBy="status")
	private List<JProject> project;

	/**
	 * Getters & Setters
	 */
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<JTask> getTask() {
		return task;
	}

	public void setTask(List<JTask> task) {
		this.task = task;
	}

	public JHistory getHistory() {
		return history;
	}

	public void setHistory(JHistory history) {
		this.history = history;
	}

	public List<JProject> getProject() {
		return project;
	}

	public void setProject(List<JProject> project) {
		this.project = project;
	}
	
	
}
