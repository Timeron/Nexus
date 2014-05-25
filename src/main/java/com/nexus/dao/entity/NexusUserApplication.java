package com.nexus.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NexusUserApplication {

	@Id
	@GeneratedValue
	private Integer userApplicationId;
	private Integer userId;
	private Integer applicationId;
	
	
	
	
}
