package com.timeron.nexus.apps.wallet.service.dto.graph;

public class KeyValueDTO extends KeyDTO{

	private String value = "";
	
	public KeyValueDTO (){
		super();
	}
	
	public KeyValueDTO (String key, String value){
		super(key);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
