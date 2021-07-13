package com.kvn.poi.exp.domain;

import java.util.Map;

public class MutiRowModel {
	private Integer begin; // 开始行（相对于模板）
	private Integer end; // 结束行（相对于模板）
	private String listKey; // 循环标签取数据的key
//	第一个Integer：行号    第二个Integer：单元列号   Object：单元格里面的值
	private Map<Integer,Map<Integer,Object>> cellMap; // 存放需要循环的行里面的模板值
	
	public Integer getBegin() {
		return begin;
	}
	public void setBegin(Integer begin) {
		this.begin = begin;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	public String getListKey() {
		return listKey;
	}
	public void setListKey(String listKey) {
		this.listKey = listKey;
	}
	public Map<Integer, Map<Integer, Object>> getCellMap() {
		return cellMap;
	}
	public void setCellMap(Map<Integer, Map<Integer, Object>> cellMap) {
		this.cellMap = cellMap;
	}
	
}
