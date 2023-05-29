package com.jun.plugin.poi.learn.test;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import com.jun.plugin.poi.learn.DefaultCellStyles;

public class RedCellStyles extends DefaultCellStyles {

	/**
	 * @param workbook
	 */
	public RedCellStyles(Workbook workbook) {
		super(workbook);
	}

	@Override
	protected void setBorder(CellStyle style) {
		//设置边框
		Font font = wb.createFont();
		font.setColor(IndexedColors.RED.index);
		
		style.setFont(font);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	}

	
}
