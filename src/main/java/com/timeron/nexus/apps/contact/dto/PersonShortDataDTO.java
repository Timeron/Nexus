package com.timeron.nexus.apps.contact.dto;

import com.google.gson.annotations.Expose;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;

public class PersonShortDataDTO {

	@Expose
	private int id;
	@Expose
	private String firstName = "";
	@Expose
	private String lastName = "";
	@Expose
	private String pseudo = "";
	
	public PersonShortDataDTO(NexusPerson person) {
		super();
		this.id = person.getId();
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.pseudo = person.getPseudo();
	}
	
	public PersonShortDataDTO() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	
}
