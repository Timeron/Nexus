package com.nexus.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "proposed_product_kay")
public class ProposedProductKay {

	@Id
	@GeneratedValue
	private Integer id;
	@Column(name = "percent_compatibility", length = 3)
	private int percentCompatibility;
	private Date timestamp;
	private boolean aprovedProductKay;

	@ManyToOne
	@JoinColumn(name="observed_site_id")
	private ObservedSite observedSite;
	
	@ManyToOne
	@JoinColumn(name="observed_object_id")
	private ObservedObject observedObject;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getPercentCompatibility() {
		return percentCompatibility;
	}

	public void setPercentCompatibility(int percentCompatibility) {
		this.percentCompatibility = percentCompatibility;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isAprovedProductKay() {
		return aprovedProductKay;
	}

	public void setAprovedProductKay(boolean aprovedProductKay) {
		this.aprovedProductKay = aprovedProductKay;
	}

	public ObservedSite getObservedSite() {
		return observedSite;
	}

	public void setObservedSite(ObservedSite observedSite) {
		this.observedSite = observedSite;
	}

	public ObservedObject getObservedObject() {
		return observedObject;
	}

	public void setObservedObject(ObservedObject observedObject) {
		this.observedObject = observedObject;
	}
	
	

}
