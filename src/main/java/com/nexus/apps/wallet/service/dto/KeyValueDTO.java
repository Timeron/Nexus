package com.nexus.apps.wallet.service.dto;

public class KeyValueDTO {

	private String key = "";
	private String value = "";
	
	public KeyValueDTO (){}
	
	public KeyValueDTO (String key, String value){
		
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

	
}
