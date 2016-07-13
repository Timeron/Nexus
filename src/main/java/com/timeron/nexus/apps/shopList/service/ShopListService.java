package com.timeron.nexus.apps.shopList.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.NexusDatabaseLibrary.Entity.Product;
import com.timeron.NexusDatabaseLibrary.dao.ProductDAO;
import com.timeron.nexus.apps.shopList.dto.ProductDTO;

@Component
public class ShopListService {

	@Autowired
	ProductDAO productDAO;
	
	public List<ProductDTO> getActiveProducts(){
		List<ProductDTO> productsDto = new ArrayList<ProductDTO>();
		List<Product> products = new ArrayList<Product>();
		products = productDAO.getAll();
		for(Product product : products){
			if(product.getChecked() != null){
				long checked = product.getChecked().getTime();
				DateTime maxDate = new DateTime();
	
				if(maxDate.minusDays(2).getMillis() - checked < 0){
					productsDto.add(new ProductDTO(product.getId(), product.getName(), product.getSize(), product.getNumber(), product.getPriority(), product.isBuy()));
				}
			}else{
				productsDto.add(new ProductDTO(product.getId(), product.getName(), product.getSize(), product.getNumber(), product.getPriority(), product.isBuy()));
			}
		}
		return productsDto;
	}

	public boolean saveProduct(ProductDTO product) {
		Product entity = new Product();
		entity.setAdded(new Date());
		entity.setBuy(true);
		entity.setChecked(null);
		entity.setName(product.getName());
		entity.setNumber(product.getNumber());
		entity.setPriority(product.getPriority());
		entity.setSize(product.getSize());
		try{
			productDAO.save(entity);
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public void updateBuyProduct(ProductDTO product) {
		Product entity = productDAO.getById(product.getId());
		entity.setBuy(product.isBuy());	
		entity.setChecked(new Date());
		productDAO.update(entity);
		
	}
	
}
