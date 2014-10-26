package com.nexus.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="MO_site_type")
public class SiteType {

	@Id
	@GeneratedValue
	private Integer id;
	@Column(length = 100)
	private String description;
	private Date timestamp;
	
	@ManyToOne
	@JoinColumn(name="MO_product_category_id")
	private ProductCategory productCategory;
 	
	@OneToMany(mappedBy="siteType")
	private List<ObservedLinksPackage> observedLinksPackage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ObservedLinksPackage> getObservedLinksPackage() {
		return observedLinksPackage;
	}

	public void setObservedLinksPackage(
			List<ObservedLinksPackage> observedLinksPackage) {
		this.observedLinksPackage = observedLinksPackage;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return description;
	}

	public boolean isEmpty(){
		if(this.description == null || this.description.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
}
