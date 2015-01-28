package com.nexus.apps.multiObserver.form;

import java.util.List;

import com.nexus.apps.form.MainForm;
import com.timeron.NexusDatabaseLibrary.Entity.ProductCategory;
import com.timeron.NexusDatabaseLibrary.Entity.SiteType;

public class AddSiteTypeForm extends MainForm {

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
