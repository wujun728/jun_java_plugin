package com.jun.plugin.solr.model;

import java.io.Serializable;
import java.util.List;

public class FacetItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private Integer valueCount;
	private List<FacetValue> valueList;
	
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
	public Integer getValueCount() {
		return valueCount;
	}
	public void setValueCount(Integer valueCount) {
		this.valueCount = valueCount;
	}
	public List<FacetValue> getValueList() {
		return valueList;
	}
	public void setValueList(List<FacetValue> valueList) {
		this.valueList = valueList;
	}
	
	
}
