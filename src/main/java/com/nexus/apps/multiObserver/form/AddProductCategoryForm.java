package com.nexus.apps.multiObserver.form;

import java.util.ArrayList;
import java.util.List;

import com.nexus.apps.form.MainForm;
import com.timeron.NexusDatabaseLibrary.Entity.ProductCategory;

public class AddProductCategoryForm extends MainForm {

	ProductCategory productCategory = new ProductCategory();
	List<ProductCategory> allProductCategorys = new ArrayList<ProductCategory>();

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public List<ProductCategory> getAllProductCategorys() {
		return allProductCategorys;
	}

	public void setAllProductCategorys(List<ProductCategory> allProductCategorys) {
		this.allProductCategorys = allProductCategorys;
	}
	
	
}
