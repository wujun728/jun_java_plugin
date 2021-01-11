package com.jin.calendar.bo;

import java.io.Serializable;

/**
 * DWZ响应实体
 * @author JinLiang
 * @datetime 2014年4月27日 下午9:16:34
 */
public class DwzResponseBO implements Serializable {

	private static final long serialVersionUID = 1645985164704304667L;
	private String statusCode;
	private String message;
	private String navTabId;
	private String rel;
	private String callbackType;
	private String forwardUrl;
	
	public DwzResponseBO() {
		super();
		this.statusCode="200";
		this.message="操作成功";
	}
	public DwzResponseBO(String navTabId, String callbackType) {
		super();
		this.statusCode="200";
		this.message="操作成功";
		this.navTabId=navTabId;
		this.callbackType=callbackType;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNavTabId() {
		return navTabId;
	}
	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	public String getCallbackType() {
		return callbackType;
	}
	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
}
