package com.nexus.apps.car.dto.chart;

import java.util.Date;

public class DateValueChart {
	private float value = 0;
	private Date date = new Date();
	
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
