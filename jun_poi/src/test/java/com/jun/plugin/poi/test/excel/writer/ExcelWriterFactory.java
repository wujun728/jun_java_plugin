package com.jun.plugin.poi.test.excel.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jun.plugin.poi.test.utils.FileCreateUtils;

public class ExcelWriterFactory {
	private static final Pattern OLD_EXCEL_PATH = Pattern
			.compile("^\\S*\\.xls$");
	private static final Pattern EXCEL_PATH = Pattern.compile("^\\S*\\.xlsx$");

	private static void isOldPath(String path) {
		if (!OLD_EXCEL_PATH.matcher(path).matches()) {
			throw new IllegalArgumentException("the file has a illegal name");
		}
	}

	private static void isNewPath(String path) {
		if (!EXCEL_PATH.matcher(path).matches()) {
			throw new IllegalArgumentException("the file has a illegal name");
		}
	}

	public static WriteHandler createHSSF(String path) {
		isOldPath(path);
		Workbook wb = new HSSFWorkbook();
		return new DefaultFileWriteHandler(wb, path);
	}

	public static WriteHandler createHSSF(File file)
			throws FileNotFoundException {
		isOldPath(file.getAbsolutePath());
		Workbook wb = new HSSFWorkbook();
		return new DefaultFileWriteHandler(wb, file);
	}

	public static WriteHandler createHSSF(OutputStream os) {
		Workbook wb = new HSSFWorkbook();
		return new DefaultStreamWriteHandler(wb, os);
	}

	public static WriteHandler createXSSF(String path) {
		isNewPath(path);
		Workbook wb = new XSSFWorkbook();
		return new DefaultFileWriteHandler(wb, path);
	}

	public static WriteHandler createXSSF(OutputStream os) {
		Workbook wb = new HSSFWorkbook();
		return new DefaultStreamWriteHandler(wb, os);
	}

	public static WriteHandler createXSSF(File file)
			throws FileNotFoundException {
		isNewPath(file.getAbsolutePath());
		Workbook wb = new XSSFWorkbook();
		return new DefaultFileWriteHandler(wb, file);
	}

	public static WriteHandler createSXSSF(String path) {
		isNewPath(path);
		SXSSFWorkbook wb = new SXSSFWorkbook(200);
		return new SXSSFWriterHandler(wb, path);
	}

	public static WriteHandler createSXSSF(File file)
			throws FileNotFoundException {
		isNewPath(file.getAbsolutePath());
		SXSSFWorkbook wb = new SXSSFWorkbook(200);
		FileOutputStream out = new FileOutputStream(file);
		return new SXSSFWriterHandler(wb, out);
	}

}
