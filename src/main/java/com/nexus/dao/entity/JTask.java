package com.nexus.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="j_task")
public class JTask {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	private String summary;
	private String description;
	private Date created;
	
	@OneToOne
    @JoinColumn(name = "j_task_type")
	private JTaskType taskType;
	
	@OneToMany(mappedBy="task")
	private List<JHistory> history;
	
	@OneToMany(mappedBy="task")
	private List<JNote> notes;
	
	@ManyToOne
	@JoinColumn(name="j_project")
	private JProject project;
	
	@ManyToOne
	@JoinColumn(name="j_status")
	private JStatus status;
	
	@ManyToOne
	@JoinColumn(name="j_user")
	private NexusUser user;
	
	@OneToOne
	@JoinColumn(name="subtask")
	private JTask subtask;
	
	@OneToOne(mappedBy="subtask")
	private JTask mainTask;

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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public JTaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(JTaskType taskType) {
		this.taskType = taskType;
	}

	public List<JHistory> getHistory() {
		return history;
	}

	public void setHistory(List<JHistory> history) {
		this.history = history;
	}

	public List<JNote> getNotes() {
		return notes;
	}

	public void setNotes(List<JNote> notes) {
		this.notes = notes;
	}

	public JProject getProject() {
		return project;
	}

	public void setProject(JProject project) {
		this.project = project;
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

	public JTask getSubtask() {
		return subtask;
	}

	public void setSubtask(JTask subtask) {
		this.subtask = subtask;
	}

	public JTask getMainTask() {
		return mainTask;
	}

	public void setMainTask(JTask mainTask) {
		this.mainTask = mainTask;
	}
	

	

}
