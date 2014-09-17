package com.nexus.dao.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.nexus.dao.DaoImp;
import com.nexus.dao.entity.ProductCategory;

@Repository
public class ProductCategoryDAO extends DaoImp{
	
	static Logger log = Logger.getLogger(ProductCategoryDAO.class.getName());
	
	public void saveProductCategory(ProductCategory productCategory){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(productCategory);
		session.getTransaction().commit();
		session.close();
		log.info("Site saved");
	}

	@SuppressWarnings("unchecked")
	public List<ProductCategory> getAll() {
		List<ProductCategory> productCategory = new ArrayList<ProductCategory>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hql = "FROM ProductCategory";
		
		Query query = session.createQuery(hql);
		productCategory = (List<ProductCategory>) query.list();
		
		session.close();
		
		if (productCategory.size() > 0) {
			return productCategory;
		} else {
			List<ProductCategory> emptyList = Collections.emptyList();
			return emptyList;
		}
	}

	public ProductCategory getById(int id) {
		ProductCategory productCategory = new ProductCategory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		productCategory = (ProductCategory) session.get(ProductCategory.class, id);
		session.close();
		return productCategory;
	}

}
