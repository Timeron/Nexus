package com.nexus.apps.jTask.dto.bean;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.JNote;

public class JNoteDTO {

	private int id;
	private String name;
	private String contant;
	private Date created;
	
	public JNoteDTO(JNote jNote) {
		this.id = jNote.getId();
		this.name = jNote.getName();
		this.contant = jNote.getContant();
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
	
		
	
}
