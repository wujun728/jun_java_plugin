package com.bing.excel.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.omg.CORBA.portable.UnknownException;

import com.bing.excel.vo.CellKV;
import com.bing.excel.vo.ListLine;
import com.bing.utils.FileCreateUtils;

public abstract class AbstractWriteHandler implements WriteHandler {
	Sheet currentSheet;
	private final Workbook wb;
	transient OutputStream os;
	private CellStyle headerCellStyle;
	private CellStyle dateCellStyle;

	public AbstractWriteHandler(Workbook wb, OutputStream outStream) {
		this.wb = wb;
		os = outStream;
	}

	public AbstractWriteHandler(Workbook wb, String path) {
		this.wb = wb;
		File f = FileCreateUtils.createFile(path);
		try {
			os = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// system bug
			f.deleteOnExit();
			throw new UnknownException(e);
		}

	}

	
	
	 int currentRowIndex = -1;

	CellStyle createHeadStyle() {
		if (headerCellStyle != null) {
			return headerCellStyle;
		}
		CellStyle style = wb.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		style.setAlignment(CellStyle.ALIGN_CENTER);
		// 生成一个字体
		Font font = wb.createFont();
		font.setColor(IndexedColors.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		headerCellStyle = style;
		return style;
	}

	CellStyle createDateStyle() {
		if (dateCellStyle != null) {
			return dateCellStyle;
		}
		CellStyle cellStyle = wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("m/d/yy h:mm"));
		dateCellStyle = cellStyle;
		return cellStyle;
	}

	void writeDataToRow(ListLine line, Row row) {

		CellStyle cellStyle = createDateStyle();
		for (CellKV<String> kv : line.getListStr()) {
			Cell cell = row.createCell(kv.getIndex());
			cell.setCellValue(kv.getValue());
		}
		for (CellKV<Boolean> kv : line.getListBoolean()) {
			Cell cell = row.createCell(kv.getIndex());
			cell.setCellValue(kv.getValue());
		}
		for (CellKV<Date> kv : line.getListDate()) {
			Cell cell = row.createCell(kv.getIndex());
			if (currentRowIndex < 2) {
				currentSheet.setColumnWidth((short) (kv.getIndex()),
						(short) 5000);
			}

			cell.setCellStyle(cellStyle);
			cell.setCellValue(kv.getValue());
		}
		for (CellKV<Double> kv : line.getListDouble()) {
			Cell cell = row.createCell(kv.getIndex());
			cell.setCellValue(kv.getValue());
		}
	}

	@Override
	public void writeLine(ListLine line) {
		if (line != null) {
			currentRowIndex++;
			Row currentRow = currentSheet.createRow(currentRowIndex);
			writeDataToRow(line, currentRow);
		}
	}

	@Override
	public void writeHeader(List<CellKV<String>> listStr) {

		currentRowIndex = 0;
		CellStyle style = createHeadStyle();
		Row currentRow = currentSheet.createRow(currentRowIndex);
		currentRow.setHeight((short) 0x180);
		for (CellKV<String> cellKV : listStr) {
			Cell cell = currentRow.createCell(cellKV.getIndex());
			cell.setCellValue(cellKV.getValue());
			cell.setCellStyle(style);

			int size = cellKV.getValue().length();
			if (size > 10) {
				size = 10;
			}
			if (size < 4) {
				size = 4;
			}
			currentSheet.setColumnWidth((short) (cellKV.getIndex()),
					(short) ((25 * size) * 20));
		}

	}

	@Override
	public String createSheet(String name) {
		if (StringUtils.isBlank(name)) {
			currentSheet = wb.createSheet();
			
		} else {
			Sheet sheet = wb.getSheet(name);
			if (sheet == null) {
				currentSheet = wb.createSheet(name);
			} else {
				createOrderNumSheet(name, 1);
			}
		}
		return currentSheet.getSheetName();
	}

	private void createOrderNumSheet(String name, int num) {
		Sheet sheet = wb.getSheet(name + "-" + num);
		if (sheet != null) {
			createOrderNumSheet(name,num+1);
		} else {
			currentSheet = wb.createSheet(name + "-" + num);
		}
	}

	@Override
	public void flush() {

		try {
			wb.write(os);
			wb.close();
		} catch (IOException e) {
			throw new IllegalStateException(e);
			// e.printStackTrace();
		}
	}

}
