package com.jun.common.report.printer;

import com.jun.common.report.Report;

public class HTMLCss {
	public HTMLCss() {}

	private String groupTotal = "";

	private String total = "";

	private String head = "";

	private String data = "";

	private String title = "";

	private String crossHeadHead = "";
	/**
	 * 自定义CSS
	 */
	private String custom = "";

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getData() {
		return data;
	}

	public String getGroupTotal() {
		return groupTotal;
	}

	public String getHead() {
		return head;
	}

	public String getTitle() {
		return title;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public void setGroupTotal(String groupTotal) {
		this.groupTotal = groupTotal;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("<style type=\"text/css\">\r\n");
		buf.append(".").append(Report.GROUP_TOTAL_TYPE).append("{").append(this.groupTotal).append("}\r\n");
		buf.append(".").append(Report.TOTAL_TYPE).append("{").append(this.total).append("}\r\n");
		buf.append(".").append(Report.HEAD_TYPE).append("{").append(this.head).append("}\r\n");
		buf.append(".").append(Report.DATA_TYPE).append("{").append(this.data).append("}\r\n");
		buf.append(".").append(Report.TITLE_TYPE).append("{").append(this.title).append("}\r\n");
		buf.append(".").append(Report.CROSS_HEAD_HEAD_TYPE).append("{").append(this.crossHeadHead).append("}\r\n");
		buf.append(".").append(Report.CUSTOM_TYPE).append("{").append(this.custom).append("}\r\n");
		buf.append("</style>\r\n");
		return buf.toString();
	}

	public String getCrossHeadHead() {
		return crossHeadHead;
	}

	public void setCrossHeadHead(String crossHeadHead) {
		this.crossHeadHead = crossHeadHead;
	}
}