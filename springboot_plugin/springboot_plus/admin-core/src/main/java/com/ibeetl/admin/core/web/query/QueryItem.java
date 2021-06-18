package com.ibeetl.admin.core.web.query;

/**
 * 查询选项
 * @author Wujun
 *
 */
public class QueryItem {
	private String fieldName;
	private String name;
	private int type =1 ;
	private boolean show=false;
	private String dictName="";
	private boolean fuzzy = false;
	private String group = null;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public String getDictName() {
		return dictName;
	}
	
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public boolean isFuzzy() {
		return fuzzy;
	}
	public void setFuzzy(boolean fuzzy) {
		this.fuzzy = fuzzy;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	
}
