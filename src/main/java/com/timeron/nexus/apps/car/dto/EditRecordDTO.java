package com.timeron.nexus.apps.car.dto;

import com.timeron.NexusDatabaseLibrary.Entity.Fuel;

public class EditRecordDTO {

	private Fuel newFuel;
	private Fuel oldFuel;
	
	public Fuel getNewFuel() {
		return newFuel;
	}
	public void setNewFuel(Fuel newFuel) {
		this.newFuel = newFuel;
	}
	public Fuel getOldFuel() {
		return oldFuel;
	}
	public void setOldFuel(Fuel oldFuel) {
		this.oldFuel = oldFuel;
	}
	
	
	
}
