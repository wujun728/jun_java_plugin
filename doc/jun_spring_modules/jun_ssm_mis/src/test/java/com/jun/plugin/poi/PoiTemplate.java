package com.jun.plugin.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.jun.plugin.poi.poiTemplate.Tag;
import com.jun.plugin.poi.poiTemplate.TempCell;

/**
 * Excel模板类，功能入口
 * @author Wujun
 *
 */
public class PoiTemplate {
	
	static final String VALUED_DELIM = "${";

	static final String VALUED_DELIM2 = "}";
	
	DynaBean context = null;//上下文对象
	
	public List<TempCell> tempCells = null;//单元格模板
	
	Workbook wb = null;//当前workbook
	
	Sheet sheet = null;//当前sheet
	
	Sheet sourceSheet = null;//原始sheet(在自动增加sheet时，用来获取sheet的属性)
	
	int maxRow = -1;//最大行数
	
	int maxColumn = -1;//最大列数
	
	InputStream in = null;//模板输入流
	
	OutputStream out = null;//导出输出流
	
	boolean is2007 = false;//是否2007版本
	
	public int moveRow = 0;//当前移动行数
	
	public int moveColumn = 0;//当前移动列数
	
	int moreSheet = 0;//当前新增的sheet数

	
	
	public PoiTemplate() {
		super();
	}

	/**
	 * 创建模板对象
	 * @param in 模板输入流
	 * @param out 导出文件输出流
	 */
	public PoiTemplate(InputStream in, OutputStream out) {
		super();
		this.in = in;
		this.out = out;
	}
	
	/**
	 * 创建模板对象
	 * @param temp 模板文件路径
	 * @param out 导出文件输出流
	 * @throws FileNotFoundException
	 */
	public PoiTemplate(String temp, OutputStream out) throws FileNotFoundException {
		super();
		if(temp.indexOf(".xlsx") > 0){
			is2007 = true;
		}
		this.in = new FileInputStream(new File(temp));
		this.out = out;
	}
	
	/**
	 * 创建模板对象
	 * @param temp 模板文件路径
	 * @param out 导出文件路径
	 * @throws FileNotFoundException
	 */
	public PoiTemplate(String temp, String out) throws FileNotFoundException {
		super();
		if(temp.indexOf(".xlsx") > 0){
			is2007 = true;
		}
		this.in = new FileInputStream(new File(temp));
		this.out = new FileOutputStream(new File(out));
		
	}
	
	/**
	 * 创建模板对象
	 * @param in 模板文件对象
	 * @param out 导出输出流
	 * @throws FileNotFoundException
	 */
	public PoiTemplate(File in, OutputStream out) throws FileNotFoundException {
		super();
		if(in.getName().indexOf(".xlsx") > 0){
			is2007 = true;
		}
		this.in = new FileInputStream(in);
		this.out = out;
	}
	
	/**
	 * 创建模板对象
	 * @param in 模板文件对象
	 * @param out 导出文件对象
	 * @throws FileNotFoundException
	 */
	public PoiTemplate(File in, File out) throws FileNotFoundException {
		super();
		if(in.getName().indexOf(".xlsx") > 0){
			is2007 = true;
		}
		this.in = new FileInputStream(in);
		this.out = new FileOutputStream(out);
	}
	

