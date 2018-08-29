package com.timeron.nexus.apps.wallet.service.dto;

import java.util.List;

public class KeyValueListDTO<TK, TV> {

	private TK key;
	private List<TV> value;
	
	public KeyValueListDTO() {
	}
	
	public KeyValueListDTO(TK key, List<TV> value) {
		super();
		this.key = key;
		this.value = value;
	}

	public TK getKey() {
		return key;
	}

	public void setKey(TK key) {
		this.key = key;
	}

	public List<TV> getValue() {
		return value;
	}

	public void setValue(List<TV> value) {
		this.value = value;
	}

	
	
}
