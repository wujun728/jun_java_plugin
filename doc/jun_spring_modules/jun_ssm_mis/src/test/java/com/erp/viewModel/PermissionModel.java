package com.erp.viewModel;

import java.util.Date;

public class PermissionModel {
	private Integer permissionId;
	private Integer pid;
	private String name;
	private String pname;
	private Integer sort;
	private String myid;
	private String type;
	private String state;
	private String isused;
	private String url;
	private String iconCls;
	private String status;
	private String description;
	private String created;
	// private Date lastmod;
	private Integer creater;

	
	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	// private Integer modifyer;
	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

 

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMyid() {
		return myid;
	}

	public void setMyid(String myid) {
		this.myid = myid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIsused() {
		return isused;
	}

	public void setIsused(String isused) {
		this.isused = isused;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCreater() {
		return creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

}
