package com.nexus.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.web.bind.annotation.Mapping;

@Entity
@Table(name = "nexs_user")
public class NexusUser extends NexusPerson{

	private boolean admin;
	private boolean active;
	
	@OneToMany(mappedBy="userId")
	private List<NexusApplications> userApplications;
	
	@OneToMany(mappedBy="userId")
	private List<NexusRole> roles;
	
	
}
