package com.nexus.form.multiObserver;

import com.nexus.dao.entity.ObservedObject;
import com.nexus.dao.entity.ObservedSite;
import com.nexus.form.MainForm;

public class EditObservedSiteForm extends MainForm{
	private ObservedSite observedSite;
	private ObservedObject observedObject;

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
	
	

}
