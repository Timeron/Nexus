package com.nexus.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="MO_site_type")
public class SiteType {

	@Id
	@GeneratedValue
	private int id;
	private String descriptioon;
	
	@OneToMany(mappedBy="siteType")
	private List<ObservedLinksPackage> observedLinksPackage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescriptioon() {
		return descriptioon;
	}

	public void setDescriptioon(String descriptioon) {
		this.descriptioon = descriptioon;
	}

	public List<ObservedLinksPackage> getObservedLinksPackage() {
		return observedLinksPackage;
	}

	public void setObservedLinksPackage(
			List<ObservedLinksPackage> observedLinksPackage) {
		this.observedLinksPackage = observedLinksPackage;
	}


	
	
}
