package org.springrain.frame.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtils {
	
	private ExcelUtils(){
		throw new IllegalAccessError("工具类不能实例化");
	}
	
	
	private static final  Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
	public static Cell[] getExcleRow(File excelFile, int rowIndex) throws Exception {
		Workbook workbook = null;
		
		Cell[] row = null;
		try {
			workbook = Workbook.getWorkbook(excelFile);
			row = workbook.getSheet(0).getRow(rowIndex);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return row;

	}
	
	
	public static List<Cell[]> getExcle(File excelFile) throws Exception {
		Workbook workbook = null;
		int count=0;
		List<Cell[]> list=new ArrayList<>();
		try {
			workbook = Workbook.getWorkbook(excelFile);
			Sheet sheet = workbook.getSheet(0);
			count = sheet.getRows();
			for(int i=0;i<count;i++){
				Cell[] row = sheet.getRow(i);
				list.add(row);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return list;

	}
	

}
