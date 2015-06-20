package com.nexus.apps.jTask.dto.bean;

import java.util.Date;

import com.timeron.NexusDatabaseLibrary.Entity.JHistory;

public class JHistoryDTO {

	private int id;
	private Date created;
	
	public JHistoryDTO(JHistory jHistory) {
		this.id = jHistory.getId();
		this.created = jHistory.getCreated();
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
	
	
	
}