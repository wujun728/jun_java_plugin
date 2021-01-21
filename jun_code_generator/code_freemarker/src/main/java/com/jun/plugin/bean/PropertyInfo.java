package com.jun.plugin.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 表对应的属性
 * @author Wujun
 */
public class PropertyInfo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 属性名
	 */
	private String propertyName;
	/**
	 * 属性类型
	 */
	private String propertyType;
	/**
	 * 属性描述
	 */
	private String propertyDesc;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyDesc() {
		return propertyDesc;
	}

	public void setPropertyDesc(String propertyDesc) {
		this.propertyDesc = propertyDesc;
	}

	@Override
	public String toString() {
		return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss");
	}
}
