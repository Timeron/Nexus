package com.timeron.nexus.apps.shopList.dto;

import java.util.Date;

public class ProductDTO {

	private int id;
	private String name;
	private String size;
	private float number;
	private int priority; 
	private boolean buy;
	
	private Date added;
	private Date checked;
	
	public ProductDTO() {
		super();
	}
	
	public ProductDTO(String name, String size, float number, int priority, boolean buy) {
		super();
		this.name = name;
		this.size = size;
		this.number = number;
		this.priority = priority;
		this.added = new Date();
		this.buy = buy;
	}
	
	public ProductDTO(int id, String name, String size, float number, int priority, boolean buy) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.number = number;
		this.priority = priority;
		this.added = new Date();
		this.buy = buy;
	}
	
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

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public float getNumber() {
		return number;
	}

	public void setNumber(float number) {
		this.number = number;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isBuy() {
		return buy;
	}

	public void setBuy(boolean buy) {
		this.buy = buy;
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public Date getChecked() {
		return checked;
	}

	public void setChecked(Date checked) {
		this.checked = checked;
	}


	
}
