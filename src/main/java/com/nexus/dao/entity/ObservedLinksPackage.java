package com.nexus.dao.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="MO_observed_links_package")
public class ObservedLinksPackage {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String url;
	
	@ManyToOne
	@JoinColumn(name="site_id")
	private Site site;
	
	@OneToMany(mappedBy="observedLinksPackage")
	private List<ObservedSite> observedSite;
	
	@ManyToOne
	@JoinColumn(name="site_type_id")
	private SiteType siteType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ObservedSite> getObservedSite() {
		return observedSite;
	}

	public void setObservedSite(List<ObservedSite> observedSite) {
		this.observedSite = observedSite;
	}

	public SiteType getSiteType() {
		return siteType;
	}

	public void setSiteType(SiteType siteType) {
		this.siteType = siteType;
	}

	@Override
	public String toString() {
		return "ObservedLinksPackage [id=" + id + ", name=" + name + ", url="
				+ url + ", site=" + site + ", observedSite=" + observedSite
				+ ", siteType=" + siteType + "]";
	}
	
	
}
