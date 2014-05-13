package com.nexus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

	@Controller
	@RequestMapping("/")
	public class Nexus {
		
		@RequestMapping(value="/", method = RequestMethod.GET)
		public ModelAndView HomePage(ModelMap model) {
	 
			model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");
	 
			//Spring uses InternalResourceViewResolver and return back index.jsp
			return new ModelAndView("/index", "user", new Object());
	 
		}
		
		@RequestMapping(value="test", method = RequestMethod.GET)
		public String welcome(ModelMap model) {
	 
			model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");
	 
			//Spring uses InternalResourceViewResolver and return back index.jsp
			return "asd";
	 
		}
	 
		@RequestMapping(value="/welcome/{name}", method = RequestMethod.GET)
		public String welcomeName(@PathVariable String name, ModelMap model) {
	 
			model.addAttribute("message", "Maven Web Project + Spring 3 MVC - " + name);
			return "index";
	 
		}

	
}
