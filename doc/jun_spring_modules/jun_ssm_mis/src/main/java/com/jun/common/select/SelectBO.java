package com.jun.common.select;

import java.io.Serializable;

/**
 * 下拉框对象(用于传递数据)
 * @author Wujun
 *
 */
public class SelectBO implements Serializable{
	/**
	 * 当前下拉框页面元素id
	 */
	private String id;
	/**
	 * 下拉框显示值
	 */
	private String display;
	/**
	 * 下拉框value值
	 */
	private String value;
	/**
	 * 上级下拉框Value对应下级属性名
	 */
	private String preProperty;
	/**
	 * 上级下拉框value值
	 */
	private String preValue;
	/**
	 * 当前下拉框数据的实体名称(含包名)
	 */
	private String entity;
	/**
	 * 当前下拉框显示值对应实体类的属性名
	 */
	private String keyField;
	/**
	 * 当前下拉框实际提交值对应实体类的属性名
	 */
	private String valueField;
	/**
	 * 额外传参，如id=1&name=zhangsan(可选)
	 */
	private String params;
	/**
	 * 用于下拉框数据排序,如 id desc(可选)
	 */
	private String orderBy;
	/**
	 * 是否需要添加一个【---请选择---】的空选项，默认为true(可选)
	 */
	private boolean needEmptySelect = true;
	/**
	 * 下拉框的行内样式
	 */
	private String style;
	/**
	 * 下拉框应用的类样式
	 */
	private String cssClass;
	/**
	 * 是否多选，默认false(可选)
	 */
	private boolean multiple;
	/**
	 * 设置下拉框默认显示哪一项(可选)
	 */
	private String defaultValue="";
	/**
	 * 设置显示语言(用于国际化)
	 */
	private String language = "zh";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPreProperty() {
		return preProperty;
	}
	public void setPreProperty(String preProperty) {
		this.preProperty = preProperty;
	}
	public String getPreValue() {
		return preValue;
	}
	public void setPreValue(String preValue) {
		this.preValue = preValue;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public boolean isNeedEmptySelect() {
		return needEmptySelect;
	}
	public void setNeedEmptySelect(boolean needEmptySelect) {
		this.needEmptySelect = needEmptySelect;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public boolean isMultiple() {
		return multiple;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public SelectBO(String display, String value) {
		this.display = display;
		this.value = value;
	}
	public SelectBO(){}
	public SelectBO(String display, Long id) {
		this.display = display;
		this.value = id.intValue() + "";
	}
}
