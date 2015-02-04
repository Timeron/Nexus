package com.nexus.apps.car.dto;

import java.util.List;

import com.timeron.NexusDatabaseLibrary.Entity.Fuel;

public class RecordsDTO {

	List<Fuel> records;

	public List<Fuel> getRecords() {
		return records;
	}

	public void setRecords(List<Fuel> records) {
		this.records = records;
	}
	
	
}
