/**
 * Copyright 2015-2017 广州市领课网络科技有限公司
 */
package com.roncoo.jui.common.util.excel;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.roncoo.jui.common.entity.RcReport;

/**
 * @author Wujun
 */
public final class ReportExcelUtil {
	private ReportExcelUtil() {
	}

	public static void exportExcel(String sheetName, String[] titles, List<RcReport> list, ServletOutputStream outputStream) {
		// 创建一个workbook 对应一个excel文件
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(sheetName);
		ExcelUtil excelUtil = new ExcelUtil(workBook, sheet);
		XSSFCellStyle headStyle = excelUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = excelUtil.getBodyStyle();
		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		XSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}

		// 构建表体数据
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				XSSFRow bodyRow = sheet.createRow(j + 1);
				RcReport bean = list.get(j);

				cell = bodyRow.createCell(0);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(bean.getUserEmail());

				cell = bodyRow.createCell(1);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(bean.getUserNickname());
			}
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
