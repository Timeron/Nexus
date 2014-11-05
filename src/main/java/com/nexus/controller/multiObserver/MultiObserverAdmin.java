package com.nexus.controller.multiObserver;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateJdbcException;
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
import com.nexus.form.multiObserver.MultiObserverForm;

@Controller
@RequestMapping("/multiobserver/admin")
public class MultiObserverAdmin {

	static Logger log = Logger.getLogger(MultiObserverAdmin.class.getName());

	@Autowired
	SiteDAO siteDAO;
	@Autowired
	SiteTypeDAO siteTypeDAO;
	@Autowired
	ProductCategoryDAO productCategoryDAO;
	@Autowired
	ObservedLinksPackageDAO observedLinksPackageDAO;

	/**
	 * strona główna Admin Multi Observera
	 * 
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String MultiObserverAdminPage(ModelMap model) {

		MultiObserverForm multiObserverForm = new MultiObserverForm();
		
		model.addAttribute("form", multiObserverForm);

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return "multiObserver";

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
	@RequestMapping(value = "/addLinkPackage", method = RequestMethod.GET)
	public String AddNewLinkPackage(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		AddNewLinkPackageForm addNewLinkPackageForm = new AddNewLinkPackageForm();

		try {
			addNewLinkPackageForm.setSites(siteDAO.getAllSites());
		} catch (HibernateJdbcException e){
			addNewLinkPackageForm.setError("Połączenie z bazą danych przerwane");
			e.printStackTrace();
		} catch(GenericJDBCException e){
			e.printStackTrace();
		}

		model.addAttribute("form", addNewLinkPackageForm);

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

	@RequestMapping(value = "/addSite", method = RequestMethod.GET)
	public String AddSite(ModelMap model) {

		AddSiteForm addSiteForm = new AddSiteForm();
		
		model.addAttribute("form", addSiteForm);

		return "addSite";

	}

	/**
	 * AddToNewSite Dodawanie pakietów linków do dodanej strony.
	 * 
	 * @param model
	 * @param site
	 * @return
	 */

	@RequestMapping(value = "/addLinkPackageToNewSite", method = RequestMethod.POST)
	public String AddLinksPackageToNewSite(ModelMap model, @ModelAttribute("addSiteForm") AddSiteForm addSiteForm) {
		
		AddLinksPackageToNewSiteForm addLinksPackageToNewSiteForm = new AddLinksPackageToNewSiteForm();
		addLinksPackageToNewSiteForm.setSiteTypes(siteTypeDAO.getAllSiteTypes());
		addLinksPackageToNewSiteForm.setSite(addSiteForm.getSite());

		model.addAttribute("form", addLinksPackageToNewSiteForm);

		return "addLinkPackageToNewSite";
	}

	/**
	 * AddToOldSite Dodawanie pakietów linków do wybranej strony.
	 * 
	 * @param model
	 * @param site
	 * @return
	 */

