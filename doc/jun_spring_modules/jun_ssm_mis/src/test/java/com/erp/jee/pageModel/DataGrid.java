package com.erp.jee.pageModel;

import java.util.List;

/**
 * easyui的datagrid模型
 * 
 * @author Wujun
 * 
 */
public class DataGrid implements java.io.Serializable {

	private Long total;// 总记录数
	private List rows;// 每行记录
	private List footer;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public List getFooter() {
		return footer;
	}

	public void setFooter(List footer) {
		this.footer = footer;
	}

}
