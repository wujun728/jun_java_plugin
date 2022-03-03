package com.erp.page;

import java.util.Date;

public abstract class BasePage {

	private String ids;
	private int page;// 当前页
	private int rows;// 每页显示记录数
	private String sort;// 排序字段名
	private String order;// 按什么排序(asc,desc)
	private Date ccreatedatetimeStart;
	private Date ccreatedatetimeEnd;
	private Date cmodifydatetimeStart;
	private Date cmodifydatetimeEnd;
	
	public Date getCcreatedatetimeEnd() {
		return ccreatedatetimeEnd;
	}
	public void setCcreatedatetimeEnd(Date ccreatedatetimeEnd) {
		this.ccreatedatetimeEnd = ccreatedatetimeEnd;
	}
	public Date getCcreatedatetimeStart() {
		return ccreatedatetimeStart;
	}
	public void setCcreatedatetimeStart(Date ccreatedatetimeStart) {
		this.ccreatedatetimeStart = ccreatedatetimeStart;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
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
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public Date getCmodifydatetimeStart() {
		return cmodifydatetimeStart;
	}
	public Date getCmodifydatetimeEnd() {
		return cmodifydatetimeEnd;
	}
	public void setCmodifydatetimeStart(Date cmodifydatetimeStart) {
		this.cmodifydatetimeStart = cmodifydatetimeStart;
	}
	public void setCmodifydatetimeEnd(Date cmodifydatetimeEnd) {
		this.cmodifydatetimeEnd = cmodifydatetimeEnd;
	}
}
