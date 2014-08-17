package com.nexus.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "nexus_person")
public class NexusPerson {

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "first_name", length = 30)
	private String firstName;

	@Column(name = "last_name", length = 30)
	private String lastName;

	@Column(length = 30)
	private String pseudo;

	@Column(length = 40)
	private String email;

	@Column(length = 40)
	private String city;

	@Column(length = 30)
	private String country;

	@Column(length = 40)
	private String address;

	@Column(length = 15)
	private String phone1;

	@Column(length = 15)
	private String phone2;

	@Column(length = 15)
	private String phone3;

	@Lob
	private String description;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(name = "name_day")
	@Temporal(TemporalType.DATE)
	private Date nameDay;

	@GeneratedValue
	@Column(name = "updat_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTimestamp;

	@GeneratedValue
	@Column(name = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTimestamp;

	/*
	 * Getters & setters
	 */

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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getNameDay() {
		return nameDay;
	}

	public void setNameDay(Date nameDay) {
		this.nameDay = nameDay;
	}

	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	@Override
	public String toString() {
		return "NexusPerson [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", pseudo=" + pseudo + ", email="
				+ email + ", city=" + city + ", country=" + country
				+ ", address=" + address + ", phone1=" + phone1 + ", phone2="
				+ phone2 + ", phone3=" + phone3 + ", description="
				+ description + ", birthday=" + birthday + ", nameDay="
				+ nameDay + ", updateTimestamp=" + updateTimestamp
				+ ", createTimestamp=" + createTimestamp + "]";
	}
	
	

}
