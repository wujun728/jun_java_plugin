package com.cl.search.model;

import java.io.Serializable;

public class SearchModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer pageNo;
	private String keyword;
	private Integer brandId;	

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}	
	
}
