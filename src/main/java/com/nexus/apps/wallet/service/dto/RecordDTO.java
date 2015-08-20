package com.nexus.apps.wallet.service.dto;

import java.util.Date;

public class RecordDTO {
	
	private Integer id;
	private float value;
	private String description;
	private boolean income;
	private boolean transfer;
	private Date date;
	private Date updated;
	private RecordTypeDTO recordTypeDTO;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isIncome() {
		return income;
	}
	public void setIncome(boolean income) {
		this.income = income;
	}
	public boolean isTransfer() {
		return transfer;
	}
	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public RecordTypeDTO getRecordTypeDTO() {
		return recordTypeDTO;
	}
	public void setRecordTypeDTO(RecordTypeDTO recordTypeDTO) {
		this.recordTypeDTO = recordTypeDTO;
	}
	
	
}
