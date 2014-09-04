package com.nexus.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="MO_site")
public class Site {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String url;
	private boolean valid;
	private Date timestamp;
	
	@OneToMany(mappedBy="site")
	private List<ObservedLinksPackage> observedLinksPackage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<ObservedLinksPackage> getObservedLinksPackage() {
		return observedLinksPackage;
	}

	public void setObservedLinksPackage(
			List<ObservedLinksPackage> observedLinksPackage) {
		this.observedLinksPackage = observedLinksPackage;
	}

	@Override
	public String toString() {
		return "Site [id=" + id + ", name=" + name + ", url=" + url
				+ ", valid=" + valid + ", timestamp=" + timestamp
				+ ", observedLinksPackage=" + observedLinksPackage + "]";
	}
	
	
}
