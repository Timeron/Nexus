package com.nexus.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;

@Entity
@Table(name="MO_observed_site")
public class ObservedSite {

	@Id
	@GeneratedValue
	private Integer id;
	@Column(name="article_name")
	private String articleName;
	private String url;
	@Column(name="hash_url")
	private Integer hashUrl;
	private Date timestamp;
	
	@OneToMany(mappedBy="observedSite", cascade=CascadeType.ALL)
	private List<ObservedSiteHistory> observedSiteHistory;
	
	@ManyToOne
	@JoinColumn(name="observed_links_package_id")
	private ObservedLinksPackage observedLinksPackage;
	
	@ManyToOne
	@JoinColumn(name="observed_object_id")
	private ObservedObject observedObject;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getHashUrl() {
		return hashUrl;
	}

	public void setHashUrl(Integer hashUrl) {
		this.hashUrl = hashUrl;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<ObservedSiteHistory> getObservedSiteHistory() {
		return observedSiteHistory;
	}

	public void setObservedSiteHistory(List<ObservedSiteHistory> observedSiteHistory) {
		this.observedSiteHistory = observedSiteHistory;
	}

	public ObservedLinksPackage getObservedLinksPackage() {
		return observedLinksPackage;
	}

	public void setObservedLinksPackage(ObservedLinksPackage observedLinksPackage) {
		this.observedLinksPackage = observedLinksPackage;
	}

	public ObservedObject getObservedObject() {
		return observedObject;
	}

	public void setObservedObject(ObservedObject observedObject) {
		this.observedObject = observedObject;
	}
	
}
