package com.osmp.web.user.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限菜单树形列表VO
 * @author wangkaiping
 * @version V1.0, 2013-5-13 下午04:59:34
 */
public class SysPrivilegeTreeGrid {

	/**
	 * ID
	 */
	private int id;
	
	/**
	 * 功能名
	 */
	private String name;
	
	/**
	 * 功能uri
	 */
	private String uri;
	
	/**
	 * 功能图标
	 */
	private String icon;

	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 排序
	 */
	private int ord;

	/**
	 * 父ID
	 */
	private int pid;
	
	/**
	 * 类型  菜单:SysPrivilege.TYPE_MENU = 1         功能:SysPrivilege.TYPE_FUNC = 2
	 */
	private int type;
	
	/**
	 * 权限菜单状态
	 */
	private int prstate;
	
	/**
	 * 树形列表是否有下级 close：有下一级  open:没有下一级
	 */
	private String state = "open";
	
	/**
	 * 子节点
	 */
	private List<SysPrivilegeTreeGrid> children = new ArrayList<SysPrivilegeTreeGrid>();
	
	/**
	 * 构造函数
	 * @param p
	 */
	public SysPrivilegeTreeGrid (SysPrivilege p){
		this.id = p.getId();
		this.name = p.getName();
		this.ord = p.getOrd();
		this.pid = p.getPid();
		this.prstate = p.getPrstate();
		this.type = p.getType();
		this.uri = p.getUri();
		this.icon = p.getIcon();
		this.description = p.getDescription();
	}
	
	
	public List<SysPrivilegeTreeGrid> getChildren() {
		return children;
	}

	public void setChildren(List<SysPrivilegeTreeGrid> children) {
		this.children = children;
	}

	public int getPrstate() {
		return prstate;
	}

	public void setPrstate(int prstate) {
		this.prstate = prstate;
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

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
