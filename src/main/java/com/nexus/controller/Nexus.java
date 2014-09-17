package com.nexus.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

	@Controller
	@RequestMapping("/")
	public class Nexus {
		
		static Logger log = Logger.getLogger(
				Nexus.class.getName());
		
		@RequestMapping(value="/", method = RequestMethod.GET)
		public ModelAndView HomePage(ModelMap model) {
			log.info("Home page");
			return new ModelAndView("nexusHome");
	 
		}
}
