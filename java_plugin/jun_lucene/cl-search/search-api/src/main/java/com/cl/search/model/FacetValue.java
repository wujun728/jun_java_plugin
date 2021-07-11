package com.cl.search.model;

import java.io.Serializable;

public class FacetValue implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private Integer count;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
