package com.jun.plugin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddressList;


/**
* 类功能说明 
* 类修改者
* 修改日期
* 修改说明
* <p>Title: ExcelUtil.java</p>
* <p>Description:excel导入导出类</p>
* <p>Copyright: Copyright (c) 2006</p>
* @date 2013-6-28 上午9:08:33
* @version V1.0
*/

public class ExcelUtil<T> {
	Class<T> clazz;

	public ExcelUtil(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**  
	* 函数功能说明 
	* Administrator修改者名字
	* 2013-6-28修改日期
	* 修改内容
	* @Title: importExcel 
	* @Description: TODO:excel导入
	* @param @param sheetName
	* @param @param input
	* @param @return    设定文件 
	* @return List<T>    返回类型 
	* @throws 
	*/
	public List<T> importExcel(String sheetName, InputStream input) {
		List<T> list = new ArrayList<T>();
		try {
			Workbook book = Workbook.getWorkbook(input);
			Sheet sheet = null;
			if (!sheetName.trim().equals("")) {
				sheet = book.getSheet(sheetName);// 根据名称获取sheet
			}
			if (sheet == null) {
				sheet = book.getSheet(0);// 根据索引获取sheet
			}
			int rows = sheet.getRows();// 获取行数
			if (rows > 0) {
				Field[] allFields = clazz.getDeclaredFields();//获取类的字段属性
				Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
				for (Field field : allFields) {
					if (field.isAnnotationPresent(ExcelVO.class)) {
						ExcelVO attr = field
								.getAnnotation(ExcelVO.class);
						int col = getExcelCol(attr.column());
						// System.out.println(col + "====" + field.getName());
						field.setAccessible(true);
						fieldsMap.put(col, field);
					}
				}
				for (int i = 1; i < rows; i++) {
					Cell[] cells = sheet.getRow(i);
					T entity = null;
					for (int j = 0; j < cells.length; j++) {
						String c = cells[j].getContents();
						if (c.equals("")) {
							continue;
						}
						entity = (entity == null ? clazz.newInstance() : entity);
						// System.out.println(cells[j].getContents());
						Field field = fieldsMap.get(j);
						Class<?> fieldType = field.getType();
						if ((Integer.TYPE == fieldType)
								|| (Integer.class == fieldType)) {
							field.set(entity, Integer.parseInt(c));
						} else if (String.class == fieldType) {
							field.set(entity, String.valueOf(c));
						} else if ((Long.TYPE == fieldType)
								|| (Long.class == fieldType)) {
							field.set(entity, Long.valueOf(c));
						} else if ((Float.TYPE == fieldType)
								|| (Float.class == fieldType)) {
							field.set(entity, Float.valueOf(c));
						} else if ((Short.TYPE == fieldType)
								|| (Short.class == fieldType)) {
							field.set(entity, Short.valueOf(c));
						} else if ((Double.TYPE == fieldType)
								|| (Double.class == fieldType)) {
							field.set(entity, Double.valueOf(c));
						} else if (Character.TYPE == fieldType) {
							if ((c != null) && (c.length() > 0)) {
								field.set(entity, Character
										.valueOf(c.charAt(0)));
							}
						}

					}
					if (entity != null) {
						list.add(entity);
					}
				}
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**  
	* 函数功能说明 
	* Administrator修改者名字
	* 2013-6-28修改日期
	* 修改内容
	* @Title: exportExcel 
	* @Description: TODO:导出excel
	* @param @param list
	* @param @param sheetName
	* @param @param sheetSize
	* @param @param output
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	public boolean exportExcel(List<T> list, String sheetName, int sheetSize,
			OutputStream output) {

		Field[] allFields = clazz.getDeclaredFields();
		List<Field> fields = new ArrayList<Field>();
		for (Field field : allFields) {
			if (field.isAnnotationPresent(ExcelVO.class)) {
				fields.add(field);
			}
		}

		HSSFWorkbook workbook = new HSSFWorkbook();

		// excel2003最大行数65536
		if (sheetSize > 65536 || sheetSize < 1) {
			sheetSize = 65536;
		}
		double sheetNo = Math.ceil(list.size() / sheetSize);
		for (int index = 0; index <= sheetNo; index++) {
			HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(index, sheetName + index);
			HSSFRow row;
			HSSFCell cell;

			row = sheet.createRow(0);
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				ExcelVO attr = field.getAnnotation(ExcelVO.class);
				int col = getExcelCol(attr.column());
				cell = row.createCell(col);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(attr.name());
				if (!attr.prompt().trim().equals("")) {
					setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);
				}
				if (attr.combo().length > 0) {
					setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);
				}
			}

			int startNo = index * sheetSize;
			int endNo = Math.min(startNo + sheetSize, list.size());
			for (int i = startNo; i < endNo; i++) {
				row = sheet.createRow(i + 1 - startNo);
				T vo = (T) list.get(i); 
				for (int j = 0; j < fields.size(); j++) {
					Field field = fields.get(j);
					field.setAccessible(true);
					ExcelVO attr = field
							.getAnnotation(ExcelVO.class);
					try {
						if (attr.isExport()) {
							cell = row.createCell(getExcelCol(attr.column()));
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(field.get(vo) == null ? ""
									: String.valueOf(field.get(vo)));
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}

		}
		try {
			output.flush();
			workbook.write(output);
			output.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Output is closed ");
			return false;
		}

	}

	/**  
	* 函数功能说明
	* Administrator修改者名字
	* 2013-6-28修改日期
	* 修改内容
	* @Title: getExcelCol 
	* @Description: TODO:获取excel转化到数字 A->1 B->2
	* @param @param col
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public static int getExcelCol(String col) {
		col = col.toUpperCase();
		int count = -1;
		char[] cs = col.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
		}
		return count;
	}

	/**  
	* 函数功能说明
	* Administrator修改者名字
	* 2013-6-28修改日期
	* 修改内容
	* @Title: setHSSFPrompt 
	* @Description: TODO:设置提示信息
	* @param @param sheet
	* @param @param promptTitle
	* @param @param promptContent
	* @param @param firstRow
	* @param @param endRow
	* @param @param firstCol
	* @param @param endCol
	* @param @return    设定文件 
	* @return HSSFSheet    返回类型 
	* @throws 
	*/
	public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,
			String promptContent, int firstRow, int endRow, int firstCol,
			int endCol) {
		DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
		CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow, firstCol, endCol);
		HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);
		data_validation_view.createPromptBox(promptTitle, promptContent);
		sheet.addValidationData(data_validation_view);
		return sheet;
	}

	/**  
	* 函数功能说明 
	* Administrator修改者名字
	* 2013-6-28修改日期
	* 修改内容
	* @Title: setHSSFValidation 
	* @Description: TODO:设置验证行数据
	* @param @param sheet
	* @param @param textlist
	* @param @param firstRow
	* @param @param endRow
	* @param @param firstCol
	* @param @param endCol
	* @param @return    设定文件 
	* @return HSSFSheet    返回类型 
	* @throws 
	*/
	public static HSSFSheet setHSSFValidation(HSSFSheet sheet,
			String[] textlist, int firstRow, int endRow, int firstCol,
			int endCol) {
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
		CellRangeAddressList regions = new CellRangeAddressList(firstRow,endRow, firstCol, endCol);
		HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
		sheet.addValidationData(data_validation_list);
		return sheet;
	}
}
