package com.nexus.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NexusOdservedObject {

	@Id
	@GeneratedValue
	private Integer observedObject;
	private String name;
	private Date timestamp;
	
}
