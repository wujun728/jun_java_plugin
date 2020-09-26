package com.jun.plugin.poi.demo.test;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import com.jun.plugin.poi.demo.DefaultCellStyles;

/** 
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年9月29日 下午3:22:31 
 */
public class RedCellStyles extends DefaultCellStyles {

	/**
	 * @param workbook
	 */
	public RedCellStyles(Workbook workbook) {
		super(workbook);
	}

	/* (non-Javadoc)
	 * @see com.ketayao.learn.poi.DefaultCellStyles#setBorder(org.apache.poi.ss.usermodel.CellStyle)
	 */
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
