package com.timeron.nexus.apps.wallet.service.dto.graph;

import java.util.ArrayList;
import java.util.List;

public class KeyValuesByObjectDTO extends KeyDTO{

	List<Object> values;

	public KeyValuesByObjectDTO(String key, List<Object> values) {
		super(key);
		this.values = values;
	}

	public KeyValuesByObjectDTO() {
		super();
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}
	
	public void addValue(Object value){
		if(this.values == null){
			this.values = new ArrayList<Object>();
			this.values.add(value);
		}else{
			this.values.add(value);
		}
	}
	
}