	@RequestMapping(value = "/addLinkPackageToOldSite", method = RequestMethod.POST)
	public String AddLinksPackageToOldSite(ModelMap model, @ModelAttribute("addNewLinkPackageForm") AddNewLinkPackageForm addNewLinkPackageForm) {
		AddLinksPackageToOldSiteForm addLinksPackageToOldSiteForm = new AddLinksPackageToOldSiteForm();
		
		addLinksPackageToOldSiteForm.setSite(siteDAO.getSiteById(addNewLinkPackageForm.getSite().getId()));
		addLinksPackageToOldSiteForm.setSiteTypes(siteTypeDAO.getAllSiteTypes());

		model.addAttribute("form", addLinksPackageToOldSiteForm);

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

	@RequestMapping(value = "/addLinkPackageToNewSiteResult", method = RequestMethod.POST)
	public String AddLinkPackageToNewSiteResult(ModelMap model, @ModelAttribute("addLinksPackageToOldSiteForm") AddLinksPackageToOldSiteForm addLinksPackageToOldSiteForm) {
		log.info("Dodajemy Stronę");
		
		AddLinksPackageToOldSiteResultForm addLinksPackageToOldSiteResultForm = new AddLinksPackageToOldSiteResultForm();
		
		addLinksPackageToOldSiteForm.getSite().setTimestamp(new Date());
		for (ObservedLinksPackage observedLinksPackage : addLinksPackageToOldSiteForm.getSite().getObservedLinksPackage()) {
			if(observedLinksPackageDAO.getByUrl(observedLinksPackage.getUrl()).isEmpty()){
				observedLinksPackage.setSiteType(siteTypeDAO.getByDescription(observedLinksPackage.getSiteType().getDescription()));
				observedLinksPackage.setSite(addLinksPackageToOldSiteForm.getSite());
				observedLinksPackage.setTimestamp(new Date());
				siteDAO.saveSite(addLinksPackageToOldSiteForm.getSite());
				log.info("LinksPackage dodany: "+observedLinksPackage.getUrl());
			}else{
				observedLinksPackage.setDuplicated(true);
				log.warn("LinksPackage już istnieje: "+observedLinksPackage.getUrl());
			}
		}
		

		model.addAttribute("form", addLinksPackageToOldSiteResultForm);
		
		return "addLinkPackageToSiteResult";
	}

	@RequestMapping(value = "/addLinksPackageToOldSiteResult", method = RequestMethod.POST)
	public String AddLinksPackageToOldSiteResult(ModelMap model, @ModelAttribute("addLinksPackageToOldSiteForm") AddLinksPackageToOldSiteForm addLinksPackageToOldSiteForm) {
		AddLinksPackageToOldSiteResultForm addLinksPackageToOldSiteResultForm = new AddLinksPackageToOldSiteResultForm();
		for(ObservedLinksPackage observedLinksPackage : addLinksPackageToOldSiteForm.getObservedLinksPackage()){
			if(observedLinksPackageDAO.getByUrl(observedLinksPackage.getUrl()).isEmpty()){
				observedLinksPackage.setSite(addLinksPackageToOldSiteForm.getSite());
				SiteType siteType = siteTypeDAO.getByDescription(observedLinksPackage.getSiteType().getDescription());
				observedLinksPackage.setSiteType(siteType);
				observedLinksPackage.setTimestamp(new Date());
				
				observedLinksPackageDAO.save(observedLinksPackage);
			}else{
				observedLinksPackage.setDuplicated(true);
				log.warn("LinksPackage już istnieje: "+observedLinksPackage.getUrl());
			}
		}

		model.addAttribute("form", addLinksPackageToOldSiteResultForm);
		
		return "addLinkPackageToSiteResult";
	}
	
	@RequestMapping(value = "/addLinksPackageFromFileResult", method = RequestMethod.POST)
	public String AddLinksPackageFromFileResult(ModelMap model, @ModelAttribute("addLinksPackageToOldSiteForm") AddLinksPackageToOldSiteForm addLinksPackageToOldSiteForm){
		
//		 try (InputStream in = URI.create(addLinksPackageToOldSiteForm.getFile().getPath()).toURL().openStream()) {
//		        Files.copy(in, Paths.get("C:/test.txt"));
//		    } catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		return "addLinksPackageFromFileResult";
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

	@RequestMapping(value = "/addSiteType", method = RequestMethod.GET)
	public String AddSiteType(ModelMap model) {
		AddSiteTypeForm addSiteTypeForm = new AddSiteTypeForm();
		
		try {
			addSiteTypeForm.setProductCategorys(productCategoryDAO.getAll());
		} catch (HibernateJdbcException e) {
			addSiteTypeForm.setError("Połączenie z bazą danych przerwane");
			e.printStackTrace();
		}

		model.addAttribute("form", addSiteTypeForm);

		return "addSiteType";
	}

	/**
	 * AddSiteTypeResult Zapisywanie nowego typu artykułu
	 * 
	 * @param model
	 * @param siteType
	 * @return
	 */

	@RequestMapping(value = "/addSiteTypeResult", method = RequestMethod.POST)
	public String AddSiteTypeResult(ModelMap model,
			@ModelAttribute("addSiteTypeForm") AddSiteTypeForm addSiteTypeForm) {
		
		AddSiteTypeResultForm addSiteTypeResultForm = new AddSiteTypeResultForm();
		
		if(siteTypeDAO.getByDescription(addSiteTypeForm.getSiteType().getDescription()).isEmpty()){
			ProductCategory productCategory = new ProductCategory();
			
			productCategory = productCategoryDAO.getById(addSiteTypeForm.getSiteType().getProductCategory().getId());
			addSiteTypeForm.getSiteType().setProductCategory(productCategory);
			addSiteTypeForm.getSiteType().setTimestamp(new Date());
			
			addSiteTypeResultForm.setSiteType(addSiteTypeForm.getSiteType());
			
			siteTypeDAO.saveSiteType(addSiteTypeForm.getSiteType());
		}else{
			addSiteTypeResultForm.setError("SiteType już istnieje");
		}
		model.addAttribute("form", addSiteTypeResultForm);

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

	@RequestMapping(value = "/addProductCategory", method = RequestMethod.GET)
	public String AddProductCategory(ModelMap model) {
		AddProductCategoryForm addProductCategoryForm = new AddProductCategoryForm();

		try {
			addProductCategoryForm.setAllProductCategorys(productCategoryDAO.getAll());
		} catch (HibernateJdbcException e) {
			addProductCategoryForm.setError("Połączenie z bazą danych przerwane");
			e.printStackTrace();
		}
		
		model.addAttribute("form", addProductCategoryForm);

		return "addProductCategory";
	}

	/**
	 * AddProductCategoryResult Zapisywanie nowej kategorii produktu
	 * 
	 * @param model
	 * @param productCategory
	 * @return
	 */

	@RequestMapping(value = "/addProductCategoryResult", method = RequestMethod.POST)
	public String AddProductCategoryResult(ModelMap model,
			@ModelAttribute("addProductCategoryForm") AddProductCategoryForm addProductCategoryForm) {
		
		AddProductCategoryResultForm addProductCategoryResultForm = new AddProductCategoryResultForm();
		
		if(productCategoryDAO.getByDescription(addProductCategoryForm.getProductCategory().getDescription()).isEmpty()){
			addProductCategoryForm.getProductCategory().setTimestamp(new Date());
			productCategoryDAO.saveProductCategory(addProductCategoryForm.getProductCategory());
			addProductCategoryResultForm.setProductCategory(addProductCategoryForm.getProductCategory());
		}else{
			addProductCategoryResultForm.setWarning("Kategoria już istnieje");
		}		
		model.addAttribute("form", addProductCategoryResultForm);

		return "addProductCategoryResult";
	}

}
