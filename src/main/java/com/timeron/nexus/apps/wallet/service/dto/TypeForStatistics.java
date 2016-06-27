package com.timeron.nexus.apps.wallet.service.dto;

public class TypeForStatistics {

	private int account;
	private int type;
	private boolean income = false;
	
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isIncome() {
		return income;
	}
	public void setIncome(boolean income) {
		this.income = income;
	}
	

}
