package com.jun.plugin.poi.learn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadUtils {

	public static ArrayList<ArrayList<Object>> readRows(String excelFile,
			int startRowIndex, int rowCount) throws IOException {
		return readRows(new FileInputStream(excelFile), startRowIndex, rowCount);
	}

	public static ArrayList<ArrayList<Object>> readRows(InputStream is,
			int startRowIndex, int rowCount) throws IOException {

		Workbook wb = null;

		ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
		byte[] buffer = new byte[512];
		int count = -1;
		while ((count = is.read(buffer)) != -1)
			byteOS.write(buffer, 0, count);
		byteOS.close();
		byte[] allBytes = byteOS.toByteArray();

		try {
			wb = new XSSFWorkbook(new ByteArrayInputStream(allBytes));
		} catch (InvalidOperationException ex) {
			wb = new HSSFWorkbook(new ByteArrayInputStream(allBytes));
		}

		Sheet sheet = wb.getSheetAt(0);

		return readRows(sheet, startRowIndex, rowCount);
	}

	public static ArrayList<ArrayList<Object>> readRows(Sheet sheet,
			int startRowIndex, int rowCount) {
		ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();

		int i = 0;
		for (Row row : sheet) {
			i++;
			if (i > startRowIndex && i <= (startRowIndex + rowCount)) {

				ArrayList<Object> cellList = new ArrayList<Object>();
				for (Cell cell : row) {
					cellList.add(readCell(cell));
				}

				rowList.add(cellList);
			}
		}
		return rowList;
	}

	/**
	 * 从Excel读Cell
	 * 
	 * @param cell
	 * @return
	 */
	private static Object readCell(Cell cell) {
		if (cell == null) {
			return null;
		}

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			String str = cell.getRichStringCellValue().getString();
			return str == null ? "" : str.trim();

		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_FORMULA:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getCellFormula();
			}

		case Cell.CELL_TYPE_BLANK:
			return "";

		default:
			System.err.println("Data error for cell of excel: " + cell.getCellType());
			return "";
		}

	}
}
