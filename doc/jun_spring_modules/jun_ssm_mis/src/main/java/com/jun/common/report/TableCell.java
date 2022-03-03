package com.jun.common.report;

import com.itextpdf.text.Font;

public class TableCell extends Rectangle {
	/**
	 * 行跨度
	 */
	private int rowSpan = 1;
	/**
	 * 列跨度
	 */
	private int colSpan = 1;
	/**
	 * 单元格内容
	 */
	private Object content = "";
	/**
	 * 是否隐藏不显示
	 */
	private boolean isHidden = false;
	/**
	 * 类样式
	 */
	private String cssClass = Report.NONE_TYPE;
	/**
	 * 是否环绕
	 */
	private boolean noWrap = true;
	/**
	 * 单元格内嵌样式
	 */
	private String cssStyle = "";
	/**
	 * 宽度
	 */
	private int width = -1;
	/**
	 * 父宽度
	 */
	private int parentWidth = -1;
	private int orderNum = -1;
	private float leading = 0.0f;
	/**
	 * 设置小字体，用于数据太长
	 */
	private Font font;

	public boolean isNoWrap() {
		return noWrap;
	}

	public void setNoWrap(boolean noWrap) {
		this.noWrap = noWrap;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public float getLeading() {
		return leading;
	}

	public void setLeading(float leading) {
		this.leading = leading;
	}

	public int getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public int getColSpan() {
		return colSpan;
	}

	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public int getParentWidth() {
		return parentWidth;
	}

	public void setParentWidth(int parentWidth) {
		this.parentWidth = parentWidth;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
	
	public TableCell() {
		this.setAlign(Rectangle.ALIGN_CENTER);
	}

	public TableCell(String str) {
		super();
		if (null == str){
			str = "";
		}
		this.setAlign(Rectangle.ALIGN_CENTER);
		this.content = str;
	}

	public TableCell(Number num) {
		super();
		if (null == num){
			num = 0;
		}
		this.setAlign(Rectangle.ALIGN_CENTER);
		this.content = num;
	}
    
	/**
	 * 克隆当前对象并返回其副本
	 */
	public TableCell clone() throws CloneNotSupportedException {
		TableCell rt = new TableCell();
		rt = (TableCell) clone(rt);
		rt.content = this.content;
		rt.isHidden = this.isHidden;
		rt.colSpan = this.colSpan;
		rt.rowSpan = this.rowSpan;
		rt.cssClass = this.cssClass;
		rt.noWrap = this.noWrap;
		rt.cssStyle = this.cssStyle;
		rt.width = this.width;
		rt.parentWidth = this.parentWidth;
		rt.orderNum = this.orderNum;
		rt.leading = this.leading;
		rt.font = this.font;
		return rt;
	}
    
	/**
	 * 返回传入TableCell对象的副本
	 * @param cell 待克隆源对象
	 * @throws CloneNotSupportedException
	 */
	public void reverse_clone(TableCell cell) throws CloneNotSupportedException {
		this.content = cell.content;
		this.isHidden = cell.isHidden;
		this.colSpan = cell.colSpan;
		this.rowSpan = cell.rowSpan;
		this.cssClass = cell.cssClass;
		this.noWrap = cell.noWrap;
		this.cssStyle = cell.cssStyle;
		this.width = cell.width;
		this.parentWidth = cell.parentWidth;
		this.orderNum = cell.orderNum;
		this.leading = cell.leading;
		this.font = cell.font;
	}
}