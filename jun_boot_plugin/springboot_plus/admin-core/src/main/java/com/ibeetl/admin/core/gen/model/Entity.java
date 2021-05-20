package com.ibeetl.admin.core.gen.model;

import java.util.ArrayList;
import java.util.List;

public class Entity {
	String name;
	String tableName;
	String code;
	String displayName;
	ArrayList<Attribute> list = new ArrayList<Attribute>();
	Attribute idAttribute;
	Attribute nameAttribute;
	String comment;
	String system;
	//是否生成excel导入导出按钮
	boolean includeExcel =false;
	//是否包含附件信息
	boolean attachment=false;
	//是否生成代码，也同时生成功能点
	boolean autoAddFunction = false;
	boolean autoAddMenu = false;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDisplayName() {
		if(displayName==null) {
			return this.displayName;
		}
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public ArrayList<Attribute> getList() {
		return list;
	}
	public void setList(ArrayList<Attribute> list) {
		this.list = list;
	}
	public Attribute getIdAttribute() {
		return idAttribute;
	}
	public void setIdAttribute(Attribute idAttribute) {
		this.idAttribute = idAttribute;
	}
	public Attribute getNameAttribute() {
		return nameAttribute;
	}
	public void setNameAttribute(Attribute nameAttribute) {
		this.nameAttribute = nameAttribute;
	}
	
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public List<Attribute> getGeneralList(){
		List<Attribute> newList = new ArrayList<Attribute>();
		for(Attribute attr:list) {
			if(attr.getName().equals(this.idAttribute.getName())) {
				continue;
			}
			newList.add(attr);
		}
		return newList;
	}
    public boolean isIncludeExcel() {
        return includeExcel;
    }
    public void setIncludeExcel(boolean includeExcel) {
        this.includeExcel = includeExcel;
    }
    public boolean isAttachment() {
        return attachment;
    }
    public void setAttachment(boolean attachment) {
        this.attachment = attachment;
    }
	public boolean isAutoAddFunction() {
		return autoAddFunction;
	}
	public void setAutoAddFunction(boolean autoAddFunction) {
		this.autoAddFunction = autoAddFunction;
	}
	public boolean isAutoAddMenu() {
		return autoAddMenu;
	}
	public void setAutoAddMenu(boolean autoAddMenu) {
		this.autoAddMenu = autoAddMenu;
	}
	
}
