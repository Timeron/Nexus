package com.timeron.nexus.apps.contact.dto;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.gson.annotations.Expose;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;

@SuppressWarnings("rawtypes")
public class OccasionDTO implements Comparable{

	@Expose
	private String dateStr;
	private DateTime date;
	@Expose
	private String name;
	@Expose
	private String description;
	@Expose
	private List<PersonShortDataDTO> users = new ArrayList<PersonShortDataDTO>();
	@Expose
	private Boolean event;

	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public DateTime getDate() {
		return date;
	}
	public void setDate(DateTime date) {
		this.date = date;
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
	public List<PersonShortDataDTO> getUsers() {
		return users;
	}
	public void setUsers(List<PersonShortDataDTO> users) {
		this.users = users;
	}
	public void addUser(NexusPerson user){
		PersonShortDataDTO personShortDataDTO = new PersonShortDataDTO(user);
		this.users.add(personShortDataDTO);
	}

	public Boolean getEvent() {
		return event;
	}
	public void setEvent(Boolean event) {
		this.event = event;
	}
	@Override
	public int compareTo(Object object) {
		OccasionDTO occasion = (OccasionDTO) object;
		DateTime tempDate1 = new DateTime(0, date.getMonthOfYear(), date.getDayOfMonth(), 0, 0);
		DateTime tempDate2 = new DateTime(0, occasion.getDate().getMonthOfYear(), occasion.getDate().getDayOfMonth(), 0, 0);
		return(tempDate1.compareTo(tempDate2));
	}
	
}
