package com.tjgd.bean;

public class Property {
	//-------声明私有属性--------------------------
	private String url;
	private String action;
	//-------构造方法初始化私有属性------------------------------
	public Property(String url, String action) {
		this.url = url;
		this.action = action;

	}
    //-------各个属性的getXxx()和setXxx()方法-------
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	@Override
	public String toString() {
		return "Property [url=" + url + ", action=" + action + "]";
	}
	
}
