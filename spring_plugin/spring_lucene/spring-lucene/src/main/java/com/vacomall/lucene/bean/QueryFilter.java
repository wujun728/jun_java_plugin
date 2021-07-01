package com.vacomall.lucene.bean;
/**
 * 精确过滤模型
 * @author Administrator
 *
 */
public class QueryFilter {

	/**
	 * 过滤字段
	 */
	private String field;
	/**
	 * 过滤值
	 */
	private String value;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public QueryFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QueryFilter(String field, String value) {
		super();
		this.field = field;
		this.value = value;
	}
	
	
	
}
