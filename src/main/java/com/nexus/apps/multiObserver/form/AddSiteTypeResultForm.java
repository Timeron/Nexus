package com.nexus.apps.multiObserver.form;

import com.nexus.apps.form.MainForm;
import com.nexus.dao.entity.SiteType;

public class AddSiteTypeResultForm extends MainForm {
	
	SiteType siteType = new SiteType();

	public SiteType getSiteType() {
		return siteType;
	}

	public void setSiteType(SiteType siteType) {
		this.siteType = siteType;
	}
	
	
	
}
