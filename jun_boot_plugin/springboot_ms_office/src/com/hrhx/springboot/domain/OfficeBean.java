package com.hrhx.springboot.domain;

import java.io.Serializable;

public class OfficeBean implements Serializable{
	
	private static final long serialVersionUID = 1944465925038518131L;
	
	private String officeType;
	private String officePath;
	
	public OfficeBean() {
		super();
	}
	public String getOfficeType() {
		return officeType;
	}
	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}
	public String getOfficePath() {
		return officePath;
	}
	public void setOfficePath(String officePath) {
		this.officePath = officePath;
	}
	
	
}
