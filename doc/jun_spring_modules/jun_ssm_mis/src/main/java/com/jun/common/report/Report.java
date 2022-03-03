package com.jun.common.report;

/**
 * 报表类
 * 
 * @author Wujun
 * 
 */
public class Report {
	public static final String DATA_TYPE = "MyReport_data";

	public static final String GROUP_TOTAL_TYPE = "MyReport_groupTotal";

	public static final String TOTAL_TYPE = "MyReport_total";

	public static final String HEAD_TYPE = "MyReport_head"; // 表头

	public static final String TITLE_TYPE = "MyReport_title";

	public static final String NONE_TYPE = "NONE";

	public static final String BLANK_TYPE = "";

	public static final String CROSS_HEAD_HEAD_TYPE = "MyReport_cross_head_head";

	public static final String CUSTOM_TYPE = "Custom_type"; // 自定义类型
    /**
     * 表格
     */
	private Table headerTable;
    /**
     * 报表主体部分
     */
	private ReportBody body;
    /**
     * 页脚
     */
	private Table footerTable;

	public Report() {}

	public Table getHeaderTable() {
		return headerTable;
	}

	public void setHeaderTable(Table headerTable) {
		this.headerTable = headerTable;
	}

	public ReportBody getBody() {
		return body;
	}

	public void setBody(ReportBody body) {
		this.body = body;
	}

	public Table getFooterTable() {
		return footerTable;
	}

	public void setFooterTable(Table footerTable) {
		this.footerTable = footerTable;
	}
}
