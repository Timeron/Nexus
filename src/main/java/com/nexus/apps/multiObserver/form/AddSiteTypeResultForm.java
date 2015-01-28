package com.nexus.apps.multiObserver.form;

import com.nexus.apps.form.MainForm;
import com.timeron.NexusDatabaseLibrary.Entity.SiteType;

public class AddSiteTypeResultForm extends MainForm {
	
	SiteType siteType = new SiteType();

	public SiteType getSiteType() {
		return siteType;
	}

	public void setSiteType(SiteType siteType) {
		this.siteType = siteType;
	}
	
	
	
}
