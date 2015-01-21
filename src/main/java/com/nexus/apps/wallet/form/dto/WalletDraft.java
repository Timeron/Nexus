package com.nexus.apps.wallet.form.dto;

import java.util.Map;

public class WalletDraft {

	private String name;
	private Map<String, Float> values;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Float> getValues() {
		return values;
	}
	public void setValues(Map<String, Float> values) {
		this.values = values;
	}
	
	
}
