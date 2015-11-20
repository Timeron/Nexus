package com.nexus.apps.wallet.service.dto;

public class SumForAccountByType {

	private int accountId;
	private Boolean income;
	
	public int getId() {
		return accountId;
	}
	public void setId(int accountId) {
		this.accountId = accountId;
	}
	public Boolean getIncome() {
		return income;
	}
	public void setIncome(Boolean income) {
		this.income = income;
	}
	
	
}
