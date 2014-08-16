package com.nexus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nexus.dao.DaoImp;

	@Controller
	@RequestMapping("/")
	public class Nexus {
		
		@Autowired
		private DaoImp hib;
		
		@RequestMapping(value="/", method = RequestMethod.GET)
		public ModelAndView HomePage(ModelMap model) {
			
			return new ModelAndView("nexus_home");
	 
		}
}
