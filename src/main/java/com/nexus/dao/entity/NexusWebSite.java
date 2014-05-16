package com.nexus.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NexusWebSite {

	@Id
	@GeneratedValue
	private Integer siteId;
	@Id
	@GeneratedValue
	private String name;
	@Id
	@GeneratedValue
	private String url;
	@Id
	@GeneratedValue
	private Integer timestamp;
	
}
