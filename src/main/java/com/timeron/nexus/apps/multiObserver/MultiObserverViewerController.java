package com.timeron.nexus.apps.multiObserver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.timeron.NexusDatabaseLibrary.Entity.ObservedObject;
import com.timeron.NexusDatabaseLibrary.Entity.ObservedSite;
import com.timeron.NexusDatabaseLibrary.Entity.ObservedSiteHistory;
import com.timeron.NexusDatabaseLibrary.dao.ObservedObjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.ObservedSiteDAO;
import com.timeron.NexusDatabaseLibrary.dao.ObservedSiteHistoryDAO;
import com.timeron.nexus.apps.multiObserver.form.EditObservedSiteForm;
import com.timeron.nexus.apps.multiObserver.form.SearchObservedObjectsForm;
import com.timeron.nexus.apps.multiObserver.form.SearchObservedSiteForm;

@Controller
@RequestMapping("/multiobserver/viewer")
public class MultiObserverViewerController {

	@Autowired
	ObservedSiteDAO observedSiteDAO;
	@Autowired
	ObservedObjectDAO observedObjectDAO;
	@Autowired
	ObservedSiteHistoryDAO observedSiteHistoryDAO;
	
	@RequestMapping(value = "/searchObservedObject", method = RequestMethod.GET)
	public String searchObservedObjectInit(ModelMap model){
		SearchObservedObjectsForm searchObservedObjectsForm = new SearchObservedObjectsForm();
		
		searchObservedObjectsForm.setResults(observedObjectDAO.getAll());
		
		model.addAttribute("form", searchObservedObjectsForm);

		return "searchObservedObject";
	}
	
	@RequestMapping(value = "/searchObservedSite", method = RequestMethod.GET)
	public String searchObservedSiteInit(ModelMap model){
		SearchObservedSiteForm searchObservedSiteForm = new SearchObservedSiteForm();
		
		searchObservedSiteForm.setResults(observedSiteDAO.getAll());
		
		model.addAttribute("form", searchObservedSiteForm);

		return "searchObservedSite";
	}
	
	@RequestMapping(value = "/searchObservedObject", method = RequestMethod.POST)
	public String searchObservedObject(ModelMap model, @ModelAttribute("form") SearchObservedObjectsForm searchObservedObjectsRequest){
		SearchObservedObjectsForm SearchObservedObjectsForm = new SearchObservedObjectsForm();
		
		SearchObservedObjectsForm.setResults(observedObjectDAO.search(searchObservedObjectsRequest.getSearchParameters()));
		
		model.addAttribute("form", SearchObservedObjectsForm);

		return "searchObservedObject";
	}
	
	@RequestMapping(value = "/searchObservedSite", method = RequestMethod.POST)
	public String searchObservedSite(ModelMap model, @ModelAttribute("form") SearchObservedSiteForm searchObservedSiteRequest){
		SearchObservedSiteForm searchObservedSiteForm = new SearchObservedSiteForm();
		
		searchObservedSiteForm.setResults(observedSiteDAO.search(searchObservedSiteRequest.getSearchParameters()));
		
		model.addAttribute("form", searchObservedSiteForm);

		return "searchObservedSite";
	}
	
	@RequestMapping(value = "/editObservedSite", method = RequestMethod.GET)
	public String editObservedSite(ModelMap model, HttpServletRequest request,
			HttpServletResponse response){
		EditObservedSiteForm editObservedSiteForm = new EditObservedSiteForm();
		
		ObservedSite observedSite = observedSiteDAO.getById(Integer.parseInt(request.getParameter("id")));
		
		editObservedSiteForm.setObservedSite(observedSiteDAO.getById(Integer.parseInt(request.getParameter("id"))));
		editObservedSiteForm.setObservedObject(observedSite.getObservedObject());
		editObservedSiteForm.setObservedSiteHistory(observedSiteHistoryDAO.getByObservedSite(observedSite));
		
		model.addAttribute("form", editObservedSiteForm);

		return "editObservedSite";
	}
	
	
	
}
