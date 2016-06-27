package com.timeron.nexus.apps.car.dto;


public class RecordMobileDTO {
	private float liters;
	private float distance;
	private long date;
	private String dateString;
	
	public float getLiters() {
		return liters;
	}
	public void setLiters(float liters) {
		this.liters = liters;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	
}
