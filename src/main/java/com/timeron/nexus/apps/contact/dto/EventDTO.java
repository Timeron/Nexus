package com.timeron.nexus.apps.contact.dto;

import java.util.List;

public class EventDTO {

	private int id;
	private String name;
	private String description;
	private int eventYear;
	private int eventMonth;
	private int eventDay;
	List<ContactDTO> contacts;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getEventYear() {
		return eventYear;
	}
	public void setEventYear(int eventYear) {
		this.eventYear = eventYear;
	}
	public int getEventMonth() {
		return eventMonth;
	}
	public void setEventMonth(int eventMonth) {
		this.eventMonth = eventMonth;
	}
	public int getEventDay() {
		return eventDay;
	}
	public void setEventDay(int eventDay) {
		this.eventDay = eventDay;
	}
	public List<ContactDTO> getContacts() {
		return contacts;
	}
	public void setContacts(List<ContactDTO> contacts) {
		this.contacts = contacts;
	}
	
}
