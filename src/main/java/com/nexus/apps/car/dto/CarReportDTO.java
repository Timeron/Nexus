package com.nexus.apps.car.dto;

import java.util.List;

import com.timeron.NexusDatabaseLibrary.Entity.Fuel;

public class CarReportDTO {

	private List<Fuel> records;
	private float averageFuelConsumption;
	private float lastAverageFuelConsumption;
	private float totalDistance;
	private float totalFuel;
	private float lastDistance;
	private float lastFuel;
	private String chart = "{[ {'date':'1-May-13','close':58.13},{'date':'30-Apr-13','close':53.98},{'date':'27-Apr-13','close':67.00},{'date':'26-Apr-13','close':89.70},  {'date':'25-Apr-13','close':99.00},  {'date':'24-Apr-13','close':130.28},  {'date':'23-Apr-13','close':166.70},  {'date':'20-Apr-13','close':234.98},  {'date':'19-Apr-13','close':345.44},  {'date':'18-Apr-13','close':443.34},]}";
	
	public float getAverageFuelConsumption() {
		return averageFuelConsumption;
	}
	public void setAverageFuelConsumption(float averageFuelConsumption) {
		this.averageFuelConsumption = averageFuelConsumption;
	}
	public float getLastAverageFuelConsumption() {
		return lastAverageFuelConsumption;
	}
	public void setLastAverageFuelConsumption(float lastAverageFuelConsumption) {
		this.lastAverageFuelConsumption = lastAverageFuelConsumption;
	}
	public float getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(float totalDistance) {
		this.totalDistance = totalDistance;
	}
	public float getTotalFuel() {
		return totalFuel;
	}
	public void setTotalFuel(float totalFuel) {
		this.totalFuel = totalFuel;
	}
	public float getLastDistance() {
		return lastDistance;
	}
	public void setLastDistance(float lastDistance) {
		this.lastDistance = lastDistance;
	}
	public float getLastFuel() {
		return lastFuel;
	}
	public void setLastFuel(float lastFuel) {
		this.lastFuel = lastFuel;
	}

	public List<Fuel> getRecords() {
		return records;
	}

	public void setRecords(List<Fuel> records) {
		this.records = records;
	}
	
	public String getChart() {
		System.out.println(chart);
		return chart;
	}
	
	public void setChart(String chart) {
		this.chart = chart;
	}
	
}
