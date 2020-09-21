package com.osmp.web.user.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单及权限信息
 * 
 * @author wangkaiping
 * @version V1.0, 2013-4-7 下午03:34:34
 */
@Table(name = "t_sys_privilege")
public class SysPrivilege {

	private static final long serialVersionUID = 1L;

	/**
	 * 状态启动
	 */
	public final static int STATE_NORMAL = 1;

	/**
	 * 状态停用
	 */
	public final static int STATE_STOP = 0;

	/**
	 * 菜单类型
	 */
	public final static int TYPE_MENU = 1;

	/**
	 * 功能类型
	 */
	public final static int TYPE_FUNC = 2;

	/**
	 * ID
	 */
	@Id
	private int id = 0;

	/**
	 * 功能名
	 */
	@Column
	private String name;

	/**
	 * 功能uri
	 */
	@Column
	private String uri;

	/**
	 * 功能图标
	 */
	@Column
	private String icon;

	/**
	 * 描述
	 */
	@Column
	private String description;

	/**
	 * 排序
	 */
	@Column
	private int ord;

	/**
	 * 父ID
	 */
	@Column
	private int pid;

	/**
	 * 状态 启用:SysPrivilege.STATE_NORMAL=1 停用:SysPrivilege.STATE_STOP=0
	 */
	@Column
	private int prstate;

	/**
	 * 类型 菜单:SysPrivilege.TYPE_MENU = 1 功能:SysPrivilege.TYPE_FUNC = 2
	 */
	@Column
	private int type;

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

	public int getPrstate() {
		return prstate;
	}

	public void setPrstate(int prstate) {
		this.prstate = prstate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
