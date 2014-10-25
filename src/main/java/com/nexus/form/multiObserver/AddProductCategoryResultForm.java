package com.nexus.form.multiObserver;

import com.nexus.dao.entity.ProductCategory;
import com.nexus.form.MainForm;

public class AddProductCategoryResultForm extends MainForm {

	ProductCategory productCategory = new ProductCategory();

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	
	
	
	
}