	/**
	 * 读取模板中的属性
	 * @param sheet
	 * @return
	 */
	private List<TempCell> getTempCells(Sheet sheet) {
		List<TempCell> tempCellss = new ArrayList<TempCell>();
		int rowNums = sheet.getLastRowNum();
		maxRow = rowNums;
		for(int i = 0; i <= rowNums; i++){
			Row row = sheet.getRow(i);
			if(row != null){
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()){
					Cell cell = cellIterator.next();
					if(cell.getColumnIndex() > maxColumn) maxColumn = cell.getColumnIndex();
					if(cell != null){
						Object value = ExcelUtil2.getValue(cell);
						TempCell tempCell = new TempCell(cell.getRowIndex(), cell.getColumnIndex(), value, cell.getCellStyle());
						tempCell.cellType = cell.getCellType();
						tempCell.rowHeight = cell.getRow().getHeightInPoints();
						if (value instanceof String) {
							Tag tag = TagUtil.getTag((String) value);
							if(tag != null){
								tempCell.isTag = true;
								tempCell.tag = tag;
							}
						}
						tempCellss.add(tempCell);
					}
				}
				sheet.removeRow(row);
			}
		}
		for(int i = 0; i < tempCellss.size(); i++){
			TempCell tc = tempCellss.get(i);
			if(tc.isTag && tc.tag.hasEnd()){
				tc.endIndex = tc.tag.getEndCell(tempCellss, i, tc.tag.getEndKey());
			}
		}
		return tempCellss;
	}

	/**
	 * 通过模板构建excel工作簿
	 * @return
	 * @throws IOException
	 */
	public Workbook buildWorkbook() throws IOException{
		if(in != null && wb == null){
			if(is2007){
//				wb = new XSSFWorkbook(in);
			}else{
				wb = new HSSFWorkbook(in);
			}
			parseWorkbook(wb);
		}
		return wb;
	}
	
	/**
	 * 向导出流中写数据
	 * @throws IOException
	 */
	public void writeExcel() throws IOException {
		if(wb == null){
			buildWorkbook();
		}
		if(out != null){
			wb.write(out);
			in.close();
			out.close();
		}
	}

	/**
	 * 处理workbook
	 * @param wb 
	 */
	private void parseWorkbook(Workbook wb) {
		int sheetCount = wb.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			sheet = wb.getSheetAt(i);
			tempCells = getTempCells(sheet);
			for (int j = 0; j < tempCells.size(); j++) {
				parseColumn(j);
			}
			sourceSheet = null;
		}
	}
	
	
	/**
	 * 处理单元格
	 * @param index 行号
	 */
	private void parseColumn(int index){
		TempCell cell = tempCells.get(index);
		if(!cell.isHandled){
			if(cell.isTag){
				cell.tag.parseTag(this, index);
			}else{
				setValue(cell);
				cell.isHandled = true;
			}
		}
	}

	/**
	 * 处理字符串值
	 * @param tempStr
	 * @return
	 */
	public Object parseString(String tempStr){
		if(isOnlyEl(tempStr)){
			return getEl(tempStr);
		}
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while(tempStr.indexOf(VALUED_DELIM, i) >= 0){
			int start = tempStr.indexOf(VALUED_DELIM, i);
			int end = tempStr.indexOf(VALUED_DELIM2, i);
			if(start > i){
				sb.append(tempStr.substring(i, start));
			}
			String el = tempStr.substring(start, end + 1);
			sb.append(getEl(el));
			i = end + 1;
		}
		return sb.append(tempStr.substring(i)).toString();
	}
	
	/**
	 * 向单元格设置值
	 * @param tempCell 单元格模板
	 * @param value 单元格值
	 */
	public void setValue(TempCell tempCell){
		int currentRow = moveRow + tempCell.row;
		if(currentRow > 65000){
			if(sourceSheet == null){
				sourceSheet = sheet;
			}
			sheet = wb.createSheet(sourceSheet.getSheetName() + "more" + ++moreSheet);
			moveRow = 0 - tempCell.row;
			for(int i = 0; i < maxColumn; i++){
				sheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
			}
		}
		Object value = tempCell.value;
		if(value instanceof String){
			value = parseString((String) value);
		}
		ExcelUtil2.setValue(sheet, moveRow + tempCell.row, moveColumn + tempCell.column, value, tempCell.style, tempCell.cellType.getCode(), tempCell.rowHeight);
	}
	
	
	/**
	 * 获取el表达式的值
	 * @param temp
	 * @param str
	 * @return
	 */
	public Object getEl(String str) {
		if(str != null){
			int i = str.indexOf(VALUED_DELIM);
			int j = str.indexOf(VALUED_DELIM2);
			if(i == 0 && j > 0){
				String key = str.substring(i + 2, j);
				return getValue(key);
			}
		}
		return null;
	}
	
	/**
	 * 判断是否只有El表达式
	 * @param str
	 * @return
	 */
	public static boolean isOnlyEl(String str){
		if(str != null && !str.equals("")){
			int i = str.indexOf(VALUED_DELIM);
			int j = str.indexOf(VALUED_DELIM2);
			if(i == 0 && j + 1 == str.length()){
				return true;
			}
		}
		return false;
	}
	
	private DynaBean getContext() {
		if (null == context) {
			context = new LazyDynaBean();
		}
		return context;
	}

	/**
	 * 增加上下文属性值
	 * @param key key
	 * @param value value
	 */
	public void addValue(String key, Object value) {
		getContext().set(key, value);
	}
	
	
	/**
	 * 直接将整个Map的键值对放入上下文
	 * @param map
	 */
	public void addMap(Map<String, Object> map){
		if(map != null && !map.isEmpty()){
			for(String key : map.keySet()){
				getContext().set(key, map.get(key));
			}
		}
	}
	
	/**
	 * 获取上下文中的值
	 * @param key key
	 * @return value
	 */
	public Object getValue(String key) {
		return getValue(getContext(), key);
	}

	/**
	 * 获取普通bean中的属性值
	 * @param bean java对象
	 * @param key key(可以使用"."链接符取得深层次的值)
	 * @return
	 */
	public static Object getValue(Object bean, String key) {
		Object value = null;
		try {
			value = PropertyUtils.getProperty(bean, key);
		} catch (Exception e) {
//			e.printStackTrace();
//			value = -1;
		}
		return value;
	}

	/**
	 * 从集合对象中获取遍历对象
	 * @param collection
	 * @return Iterator of the object
	 */
	public Iterator<?> getIterator(Object collection) {
		Iterator<?> iterator = null;
		if(collection != null){
			if (collection.getClass().isArray()) {
				try {
					// If we're lucky, it is an array of objects
					// that we can iterate over with no copying
					iterator = Arrays.asList((Object[]) collection).iterator();
				} catch (ClassCastException e) {
					// Rats -- it is an array of primitives
					int length = Array.getLength(collection);
					List<Object> c = new ArrayList<Object>(length);
					for (int i = 0; i < length; i++) {
						c.add(Array.get(collection, i));
					}
					iterator = c.iterator();
				}
			} else if (collection instanceof Collection) {
				iterator = ((Collection<?>) collection).iterator();
			} else if (collection instanceof Iterator) {
				iterator = (Iterator<?>) collection;
			} else if (collection instanceof Map) {
				iterator = ((Map<?, ?>) collection).entrySet().iterator();
			}
		}
		return iterator;
	}

}
