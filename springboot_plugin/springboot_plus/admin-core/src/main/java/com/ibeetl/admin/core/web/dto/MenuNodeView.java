package com.ibeetl.admin.core.web.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuNodeView {

	private String name;
	private String code;
	private Long id;
	private String path;
	private String icon;
	private List<MenuNodeView> children = new ArrayList<MenuNodeView>();

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

	public List<MenuNodeView> getChildren() {
		return children;
	}

	public void setChildren(List<MenuNodeView> children) {
		this.children = children;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
