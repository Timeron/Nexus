package com.nexus.apps;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		public ModelAndView HomePage(HttpServletRequest request, ModelMap model) {
			return new ModelAndView("nexusHome");
	 
		}
		
		@RequestMapping(value="/403", method = RequestMethod.GET)
		public String forbidden(Model model) {
			return "forbidden";
	 
		}
		
//		@RequestMapping(value="/login", method = RequestMethod.GET)
//		public ModelAndView loginPage(ModelMap model) {
//			return new ModelAndView("loginPage");
//	 
//		}
}
