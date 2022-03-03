package com.baomidou.springwind.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 权限表
 *
 */
@TableName(value = "permission")
public class Permission implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId
	private Long id;

	/** 上级ID */
	private Long pid;

	/** 标题 */
	private String title;

	/** 类型 0、菜单 1、功能 */
	private Integer type;

	/** 状态 0、正常 1、禁用 */
	private Integer state;

	/** 排序 */
	private Integer sort;

	/** 地址 */
	private String url;

	/** 权限编码 */
	private String permCode;

	/** 图标 */
	private String icon;

	/** 描述 */
	private String description;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermCode() {
		return this.permCode;
	}

	public void setPermCode(String permCode) {
		this.permCode = permCode;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
