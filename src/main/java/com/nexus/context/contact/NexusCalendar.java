package com.nexus.context.contact;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public class NexusCalendar {

	private Map<Integer, Integer> years;
	private Map<String, String> months;
	private Map<Integer, Integer> days;
	private static final int DAYSINMONTH = 31;
	
	public Map<Integer, Integer> getYears(int interval) {
		years = new TreeMap<Integer, Integer>();
		for(int year = Calendar.getInstance().get(Calendar.YEAR); interval>0; year--, interval--){
			years.put(year, year);
		}
		return years;
	}
	
	public Map<String, String> getMonths() {
		months = new TreeMap<String, String>();
		months.put("Styczeń", "Styczeń");
		months.put("Luty", "Luty");
		months.put("Marzec", "Marzec");
		months.put("Kwiecień", "Kwiecień");
		months.put("Maj", "Maj");
		months.put("Czerwiec", "Czerwiec");
		months.put("Lipiec", "Lipiec");
		months.put("Sierpień", "Sierpień");
		months.put("Wrzesień", "Wrzesień");
		months.put("Październik", "Październik");
		months.put("Listopad", "Listopad");
		months.put("Grudzień", "Grudzień");
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
