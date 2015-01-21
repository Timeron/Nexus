package com.nexus.apps.multiObserver.form;

import java.util.List;

import com.nexus.apps.form.MainForm;
import com.nexus.dao.entity.ObservedObject;
import com.nexus.dao.entity.ObservedSite;
import com.nexus.dao.entity.ObservedSiteHistory;

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
