package com.nexus.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexus.dao.DaoImp;

@Controller
@RequestMapping("/multiobserver")
public class MultiObserver {

	static Logger log = Logger.getLogger(
			MultiObserver.class.getName());
	
	@Autowired
	private DaoImp dao;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String MultiObserverPage(ModelMap model) {
 
		model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");
 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "multiObserver";
 
	}
	
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String MultiObserverAdminPage(ModelMap model) {
 
		model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");
		dao.setUser();
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "multiObserverAdmin";
 
	}
	
	@RequestMapping(value="/admin/addLinks", method = RequestMethod.GET)
	public String MultiObserverAdminAddLinksPage(ModelMap model) {
 
		model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");
 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "multiObserverAdminAddLinks";
 
	}
	
	
	@RequestMapping(value="/admin/linkadded", method = RequestMethod.GET)
	public String MultiObserverAdminLinkAddedPage(ModelMap model) {
 
		model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");
 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "multiObserverChangeResult";
 
	}
}
