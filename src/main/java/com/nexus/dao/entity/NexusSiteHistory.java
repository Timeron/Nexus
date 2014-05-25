package com.nexus.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NexusSiteHistory {

	@Id
	@GeneratedValue
	private Integer ObservedSiteHistory;
	private Integer observedSite;
	private float price;
	private float promotionPrice;
	private float oldPrice;
	private Date timestamp;
	
	public Integer getObservedSiteHistory() {
		return ObservedSiteHistory;
	}
	public void setObservedSiteHistory(Integer observedSiteHistory) {
		ObservedSiteHistory = observedSiteHistory;
	}
	public Integer getObservedSite() {
		return observedSite;
	}
	public void setObservedSite(Integer observedSite) {
		this.observedSite = observedSite;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(float promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	public float getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(float oldPrice) {
		this.oldPrice = oldPrice;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
