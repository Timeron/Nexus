package com.nexus.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="MO_observed_object")
public class ObservedObject {

	@Id
	@GeneratedValue
	private Integer id;
	@Column(length = 150)
	private String name;
	@Column(length = 50, name="product_kay")
	private String productKay;
	private Date timestamp;
	
	@OneToMany(mappedBy="observedObject")
	private List<ObservedSite> observedSite;
	
	@OneToMany(mappedBy="observedObject")
	private List<ProposedProductKay> proposedProductKay;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<ObservedSite> getObservedSite() {
		return observedSite;
	}

	public void setObservedSite(List<ObservedSite> observedSite) {
		this.observedSite = observedSite;
	}

	public String getProductKay() {
		return productKay;
	}

	public void setProductKay(String productKay) {
		this.productKay = productKay;
	}

	public List<ProposedProductKay> getProposedProductKay() {
		return proposedProductKay;
	}

	public void setProposedProductKay(List<ProposedProductKay> proposedProductKay) {
		this.proposedProductKay = proposedProductKay;
	}
	
}
