package com.timeron.nexus.apps.wallet.service.dto;

import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;


public class RecordDTO {


	private Integer id;
	private float value = 0;
	private String description = "";
	private Boolean income = false;
	private boolean transfer = false;
	private long date = 0;
	private long updated = 0;
	private int recordTypeId = 0;
	private int accountId = 0;
	private int destynationAccountId = 0;
	private int sourceWalletAccountId = 0;
	
	public RecordDTO(){}
	
	public RecordDTO(WalletRecord record) {
		this.id = record.getId();
		this.value = record.getValue();
		this.description = record.getDescription();
		this.income = record.isIncome();
		this.transfer = record.isTransfer();
		if(record.getDate() != null){
			this.date = record.getDate().getTime();
		}
		if(record.getUpdated() != null){
			this.updated = record.getUpdated().getTime();
		}
		if(record.getWalletType() != null){
			this.recordTypeId = record.getWalletType().getId();
		}
		if(record.getDestinationWalletAccount() != null){
			this.destynationAccountId = record.getDestinationWalletAccount().getId();
		}
		if(record.getSourceWalletAccount() != null){
			this.sourceWalletAccountId = record.getSourceWalletAccount().getId();
		}
	}
	
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
	public Boolean isIncome() {
		return income;
	}
	public void setIncome(Boolean income) {
		this.income = income;
	}
	public boolean isTransfer() {
		return transfer;
	}
	public void setTransfer(boolean transfer) {
		this.transfer = transfer;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public long getUpdated() {
		return updated;
	}
	public void setUpdated(long updated) {
		this.updated = updated;
	}
	public int getRecordTypeId() {
		return recordTypeId;
	}
	public void setRecordTypeId(int recordTypeId) {
		this.recordTypeId = recordTypeId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getDestynationAccountId() {
		return destynationAccountId;
	}
	public void setDestynationAccountId(int destynationAccountId) {
		this.destynationAccountId = destynationAccountId;
	}

	public int getSourceWalletAccountId() {
		return sourceWalletAccountId;
	}

	public void setSourceWalletAccountId(int sourceWalletAccountId) {
		this.sourceWalletAccountId = sourceWalletAccountId;
	}
	
	@Override
	public String toString() {
		return "RecordDTO [id=" + id + ", value=" + value + ", description="
				+ description + ", income=" + income + ", transfer=" + transfer
				+ ", date=" + date + ", updated=" + updated + ", recordTypeId="
				+ recordTypeId + ", accountId=" + accountId
				+ ", destynationAccountId=" + destynationAccountId
				+ ", sourceWalletAccountId=" + sourceWalletAccountId + "]";
	}
}
