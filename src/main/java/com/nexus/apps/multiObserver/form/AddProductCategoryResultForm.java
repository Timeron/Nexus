package com.nexus.apps.multiObserver.form;

import com.nexus.apps.form.MainForm;
import com.nexus.dao.entity.ProductCategory;

public class AddProductCategoryResultForm extends MainForm {

	ProductCategory productCategory = new ProductCategory();

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	
	
	
	
}
