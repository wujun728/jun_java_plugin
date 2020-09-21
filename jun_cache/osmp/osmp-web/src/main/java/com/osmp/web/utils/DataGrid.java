package com.osmp.web.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangkaiping
 * @version V1.0, 2013-4-24 上午10:47:17
 */
public class DataGrid {

	/**
	 * 当前页
	 */
	private int page = 1;

	/**
	 * 每页显示记录数
	 */
	private int rows = 10;

	/**
	 * 排序字段名
	 */
	private String sort;

	/**
	 * 排序方式 desc asc
	 */
	private String order;

	/**
	 * 字段
	 */
	private String field;// 字段

	/**
	 * 结果集
	 */
	private List<?> result;

	/**
	 * 总记录数
	 */
	private int total;// 总记录数

	/**
	 * 前台界面值传递
	 */
	private Map<String, Object> map = new HashMap<String, Object>();

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
		this.map.put("rows", result);
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		this.map.put("total", total);
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
