package com.nexus.apps.jTask.dto.bean;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.JHistory;

public class JHistoryDTO {

	private int id;
	private Date created;
	private String status;
	private String note;
	private String message;
	
	public JHistoryDTO(JHistory jHistory) {
		this.id = jHistory.getId();
		this.created = jHistory.getCreated();
		if(jHistory.getStatus() != null){
			this.status = jHistory.getStatus().getDescription();
		}
		if(jHistory.getNote() != null){
			this.note = jHistory.getNote().getContent();
		}
		this.message = jHistory.getMessage();
	}
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
