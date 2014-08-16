package com.nexus.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="nexus_applications")
public class NexusApplications {

	@Id
	@GeneratedValue
	private int id;

	@Column(name="application_name", length = 30)
	private String applicationName;
	
	@Column(name="application_description")
	private String applicationDescription;
	
	private boolean deployed;
	
	@Column(name = "updat_timestamp")
	@Temporal (TemporalType.TIMESTAMP)
	private Date updateTimestamp;
	
	@Column(name = "timestamp")
	@Temporal (TemporalType.TIMESTAMP)
	private Date createTimestamp;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private NexusUser userId;
	
	
	
	
}
