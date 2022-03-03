package com.jun.common.report.printer;

import java.awt.Color;
import java.awt.Font;

/**
 * 报表样式项
 * @author Wujun
 *
 */
public class PDFCssItem {
	public PDFCssItem() {}

	/**
	 * 字体
	 */
	private Font font;
	/**
	 * 背景颜色
	 */
	private Color backgroudColor = Color.WHITE;

	/**
	 * 获取字体
	 * 
	 * @return
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * 获取背景颜色
	 * 
	 * @return
	 */
	public Color getBackgroudColor() {
		return backgroudColor;
	}

	/**
	 * 设置字体
	 * 
	 * @param font
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * 设置背景颜色
	 * 
	 * @param backgroudColor
	 */
	public void setBackgroudColor(Color backgroudColor) {
		this.backgroudColor = backgroudColor;
	}
}