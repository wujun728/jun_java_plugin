package com.osmp.web.user.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年4月20日
 */
public class SysPrivilegeZtreeGrid {

	/**
	 * ID
	 */
	private int id;
	
	/**
	 * 功能名
	 */
	private String name;
	
	/**
	 * 功能图标
	 */
	private String icon;

	/**
	 * 父ID
	 */
	private int pid;
	
	/**
	 * 打开
	 */
	private String open = "true";
	
	private boolean checked;
	
	/**
	 * 子节点
	 */
	private List<SysPrivilegeZtreeGrid> children = new ArrayList<SysPrivilegeZtreeGrid>();
	
	/**
	 * 构造函数
	 * @param p
	 */
	public SysPrivilegeZtreeGrid (SysPrivilege p){
		this.id = p.getId();
		this.name = p.getName();
		this.pid = p.getPid();
		this.icon = p.getIcon();
	}
	
	
	public List<SysPrivilegeZtreeGrid> getChildren() {
		return children;
	}

	public void setChildren(List<SysPrivilegeZtreeGrid> children) {
		this.children = children;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}


	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

}
