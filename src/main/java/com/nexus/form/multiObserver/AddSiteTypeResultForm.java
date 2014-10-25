package com.nexus.form.multiObserver;

import com.nexus.dao.entity.SiteType;
import com.nexus.form.MainForm;

public class AddSiteTypeResultForm extends MainForm {
	
	SiteType siteType = new SiteType();

	public SiteType getSiteType() {
		return siteType;
	}

	public void setSiteType(SiteType siteType) {
		this.siteType = siteType;
	}
	
	
	
}
