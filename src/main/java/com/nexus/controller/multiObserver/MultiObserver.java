package com.nexus.controller.multiObserver;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexus.dao.Implementation.ObservedLinksPackageDAO;
import com.nexus.dao.Implementation.ProductCategoryDAO;
import com.nexus.dao.Implementation.SiteDAO;
import com.nexus.dao.Implementation.SiteTypeDAO;
import com.nexus.dao.entity.ObservedLinksPackage;
import com.nexus.dao.entity.ProductCategory;
import com.nexus.dao.entity.SiteType;
import com.nexus.form.multiObserver.AddLinksPackageToNewSiteForm;
import com.nexus.form.multiObserver.AddLinksPackageToOldSiteForm;
import com.nexus.form.multiObserver.AddLinksPackageToOldSiteResultForm;
import com.nexus.form.multiObserver.AddNewLinkPackageForm;
import com.nexus.form.multiObserver.AddProductCategoryForm;
import com.nexus.form.multiObserver.AddProductCategoryResultForm;
import com.nexus.form.multiObserver.AddSiteForm;
import com.nexus.form.multiObserver.AddSiteTypeForm;
import com.nexus.form.multiObserver.AddSiteTypeResultForm;

@Controller
@RequestMapping("/multiobserver")
public class MultiObserver {

	static Logger log = Logger.getLogger(MultiObserver.class.getName());

	@Autowired
	SiteDAO siteDAO;
	@Autowired
	SiteTypeDAO siteTypeDAO;
	@Autowired
	ProductCategoryDAO productCategoryDAO;
	@Autowired
	ObservedLinksPackageDAO observedLinksPackageDAO;

	/**
	 * strona główna Multi Observera
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String MultiObserverPage(ModelMap model) {

		model.addAttribute("message",
				"Maven Web Project + Spring 3 MVC - welcome()");

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return "multiObserver";

	}

	/**
	 * Administracja Muiltiobserverem
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String MultiObserverAdminPage(ModelMap model) {

		model.addAttribute("message",
				"Maven Web Project + Spring 3 MVC - welcome()");
		// Spring uses InternalResourceViewResolver and return back index.jsp
		return "multiObserverAdmin";

	}

	/**
	 * Dodawanie Stron i pakietów linków
	 */

	/**
	 * AddNewLinkPackage Wybieramy stronę aby dodać do niej pakiet linków
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/addLinkPackage", method = RequestMethod.GET)
	public String AddNewLinkPackage(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		AddNewLinkPackageForm addNewLinkPackageForm = new AddNewLinkPackageForm();

		addNewLinkPackageForm.setSites(siteDAO.getAllSites());

		model.addAttribute("addNewLinkPackageForm", addNewLinkPackageForm);

		return "addNewLinkPackage";

	}

	/**
	 * AddSite Dodajemy stronę
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/admin/addSite", method = RequestMethod.GET)
	public String AddSite(ModelMap model) {

		AddSiteForm addSiteForm = new AddSiteForm();
		
		model.addAttribute("addSiteForm", addSiteForm);

		return "addSite";

	}

	/**
	 * AddToNewSite Dodawanie pakietów linków do dodanej strony.
	 * 
	 * @param model
	 * @param site
	 * @return
	 */

	@RequestMapping(value = "/admin/addLinkPackageToNewSite", method = RequestMethod.POST)
	public String AddLinksPackageToNewSite(ModelMap model, @ModelAttribute("addSiteForm") AddSiteForm addSiteForm) {
		
		AddLinksPackageToNewSiteForm addLinksPackageToNewSiteForm = new AddLinksPackageToNewSiteForm();
		addLinksPackageToNewSiteForm.setSiteTypes(siteTypeDAO.getAllSiteTypes());
		addLinksPackageToNewSiteForm.setSite(addSiteForm.getSite());

		model.addAttribute("addLinksPackageToNewSiteForm", addLinksPackageToNewSiteForm);

		return "addLinkPackageToNewSite";
	}

	/**
	 * AddToOldSite Dodawanie pakietów linków do wybranej strony.
	 * 
	 * @param model
	 * @param site
	 * @return
	 */

	@RequestMapping(value = "/admin/addLinkPackageToOldSite", method = RequestMethod.POST)
	public String AddLinksPackageToOldSite(ModelMap model, @ModelAttribute("addNewLinkPackageForm") AddNewLinkPackageForm addNewLinkPackageForm) {
		AddLinksPackageToOldSiteForm addLinksPackageToOldSiteForm = new AddLinksPackageToOldSiteForm();
		
		addLinksPackageToOldSiteForm.setSite(siteDAO.getSiteById(addNewLinkPackageForm.getSite().getId()));
		addLinksPackageToOldSiteForm.setSiteTypes(siteTypeDAO.getAllSiteTypes());

		model.addAttribute("addLinksPackageToOldSiteForm", addLinksPackageToOldSiteForm);

		return "addLinkPackageToOldSite";
	}

	/**
	 * AddLinkPackageToSiteResult Zapisywanie strony wraz z jej pakietami.
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param site
	 * @return
	 */

