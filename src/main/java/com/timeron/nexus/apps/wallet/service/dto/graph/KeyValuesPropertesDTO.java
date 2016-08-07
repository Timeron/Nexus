package com.timeron.nexus.apps.wallet.service.dto.graph;

import java.util.ArrayList;
import java.util.List;

public class KeyValuesPropertesDTO extends KeyValuesDTO{

	private List<String> propertes = new ArrayList<String>();

	public KeyValuesPropertesDTO() {
		super();
	}
	
	public KeyValuesPropertesDTO(String key, List<String> values, List<String> propertes) {
		super(key, values);
		this.propertes = propertes;
	}

	public List<String> getPropertes() {
		return this.propertes;
	}

	public void setPropertes(List<String> propertes) {
		this.propertes = propertes;
	}
	
	public void addProperties(String property){
		if(!this.propertes.contains(property)){
			propertes.add(property);
		}
	}
	
}
