package com.timeron.nexus.apps.shopList.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shoplist")
public class ShopListController {
	
	static Logger LOG = Logger.getLogger(ShopListController.class);
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String mainSite(Model model){
		return "shopListMainSite";
	}
	
}
