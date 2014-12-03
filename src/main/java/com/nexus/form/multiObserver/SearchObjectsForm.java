package com.nexus.form.multiObserver;

import java.util.List;

import org.apache.log4j.Logger;

import com.nexus.dao.entity.ObservedSite;
import com.nexus.form.MainForm;

public class SearchObjectsForm extends MainForm{

	static Logger log = Logger.getLogger(SearchObjectsForm.class.getName());

	private List<ObservedSite> observedSites;

	public List<ObservedSite> getObservedSites() {
		return observedSites;
	}

	public void setObservedSites(List<ObservedSite> observedSites) {
		this.observedSites = observedSites;
	}
	
	
}
