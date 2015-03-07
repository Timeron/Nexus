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
	private String chart;
	private String chartDistance;
	private String chartRefuel;
	
	
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
		return chart;
	}
	
	public void setChart(String chart) {
		this.chart = chart;
	}
	public String getChartDistance() {
		return chartDistance;
	}
	public void setChartDistance(String chartDistance) {
		this.chartDistance = chartDistance;
	}
	public String getChartRefuel() {
		return chartRefuel;
	}
	public void setChartRefuel(String chartRefuel) {
		this.chartRefuel = chartRefuel;
	}
	
}
