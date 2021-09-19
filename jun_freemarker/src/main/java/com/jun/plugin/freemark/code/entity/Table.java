package com.jun.plugin.freemark.code.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * 实体类
 * @author shichenyang89@gmail.com
 *
 */
public class Table {
	//表名称
	private String tableName;
	//表含义
	private String tableRemarks;
	//列集合
	private List<Columns> list=new ArrayList<Columns>();
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableRemarks() {
		return tableRemarks;
	}
	public void setTableRemarks(String tableRemarks) {
		this.tableRemarks = tableRemarks;
	}
	public List<Columns> getList() {
		return list;
	}
	public void setList(List<Columns> list) {
		this.list = list;
	}
	
	

}
