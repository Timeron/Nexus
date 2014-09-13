package com.nexus.dao.entity;

import java.util.List;

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
	private String Description;
	
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

	@Override
	public String toString() {
		return "ProductCategory [id=" + id + ", Description=" + Description
				+ ", SiteTypes=" + SiteTypes + "]";
	}
	
	
	
}
