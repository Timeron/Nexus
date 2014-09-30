package com.nexus.form.multiObserver;

import java.util.List;

import com.nexus.dao.entity.ProductCategory;
import com.nexus.dao.entity.SiteType;

public class AddSiteTypeForm {

	SiteType siteType;
	List<ProductCategory> productCategorys;
	
	public SiteType getSiteType() {
		return siteType;
	}
	public void setSiteType(SiteType siteType) {
		this.siteType = siteType;
	}
	public List<ProductCategory> getProductCategorys() {
		return productCategorys;
	}
	public void setProductCategorys(List<ProductCategory> productCategorys) {
		this.productCategorys = productCategorys;
	}
	
	
}
