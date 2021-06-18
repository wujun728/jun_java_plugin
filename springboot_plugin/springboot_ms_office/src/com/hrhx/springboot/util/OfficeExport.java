package com.hrhx.springboot.util;

import javax.servlet.http.HttpServletResponse;


public class OfficeExport implements ExportInterface {	
	private HttpServletResponse response;
	private String officePath;
	private String officeTitle;
	
	@Override
	public void export() {
		
	}
	
	public OfficeExport(HttpServletResponse response, String officePath,
			String officeTitle) {
		super();
		this.response = response;
		this.officePath = officePath;
		this.officeTitle = officeTitle;  
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getOfficePath() {
		return officePath;
	}
	public void setOfficePath(String officePath) {
		this.officePath = officePath;
	}
	public String getOfficeTitle() {
		return officeTitle;
	}
	public void setOfficeTitle(String officeTitle) {
		this.officeTitle = officeTitle;
	}
	

}
