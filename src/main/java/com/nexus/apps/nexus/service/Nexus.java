package com.nexus.apps.nexus.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nexus.apps.nexus.service.helper.NexusHelper;


	@Controller
	@RequestMapping("/")
	public class Nexus {
		
		@Autowired
		NexusHelper helper;
		
		static Logger log = Logger.getLogger(
				Nexus.class.getName());
		
		@RequestMapping(value="/", method = RequestMethod.GET)
		public ModelAndView homePage(HttpServletRequest request, ModelMap model) {
			return new ModelAndView("nexusHome");
	 
		}
		
		@RequestMapping(value="/403", method = RequestMethod.GET)
		public String forbidden(Model model) {
			return "forbidden";
	 
		}
		
		@RequestMapping(value="/adminPanel", method = RequestMethod.GET)
		public ModelAndView adminPanel(HttpServletRequest request, ModelMap model) {
			return new ModelAndView("adminPanel");
	 
		}
		
		@RequestMapping(value="/adminPanel/{appId}", method = RequestMethod.GET)
		public ModelAndView appAdminPanel(HttpServletRequest request, @PathVariable("appId") String app, ModelMap model) {
			model.addAttribute("application", helper.getApplication(app));
			switch(app){
			case "multiobserver": 
				break;
			case "jtask": 
				break;
			case "contacts": 
				break;
			case "wallet": 
				break;
			}
			return new ModelAndView("appAdminPanel");
	 
		}
		
//		@RequestMapping(value="/login", method = RequestMethod.GET)
//		public ModelAndView loginPage(ModelMap model) {
//			return new ModelAndView("loginPage");
//	 
//		}
}
