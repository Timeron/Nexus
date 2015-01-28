package com.nexus.apps.car.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexus.apps.car.dto.addRecordDTO;

@Controller
@RequestMapping("/car")
public class CarController {
	
	static Logger log = Logger.getLogger(
			CarController.class.getName());
	
	@RequestMapping("/")
	public String mainSite(ModelMap  model){
		
		return "carMainSite";
		
	}
	
	@RequestMapping("/addRecord")
	public String addRecord(ModelMap model){
		addRecordDTO addRecordDTO = new addRecordDTO();
		model.addAttribute("form", addRecordDTO);
		return "carAddRecord";
	}
	
	@RequestMapping("editRecord")
	public String editRecord(ModelMap model){
		return null;
	}
	
	@RequestMapping("removeRecord")
	public String removeRecord(ModelMap model){
		return null;
	}
	
	

}
