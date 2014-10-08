package com.nexus.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="j_Note")
public class JNote {

	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	private String contant;
	
	private Date created;
	
	@ManyToOne
	@JoinColumn(name="j_task")
	private JTask task;
	
	@OneToOne(mappedBy="note")
	private JHistory history;
	
	@ManyToOne
	@JoinColumn(name="j_user")
	private NexusUser user;

	/**
	 * Getters & Setters
	 */
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContant() {
		return contant;
	}

	public void setContant(String contant) {
		this.contant = contant;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public JTask getTask() {
		return task;
	}

	public void setTask(JTask task) {
		this.task = task;
	}

	public JHistory getHistory() {
		return history;
	}

	public void setHistory(JHistory history) {
		this.history = history;
	}

	public NexusUser getUser() {
		return user;
	}

	public void setUser(NexusUser user) {
		this.user = user;
	}
	
	
}
