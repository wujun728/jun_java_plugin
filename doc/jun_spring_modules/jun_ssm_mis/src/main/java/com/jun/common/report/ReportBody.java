package com.jun.common.report;

import java.util.Vector;

/**
 * 报表主体类
 * @author Wujun
 *
 */
public class ReportBody {
	public ReportBody() {}

	/**
	 * 表的列头
	 */
	private HeaderTable tableColHeader;

	/**
	 * 数据表
	 */
	private Object[] data = null;
	
	public HeaderTable getTableColHeader() {
		return tableColHeader;
	}

	public void setTableColHeader(HeaderTable tableColHeader) {
		this.tableColHeader = tableColHeader;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

	public Table getData() {
		return (Table) data[0];
	}

	public void setData(Table data) {
		this.data = new Table[1];
		this.data[0] = data;
	}
     
	public void setDatas(Vector vec) {
		if (vec.size() > 0) {
			this.data = new Object[vec.size()];
			for (int i = 0; i < vec.size(); i++) {
				this.data[i] = vec.get(i);
			}
		}
	}
	public Object[] getDatas() {
		return this.data;
	}
}