package com.nexus.controller.multiObserver;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexus.dao.DaoImp;
import com.nexus.dao.Implementation.PersonDAO;
import com.nexus.dao.Implementation.SiteDAO;
import com.nexus.dao.entity.NexusPerson;
import com.nexus.dao.entity.Site;

@Controller
@RequestMapping("/multiobserver")
public class MultiObserver {

	static Logger log = Logger.getLogger(
			MultiObserver.class.getName());
	
	@Autowired
	SiteDAO siteDAO;
	
	@ModelAttribute("site")
	private Site counstruct(){
		return new Site();
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String MultiObserverPage(ModelMap model) {
 
		model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");
 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "multiObserver";
 
	}
	
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String MultiObserverAdminPage(ModelMap model) {
 
		model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");
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
	
	@RequestMapping(value="/admin/addLinkPackage", method = RequestMethod.GET)
	public String AddNewLinkPackage(ModelMap model) {
		
		List<Site> sites;
		SiteDAO siteDAO = new SiteDAO();
		
		sites = siteDAO.getAllSites();
 
		model.addAttribute("siteTemp", sites);
 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "addNewLinkPackage";
 
	}
	
	
	@RequestMapping(value="/admin/addSite", method = RequestMethod.GET)
	public String AddSite(ModelMap model) {
		
		//model.addAttribute("site", "");
 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "addSite";
 
	}
	
	@RequestMapping(value="/admin/addSite", method = RequestMethod.POST)
	public String AddSite(ModelMap model, @ModelAttribute Site site) {
		
	
		model.addAttribute("site", site);
		siteDAO.saveSite(site);
 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "addSiteResult";
	}
	
	@RequestMapping(value="/admin/addLinkPackageToSite", method = RequestMethod.GET)
	public String AddLinkPackageToSite(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute Site site) {
		
		String siteId = request.getParameter("siteId");
		model.addAttribute("siteId", siteId);
 
		//Spring uses InternalResourceViewResolver and return back index.jsp
		return "addLinkPackageToSite";
	}
}
