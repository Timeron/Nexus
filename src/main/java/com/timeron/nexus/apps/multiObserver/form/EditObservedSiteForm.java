package com.timeron.nexus.apps.multiObserver.form;

import java.util.List;

import com.timeron.NexusDatabaseLibrary.Entity.ObservedObject;
import com.timeron.NexusDatabaseLibrary.Entity.ObservedSite;
import com.timeron.NexusDatabaseLibrary.Entity.ObservedSiteHistory;
import com.timeron.nexus.apps.form.MainForm;

public class EditObservedSiteForm extends MainForm{
	private ObservedSite observedSite;
	private ObservedObject observedObject;
	private List<ObservedSiteHistory> observedSiteHistory;

	public ObservedSite getObservedSite() {
		return observedSite;
	}

	public void setObservedSite(ObservedSite observedSite) {
		this.observedSite = observedSite;
	}

	public ObservedObject getObservedObject() {
		return observedObject;
	}

	public void setObservedObject(ObservedObject observedObject) {
		this.observedObject = observedObject;
	}

	public List<ObservedSiteHistory> getObservedSiteHistory() {
		return observedSiteHistory;
	}

	public void setObservedSiteHistory(List<ObservedSiteHistory> observedSiteHistory) {
		this.observedSiteHistory = observedSiteHistory;
	}
}
