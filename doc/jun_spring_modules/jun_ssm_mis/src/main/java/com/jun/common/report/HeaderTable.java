package com.jun.common.report;

/**
 * 表格表头(标题列)
 * 
 * @author Wujun
 * 
 */
public class HeaderTable extends Table {
	public HeaderTable() {
		super();
		this.setType(Report.HEAD_TYPE);
	}

	public HeaderTable(int row, int col) {
		super(row, col);
		this.setType(Report.HEAD_TYPE);
	}
}