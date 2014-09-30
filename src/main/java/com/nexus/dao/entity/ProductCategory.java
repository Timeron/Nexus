package com.nexus.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="MO_product_category")
public class ProductCategory {

	@Id
	@GeneratedValue
	private Integer id;
	@Column(length = 100)
	private String Description;
	private Date timestamp;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="productCategory")
	private List<SiteType> SiteTypes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public List<SiteType> getSiteTypes() {
		return SiteTypes;
	}

	public void setSiteTypes(List<SiteType> siteTypes) {
		SiteTypes = siteTypes;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
