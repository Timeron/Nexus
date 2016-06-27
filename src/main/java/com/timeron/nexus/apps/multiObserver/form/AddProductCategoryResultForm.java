package com.timeron.nexus.apps.multiObserver.form;

import com.timeron.NexusDatabaseLibrary.Entity.ProductCategory;
import com.timeron.nexus.apps.form.MainForm;

public class AddProductCategoryResultForm extends MainForm {

	ProductCategory productCategory = new ProductCategory();

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	
	
	
	
}
