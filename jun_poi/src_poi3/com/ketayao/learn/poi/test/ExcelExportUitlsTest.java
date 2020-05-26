package com.ketayao.learn.poi.test;

import org.junit.Test;

import com.ketayao.learn.poi.ExcelExportHelper;
import com.ketayao.learn.poi.ExcelExportHelper.ExcelType;


/** 
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since   2013年9月29日 下午3:05:33 
 */
public class ExcelExportUitlsTest {

	@Test
	public void test2003() {
		ExcelExportHelper excelExportHelper = new ExcelExportHelper();
		
		excelExportHelper.createRow();
		excelExportHelper.setCellHeader(0, "仅仅是一个测试");
		
		excelExportHelper.createRow();
		excelExportHelper.setCell(0, "嘿嘿测试成功");
		
		excelExportHelper.export("d:/test2003.xlsx");
	}
	
	@Test
	public void test2007() {
		ExcelExportHelper excelExportHelper = new ExcelExportHelper(ExcelType.XLSX);
		
		excelExportHelper.createRow();
		excelExportHelper.setCellHeader(0, "仅仅是一个测试");
		
		excelExportHelper.createRow();
		excelExportHelper.setCell(0, "嘿嘿测试成功2007");
		
		excelExportHelper.export("d:/test2007.xlsx");
	}
	
	@Test
	public void test2007_2() {
		ExcelExportHelper excelExportHelper = new ExcelExportHelper("d:/test2007_2.xlsx");
		
		excelExportHelper.createRow();
		excelExportHelper.setCellHeader(0, "仅仅是一个测试");
		
		excelExportHelper.createRow();
		excelExportHelper.setCell(0, "嘿嘿测试成功2007_2");
		
		excelExportHelper.export("d:/test2007_2_1.xlsx");
	}
	
	@Test
	public void testCellStyles() {
		ExcelExportHelper excelExportHelper = new ExcelExportHelper();
		
		excelExportHelper.setCellStyles(new RedCellStyles(excelExportHelper.getWorkbook()));
		
		excelExportHelper.createRow();
		excelExportHelper.setCellHeader(0, "仅仅是一个测试");
		
		excelExportHelper.createRow();
		excelExportHelper.setCell(0, "testCellStyles");
		
		excelExportHelper.export("d:/testCellStyles.xlsx");
	}

}
