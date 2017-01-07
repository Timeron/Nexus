package com.timeron.nexus.apps.wallet.service.dto;

import java.util.Date;
import java.util.List;

import com.timeron.NexusDatabaseLibrary.Entity.WalletAccount;
import com.timeron.nexus.common.dto.NexusPersonDTO;

public class AccountDTO{

	private int id;
	private String name;
	private String description;
	private Date created;
	private Date updated;
	private NexusPersonDTO personDTO;
	private String currency;
	private List<RecordDTO> records;
	private double sum;
	
	public AccountDTO(){}
	
	public AccountDTO(int id){
		this.id = id;
	}
	
	public AccountDTO(WalletAccount account) {
		this.id = account.getId();
		this.name = account.getName();
		this.description = account.getDescription();
		this.created = account.getTimestamp();
		if(account.getUpdated() != null){
			this.updated = account.getUpdated();
		}
		this.currency = account.getCurrency();
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
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public NexusPersonDTO getPersonDTO() {
		return personDTO;
	}
	public void setPersonDTO(NexusPersonDTO personDTO) {
		this.personDTO = personDTO;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public List<RecordDTO> getRecords() {
		return records;
	}
	public void setRecords(List<RecordDTO> records) {
		this.records = records;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountDTO other = (AccountDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
