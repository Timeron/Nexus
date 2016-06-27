package com.timeron.nexus.apps.shopList.rest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.timeron.nexus.apps.shopList.dto.ProductDTO;
import com.timeron.nexus.apps.shopList.rest.helper.ShopListRestHelper;

@RestController
@RequestMapping("/v1/sl")
public class ShopListRestService {
		
		static Logger LOG = Logger.getLogger(ShopListRestService.class);
		
		Gson gson = new Gson();
		ObjectMapper objectMapper = new ObjectMapper();
		
		@Autowired
		ShopListRestHelper helper;
		
		@RequestMapping(value = "/getShopList", method = RequestMethod.GET)
		public String getShopList(){
			LOG.info("service: getShopList");
			String response = gson.toJson(helper.getShopList("user"));
			LOG.info("service response: getShopList -> "+response);
			return response;
		}
		
		@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
		public String updateProduct(@RequestBody String json){
			LOG.info("service: updateProduct <- "+json);
			ProductDTO product = gson.fromJson(json, ProductDTO.class);
			String response = gson.toJson(helper.updateBuyProduct(product));
			LOG.info("service response: updateProduct -> "+response);
			return response;
		}
		
		@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
		public String saveProduct(@RequestBody String json){
			LOG.info("service: saveProduct <- "+json);
			ProductDTO product = gson.fromJson(json, ProductDTO.class);
			String response = gson.toJson(helper.saveProduct(product));
			LOG.info("service response: saveProduct -> "+response);
			return response;
		}
}
