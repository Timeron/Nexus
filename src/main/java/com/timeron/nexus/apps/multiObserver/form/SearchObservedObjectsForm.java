package com.timeron.nexus.apps.multiObserver.form;

import java.util.List;

import org.apache.log4j.Logger;

import com.timeron.NexusDatabaseLibrary.Entity.ObservedObject;
import com.timeron.nexus.apps.form.MainForm;

public class SearchObservedObjectsForm extends MainForm{

	static Logger log = Logger.getLogger(SearchObservedObjectsForm.class.getName());

	private List<ObservedObject> results = null;
	private ObservedObject searchParameters = null;
	

	public List<ObservedObject> getResults() {
		return results;
	}
	public void setResults(List<ObservedObject> results) {
		this.results = results;
	}
	public ObservedObject getSearchParameters() {
		return searchParameters;
	}
	public void setSearchParameters(ObservedObject searchParameters) {
		this.searchParameters = searchParameters;
	}


	
	
}
