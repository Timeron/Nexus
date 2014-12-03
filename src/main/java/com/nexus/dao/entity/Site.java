package com.nexus.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	private Integer id;
	@Column(length = 50)
	private String name;
	@Column(length = 150)
	private String url;
	private boolean valid;
	private Date timestamp;
	@Column(length = 150, name="articles_div_x_path")
	private String articlesDivXPath;
	@Column(length = 150, name="next_x_path")
	private String nextXPath;
	@Column(length = 150, name="product_name_x_path")
	private String productNameXPath;
	@Column(length = 150, name="produsct_url_x_path")
	private String productURLXPath;
	@Column(length = 150, name="product_price_x_path")
	private String productPriceXPath;
	@Column(length = 150, name="product_new_price_x_path")
	private String productNewPriceXPath;
	@Column(length = 150, name="product_old_price_x_path")
	private String productOldPriceXPath;
	@Column(length = 150, name="product_key_x_path")
	private String productKayXPath;

	@OneToMany(mappedBy="site", cascade=CascadeType.ALL)
	private List<ObservedLinksPackage> observedLinksPackage;

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

	public String getArticlesDivXPath() {
		return articlesDivXPath;
	}

	public void setArticlesDivXPath(String articlesDivXPath) {
		this.articlesDivXPath = articlesDivXPath;
	}

	public String getNextXPath() {
		return nextXPath;
	}

	public void setNextXPath(String nextXPath) {
		this.nextXPath = nextXPath;
	}

	public String getProductNameXPath() {
		return productNameXPath;
	}

	public void setProductNameXPath(String productNameXPath) {
		this.productNameXPath = productNameXPath;
	}

	public String getProductURLXPath() {
		return productURLXPath;
	}

	public void setProductURLXPath(String productURLXPath) {
		this.productURLXPath = productURLXPath;
	}

	public String getProductPriceXPath() {
		return productPriceXPath;
	}

	public void setProductPriceXPath(String productPriceXPath) {
		this.productPriceXPath = productPriceXPath;
	}

	public String getProductNewPriceXPath() {
		return productNewPriceXPath;
	}

	public void setProductNewPriceXPath(String productNewPriceXPath) {
		this.productNewPriceXPath = productNewPriceXPath;
	}

	public String getProductOldPriceXPath() {
		return productOldPriceXPath;
	}

	public void setProductOldPriceXPath(String productOldPriceXPath) {
		this.productOldPriceXPath = productOldPriceXPath;
	}

	public String getProductKayXPath() {
		return productKayXPath;
	}

	public void setProductKayXPath(String productKayXPath) {
		this.productKayXPath = productKayXPath;
	}

	public List<ObservedLinksPackage> getObservedLinksPackage() {
		return observedLinksPackage;
	}

	public void setObservedLinksPackage(
			List<ObservedLinksPackage> observedLinksPackage) {
		this.observedLinksPackage = observedLinksPackage;
	}

}
