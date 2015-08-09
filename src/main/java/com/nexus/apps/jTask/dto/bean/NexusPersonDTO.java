package com.nexus.apps.jTask.dto.bean;

import com.timeron.NexusDatabaseLibrary.Entity.NexusPerson;

public class NexusPersonDTO {

		private Integer id;
		private String firstName;
		private String lastName;
		private String nick;
		private String nickLogo;
		private String email;
		private String city;
		private String country;
		private String address;
		private String phone1;
		private String phone2;
		private String phone3;
		private String description;
		
		public NexusPersonDTO(NexusPerson nexusPerson){
			this.id = nexusPerson.getId();
			this.firstName = nexusPerson.getFirstName();
			this.lastName = nexusPerson.getLastName();
			this.nick = nexusPerson.getNick();
			this.nickLogo = nexusPerson.getNickLogo();
			this.email = nexusPerson.getEmail();
			this.city = nexusPerson.getCity();
			this.country = nexusPerson.getCountry();
			this.address = nexusPerson.getAddress();
			this.phone1 = nexusPerson.getPhone1();
			this.phone2 = nexusPerson.getPhone2();
			this.phone3 = nexusPerson.getPhone3();
			this.description = nexusPerson.getDescription();
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
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
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
}
