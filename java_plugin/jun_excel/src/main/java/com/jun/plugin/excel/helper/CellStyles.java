package com.jun.plugin.excel.helper;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/** 
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年9月29日 上午11:44:20 
 */
public interface CellStyles {
	
	void setWorkBook(Workbook workbook);
	
	/**
	 * 标题样式
	 * @return
	 */
	CellStyle getHeaderStyle();
	
	/**
	 * String样式
	 * @return
	 */
	CellStyle getStringStyle();
	
	/**
	 * 日期样式
	 * @return
	 */
	CellStyle getDateStyle();
	
	/**
	 * 数字样式
	 * @return
	 */
	CellStyle getNumberStyle();

	/**
	 * 合计列样式
	 * @return
	 */
	CellStyle getFormulaStyle();
}
