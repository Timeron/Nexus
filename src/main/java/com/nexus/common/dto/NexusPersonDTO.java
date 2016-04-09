package com.nexus.common.dto;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.google.gson.annotations.Expose;
import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;

public class NexusPersonDTO {

	@Expose
	private Integer id;
	@Expose
	private String firstName;
	@Expose
	private String lastName;
	@Expose
	private String nick;
	@Expose
	private String nickLogo;
	@Expose
	private String emailOffice;
	@Expose
	private String emailPrv;
	@Expose
	private String city;
	@Expose
	private String country;
	@Expose
	private String address;
	@Expose
	private String phone1;
	@Expose
	private String phone2;
	@Expose
	private String phone3;
	@Expose
	private String description;
	@Expose
	private String tags;
	@Expose
	private String birthday;
	@Expose
	private String nameday;

	public NexusPersonDTO(NexusPerson nexusPerson) {
		Locale localeObject=new Locale("pl"); 
		SimpleDateFormat formatDayMonth = new SimpleDateFormat("dd MMMM",
				localeObject);
		SimpleDateFormat formatDayYear = new SimpleDateFormat("dd MMMM yyyy",
				localeObject);
		
		this.id = nexusPerson.getId();
		this.firstName = nexusPerson.getFirstName();
		this.lastName = nexusPerson.getLastName();
		this.nick = nexusPerson.getNick();
		this.nickLogo = nexusPerson.getNickLogo();
		this.emailOffice = nexusPerson.getEmailOffice();
		this.emailPrv = nexusPerson.getEmailPrv();
		this.city = nexusPerson.getCity();
		this.country = nexusPerson.getCountry();
		this.address = nexusPerson.getAddress();
		this.phone1 = nexusPerson.getPhone1();
		this.phone2 = nexusPerson.getPhone2();
		this.phone3 = nexusPerson.getPhone3();
		this.description = nexusPerson.getDescription();
		this.tags = nexusPerson.getTags();
		if(nexusPerson.getBirthday() != null){
			this.birthday = formatDayYear.format(nexusPerson.getBirthday());
		}
		if(nexusPerson.getNameDay() != null){
			this.nameday = formatDayMonth.format(nexusPerson.getNameDay());
		}
	}

	/*
	 * Getters & setters
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNickLogo() {
		return nickLogo;
	}

	public void setNickLogo(String nickLogo) {
		this.nickLogo = nickLogo;
	}

	public String getEmailOffice() {
		return emailOffice;
	}

	public void setEmailOffice(String emailOffice) {
		this.emailOffice = emailOffice;
	}

	public String getEmailPrv() {
		return emailPrv;
	}

	public void setEmailPrv(String emailPrv) {
		this.emailPrv = emailPrv;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNameday() {
		return nameday;
	}

	public void setNameday(String nameday) {
		this.nameday = nameday;
	}

}
