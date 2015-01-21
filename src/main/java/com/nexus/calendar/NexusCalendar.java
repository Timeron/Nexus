package com.nexus.calendar;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public class NexusCalendar {

	private Map<Integer, Integer> years;
	private Map<Integer, String> months;
	private Map<Integer, Integer> days;
	private static final int DAYSINMONTH = 31;
	
	public Map<Integer, Integer> getYears(int interval) {
		years = new TreeMap<Integer, Integer>();
		for(int year = Calendar.getInstance().get(Calendar.YEAR); interval>0; year--, interval--){
			years.put(year, year);
		}
		return years;
	}
	
	public Map<Integer, String> getMonths() {
		months = new TreeMap<Integer, String>();
		months.put(1, "Styczeń");
		months.put(2, "Luty");
		months.put(3, "Marzec");
		months.put(4, "Kwiecień");
		months.put(5, "Maj");
		months.put(6, "Czerwiec");
		months.put(7, "Lipiec");
		months.put(8, "Sierpień");
		months.put(9, "Wrzesień");
		months.put(10, "Październik");
		months.put(11, "Listopad");
		months.put(12, "Grudzień");
		return months;
	}
	
	public Map<Integer, Integer> getDays() {
		days = new TreeMap<Integer, Integer>();
		for (int day = 1; day <= DAYSINMONTH; day++ ){
			days.put(day, day);
		}
		return days;
	}
}
