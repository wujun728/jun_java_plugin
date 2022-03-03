package com.jun.common.basiccode;

import java.io.Serializable;

/**
 * 基础数据类(如省份城市、性别、血型、星座等)
 * @author Wujun
 *
 */
public class BasicCode implements Serializable{
	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 中文名称
	 */
	private String cname;
	/**
	 * 英文名称
	 */
	private String ename;
	/**
	 * 父id
	 */
	private Long parentId;
	/**
	 * 基础数据分组代码
	 */
	private String code;
	/**
	 * 基础数据值(同一分组下基础数据value保持唯一)
	 */
	private String value;
	/**
	 * 备注
	 */
	private String memo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	public BasicCode(){}
}
