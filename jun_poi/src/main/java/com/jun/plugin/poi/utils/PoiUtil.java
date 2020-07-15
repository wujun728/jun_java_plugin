package com.jun.plugin.poi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * poi处理工具类
 * @author Administrator
 *
 */
@SuppressWarnings({ "unchecked", "resource", "rawtypes"})
public class PoiUtil {
	
	private static Map<String, Short> styleMap = new HashMap<String, Short>();
	
	/**
	 * 读取excel文档，返回二维的list
	 * @param is excel文档输入流
	 * @param fileName 文件名
	 * @return excel中的表格数据
	 */
	public static List readExcel(InputStream is, String fileName){
		Workbook workbook = null;
		List res = new ArrayList();
		try{
			if(fileName.indexOf(".xlsx") >= 0){
//				workbook = new XSSFWorkbook(is);
			}else{
				workbook = new HSSFWorkbook(is);
			}
			Sheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getPhysicalNumberOfRows();
			for(int i = 0;i < rowCount; i++){
				Row row = sheet.getRow(i);
				List rowValue = new ArrayList();
				if(row != null){
					int columnCount = row.getPhysicalNumberOfCells();
					for(int j = 0; j < columnCount; j++){
						Cell cell = row.getCell(j);
						Object value = getValue(cell, null);
						rowValue.add(value);
					}
				}
				res.add(rowValue);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 读取excel文档，并通过反射直接赋值到实体类
	 * @param is excel文档输入流
	 * @param fileName 文件明，用以判断是否为2007版EXCEL文档
	 * @param clazz 输出List中的实体类
	 * @return 实体列表
	 */
	
	public static <T> List<T> readExcel(InputStream is, String fileName, Class<T> clazz){
		List<T> result = new ArrayList<T>();
		Map<Integer, Method> titleIndex = new LinkedHashMap<Integer, Method>();
		try{
			//获取标题与实体属性明的映射关系
			Map<String, String> titleMap = (Map<String, String>) clazz.getMethod("getTitleMap").invoke(clazz);
			Workbook workbook = null;
			//判断是否为2007版excel，并创建相应的workbook
			if(fileName.indexOf(".xlsx") >= 0){
//				workbook = new XSSFWorkbook(is);
			}else{
				workbook = new HSSFWorkbook(is);
			}
			
			//通过excel中的标题列获取excel中列与反射的实体类属性set方法的映射
			Sheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getPhysicalNumberOfRows();
			Row titles = sheet.getRow(0);
			for(int i = 0; i < titles.getPhysicalNumberOfCells(); i++){
				Cell cell = titles.getCell(i);
				String title = cell.getStringCellValue().trim();
				String titleCode = titleMap.get(title);
				if(titleCode != null){
					Field field = clazz.getDeclaredField(titleCode);
					Method setMethod = clazz.getMethod("set" + firstCharToUpperCase(titleCode), field.getType());
					titleIndex.put(i, setMethod);
				}
			}
			//获取数据列，并创建实体类，对实体类属性进行赋值
			for(int i = 1; i < rowCount; i++){
				T o = clazz.newInstance();
				for(Integer index : titleIndex.keySet()){
					Method m = titleIndex.get(index);
					Class pType = m.getParameterTypes()[0];
					Object value = getValue(sheet, i ,index, pType);
					titleIndex.get(index).invoke(o, value);
				}
				result.add(o);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 到出数据到excel
	 * @param data 待导出的数据
	 * @param os 导出的excel输出流
	 * @param titleMap 导出的数据的标题与属性名映射
	 */
	public static void  writeExcel(List data, OutputStream os, Map<String, String> titleMap){
		styleMap.clear();
		try {
			// 创建Excel的工作书册 Workbook,对应到一个excel文档  
		    Workbook wb = new HSSFWorkbook();
		    Sheet sheet = null;
		    Set<String> titles = titleMap.keySet();
		    List<Method> methods = new ArrayList<Method>();
		    Object o = data.get(0);
		    Class<?> clazz = o.getClass();
		    for(String title : titles){
				Method getMethod = clazz.getMethod("get" + firstCharToUpperCase(titleMap.get(title)));
				methods.add(getMethod);
			}
		    for(int i = 0,j = 0,k = 0;i < data.size();i++, j++, k = 0){
		    	if(i == 0 || i%65000 == 0){
		    		sheet = wb.createSheet("sheet" + ((int)i/65000 + 1));
		    		for(String title : titles){
		    			setValue(sheet, 0, k++, title);
		    		}
		    		k = 0;
		    		j = 1;
		    	}
		    	o = data.get(i);
				for(Method method : methods){
					Object value = method.invoke(o);
					setValue(sheet, j, k++, value);
				}
		    	
		    }
			wb.write(os);
		    os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 通过模板导出excel
	 * @param data 待导出的数据
	 * @param temp 模板文件
	 * @param os 导出的excel输出流
	 * @param startRow 数据开始写入的行
	 * @param startColumn 数据开始写入的列
	 */
	public static void writeExcel(List data, File temp, OutputStream os, int startRow, int startColumn){
		styleMap.clear();
		try{
			Workbook wb = null;
			String filename = temp.getName();
			boolean is2007 = filename.indexOf(".xlsx") >= 0;
			if(is2007){
//				wb = new XSSFWorkbook(new FileInputStream(temp));
			}else{
				wb = new HSSFWorkbook(new FileInputStream(temp));
			}
		    Sheet sheet = wb.getSheetAt(0);
		    Row titleRow = sheet.getRow(0);
		    Row tempRow = sheet.getRow(startRow);
			Map<String, CellStyle> formats = new LinkedHashMap<String, CellStyle>();
			int c = startColumn;
			for(Cell tempCell : tempRow){
				String attr = tempCell.getStringCellValue();
				CellStyle format = tempCell.getCellStyle();
				formats.put(attr, format);
			}
		    for(int i = 0,j = startRow;i < data.size();i++, j++ ){
		    	if(i != 0 && i%65000 == 0){
		    		sheet = wb.createSheet("sheet" + ((int)i/65000 + 1));
		    		for(int k = 0; k < titleRow.getPhysicalNumberOfCells(); k ++){
		    			setValue(sheet, 0, k, titleRow.getCell(k).getStringCellValue(), titleRow.getCell(k).getCellStyle(), -1);
		    		}
		    		j = startRow;
		    	}
		    	Object o = data.get(i);
	    		Class<?> clazz = o.getClass();
				for(String key : formats.keySet()){
					Method getMethod = clazz.getMethod("get" + firstCharToUpperCase(key));
					Object value = getMethod.invoke(o);
					setValue(sheet, j, c++, value, formats.get(key), -1);
				}
				c = startColumn;
		    }
			wb.write(os);
		    os.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 获取指定单元格指定类型的值
	 * @param cell 单元格对象
	 * @param type 需要的返回值类型
	 * @return 单元格值
	 */
	public static Object getValue(Cell cell, Class<?> type){
		Object value = getValue(cell);
		if(type == null) return value;
		if(type.equals(String.class)){
			return String.valueOf(value);
		}else if(type.equals(Date.class)){
			if(value instanceof Date){
				return value;
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				try {
					return sdf.parse((String)value);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else if(type.equals(Byte.class)){
			if(value instanceof String){
				return Byte.parseByte((String)value);
			}else{
				return ((Double)value).byteValue();
			}
		}else if(type.equals(Integer.class)){
			if(value instanceof String){
				return Integer.parseInt((String)value);
			}else{
				return ((Double)value).intValue();
			}
		}else if(type.equals(Double.class)){
			if(value instanceof String){
				return Double.parseDouble((String)value);
			}else{
				return value;
			}
		}
		return value;
	}
	
	/**
	 * 获取单元格的值
	 * @param cell 单元格对象
	 * @return 单元格值
	 */
	public static Object getValue(Cell cell){
		
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return null;
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_ERROR:
			return cell.getErrorCellValue();
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC:
			if(DateUtil.isCellDateFormatted(cell)){
				return cell.getDateCellValue();
			}else{
				return cell.getNumericCellValue();
			}
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			return cell.getStringCellValue();
		}
	}
	
	public static void removeCell(Sheet sheet, int r, int c){
		Row row = getRow(sheet, r);
		Cell cell = getCell(row, c);
		row.removeCell(cell);
	}
	
	/**
	 * 设置单元格的值
	 * @param sheet 工作表
	 * @param r 行
	 * @param c 列
	 * @param value 值
	 * @param style 单元格样式
	 */
	public static void setValue(Sheet sheet, int r, int c, Object value, CellStyle style, int cellType, float rowHeight){
		Cell cell = getCell(getRow(sheet, r), c);
		if(rowHeight != 0){
			cell.getRow().setHeightInPoints(rowHeight);
		}
		if(style != null){
			cell.setCellStyle(style);
		}
		if(value == null){
			return;
		}else if(value instanceof Number){
			cell.setCellValue(((Number)value).doubleValue());
			if(style == null){
				cell.setCellStyle(getFormat(sheet.getWorkbook(), "#,##0.00"));
			}
		}else if(value instanceof Date){
			cell.setCellValue((Date)value);
			if(style == null) cell.setCellStyle(getFormat(sheet.getWorkbook(), "yyyy年MM月dd日"));
		}else if(value instanceof String && cellType == Cell.CELL_TYPE_FORMULA){
			cell.setCellFormula((String) value);
		}else{
			cell.setCellValue(String.valueOf(value));
		}
	}
	
	public static void setValue(Sheet sheet, int r, int c, Object value, CellStyle style,int cellType){
		setValue(sheet, r, c, value, null, cellType, 0);
	}
	
	/**
	 * 设置单元格的值
	 * @param sheet 工作表
	 * @param r 行
	 * @param c 列
	 * @param value 值
	 */
	public static void setValue(Sheet sheet, int r, int c, Object value){
		setValue(sheet, r, c, value, null, -1);
	}
	
	
	/**
	 * 获取excel行
	 * @param rowCounter 行序号
	 * @param sheet 工作表
	 * @return 行对象
	 */
	public static Row getRow(Sheet sheet, int rowCounter) {
		Row row = sheet.getRow(rowCounter);
		if (row == null) {
			row = sheet.createRow(rowCounter);
		}
		return row;
	}
	
	/**
	 * 获取单元格
	 * @param row 行对象
	 * @param column 纵序号
	 * @return 单元格对象
	 */
	public static Cell getCell(Row row, int column) {
		Cell cell = row.getCell(column);
		if (cell == null) {
			cell = row.createCell(column);
		}
		return cell;
	}
	
	public static Cell getCell(Sheet sheet, int r, int c){
		return getCell(getRow(sheet, r), c);
	}
	
	/**
	 * 获取单元格值
	 * @param sheet 工作表对象
	 * @param row 行序号
	 * @param column 纵序号
	 * @param type 值类型
	 * @return 单元格的值
	 */
	public static Object getValue(Sheet sheet, int row, int column, Class<?> type){
		Cell cell = getCell(getRow(sheet, row), column);
		return getValue(cell, type);
	}
	
	public static CellStyle getFormat(Workbook wb, String p){
		Short index = styleMap.get(p);
		CellStyle style = null;
		if(index == null){
			style = wb.createCellStyle();
			DataFormat df = wb.createDataFormat();
			style.setDataFormat(df.getFormat(p));
			style.setWrapText(true);
			styleMap.put(p, style.getIndex());
		}else{
			style = wb.getCellStyleAt(index);
		}
		return style;
	}
	
	
	/**
	 * poi样式示例
	 */
	public void writeExcel(){
		// 创建Excel的工作书册 Workbook,对应到一个excel文档  
	    HSSFWorkbook wb = new HSSFWorkbook();  
	  
	    // 创建Excel的工作sheet,对应到一个excel文档的tab  
	    HSSFSheet sheet = wb.createSheet("sheet1");  
	  
	    // 设置excel每列宽度  
	    sheet.setColumnWidth(0, 4000);  
	    sheet.setColumnWidth(1, 3500);  
	  
	    // 创建字体样式  
	    HSSFFont font = wb.createFont();  
	    font.setFontName("Verdana");  
	    font.setBoldweight((short) 100);  
	    font.setFontHeight((short) 300);  
	    font.setColor(HSSFColor.BLUE.index);  
	  
	    // 创建单元格样式  
	    HSSFCellStyle style = wb.createCellStyle();  
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	    style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
	  
	    // 设置边框  
	    style.setBottomBorderColor(HSSFColor.RED.index);  
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
	  
	    style.setFont(font);// 设置字体  
	  
	    // 创建Excel的sheet的一行  
	    HSSFRow row = sheet.createRow(0);  
	    row.setHeight((short) 500);// 设定行的高度  
	    // 创建一个Excel的单元格  
	    HSSFCell cell = row.createCell(0);  
	  
	    // 合并单元格(startRow，endRow，startColumn，endColumn)  
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));  
	  
	    // 给Excel的单元格设置样式和赋值  
	    cell.setCellStyle(style);  
	    cell.setCellValue("hello world");  
	  
	    // 设置单元格内容格式  
	    HSSFCellStyle style1 = wb.createCellStyle();  
	    style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("h:mm:ss"));  
	  
	    style1.setWrapText(true);// 自动换行  
	  
	    row = sheet.createRow(1);  
	  
	    // 设置单元格的样式格式  
	  
	    cell = row.createCell(0);  
	    cell.setCellStyle(style1);  
	    cell.setCellValue(new Date());  
	  
	    // 创建超链接  
	    HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);  
	    link.setAddress("http://www.baidu.com");  
	    cell = row.createCell(1);  
	    cell.setCellValue("百度");  
	    cell.setHyperlink(link);// 设定单元格的链接  
	  
	    FileOutputStream os;
		try {
			os = new FileOutputStream("D:\\workbook.xls");
			wb.write(os);  
		    os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	    
	}
	
	public static void main(String[] args){
		new PoiUtil().writeExcel();
	}
	/**
	 * 首字母变小写
	 */
	public static String firstCharToLowerCase(String str) {
		Character firstChar = str.charAt(0);
		String tail = str.substring(1);
		str = Character.toLowerCase(firstChar) + tail;
		return str;
	}
	
	/**
	 * 首字母变大写
	 */
	public static String firstCharToUpperCase(String str) {
		Character firstChar = str.charAt(0);
		String tail = str.substring(1);
		str = Character.toUpperCase(firstChar) + tail;
		return str;
	}

}
