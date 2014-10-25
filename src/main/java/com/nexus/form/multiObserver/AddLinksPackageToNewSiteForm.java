package com.nexus.form.multiObserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nexus.dao.entity.Site;
import com.nexus.dao.entity.SiteType;
import com.nexus.form.MainForm;

public class AddLinksPackageToNewSiteForm extends MainForm {

	Site site = new Site();
	List<SiteType> siteTypes = new ArrayList<SiteType>();
	MultipartFile file;
	
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
