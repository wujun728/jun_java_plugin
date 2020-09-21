/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.tags.datagrid;

/**
 * 数据表格列值动态转换
 * 
 * @author wangkaiping
 * @version V1.0, 2013-5-8 上午11:41:07
 */
public class ColumnValue {

	/**
	 * filed 名称
	 */
	private String name;

	/**
	 * 对应的文本字段
	 */
	private String text;

	/**
	 * 原始字段值
	 */
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ColumnValue(String name, String text, String value) {
		super();
		this.name = name;
		this.text = text;
		this.value = value;
	}
}