	@RequestMapping(value = "/admin/addLinkPackageToNewSiteResult", method = RequestMethod.POST)
	public String AddLinkPackageToNewSiteResult(ModelMap model, @ModelAttribute("addLinksPackageToOldSiteForm") AddLinksPackageToOldSiteForm addLinksPackageToOldSiteForm) {
		log.info("Dodajemy Stronę");

		addLinksPackageToOldSiteForm.getSite().setTimestamp(new Date());
		for (ObservedLinksPackage observedLinksPackage : addLinksPackageToOldSiteForm.getSite().getObservedLinksPackage()) {
			observedLinksPackage.setSiteType(siteTypeDAO.getByDescription(observedLinksPackage.getSiteType().getDescription()));
			observedLinksPackage.setSite(addLinksPackageToOldSiteForm.getSite());
			observedLinksPackage.setTimestamp(new Date());
		}
		siteDAO.saveSite(addLinksPackageToOldSiteForm.getSite());

		return "addLinkPackageToSiteResult";
	}

	@RequestMapping(value = "/admin/addLinksPackageToOldSiteResult", method = RequestMethod.POST)
	public String AddLinksPackageToOldSiteResult(ModelMap model, @ModelAttribute("addLinksPackageToOldSiteForm") AddLinksPackageToOldSiteForm addLinksPackageToOldSiteForm) {
		AddLinksPackageToOldSiteResultForm addLinksPackageToOldSiteResultForm = new AddLinksPackageToOldSiteResultForm();
		addLinksPackageToOldSiteForm.getObservedLinksPackage().setSite(addLinksPackageToOldSiteForm.getSite());
		SiteType siteType = siteTypeDAO.getByDescription(addLinksPackageToOldSiteForm.getObservedLinksPackage().getSiteType().getDescription());
		addLinksPackageToOldSiteForm.getObservedLinksPackage().setSiteType(siteType);
		addLinksPackageToOldSiteForm.getObservedLinksPackage().setTimestamp(new Date());

		observedLinksPackageDAO.save(addLinksPackageToOldSiteForm.getObservedLinksPackage());

		model.addAttribute("addLinksPackageToOldSiteResultForm", addLinksPackageToOldSiteResultForm);
		
		return "addLinkPackageToSiteResult";
	}

	/**
	 * 
	 * SiteType
	 * 
	 * 
	 */

	/**
	 * AddSiteType Dadawanie typu artykułu
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/admin/addSiteType", method = RequestMethod.GET)
	public String AddSiteType(ModelMap model) {
		AddSiteTypeForm addSiteTypeForm = new AddSiteTypeForm();
		
		addSiteTypeForm.setProductCategorys(productCategoryDAO.getAll());

		model.addAttribute("addSiteTypeForm", addSiteTypeForm);

		return "addSiteType";
	}

	/**
	 * AddSiteTypeResult Zapisywanie nowego typu artykułu
	 * 
	 * @param model
	 * @param siteType
	 * @return
	 */

	@RequestMapping(value = "/admin/addSiteTypeResult", method = RequestMethod.POST)
	public String AddSiteTypeResult(ModelMap model,
			@ModelAttribute("addSiteTypeForm") AddSiteTypeForm addSiteTypeForm) {
		
		ProductCategory productCategory = new ProductCategory();
		AddSiteTypeResultForm addSiteTypeResultForm = new AddSiteTypeResultForm();

		productCategory = productCategoryDAO.getById(addSiteTypeForm.getSiteType().getProductCategory().getId());
		addSiteTypeForm.getSiteType().setProductCategory(productCategory);
		addSiteTypeForm.getSiteType().setTimestamp(new Date());

		model.addAttribute("addSiteTypeResultForm", addSiteTypeResultForm);
		siteTypeDAO.saveSiteType(addSiteTypeForm.getSiteType());

		return "addSiteTypeResult";
	}

	/**
	 * ProductCategory
	 */

	/**
	 * AddProductCategory Dodawanie nowej kategorii produktu
	 * 
	 * @param model
	 * @return addProductCategory
	 */

	@RequestMapping(value = "/admin/addProductCategory", method = RequestMethod.GET)
	public String AddProductCategory(ModelMap model) {
		AddProductCategoryForm addProductCategoryForm = new AddProductCategoryForm();

		model.addAttribute("addProductCategoryForm", addProductCategoryForm);

		return "addProductCategory";
	}

	/**
	 * AddProductCategoryResult Zapisywanie nowej kategori produktu
	 * 
	 * @param model
	 * @param productCategory
	 * @return
	 */

	@RequestMapping(value = "/admin/addProductCategoryResult", method = RequestMethod.POST)
	public String AddProductCategoryResult(ModelMap model,
			@ModelAttribute("addProductCategoryForm") AddProductCategoryForm addProductCategoryForm) {
		
		AddProductCategoryResultForm addProductCategoryResultForm = new AddProductCategoryResultForm();
		
		addProductCategoryForm.getProductCategory().setTimestamp(new Date());

		productCategoryDAO.saveProductCategory(addProductCategoryForm.getProductCategory());
		
		addProductCategoryResultForm.setProductCategory(addProductCategoryForm.getProductCategory());
		model.addAttribute("addProductCategoryResultForm", addProductCategoryResultForm);

		return "addProductCategoryResult";
	}

}
