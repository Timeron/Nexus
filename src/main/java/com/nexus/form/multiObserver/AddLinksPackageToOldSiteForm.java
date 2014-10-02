package com.nexus.form.multiObserver;

import java.util.ArrayList;
import java.util.List;

import com.nexus.dao.entity.ObservedLinksPackage;
import com.nexus.dao.entity.Site;
import com.nexus.dao.entity.SiteType;

public class AddLinksPackageToOldSiteForm {

	Site site = new Site();
	List<SiteType> siteTypes = new ArrayList<SiteType>();
	SiteType siteType = new SiteType();
	ObservedLinksPackage observedLinksPackage = new ObservedLinksPackage();

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
	
	public ObservedLinksPackage getObservedLinksPackage() {
		return observedLinksPackage;
	}

	public void setObservedLinksPackage(ObservedLinksPackage observedLinksPackage) {
		this.observedLinksPackage = observedLinksPackage;
	}
	
}
