package com.nexus.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NexusUser {

	@Id
	@GeneratedValue
	private Integer id;
	
	
	
}
