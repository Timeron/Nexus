package com.nexus.form.multiObserver;

import com.nexus.dao.entity.Site;
import com.nexus.form.MainForm;

public class AddSiteForm extends MainForm {

	Site site = new Site();

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
}
