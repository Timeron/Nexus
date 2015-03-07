package com.nexus.chart;

import java.util.ArrayList;
import java.util.List;

public class Chart {

	List<Object> chart;
	
	public Chart(){
		chart = new ArrayList<Object>();
	}

	public List<Object> getChart() {
		return chart;
	}

	public void setChart(List<Object> chart) {
		this.chart = chart;
	}
	
	public void add(Object object){
		chart.add(object);
	}
	
}
