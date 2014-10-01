package com.nexus.form.multiObserver;

import java.util.List;

import com.nexus.dao.entity.Site;

public class AddNewLinkPackageForm {

	Site site = new Site();
	List<Site> sites;
	
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public List<Site> getSites() {
		return sites;
	}
	public void setSites(List<Site> sites) {
		this.sites = sites;
	}
	
	
}
