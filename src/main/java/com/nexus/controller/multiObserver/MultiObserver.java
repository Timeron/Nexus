package com.nexus.controller.multiObserver;

import java.util.ArrayList;
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

import com.nexus.dao.Implementation.ProductCategoryDAO;
import com.nexus.dao.Implementation.SiteDAO;
import com.nexus.dao.Implementation.SiteTypeDAO;
import com.nexus.dao.entity.ObservedLinksPackage;
import com.nexus.dao.entity.ProductCategory;
import com.nexus.dao.entity.Site;
import com.nexus.dao.entity.SiteType;

@Controller
@RequestMapping("/multiobserver")
public class MultiObserver {

	static Logger log = Logger.getLogger(
			MultiObserver.class.getName());
	
	@Autowired
	SiteDAO siteDAO;
	@Autowired
	SiteTypeDAO siteTypeDAO;
	@Autowired
	ProductCategoryDAO productCategoryDAO;
	
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
	public String AddSite(ModelMap model , HttpServletRequest request, HttpServletResponse response) {
		
		Site site =  new Site();
		model.addAttribute("site", site);
		
		return "addSite";
 
	}
	
	/**
	 * AddSite
	 * @param model
	 * @param site
	 * @return
	 */
	
	@RequestMapping(value="/admin/addLinkPackageToSite", method = RequestMethod.POST)
	public String AddSite(ModelMap model, @ModelAttribute("site") Site site) {
		
		List<SiteType> siteTypes = new ArrayList<SiteType>();
		siteTypes = siteTypeDAO.getAllSiteTypes();
		
		ObservedLinksPackage observedLinksPackage = new ObservedLinksPackage();
		
		model.addAttribute("siteTypes", siteTypes);
		model.addAttribute("observedLinksPackage", observedLinksPackage);
		model.addAttribute("siteTemp", site);
 
		return "addLinkPackageToSite";
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param site
	 * @return
	 */
	
	@RequestMapping(value="/admin/addLinkPackageToSiteResult", method = RequestMethod.POST)
	public String AddLinkPackageToSiteResult(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("site") Site site) {
		log.info("Dodajemy Stronę");
		
		siteDAO.saveSite(site);
		
		return "addLinkPackageToSiteResult";
	}
	
	/**
	 * 
	 * SiteType
	 * 
	 * 
	 */
	
	/**
	 * AddSiteType
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value="/admin/addSiteType", method = RequestMethod.GET)
	public String AddSiteType(ModelMap model) {
		SiteType siteType = new SiteType();
		List<ProductCategory> productCategorys;
		
		productCategorys = productCategoryDAO.getAll();
		
		model.addAttribute("productCategorys", productCategorys);
		model.addAttribute("siteType", siteType);
		
		return "addSiteType";
	}
	
	@RequestMapping(value="/admin/addSiteTypeResult", method = RequestMethod.POST)
	public String AddSiteTypeResult(ModelMap model, @ModelAttribute("siteType") SiteType siteType) {
		ProductCategory productCategory = new ProductCategory();
		
		productCategory = productCategoryDAO.getById(siteType.getProductCategory().getId());
		siteType.setProductCategory(productCategory);
		
		model.addAttribute("siteType", siteType);
		siteTypeDAO.saveSiteType(siteType);
		
		return "addSiteTypeResult";
	}
	
	
	/**
	 * ProductCategory
	 */
	
	/**
	 * AddProductCategory
	 * @param model
	 * @return addProductCategory
	 */
	
	@RequestMapping(value="/admin/addProductCategory", method = RequestMethod.GET)
	public String AddProductCategory(ModelMap model) {
		ProductCategory productCategory = new ProductCategory();
		
		model.addAttribute("productCategory", productCategory);
		
		return "addProductCategory";
	}
	
	@RequestMapping(value="/admin/addProductCategoryResult", method = RequestMethod.POST)
	public String AddProductCategoryResult(ModelMap model, @ModelAttribute("productCategory") ProductCategory productCategory) {
		
		productCategoryDAO.saveProductCategory(productCategory);
		
		model.addAttribute("productCategory", productCategory);
		
		return "addProductCategoryResult";
	}
	
}
