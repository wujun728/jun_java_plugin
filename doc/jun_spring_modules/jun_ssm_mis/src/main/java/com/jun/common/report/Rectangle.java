package com.jun.common.report;

import java.awt.Color;

/**
 * 报表表格基类
 * @author Wujun
 * 
 */
public class Rectangle {
	/**
	 * 水平居左
	 */
	public static final int ALIGN_LEFT = 0;
	/**
	 * 水平居中
	 */
	public static final int ALIGN_CENTER = 1;
	/**
	 * 水平居右
	 */
	public static final int ALIGN_RIGHT = 2;
	/**
	 * 垂直居顶
	 */
	public static final int VALIGN_TOP = 0;
	/**
	 * 垂直居中
	 */
	public static final int VALIGN_MIDDLE = 1;
	/**
	 * 垂直居底
	 */
	public static final int VALIGN_BOTTOM = 2;
    /**
     * 默认水平居左
     */
	protected int align = ALIGN_LEFT;
	/**
	 * 默认垂直居中
	 */
	protected int valign = VALIGN_MIDDLE;
    /**
     * 表格高度
     */
	protected int height = 0;
    /**
     * 表格边框宽度
     */
	protected int border = 1;
	/**
	 * 表格边框颜色，默认黑色
	 */
	protected Color borderColor = Color.black;
	/**
	 * 表格背景色，默认白色
	 */
	protected Color backgroudColor = Color.white;

	public Rectangle() {
	}

	public int getAlign() {
		return align;
	}

	public Color getBackgroudColor() {
		return backgroudColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public int getValign() {
		return valign;
	}

	public void setValign(int valign) {
		this.valign = valign;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void setBackgroudColor(Color backgroudColor) {
		this.backgroudColor = backgroudColor;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
    
	/**
	 * 将传入的Rectangle对象克隆为当前对象的一个副本
	 * @param rect
	 * @return 当前对象副本
	 * @throws CloneNotSupportedException
	 */
	protected Rectangle clone(Rectangle rect) throws CloneNotSupportedException {
		rect.align = this.align;
		rect.backgroudColor = this.backgroudColor;
		rect.border = this.border;
		rect.borderColor = this.borderColor;
		rect.valign = this.valign;
		return rect;
	}
}
