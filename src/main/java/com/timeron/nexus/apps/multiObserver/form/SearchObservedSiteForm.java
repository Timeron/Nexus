package com.timeron.nexus.apps.multiObserver.form;

import java.util.List;

import com.timeron.NexusDatabaseLibrary.Entity.ObservedSite;
import com.timeron.nexus.apps.form.MainForm;

public class SearchObservedSiteForm extends MainForm{

	private List<ObservedSite> results;
	private ObservedSite searchParameters;
	
	public List<ObservedSite> getResults() {
		return results;
	}
	public void setResults(List<ObservedSite> results) {
		this.results = results;
	}
	public ObservedSite getSearchParameters() {
		return searchParameters;
	}
	public void setSearchParameters(ObservedSite searchParameters) {
		this.searchParameters = searchParameters;
	}
	

	
	
	
}
