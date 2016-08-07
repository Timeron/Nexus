package com.timeron.nexus.apps.wallet.service.dto.graph;

public class KeyValuePropertyDTO extends KeyValueDTO{

	private String property = "";
	
	public KeyValuePropertyDTO() {
		super();
	}

	public KeyValuePropertyDTO(String key, String value, String property) {
		super(key, value);
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
}
