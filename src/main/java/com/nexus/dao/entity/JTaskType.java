package com.nexus.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="j_task_type")
public class JTaskType {

	@Id
	@GeneratedValue
	private int id;
	
	private String description;
	
	@OneToOne(mappedBy="taskType")
	private JTask task;
	
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

	public JTask getTask() {
		return task;
	}

	public void setTask(JTask task) {
		this.task = task;
	}
	
	
}
