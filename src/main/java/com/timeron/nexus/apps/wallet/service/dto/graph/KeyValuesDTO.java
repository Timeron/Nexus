package com.timeron.nexus.apps.wallet.service.dto.graph;

import java.util.ArrayList;
import java.util.List;

public class KeyValuesDTO extends KeyDTO{

	List<String> values;

	public KeyValuesDTO(String key, List<String> values) {
		super(key);
		this.values = values;
	}

	public KeyValuesDTO() {
		super();
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public void addValue(String value){
		if(this.values == null){
			this.values = new ArrayList<String>();
			this.values.add(value);
		}else{
			this.values.add(value);
		}
	}
	
}
