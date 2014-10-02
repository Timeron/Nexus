package com.nexus.form.multiObserver;

import java.util.ArrayList;
import java.util.List;

import com.nexus.dao.entity.ObservedLinksPackage;
import com.nexus.dao.entity.Site;
import com.nexus.dao.entity.SiteType;

public class AddLinksPackageToNewSiteForm {

	Site site = new Site();
	List<SiteType> siteTypes = new ArrayList<SiteType>();
	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public List<SiteType> getSiteTypes() {
		return siteTypes;
	}

	public void setSiteTypes(List<SiteType> siteTypes) {
		this.siteTypes = siteTypes;
	}
	
	
}
