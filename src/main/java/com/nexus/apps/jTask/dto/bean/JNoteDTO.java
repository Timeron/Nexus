package com.nexus.apps.jTask.dto.bean;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.JNote;

public class JNoteDTO {

	private int id;
	private String name;
	private String content;
	private Date created;
	private int taskId;
	
	public JNoteDTO(JNote jNote) {
		this.id = jNote.getId();
		this.name = jNote.getName();
		this.content = jNote.getContent();
		this.created = jNote.getCreated();
	}
	
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
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
}
