package com.nexus.apps.multiObserver.form;

import com.nexus.apps.form.MainForm;
import com.nexus.dao.entity.Site;

public class AddSiteForm extends MainForm {

	Site site = new Site();

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
}
