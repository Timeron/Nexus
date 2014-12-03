package com.nexus.controller.multiObserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexus.dao.Implementation.ObservedSiteDAO;
import com.nexus.form.multiObserver.SearchObjectsForm;

@Controller
@RequestMapping("/multiobserver/viewer")
public class MultiObserverViewer {

	@Autowired
	ObservedSiteDAO observedSiteDAO;
	
	@RequestMapping("/search")
	public String searchObjects(ModelMap model){
		SearchObjectsForm searchObjectsForm = new SearchObjectsForm();
		
		searchObjectsForm.setObservedSites(observedSiteDAO.getAll());
		
		model.addAttribute("form", searchObjectsForm);

		return "searchObjects";
	}
}
