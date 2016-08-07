package com.timeron.nexus.apps.wallet.service.dto;

import java.util.ArrayList;
import java.util.List;

public class TypesForStatistics {

	private int account;
	private List<Integer> types;
	private boolean income = false;
	
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	
	public List<Integer> getTypes() {
		return types;
	}
	public void setTypes(List<Integer> types) {
		this.types = types;
	}
	public boolean isIncome() {
		return income;
	}
	public void setIncome(boolean income) {
		this.income = income;
	}
	
	public void addType(Integer typeId){
		if(this.types != null){
			if(!types.contains(typeId)){
				types.add(typeId);
			}
		}else{
			this.types = new ArrayList<Integer>();
			this.types.add(typeId);
		}
	}
	
}
