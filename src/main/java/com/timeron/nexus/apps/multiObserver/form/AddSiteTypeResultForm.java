package com.timeron.nexus.apps.multiObserver.form;

import com.timeron.NexusDatabaseLibrary.Entity.SiteType;
import com.timeron.nexus.apps.form.MainForm;

public class AddSiteTypeResultForm extends MainForm {
	
	SiteType siteType = new SiteType();

	public SiteType getSiteType() {
		return siteType;
	}

	public void setSiteType(SiteType siteType) {
		this.siteType = siteType;
	}
	
	
	
}
