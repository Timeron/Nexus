package com.nexus.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="nexus_person")
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
	private String contry;
	
	@Column(length = 40)
	private String adress;
	
	@Column(length = 15)
	private String phone1;
	
	@Column(length = 15)
	private String phone2;
	
	@Column(length = 15)
	private String phone3;
	
	@Lob
	private String description;
	
	@Temporal (TemporalType.DATE)
	private Date birthday;
	
	@Column(name = "name_day")
	@Temporal (TemporalType.DATE)
	private Date nameDay;
	
	@Column(name = "updat_timestamp")
	@Temporal (TemporalType.TIMESTAMP)
	private Date updateTimestamp;
	
	@Column(name = "timestamp")
	@Temporal (TemporalType.TIMESTAMP)
	private Date createTimestamp;
	
}
