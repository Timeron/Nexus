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
@Table(name="j_history")
public class JHistory {

	@Id
	@GeneratedValue
	private int id;
	
	private Date created;
	
	@ManyToOne
	@JoinColumn(name="j_task")
	private JTask task;
	
	@OneToOne
	@JoinColumn(name="j_note")
	private JNote note;
	
	@OneToOne
	@JoinColumn(name="j_status")
	private JStatus status;
	
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

	public JNote getNote() {
		return note;
	}

	public void setNote(JNote note) {
		this.note = note;
	}

	public JStatus getStatus() {
		return status;
	}

	public void setStatus(JStatus status) {
		this.status = status;
	}

	public NexusUser getUser() {
		return user;
	}

	public void setUser(NexusUser user) {
		this.user = user;
	}
	
	
}
