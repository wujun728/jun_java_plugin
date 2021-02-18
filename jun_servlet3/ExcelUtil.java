package com.dcf.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dcf.util.bean.ExcelHead;
import com.eos.system.utility.StringUtil;

import commonj.sdo.DataObject;

/**
 * @author chenjie
 * 
 * <h1>Excel工具类</h1><br>
 * <p>提供office Excel文件内容读取功能</p>
 */
public class ExcelUtil {

	private static final String FILE_END_XLS = ".xls";
	private static final String FILE_END_XLSx = ".xlsx";

	/**
	 * 读取2003版本的excel内容，
	 * 
	 * @author chenjie
	 * @param filePath
	 *            文件路径
	 * @param startRow
	 *            开始读取行
	 * @return 以List<Map<String,
	 *         Object>>方式返回，每个map中包含一行数据，从前往后依次读取的列为cell1,cell2,cell3...celln
	 * @throws FileNotFoundException 文件没有找到
	 * @throws IOException IO读写异常
	 */
	@SuppressWarnings("deprecation")
	public static List<Map<String, Object>> readExcelFor2003(String filePath,
			int startRow) throws FileNotFoundException, IOException {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		FileInputStream in = new FileInputStream(filePath);
		HSSFWorkbook wb = new HSSFWorkbook(in);
		for (int sheetCount = 0; sheetCount < wb.getNumberOfSheets(); sheetCount++) {
			HSSFSheet sheet = wb.getSheetAt(sheetCount);
			if (startRow > 0) {
				startRow = startRow - 1;
			}
			int numberRows = sheet.getPhysicalNumberOfRows();
			for (int rowCount = startRow; rowCount < numberRows; rowCount++) {
				HSSFRow sourceRow = sheet.getRow(rowCount);
				if (sourceRow == null) {
					numberRows += 1;
					continue;
				}
				Map<String, Object> values = new HashMap<String, Object>();
				// 以下构造导入的实体对象，并根据Excel单元格的内容填充实体属性值
				for (int cellCount = 0; cellCount < sourceRow.getLastCellNum(); cellCount++) {
					HSSFCell cell = sourceRow.getCell((short) cellCount);
					String value = "";
					if (cell == null) {
						continue;
					}

					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
						
					case HSSFCell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							Date date = cell.getDateCellValue();
							if (date != null) {
								value = new SimpleDateFormat("yyyy-MM-dd").format(date);
							} else {
								value = "";
							}
						} else {
							value = new DecimalFormat("0").format(cell.getNumericCellValue());
						}
						break;

					case HSSFCell.CELL_TYPE_FORMULA:
						// 导入时如果为公式生成的数据则无值
						if (!cell.getStringCellValue().equals("")) {
							value = cell.getStringCellValue();
						} else {
							value = cell.getNumericCellValue() + "";
						}
						break;

					case HSSFCell.CELL_TYPE_BLANK:
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						value = "";
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						value = (cell.getBooleanCellValue() == true ? "Y" : "N");
						break;
					default:
						value = "";
					}
					values.put("cell" + (cellCount + 1), value);
				}
				if (values != null && !values.isEmpty()) {
					resultList.add(values);
				}
			}
		}
		System.out.println(resultList.toString());
		return resultList;
	}

	/**
	 * 读取2007版本的excel内容，
	 * 
	 * @author chenjie
	 * @param filePath
	 *            文件路径
	 * @param startRow
	 *            开始读取行
	 * @return 以List<Map<String,
	 *         Object>>方式返回，每个map中包含一行数据，从前往后依次读取的列为cell1,cell2,cell3...celln
	 * @throws FileNotFoundException 文件没有找到
	 * @throws IOException IO读写异常
	 */
	public static List<Map<String, Object>> readExcelFor2007(String filePath,
			int startRow) throws FileNotFoundException, IOException {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		FileInputStream in = new FileInputStream(filePath);
		Workbook wb = new XSSFWorkbook(in); // 支持2007
		for (int sheetCount = 0; sheetCount < wb.getNumberOfSheets(); sheetCount++) {
			Sheet sheet = wb.getSheetAt(sheetCount);
			if (startRow > 0) {
				startRow = startRow - 1;
			}
			int numberRows = sheet.getPhysicalNumberOfRows();
			for (int rowCount = startRow; rowCount < numberRows; rowCount++) {
				Row sourceRow = sheet.getRow(rowCount);
				if (sourceRow == null) {
					numberRows +=1;
					continue;
				}
				Map<String, Object> values = new HashMap<String, Object>();
				// 以下构造导入的实体对象，并根据Excel单元格的内容填充实体属性值
				for (int cellCount = 0; cellCount < sourceRow.getLastCellNum(); cellCount++) {
					Cell cell = sourceRow.getCell(cellCount);
					if (cell == null) {
						continue;
					}
					String value = "";
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if ("@".equals(cell.getCellStyle().getDataFormatString())) {
							value = new DecimalFormat("0").format(cell.getNumericCellValue());

						} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
							value = new DecimalFormat("0.00").format(cell.getNumericCellValue());
						} else {
							value = new SimpleDateFormat("yyyy-MM-dd").format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
						}
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = String.valueOf(cell.getBooleanCellValue());
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					default:
						value = cell.toString();
					}

					values.put("cell" + (cellCount + 1), value);
				}
				if (values != null && !values.isEmpty()) {
					resultList.add(values);
				}
			}
		}
		System.out.println(resultList.toString());
		return resultList;
	}

	/**
	 * 根据excel后缀判断excel文件格式读取文件内容
	 * @author chenjie
	 * @param filePath 文件路径
	 * @param startRow 开始读取行
	 * @return 以List<Map<String, Object>>方式返回，每个map中包含一行数据，从前往后依次读取的列为cell1,cell2,cell3...celln
	 * @throws FileNotFoundException 文件没有找到
	 * @throws IOException  IO读写异常
	 */
	public static List<Map<String, Object>> readExcel(String filePath,
			int startRow) throws FileNotFoundException, IOException {
		if (filePath.endsWith(FILE_END_XLS)) {
			return readExcelFor2003(filePath, startRow);
		} else if (filePath.endsWith(FILE_END_XLSx)) {
			return readExcelFor2007(filePath, startRow);
		} else {
			return null;
		}
	}
	
	/**
	 * 导出excel
	 * @param headers 表头信息
	 * @param obj 数据集
	 * @param fileName 文件名称
	 * @return
	 * @throws Throwable
	 */
	public static Map<String, String> exportExcel(ExcelHead[] headers, DataObject[] obj, String fileName) throws Throwable {
		// 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(fileName);
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 15);
        
        // 产生表格标题行
        if (headers != null && headers.length > 0) {
        	HSSFCellStyle headStyle = getCellStyle(workbook, HSSFColor.GREY_40_PERCENT.index, HSSFCellStyle.ALIGN_CENTER, (short) 11);
        	
        	HSSFRow row = sheet.createRow(0);
            for (short i = 0; i < headers.length; i++) {
            	//sheet.setColumnWidth((short)(i + 1), headers[i].getColumnWidth());// 设置列宽
            	
            	HSSFCell cell = row.createCell(i);
                cell.setCellStyle(headStyle);// 设置表头单元格的样式
                HSSFRichTextString text = new HSSFRichTextString(headers[i].getHeadname());
                cell.setCellValue(text);
            }

            if (obj != null && obj.length > 0) {
            	HSSFCellStyle valueStyle = null;
            	
            	for (int k = 0; k < obj.length; k++) {
        			row = sheet.createRow(k + 1);
        			for (short i = 0; i < headers.length; i++) {
        				valueStyle = getCellStyle(workbook, HSSFColor.WHITE.index, headers[i].getColumnAlign(), (short) 10);// 设置单元格样式
        				
        				String cellValue = obj[k].getString(headers[i].getHeadCode());// 获取单元格的值
        				if (StringUtil.isNullOrBlank(cellValue)) {
        					cellValue = "";
        				} else {
        					if (StringUtil.isNotNullAndBlank(headers[i].getDictTypeId())) {
        						cellValue = DcfUtil.getDictEntryName(headers[i].getDictTypeId(), cellValue);// 根据字典项ID获取字典项名称
        					}
        				}
        				HSSFCell cell = row.createCell(i);
        				cell.setCellStyle(valueStyle);
                        HSSFRichTextString text = new HSSFRichTextString(cellValue);
                        cell.setCellValue(text);
        			}
        		}
            }
        }
        
        Map<String, String> map = exportExcelFile(workbook, fileName);// 导出Excel
        
        return map;
	}
	
	private static HSSFCellStyle getCellStyle(HSSFWorkbook workbook, short groundColor, short Alignment, short fontBig) {
		// 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        // 设置这些样式  
        style.setFillForegroundColor(groundColor);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(Alignment);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成一个字体  
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(fontBig);
        // 把字体应用到当前的样式
        style.setFont(font);
        
        return style;
	}
	
	/**
	 * 保存Excel到指定文件
	 */
	private static Map<String, String> exportExcelFile(HSSFWorkbook workbook, String fileName) throws Throwable {
		Map<String, String> map = new HashMap<String, String>();
		
		FileOutputStream fileOut = null;
		try {
			Map<String, String> excelConfig = DcfUtil.getExportExcelConfig();
			String exportExcelSuffix = excelConfig.get("exportExcelSuffix");
			String clientFileName = fileName + exportExcelSuffix;
			fileName = fileName + new Date().getTime() + exportExcelSuffix;
			String filePath = excelConfig.get("exportExcelPath") + "/" + fileName;
			
			fileOut = new FileOutputStream(filePath);
			workbook.write(fileOut);
			
			map.put("clientFileName", clientFileName);
			map.put("filePath", filePath);
			if (exportExcelSuffix.endsWith(".xls")) {
				map.put("contentType", "application/x-xls");
			} else if (exportExcelSuffix.endsWith(".xlsx")) {
				map.put("contentType", "application/octet-stream");
			}
		} catch (Exception e) {
			throw e;
		} finally{  
            if(fileOut != null){
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return map;
	}
	
	public static void main(String[] args) {
		//exportExcel("测试", new String[]{"111", "222"}, null);
	}
}