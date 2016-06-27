package com.timeron.nexus.apps.multiObserver.form;

import com.timeron.NexusDatabaseLibrary.Entity.Site;
import com.timeron.nexus.apps.form.MainForm;

public class AddSiteForm extends MainForm {

	Site site = new Site();

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
}
