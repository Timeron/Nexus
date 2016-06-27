package com.timeron.nexus.apps.shopList.rest.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.nexus.apps.shopList.dto.ProductDTO;
import com.timeron.nexus.apps.shopList.service.ShopListService;
import com.timeron.nexus.common.service.ServiceResult;

@Component
public class ShopListRestHelper {

	static Logger LOG = Logger.getLogger(ShopListRestHelper.class);
	
	@Autowired
	ShopListService shopService;
	
	public List<ProductDTO> getShopList(String name) {
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		list = shopService.getActiveProducts();
//		list.add(new ProductDTO(name, name, 1, 2, true));
//		list.add(new ProductDTO(name, name, 2, 3, true));
//		list.add(new ProductDTO(name, name, 3, 4, true));
		return  list;
	}

	public ServiceResult updateBuyProduct(ProductDTO productDto) {
		ServiceResult result = new ServiceResult();
		shopService.updateBuyProduct(productDto);
		result.setSuccess(true);
		result.setObject(productDto);
		return result;
	}

	public ServiceResult saveProduct(ProductDTO product) {
		ServiceResult result = new ServiceResult();
		result.setSuccess(shopService.saveProduct(product));
		result.setObject(shopService.getActiveProducts());
		return result;
	}

	
	
}
