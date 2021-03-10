package com.ibeetl.admin.core.web.dto;

import java.util.ArrayList;
import java.util.List;

public class FunctionNodeView {

	private String name;
	private String code;
	private String accessUrl;
	private Long id;
	
	String icon;
	List<FunctionNodeView> children=new ArrayList<FunctionNodeView>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<FunctionNodeView> getChildren() {
		return children;
	}
	public void setChildren(List<FunctionNodeView> children) {
		this.children = children;
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAccessUrl() {
		return accessUrl;
	}
	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}
	
}
