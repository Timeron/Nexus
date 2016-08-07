package com.timeron.nexus.apps.wallet.service.dto.graph;

import java.util.List;
import java.util.Map;

public class GraphListOfKeyValuesAndProperties {

	Map<String, String> property;
	List<KeyValuesByObjectDTO> kayValues;
	
	
	public Map<String, String> getProperty() {
		return property;
	}
	public void setProperty(Map<String, String> property) {
		this.property = property;
	}
	public List<KeyValuesByObjectDTO> getKayValues() {
		return kayValues;
	}
	public void setKayValues(List<KeyValuesByObjectDTO> kayValues) {
		this.kayValues = kayValues;
	}
	
	
}
