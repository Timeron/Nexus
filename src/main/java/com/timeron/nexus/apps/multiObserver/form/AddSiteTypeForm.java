package com.timeron.nexus.apps.multiObserver.form;

import java.util.List;

import com.timeron.NexusDatabaseLibrary.Entity.ProductCategory;
import com.timeron.NexusDatabaseLibrary.Entity.SiteType;
import com.timeron.nexus.apps.form.MainForm;

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
