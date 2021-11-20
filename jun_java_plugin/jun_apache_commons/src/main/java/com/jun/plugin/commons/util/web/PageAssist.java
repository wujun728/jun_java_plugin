package com.jun.plugin.commons.util.web;

import java.util.List;

public class PageAssist {
	private int pageSize;
	private int pageNo;
	private long allNum;
	private List<?> result;

	public PageAssist(int pageSize, int pageNo, int allNum) {
		super();
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.allNum = allNum;
	}

	public PageAssist(int pageSize, int pageNo) {
		super();
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.allNum = -1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public int getPageNo() {
		return pageNo;
	}

	public long getAllNum() {
		return allNum;
	}

	public void setAllNum(long allNum) {
		this.allNum = allNum;
	}
}
