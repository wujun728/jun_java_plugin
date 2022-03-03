package com.jun.common.report.printer;

/**
 * 报表样式
 * 
 * @author Wujun
 * 
 */
public class PDFCss {
	/**
	 * 分组汇总部分样式
	 */
	private PDFCssItem groupTotal;
	/**
	 * 总汇总部分样式
	 */
	private PDFCssItem total;
	/**
	 * 报表主体表头部分样式
	 */
	private PDFCssItem head;
	/**
	 * 数据部分样式
	 */
	private PDFCssItem data;
	/**
	 * 报表标题部分样式
	 */
	private PDFCssItem title;
	/**
	 * 交叉表左上角表头部分样式
	 */
	private PDFCssItem crossHeadHead;

	public PDFCss() {
	}

	/**
	 * 获得总汇总部分样式
	 * 
	 * @return
	 */
	public PDFCssItem getTotal() {
		return total;
	}

	/**
	 * 获得报表标题部分样式
	 * 
	 * @return
	 */
	public PDFCssItem getTitle() {
		return title;
	}

	/**
	 * 获得报表主体表头部分样式
	 * 
	 * @return
	 */
	public PDFCssItem getHead() {
		return head;
	}

	/**
	 * 获得分组汇总部分样式
	 * 
	 * @return
	 */

	public PDFCssItem getGroupTotal() {
		return groupTotal;
	}

	/**
	 * 获得数据部分样式
	 * 
	 * @return
	 */
	public PDFCssItem getData() {
		return data;
	}

	/**
	 * 设置总汇总部分样式
	 * 
	 * @param total
	 */
	public void setTotal(PDFCssItem total) {
		this.total = total;
	}

	/**
	 * 设置报表标题部分样式
	 * 
	 * @param title
	 */
	public void setTitle(PDFCssItem title) {
		this.title = title;
	}

	/**
	 * 设置报表主体表头部分样式
	 * 
	 * @param head
	 */
	public void setHead(PDFCssItem head) {
		this.head = head;
	}

	/**
	 * 设置分组汇总部分样式
	 * 
	 * @param groupTotal
	 */
	public void setGroupTotal(PDFCssItem groupTotal) {
		this.groupTotal = groupTotal;
	}

	/**
	 * 设置数据部分样式
	 * 
	 * @param data
	 */
	public void setData(PDFCssItem data) {
		this.data = data;
	}

	/**
	 * 获得交叉表左上角表头部分样式
	 * 
	 * @return
	 */
	public PDFCssItem getCrossHeadHead() {
		return crossHeadHead;
	}

	/**
	 * 设置交叉表左上角表头部分样式
	 * 
	 * @param crossHeadHead
	 */
	public void setCrossHeadHead(PDFCssItem crossHeadHead) {
		this.crossHeadHead = crossHeadHead;
	}
}