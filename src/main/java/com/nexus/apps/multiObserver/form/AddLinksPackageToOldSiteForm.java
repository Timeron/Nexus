package com.nexus.apps.multiObserver.form;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.nexus.apps.form.MainForm;
import com.timeron.NexusDatabaseLibrary.Entity.ObservedLinksPackage;
import com.timeron.NexusDatabaseLibrary.Entity.Site;
import com.timeron.NexusDatabaseLibrary.Entity.SiteType;

public class AddLinksPackageToOldSiteForm extends MainForm {

	Site site = new Site();
	List<SiteType> siteTypes = new ArrayList<SiteType>();
	SiteType siteType = new SiteType();
	List<ObservedLinksPackage> observedLinksPackage = new ArrayList<ObservedLinksPackage>();
	File file;

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

	public List<ObservedLinksPackage> getObservedLinksPackage() {
		return observedLinksPackage;
	}

	public void setObservedLinksPackage(
			List<ObservedLinksPackage> observedLinksPackage) {
		this.observedLinksPackage = observedLinksPackage;
	}

	public SiteType getSiteType() {
		return siteType;
	}

	public void setSiteType(SiteType siteType) {
		this.siteType = siteType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "AddLinksPackageToOldSiteForm [site=" + site + ", siteTypes="
				+ siteTypes + ", siteType=" + siteType
				+ ", observedLinksPackage=" + observedLinksPackage + ", file="
				+ file + "]";
	}

	
	
}
