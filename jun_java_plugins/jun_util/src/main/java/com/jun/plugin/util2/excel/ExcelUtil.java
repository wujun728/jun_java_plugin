package com.jun.plugin.util2.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.jun.plugin.util2.core.StringUtil;
/**
 * Excel工具类
 * 用于简单读写excel
 * jar依赖：1、poi-3.9-20121203.jar
 *        2、还依赖于
 *            poi-ooxml-3.9-20121203.jar
 *            ooxml依赖dom4j-1.6.1.jar
 *            stax-api-1.0.1.jar
 *            xmlbeans-2.3.0.jar
 * 			  poi-ooxml-schemas-3.9-20121203.jar
 * 
 * @author 罗季晖
 * @email  bigtiger02@gmail.com
 * @date   2013年8月21日
 */
public class ExcelUtil {
	
	/**
	 * Excel读取
	 * @param file        excel文件路径
	 * @param sheetName   sheet名称
	 * @return
	 */
	public static String[][] importExcel(String file,String sheetName){
		List<String[]> datas = new ArrayList<String[]>();
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheet(sheetName);
			if(sheet != null){
				for (Row row : sheet) {
					List<String> tmp = new ArrayList<String>();
					 for (Cell cell : row) {
						 tmp.add(getCellValue(cell));
					 }
					 datas.add(tmp.toArray(new String[tmp.size()]));
				}
			}
		} catch (Exception e) {
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return datas.toArray(new String[datas.size()][]);
	}
	
	//获取excel单元格的值
	private static String getCellValue(Cell cell){
		 //如Cell.CELL_TYPE_STRING
		 String value = null;
	     switch(cell.getCellType()){
		      case BOOLEAN:
		    	  value = StringUtil.toSafeString(cell.getBooleanCellValue());
		    	  break;
		      case NUMERIC:
			       //先看是否是日期格式
			       if(DateUtil.isCellDateFormatted(cell)){
			    	   value = StringUtil.toSafeString(cell.getDateCellValue());
			       }else{
			    	   value = StringUtil.toSafeString(cell.getNumericCellValue());
			       }
			       break;
		      case FORMULA:
		    	  value = StringUtil.toSafeString(cell.getCellFormula());
		    	  break;
		      case STRING:
		    	  value = StringUtil.toSafeString(cell.getRichStringCellValue());
		    	  break;
		 }
	     return value;
	}
}
