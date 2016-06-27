package com.timeron.nexus.apps.multiObserver.form;

import java.util.ArrayList;
import java.util.List;

import com.timeron.NexusDatabaseLibrary.Entity.ProductCategory;
import com.timeron.nexus.apps.form.MainForm;

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
