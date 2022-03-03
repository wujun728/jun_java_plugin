package com.jun.plugin.poi;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.jun.excel.excelTools.TableColumn;
import com.jun.excel.excelTools.TableData;
import com.jun.excel.excelTools.TableDataRow;
import com.jun.excel.excelTools.TableHeaderMetaData;
import com.jun.plugin.utils.Excel;
import com.jun.plugin.utils.ExcelVO;

/**
 * 类功能说明 类修改者 修改日期 修改说明
 * <p>
 * Title: ExcelUtil.java
 * </p>
 * <p>
 * Description:excel导入导出类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * @date 2013-6-28 上午9:08:33
 * @version V1.0
 */

public class ExcelUtil2<T> {
	Class<T> clazz;

	// public ExcelUtil(Process process, String charset) {
	// this.clazz = charset;
	// }

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-28修改日期 修改内容 @Title: importExcel @Description: TODO:excel导入 @param @param sheetName @param @param input @param @return
	 * 设定文件 @return List<T> 返回类型 @throws
	 */
	/*
	 * public List<T> importExcel(String sheetName, InputStream input) { List<T> list = new ArrayList<T>(); try { Workbook book = Workbook.getWorkbook(input);
	 * Sheet sheet = null; if (!sheetName.trim().equals("")) { sheet = book.getSheet(sheetName);// 根据名称获取sheet } if (sheet == null) { sheet =
	 * book.getSheet(0);// 根据索引获取sheet } int rows = sheet.getRows();// 获取行数 if (rows > 0) { Field[] allFields = clazz.getDeclaredFields();//获取类的字段属性
	 * Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>(); for (Field field : allFields) { if (field.isAnnotationPresent(ExcelVO.class)) { ExcelVO
	 * attr = field .getAnnotation(ExcelVO.class); int col = getExcelCol(attr.column()); // System.out.println(col + "====" + field.getName());
	 * field.setAccessible(true); fieldsMap.put(col, field); } } for (int i = 1; i < rows; i++) { Cell[] cells = sheet.getRow(i); T entity = null; for (int j =
	 * 0; j < cells.length; j++) { String c = cells[j].getContents(); if (c.equals("")) { continue; } entity = (entity == null ? clazz.newInstance() : entity);
	 * // System.out.println(cells[j].getContents()); Field field = fieldsMap.get(j); Class<?> fieldType = field.getType(); if ((Integer.TYPE == fieldType) ||
	 * (Integer.class == fieldType)) { field.set(entity, Integer.parseInt(c)); } else if (String.class == fieldType) { field.set(entity, String.valueOf(c)); }
	 * else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) { field.set(entity, Long.valueOf(c)); } else if ((Float.TYPE == fieldType) ||
	 * (Float.class == fieldType)) { field.set(entity, Float.valueOf(c)); } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
	 * field.set(entity, Short.valueOf(c)); } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) { field.set(entity, Double.valueOf(c)); } else
	 * if (Character.TYPE == fieldType) { if ((c != null) && (c.length() > 0)) { field.set(entity, Character .valueOf(c.charAt(0))); } }
	 * 
	 * } if (entity != null) { list.add(entity); } } }
	 * 
	 * } catch (BiffException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); } catch (InstantiationException e) { e.printStackTrace();
	 * } catch (IllegalAccessException e) { e.printStackTrace(); } catch (IllegalArgumentException e) { e.printStackTrace(); } return list; }
	 */

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-28修改日期 修改内容 @Title: exportExcel @Description: TODO:导出excel @param @param list @param @param sheetName @param @param
	 * sheetSize @param @param output @param @return 设定文件 @return boolean 返回类型 @throws
	 */
	public boolean exportExcel(List<T> list, String sheetName, int sheetSize, OutputStream output) {

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
				cell.setCellType(CellType.STRING);
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
					ExcelVO attr = field.getAnnotation(ExcelVO.class);
					try {
						if (attr.isExport()) {
							cell = row.createCell(getExcelCol(attr.column()));
							cell.setCellType(CellType.STRING);
							cell.setCellValue(field.get(vo) == null ? "" : String.valueOf(field.get(vo)));
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
	 * 函数功能说明 Administrator修改者名字 2013-6-28修改日期 修改内容 @Title: getExcelCol @Description: TODO:获取excel转化到数字 A->1 B->2 @param @param col @param @return 设定文件 @return
	 * int 返回类型 @throws
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
	 * 函数功能说明 Administrator修改者名字 2013-6-28修改日期 修改内容 @Title: setHSSFPrompt @Description: TODO:设置提示信息 @param @param sheet @param @param promptTitle @param @param
	 * promptContent @param @param firstRow @param @param endRow @param @param firstCol @param @param endCol @param @return 设定文件 @return HSSFSheet 返回类型 @throws
	 */
	public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle, String promptContent, int firstRow,
			int endRow, int firstCol, int endCol) {
		DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);
		data_validation_view.createPromptBox(promptTitle, promptContent);
		sheet.addValidationData(data_validation_view);
		return sheet;
	}

	/**
	 * 函数功能说明 Administrator修改者名字 2013-6-28修改日期 修改内容 @Title: setHSSFValidation @Description: TODO:设置验证行数据 @param @param sheet @param @param
	 * textlist @param @param firstRow @param @param endRow @param @param firstCol @param @param endCol @param @return 设定文件 @return HSSFSheet 返回类型 @throws
	 */
	public static HSSFSheet setHSSFValidation(HSSFSheet sheet, String[] textlist, int firstRow, int endRow,
			int firstCol, int endCol) {
		DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
		sheet.addValidationData(data_validation_list);
		return sheet;
	}

	/************************************************************************************************/
	/************************************************************************************************/

	/**
	 * Executes the specified command and arguments in a separate process, and waits for the process to finish.
	 * <p>
	 * Output from the process is expected to be text in the system's default character set.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param cmdarray
	 *            array containing the command to call and its arguments.
	 * @return The results of the execution in an ExcelUtil object.
	 * @throws SecurityException
	 *             if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException
	 *             - if an I/O error occurs
	 * @throws NullPointerException
	 *             - if cmdarray is null
	 * @throws IndexOutOfBoundsException
	 *             - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtil2 exec(String[] cmdarray) throws IOException {
		return new ExcelUtil2(Runtime.getRuntime().exec(cmdarray), null);
	}

	/**
	 * Executes the specified command and arguments in a separate process, and waits for the process to finish.
	 * <p>
	 * Output from the process is expected to be text in the system's default character set.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param cmdarray
	 *            array containing the command to call and its arguments.
	 * @param envp
	 *            array of strings, each element of which has environment variable settings in format name=value.
	 * @return The results of the execution in an ExcelUtil object.
	 * @throws SecurityException
	 *             if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException
	 *             - if an I/O error occurs
	 * @throws NullPointerException
	 *             - if cmdarray is null
	 * @throws IndexOutOfBoundsException
	 *             - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtil2 exec(String[] cmdarray, String[] envp) throws IOException {
		return new ExcelUtil2(Runtime.getRuntime().exec(cmdarray, envp), null);
	}

	/**
	 * Executes the specified command and arguments in a separate process, and waits for the process to finish.
	 * <p>
	 * Output from the process is expected to be text in the system's default character set.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param cmdarray
	 *            array containing the command to call and its arguments.
	 * @param envp
	 *            array of strings, each element of which has environment variable settings in format name=value.
	 * @param dir
	 *            the working directory of the subprocess, or null if the subprocess should inherit the working directory of the current process.
	 * @return The results of the execution in an ExcelUtil object.
	 * @throws SecurityException
	 *             if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException
	 *             - if an I/O error occurs
	 * @throws NullPointerException
	 *             - if cmdarray is null
	 * @throws IndexOutOfBoundsException
	 *             - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtil2 exec(String[] cmdarray, String[] envp, File dir) throws IOException {
		return new ExcelUtil2(Runtime.getRuntime().exec(cmdarray, envp), null);
	}

	/**
	 * Executes the specified command and arguments in a separate process, and waits for the process to finish.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param cmdarray
	 *            array containing the command to call and its arguments.
	 * @param charset
	 *            Output from the executed command is expected to be in this character set.
	 * @return The results of the execution in an ExcelUtil object.
	 * @throws SecurityException
	 *             if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException
	 *             - if an I/O error occurs
	 * @throws NullPointerException
	 *             - if cmdarray is null
	 * @throws IndexOutOfBoundsException
	 *             - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtil2 exec(String[] cmdarray, String charset) throws IOException {
		return new ExcelUtil2(Runtime.getRuntime().exec(cmdarray), charset);
	}

	/**
	 * Executes the specified command and arguments in a separate process, and waits for the process to finish.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param cmdarray
	 *            array containing the command to call and its arguments.
	 * @param envp
	 *            array of strings, each element of which has environment variable settings in format name=value.
	 * @param charset
	 *            Output from the executed command is expected to be in this character set.
	 * @return The results of the execution in an ExcelUtil object.
	 * @throws SecurityException
	 *             if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException
	 *             - if an I/O error occurs
	 * @throws NullPointerException
	 *             - if cmdarray is null
	 * @throws IndexOutOfBoundsException
	 *             - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtil2 exec(String[] cmdarray, String[] envp, String charset) throws IOException {
		return new ExcelUtil2(Runtime.getRuntime().exec(cmdarray, envp), charset);
	}

	/**
	 * Executes the specified command and arguments in a separate process, and waits for the process to finish.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param cmdarray
	 *            array containing the command to call and its arguments.
	 * @param envp
	 *            array of strings, each element of which has environment variable settings in format name=value.
	 * @param dir
	 *            the working directory of the subprocess, or null if the subprocess should inherit the working directory of the current process.
	 * @param charset
	 *            Output from the executed command is expected to be in this character set.
	 * @return The results of the execution in an ExcelUtil object.
	 * @throws SecurityException
	 *             if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException
	 *             - if an I/O error occurs
	 * @throws NullPointerException
	 *             - if cmdarray is null
	 * @throws IndexOutOfBoundsException
	 *             - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtil2 exec(String[] cmdarray, String[] envp, File dir, String charset) throws IOException {
		return new ExcelUtil2(Runtime.getRuntime().exec(cmdarray, envp), charset);
	}

	/**
	 * Executes the specified command using a shell. On windows uses cmd.exe or command.exe. On other platforms it uses /bin/sh.
	 * <p>
	 * A shell should be used to execute commands when features such as file redirection, pipes, argument parsing are desired.
	 * <p>
	 * Output from the process is expected to be text in the system's default character set.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param command
	 *            String containing a command to be parsed by the shell and executed.
	 * @return The results of the execution in an ExcelUtil object.
	 * @throws SecurityException
	 *             if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException
	 *             - if an I/O error occurs
	 * @throws NullPointerException
	 *             - if command is null
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtil2 execUsingShell(String command) throws IOException {
		return execUsingShell(command, null);
	}

	/**
	 * Executes the specified command using a shell. On windows uses cmd.exe or command.exe. On other platforms it uses /bin/sh.
	 * <p>
	 * A shell should be used to execute commands when features such as file redirection, pipes, argument parsing are desired.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param command
	 *            String containing a command to be parsed by the shell and executed.
	 * @param charset
	 *            Output from the executed command is expected to be in this character set.
	 * @return The results of the execution in an ExcelUtils object.
	 * @throws SecurityException
	 *             if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException
	 *             - if an I/O error occurs
	 * @throws NullPointerException
	 *             - if command is null
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtil2 execUsingShell(String command, String charset) throws IOException {
		if (command == null)
			throw new NullPointerException();
		String[] cmdarray;
		String os = System.getProperty("os.name");
		if (os.equals("Windows 95") || os.equals("Windows 98") || os.equals("Windows ME")) {
			cmdarray = new String[] { "command.exe", "/C", command };
		} else if (os.startsWith("Windows")) {
			cmdarray = new String[] { "cmd.exe", "/C", command };
		} else {
			cmdarray = new String[] { "/bin/sh", "-c", command };
		}
		return new ExcelUtil2(Runtime.getRuntime().exec(cmdarray), charset);
	}

	/**
	 * Take a process, record its standard error and standard out streams, wait for it to finish
	 *
	 * @param process
	 *            process to watch
	 * @throws SecurityException
	 *             if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException
	 *             - if an I/O error occurs
	 * @throws NullPointerException
	 *             - if cmdarray is null
	 * @throws IndexOutOfBoundsException
	 *             - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	private ExcelUtil2(Process process, String charset) throws IOException {
		StringBuffer output = new StringBuffer();
		StringBuffer error = new StringBuffer();

		Reader stdout;
		Reader stderr;

		if (charset == null) {
			// This is one time that the system charset is appropriate,
			// don't specify a character set.
			stdout = new InputStreamReader(process.getInputStream());
			stderr = new InputStreamReader(process.getErrorStream());
		} else {
			stdout = new InputStreamReader(process.getInputStream(), charset);
			stderr = new InputStreamReader(process.getErrorStream(), charset);
		}
		char[] buffer = new char[1024];

		boolean done = false;
		boolean stdoutclosed = false;
		boolean stderrclosed = false;
		while (!done) {
			boolean readSomething = false;
			// read from the process's standard output
			if (!stdoutclosed && stdout.ready()) {
				readSomething = true;
				int read = stdout.read(buffer, 0, buffer.length);
				if (read < 0) {
					readSomething = true;
					stdoutclosed = true;
				} else if (read > 0) {
					readSomething = true;
					output.append(buffer, 0, read);
				}
			}
			// read from the process's standard error
			if (!stderrclosed && stderr.ready()) {
				int read = stderr.read(buffer, 0, buffer.length);
				if (read < 0) {
					readSomething = true;
					stderrclosed = true;
				} else if (read > 0) {
					readSomething = true;
					error.append(buffer, 0, read);
				}
			}
			// Check the exit status only we haven't read anything,
			// if something has been read, the process is obviously not dead yet.
			if (!readSomething) {
				try {
					this.status = process.exitValue();
					done = true;
				} catch (IllegalThreadStateException itx) {
					// Exit status not ready yet.
					// Give the process a little breathing room.
					try {
						Thread.sleep(100);
					} catch (InterruptedException ix) {
						process.destroy();
						throw new IOException("Interrupted - processes killed");
					}
				}
			}
		}

		this.output = output.toString();
		this.error = error.toString();
	}

	/**
	 * The output of the job that ran.
	 *
	 * @since ostermillerutils 1.06.00
	 */
	private String output;

	/**
	 * Get the output of the job that ran.
	 *
	 * @return Everything the executed process wrote to its standard output as a String.
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * The error output of the job that ran.
	 *
	 * @since ostermillerutils 1.06.00
	 */
	private String error;

	/**
	 * Get the error output of the job that ran.
	 *
	 * @return Everything the executed process wrote to its standard error as a String.
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public String getError() {
		return error;
	}

	/**
	 * The status of the job that ran.
	 *
	 * @since ostermillerutils 1.06.00
	 */
	private int status;

	/**
	 * Get the status of the job that ran.
	 *
	 * @return exit status of the executed process, by convention, the value 0 indicates normal termination.
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public int getStatus() {
		return status;
	}
	/*
	 * public static String checkExcel(File execelFile, Integer rowIndex, List<ETL_Rule> constraints ) { Workbook wb = null; Sheet sheet = null; Integer
	 * lastRowNum = null; Integer numberOfCells = null; List<ETL_Property_Column> pcs = new ArrayList<ETL_Property_Column>(); try { for (ETL_Rule rule :
	 * constraints) { pcs.addAll(rule.getPropertyColumns()); } wb = WorkbookFactory.create(execelFile); sheet = wb.getSheetAt(0); lastRowNum =
	 * sheet.getLastRowNum(); numberOfCells = sheet.getRow(rowIndex).getPhysicalNumberOfCells(); } catch (Exception e) { return "文件异常，请重新上传"; } Row row = null;
	 * Cell cell = null; for (int i = rowIndex; i <= lastRowNum; i++) { row = sheet.getRow(i); for (int j = 0; j < numberOfCells; j++) { cell = row.getCell(j);
	 * String cellValue = getCellFormatValue(cell); for (ETL_Property_Column pc : pcs) { ETL_Column column = pc.getColumn(); if(column == null){ continue; }
	 * Integer cellIndex = column.getColumn_cellIndex(); if(cellIndex == j) { String classType = pc.getProperty().getProperty_classType(); try {
	 * if(pc.getIsRepeat() && StringUtils.isBlank(cellValue)){ return column.getColumn_name() + "不能为空"; } else if ("java.sql.Timestamp".equals(classType)) { try
	 * { Class<?> clzz = Class.forName(classType); Method method = clzz.getMethod("valueOf", String.class); method.invoke(clzz, cellValue); } catch (Exception
	 * ee) { return "第" + (i + 1) +"行 [" +column.getColumn_name() + "] 日期格式有误,请重新上传"; } } } catch (Exception e) { return "第" + (i + 1) +"行 ["
	 * +column.getColumn_name() + "] 数据错格式有误,请重新上传"; } } } } } return null; }
	 */
	/**
	 * 获取Execel文件 索引和title
	 * 
	 * @param execelFile
	 *            execel文件
	 * @param index
	 *            title所在行索引
	 * @return Map<行索引,Title>
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	/*
	 * public static Map<Integer, String> readExcelTitle(File execelFile, Integer rowIndex) throws InvalidFormatException, IOException {
	 * 
	 * Workbook wb = WorkbookFactory.create(execelFile); Sheet sheet = wb.getSheetAt(0); Row row = sheet.getRow(rowIndex - 1); // 标题总列数 int colNum =
	 * row.getPhysicalNumberOfCells(); Map<Integer, String> tileMap = new HashMap<Integer, String>(colNum);
	 * 
	 * for (int i = 0; i < colNum; i++) { String content = getCellFormatValue(row.getCell(i)); if (content != null && !content.trim().isEmpty()) {
	 * tileMap.put(i, getCellFormatValue(row.getCell(i))); } }
	 * 
	 * return tileMap; }
	 */

	/**
	 * 获取Execel 内容
	 * 
	 * @param execelFile
	 * @param index
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 * 
	 *             TODO://待修改 数据量大时 可能造成内存益处 .
	 */
	/*
	 * public static List<Map<Integer, String>> readExcelContentList(File execelFile, Integer index) throws InvalidFormatException, IOException {
	 * 
	 * List<Map<Integer, String>> data = new ArrayList<Map<Integer, String>>(); Map<Integer, String> content = null;
	 * 
	 * Workbook wb = WorkbookFactory.create(execelFile); Sheet sheet = wb.getSheetAt(0); Integer lastRowNum = sheet.getLastRowNum(); Integer numberOfCells =
	 * sheet.getRow(index).getPhysicalNumberOfCells();
	 * 
	 * Row row = null; Cell cell = null; for (int i = index; i <= lastRowNum; i++) { row = sheet.getRow(i); content = new HashMap<Integer, String>(); for (int j
	 * = 0; j < numberOfCells; j++) { cell = row.getCell(j); String cellValue = getCellFormatValue(cell); if (cellValue != null && !cellValue.isEmpty()) {
	 * content.put(j, cellValue.trim()); } } data.add(content); } return data; }
	 */

	/*
	 * public static List<Map<Integer, String>> readExcelContent(File execelFile, Integer... indexs) throws Exception { List<Map<Integer, String>> datas = new
	 * ArrayList<Map<Integer, String>>(); Workbook wb = WorkbookFactory.create(execelFile); Sheet sheet = wb.getSheetAt(0); for (Integer index : indexs) { index
	 * --; Integer numberOfCells = sheet.getRow(index).getPhysicalNumberOfCells(); Row row = sheet.getRow(index); Map<Integer, String> content = new
	 * HashMap<Integer, String>(); for (int j = 0; j < numberOfCells; j++) { Cell cell = row.getCell(j); String cellValue = getCellFormatValue(cell); if
	 * (StringUtils.isNotBlank(cellValue)) { content.put(j, cellValue.trim()); } } datas.add(content); } return datas; }
	 */

	/**
	 * 给指定行着色--另存为其他文件
	 * 
	 * @param sourcelFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @param rows
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	/*
	 * public static void colorExcel(File sourcelFile, Integer index, List<Integer> rows) {
	 * 
	 * if (rows == null || rows.size() <= 0) { return; } FileOutputStream fileOut = null; try { String destFile = sourcelFile.getAbsolutePath() + "_temp";
	 * 
	 * Workbook wb = WorkbookFactory.create(sourcelFile); CellStyle style = wb.createCellStyle(); style.setFillForegroundColor(IndexedColors.RED.getIndex());
	 * style.setFillPattern(CellStyle.SOLID_FOREGROUND); Sheet sheet = wb.getSheetAt(0); Integer numberOfCells = sheet.getRow(index).getPhysicalNumberOfCells();
	 * System.out.println(numberOfCells); Row row = null; Cell cell = null; for (Integer rowIndex : rows) { row = sheet.getRow(index + rowIndex); for (int j =
	 * 0; j < numberOfCells; j++) { if (row != null) { cell = row.getCell(j); if (cell != null) { cell.setCellStyle(style); } } } } fileOut = new
	 * FileOutputStream(destFile); wb.write(fileOut); fileOut.close(); } catch (Exception e) { e.printStackTrace(); } }
	 */

	/**
	 * 获取指定行内容
	 * 
	 * @param execelFile
	 * @param rows
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	/*
	 * public static List<Map<Integer, String>> readExcelRowsContentList(File execelFile, List<Integer> rows) throws InvalidFormatException, IOException {
	 * 
	 * List<Map<Integer, String>> data = new ArrayList<Map<Integer, String>>(); Map<Integer, String> content = null;
	 * 
	 * Workbook wb = WorkbookFactory.create(execelFile); Sheet sheet = wb.getSheetAt(0); Integer numberOfCells = sheet.getRow(0).getPhysicalNumberOfCells(); Row
	 * row = null; Cell cell = null; for (Integer rowIndex : rows) { row = sheet.getRow(rowIndex); content = new HashMap<Integer, String>(); for (int j = 0; j <
	 * numberOfCells; j++) { cell = row.getCell(j); String cellValue = getCellFormatValue(cell); if (cellValue != null && !cellValue.isEmpty()) { content.put(j,
	 * cellValue.trim()); } } data.add(content); } return data; }
	 */
	/************************************************************************************************/
	/************************************************************************************************/

	/**
	 * JavaBean转Map
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!StringUtils.equals(name, "class")) {
					params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/**
	 * 创建普通表头
	 * 
	 * @param list
	 *            表头名称列表
	 * @return
	 */
	public static TableHeaderMetaData createTableHeader(List<String> list) {
		TableHeaderMetaData headMeta = new TableHeaderMetaData();
		for (String title : list) {
			TableColumn tc = new TableColumn();
			tc.setDisplay(title);
			headMeta.addColumn(tc);
		}
		return headMeta;
	}

	/**
	 * 创建普通表头
	 * 
	 * @param titls
	 *            表头名称数组
	 * @return
	 */
	public static TableHeaderMetaData createTableHeader(String[] titls) {
		TableHeaderMetaData headMeta = new TableHeaderMetaData();
		for (String title : titls) {
			TableColumn tc = new TableColumn();
			tc.setDisplay(title);
			headMeta.addColumn(tc);
		}
		return headMeta;
	}

	/**
	 * 创建合并表头
	 * 
	 * @param parents
	 *            父表头数组
	 * @param children
	 *            子表头数组
	 * @return
	 */
	public static TableHeaderMetaData createTableHeader(String[] parents, String[][] children) {
		TableHeaderMetaData headMeta = new TableHeaderMetaData();
		TableColumn parentColumn = null;
		TableColumn sonColumn = null;
		for (int i = 0; i < parents.length; i++) {
			parentColumn = new TableColumn();
			parentColumn.setDisplay(parents[i]);
			if (children != null && children[i] != null) {
				for (int j = 0; j < children[i].length; j++) {
					sonColumn = new TableColumn();
					sonColumn.setDisplay(children[i][j]);
					parentColumn.addChild(sonColumn);
				}
			}
			headMeta.addColumn(parentColumn);
		}
		return headMeta;
	}

	/**
	 * 拼装数据
	 * 
	 * @param list
	 *            数据集
	 * @param headMeta
	 *            表头
	 * @param fields
	 *            对象或Map属性数组（注意：顺序要与表头标题顺序对应，如数据集为List<Object[]>，则该参数可以为null）
	 * @return TableData
	 */
	@SuppressWarnings("unchecked")
	public static TableData createTableData(List list, TableHeaderMetaData headMeta, String[] fields) {

		TableData td = new TableData(headMeta);
		TableDataRow row = null;
		if (list != null || list.size() > 0) {
			if (list.get(0).getClass().isArray()) {// 数组类型
				for (Object obj : list) {
					row = new TableDataRow(td);
					for (Object o : (Object[]) obj) {
						row.addCell(o);
					}
					td.addRow(row);
				}
			} else {// JavaBean或Map类型
				for (Object obj : list) {
					row = new TableDataRow(td);
					Map<String, Object> map = (obj instanceof Map) ? (Map<String, Object>) obj : beanToMap(obj);
					for (String key : fields) {
						row.addCell(map.get(key));
					}
					td.addRow(row);
				}
			}
		}
		return td;
	}

	public static void copySheetStyle(HSSFWorkbook destwb, HSSFSheet dest, HSSFWorkbook srcwb, HSSFSheet src) {
		if (src == null || dest == null)
			return;

		dest.setAlternativeExpression(src.getAlternateExpression());
		dest.setAlternativeFormula(src.getAlternateFormula());
		dest.setAutobreaks(src.getAutobreaks());
		dest.setDialog(src.getDialog());
		if (src.getColumnBreaks() != null) {
			for (int col : src.getColumnBreaks()) {
				dest.setColumnBreak((short) col);
			}
		}
		dest.setDefaultColumnWidth(src.getDefaultColumnWidth());
		dest.setDefaultRowHeight(src.getDefaultRowHeight());
		dest.setDefaultRowHeightInPoints(src.getDefaultRowHeightInPoints());
		dest.setDisplayGuts(src.getDisplayGuts());
		dest.setFitToPage(src.getFitToPage());
		dest.setHorizontallyCenter(src.getHorizontallyCenter());
		dest.setDisplayFormulas(src.isDisplayFormulas());
		dest.setDisplayGridlines(src.isDisplayGridlines());
		dest.setDisplayRowColHeadings(src.isDisplayRowColHeadings());
		dest.setGridsPrinted(src.isGridsPrinted());
		dest.setPrintGridlines(src.isPrintGridlines());

		for (int i = 0; i < src.getNumMergedRegions(); i++) {
			CellRangeAddress r = src.getMergedRegion(i);
			dest.addMergedRegion(r);
		}

		if (src.getRowBreaks() != null) {
			for (int row : src.getRowBreaks()) {
				dest.setRowBreak(row);
			}
		}
		dest.setRowSumsBelow(src.getRowSumsBelow());
		dest.setRowSumsRight(src.getRowSumsRight());

		short maxcol = 0;
		for (int i = 0; i <= src.getLastRowNum(); i++) {
			HSSFRow row = src.getRow(i);
			if (row != null) {
				if (maxcol < row.getLastCellNum())
					maxcol = row.getLastCellNum();
			}
		}
		for (short col = 0; col <= maxcol; col++) {
			if (src.getColumnWidth(col) != src.getDefaultColumnWidth())
				dest.setColumnWidth(col, src.getColumnWidth(col));
			dest.setColumnHidden(col, src.isColumnHidden(col));
		}
	}

	public static String dumpCellStyle(HSSFCellStyle style) {
		StringBuffer sb = new StringBuffer();
		sb.append(style.getHidden()).append(",");
		sb.append(style.getLocked()).append(",");
		sb.append(style.getWrapText()).append(",");
		sb.append(style.getAlignment()).append(",");
		sb.append(style.getBorderBottom()).append(",");
		sb.append(style.getBorderLeft()).append(",");
		sb.append(style.getBorderRight()).append(",");
		sb.append(style.getBorderTop()).append(",");
		sb.append(style.getBottomBorderColor()).append(",");
		sb.append(style.getDataFormat()).append(",");
		sb.append(style.getFillBackgroundColor()).append(",");
		sb.append(style.getFillForegroundColor()).append(",");
		sb.append(style.getFillPattern()).append(",");
		sb.append(style.getIndention()).append(",");
		sb.append(style.getLeftBorderColor()).append(",");
		sb.append(style.getRightBorderColor()).append(",");
		sb.append(style.getRotation()).append(",");
		sb.append(style.getTopBorderColor()).append(",");
		sb.append(style.getVerticalAlignment());

		return sb.toString();
	}

	public static String dumpFont(HSSFFont font) {
		StringBuffer sb = new StringBuffer();
		sb.append(font.getItalic()).append(",").append(font.getStrikeout()).append(",").append(font.getBold())
				.append(",").append(font.getCharSet()).append(",").append(font.getColor()).append(",")
				.append(font.getFontHeight()).append(",").append(font.getFontName()).append(",")
				.append(font.getTypeOffset()).append(",").append(font.getUnderline());
		return sb.toString();
	}

	public static void copyCellStyle(HSSFWorkbook destwb, HSSFCell dest, HSSFWorkbook srcwb, HSSFCell src) {
		if (src == null || dest == null)
			return;

		HSSFCellStyle nstyle = findStyle(src.getCellStyle(), srcwb, destwb);
		if (nstyle == null) {
			nstyle = destwb.createCellStyle();
			copyCellStyle(destwb, nstyle, srcwb, src.getCellStyle());
		}
		dest.setCellStyle(nstyle);
	}

	private static boolean isSameColor(short a, short b, HSSFPalette apalette, HSSFPalette bpalette) {
		if (a == b)
			return true;
		HSSFColor acolor = apalette.getColor(a);
		HSSFColor bcolor = bpalette.getColor(b);
		if (acolor == null)
			return true;
		if (bcolor == null)
			return false;
		return acolor.getHexString().equals(bcolor.getHexString());
	}

	private static short findColor(short index, HSSFWorkbook srcwb, HSSFWorkbook destwb) {
		Integer id = new Integer(index);
		if (HSSFColor.getIndexHash().containsKey(id))
			return index;
//		if (index == HSSFColor.AUTOMATIC.index)
//			return index;
		HSSFColor color = srcwb.getCustomPalette().getColor(index);
		if (color == null) {
			return index;
		}

		HSSFColor ncolor = destwb.getCustomPalette().findColor((byte) color.getTriplet()[0],
				(byte) color.getTriplet()[1], (byte) color.getTriplet()[2]);
		if (ncolor != null)
			return ncolor.getIndex();
		destwb.getCustomPalette().setColorAtIndex(index, (byte) color.getTriplet()[0], (byte) color.getTriplet()[1],
				(byte) color.getTriplet()[2]);
		return index;
	}

	public static HSSFCellStyle findStyle(HSSFCellStyle style, HSSFWorkbook srcwb, HSSFWorkbook destwb) {
		HSSFPalette srcpalette = srcwb.getCustomPalette();
		HSSFPalette destpalette = destwb.getCustomPalette();

		for (short i = 0; i < destwb.getNumCellStyles(); i++) {
			HSSFCellStyle old = destwb.getCellStyleAt(i);
			if (old == null)
				continue;

			if (style.getAlignment() == old.getAlignment() && style.getBorderBottom() == old.getBorderBottom()
					&& style.getBorderLeft() == old.getBorderLeft() && style.getBorderRight() == old.getBorderRight()
					&& style.getBorderTop() == old.getBorderTop()
					&& isSameColor(style.getBottomBorderColor(), old.getBottomBorderColor(), srcpalette, destpalette)
					&& style.getDataFormat() == old.getDataFormat()
					&& isSameColor(style.getFillBackgroundColor(), old.getFillBackgroundColor(), srcpalette,
							destpalette)
					&& isSameColor(style.getFillForegroundColor(), old.getFillForegroundColor(), srcpalette,
							destpalette)
					&& style.getFillPattern() == old.getFillPattern() && style.getHidden() == old.getHidden()
					&& style.getIndention() == old.getIndention()
					&& isSameColor(style.getLeftBorderColor(), old.getLeftBorderColor(), srcpalette, destpalette)
					&& style.getLocked() == old.getLocked()
					&& isSameColor(style.getRightBorderColor(), old.getRightBorderColor(), srcpalette, destpalette)
					&& style.getRotation() == old.getRotation()
					&& isSameColor(style.getTopBorderColor(), old.getTopBorderColor(), srcpalette, destpalette)
					&& style.getVerticalAlignment() == old.getVerticalAlignment()
					&& style.getWrapText() == old.getWrapText()) {

				HSSFFont oldfont = destwb.getFontAt(old.getFontIndex());
				HSSFFont font = srcwb.getFontAt(style.getFontIndex());
				if (oldfont.getBold() == font.getBold() && oldfont.getItalic() == font.getItalic()
						&& oldfont.getStrikeout() == font.getStrikeout() && oldfont.getCharSet() == font.getCharSet()
						&& isSameColor(oldfont.getColor(), font.getColor(), srcpalette, destpalette)
						&& oldfont.getFontHeight() == font.getFontHeight()
						&& oldfont.getFontName().equals(font.getFontName())
						&& oldfont.getTypeOffset() == font.getTypeOffset()
						&& oldfont.getUnderline() == font.getUnderline()) {
					return old;
				}
			}
		}
		return null;
	}

	public static void copyCellStyle(HSSFWorkbook destwb, HSSFCellStyle dest, HSSFWorkbook srcwb, HSSFCellStyle src) {
		if (src == null || dest == null)
			return;
		dest.setAlignment(src.getAlignment());
		dest.setBorderBottom(src.getBorderBottom());
		dest.setBorderLeft(src.getBorderLeft());
		dest.setBorderRight(src.getBorderRight());
		dest.setBorderTop(src.getBorderTop());
		dest.setBottomBorderColor(findColor(src.getBottomBorderColor(), srcwb, destwb));
		dest.setDataFormat(
				destwb.createDataFormat().getFormat(srcwb.createDataFormat().getFormat(src.getDataFormat())));
		dest.setFillPattern(src.getFillPattern());
		dest.setFillForegroundColor(findColor(src.getFillForegroundColor(), srcwb, destwb));
		dest.setFillBackgroundColor(findColor(src.getFillBackgroundColor(), srcwb, destwb));
		dest.setHidden(src.getHidden());
		dest.setIndention(src.getIndention());
		dest.setLeftBorderColor(findColor(src.getLeftBorderColor(), srcwb, destwb));
		dest.setLocked(src.getLocked());
		dest.setRightBorderColor(findColor(src.getRightBorderColor(), srcwb, destwb));
		dest.setRotation(src.getRotation());
		dest.setTopBorderColor(findColor(src.getTopBorderColor(), srcwb, destwb));
		dest.setVerticalAlignment(src.getVerticalAlignment());
		dest.setWrapText(src.getWrapText());

		HSSFFont f = srcwb.getFontAt(src.getFontIndex());
		HSSFFont nf = findFont(f, srcwb, destwb);
		if (nf == null) {
			nf = destwb.createFont();
			nf.setBold(f.getBold());
			nf.setCharSet(f.getCharSet());
			nf.setColor(findColor(f.getColor(), srcwb, destwb));
			nf.setFontHeight(f.getFontHeight());
			nf.setFontHeightInPoints(f.getFontHeightInPoints());
			nf.setFontName(f.getFontName());
			nf.setItalic(f.getItalic());
			nf.setStrikeout(f.getStrikeout());
			nf.setTypeOffset(f.getTypeOffset());
			nf.setUnderline(f.getUnderline());
		}
		dest.setFont(nf);
	}

	private static HSSFFont findFont(HSSFFont font, HSSFWorkbook src, HSSFWorkbook dest) {
		for (short i = 0; i < dest.getNumberOfFonts(); i++) {
			HSSFFont oldfont = dest.getFontAt(i);
			if (font.getBold() == oldfont.getBold() && font.getItalic() == oldfont.getItalic()
					&& font.getStrikeout() == oldfont.getStrikeout() && font.getCharSet() == oldfont.getCharSet()
					&& font.getColor() == oldfont.getColor() && font.getFontHeight() == oldfont.getFontHeight()
					&& font.getFontName().equals(oldfont.getFontName())
					&& font.getTypeOffset() == oldfont.getTypeOffset()
					&& font.getUnderline() == oldfont.getUnderline()) {
				return oldfont;
			}
		}
		return null;
	}

	public static void copySheet(HSSFWorkbook destwb, HSSFSheet dest, HSSFWorkbook srcwb, HSSFSheet src) {
		if (src == null || dest == null)
			return;

		copySheetStyle(destwb, dest, srcwb, src);

		for (int i = 0; i <= src.getLastRowNum(); i++) {
			HSSFRow row = src.getRow(i);
			copyRow(destwb, dest.createRow(i), srcwb, row);
		}
	}

	public static void copyRow(HSSFWorkbook destwb, HSSFRow dest, HSSFWorkbook srcwb, HSSFRow src) {
		if (src == null || dest == null)
			return;
		for (short i = 0; i <= src.getLastCellNum(); i++) {
			if (src.getCell(i) != null) {
				HSSFCell cell = dest.createCell(i);
				copyCell(destwb, cell, srcwb, src.getCell(i));
			}
		}

	}

	public static void copyCell(HSSFWorkbook destwb, HSSFCell dest, HSSFWorkbook srcwb, HSSFCell src) {
		if (src == null) {
			dest.setCellType(CellType.BLANK);
			return;
		}

		if (src.getCellComment() != null)
			dest.setCellComment(src.getCellComment());
		if (src.getCellStyle() != null) {
			HSSFCellStyle nstyle = findStyle(src.getCellStyle(), srcwb, destwb);
			if (nstyle == null) {
				nstyle = destwb.createCellStyle();
				copyCellStyle(destwb, nstyle, srcwb, src.getCellStyle());
			}
			dest.setCellStyle(nstyle);
		}
		dest.setCellType(src.getCellType());

		switch (src.getCellType()) {
		case BLANK:

			break;
		case BOOLEAN:
			dest.setCellValue(src.getBooleanCellValue());
			break;
		case FORMULA:
			dest.setCellFormula(src.getCellFormula());
			break;
		case ERROR:
			dest.setCellErrorValue(src.getErrorCellValue());
			break;
		case NUMERIC:
			dest.setCellValue(src.getNumericCellValue());
			break;
		default:
			dest.setCellValue(new HSSFRichTextString(src.getRichStringCellValue().getString()));
			break;
		}
	}

	/************************************************************************************************/
	/************************************************************************************************/

	// excel表格操作
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFRow row;

	public ExcelUtil2(String sheetname) {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet(sheetname);
	}

	public ExcelUtil2() {
		this("sheet");
	}

	public ExcelUtil2(Class class1) {
	}

	public HSSFWorkbook getwork() {
		return workbook;
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public void setRow(int rownum) {
		// 创建行
		row = sheet.createRow(rownum);
	}

	public void setCell(int rownum, int cellNumber, String value) {
		// 创建单元格
		row = sheet.createRow(rownum);
		HSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(value);
	}

	public void setCell(int rownum, int cellNumber, int value) {
		row = sheet.createRow(rownum);
		HSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(value);
	}

	public void setCell(int cellNumber, int value) {
		if (row == null)
			row = sheet.createRow(0);// 不输入行号，自动创建为第0行
		HSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(value);
	}

	public void setCell(int cellNumber, String value) {
		if (row == null)
			row = sheet.createRow(0);
		HSSFCell cell = row.createCell(cellNumber);
		cell.setCellValue(value);
	}

	public void createFile(String fileName) throws IOException {
		// 生成表格
		FileOutputStream outputStream = new FileOutputStream(fileName);
		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/

	private static Map<String, Short> styleMap = new HashMap<String, Short>();

	/**
	 * 读取excel文档，返回二维的list
	 * 
	 * @param is
	 *            excel文档输入流
	 * @param fileName
	 *            文件名
	 * @return excel中的表格数据
	 */
	public static List readExcel(InputStream is, String fileName) {
		Workbook workbook = null;
		List res = new ArrayList();
		try {
			if (fileName.indexOf(".xlsx") >= 0) {
				// workbook = new XSSFWorkbook(is);
			} else {
				workbook = new HSSFWorkbook(is);
			}
			Sheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getPhysicalNumberOfRows();
			for (int i = 0; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				List rowValue = new ArrayList();
				if (row != null) {
					int columnCount = row.getPhysicalNumberOfCells();
					for (int j = 0; j < columnCount; j++) {
						Cell cell = row.getCell(j);
						Object value = getValue(cell, null);
						rowValue.add(value);
					}
				}
				res.add(rowValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 读取excel文档，并通过反射直接赋值到实体类
	 * 
	 * @param is
	 *            excel文档输入流
	 * @param fileName
	 *            文件明，用以判断是否为2007版EXCEL文档
	 * @param clazz
	 *            输出List中的实体类
	 * @return 实体列表
	 */

	public static <T> List<T> readExcel(InputStream is, String fileName, Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		Map<Integer, Method> titleIndex = new LinkedHashMap<Integer, Method>();
		try {
			// 获取标题与实体属性明的映射关系
			Map<String, String> titleMap = (Map<String, String>) clazz.getMethod("getTitleMap").invoke(clazz);
			Workbook workbook = null;
			// 判断是否为2007版excel，并创建相应的workbook
			if (fileName.indexOf(".xlsx") >= 0) {
				// workbook = new XSSFWorkbook(is);
			} else {
				workbook = new HSSFWorkbook(is);
			}

			// 通过excel中的标题列获取excel中列与反射的实体类属性set方法的映射
			Sheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getPhysicalNumberOfRows();
			Row titles = sheet.getRow(0);
			for (int i = 0; i < titles.getPhysicalNumberOfCells(); i++) {
				Cell cell = titles.getCell(i);
				String title = cell.getStringCellValue().trim();
				String titleCode = titleMap.get(title);
				if (titleCode != null) {
					Field field = clazz.getDeclaredField(titleCode);
					Method setMethod = clazz.getMethod("set" + firstCharToUpperCase(titleCode), field.getType());
					titleIndex.put(i, setMethod);
				}
			}
			// 获取数据列，并创建实体类，对实体类属性进行赋值
			for (int i = 1; i < rowCount; i++) {
				T o = clazz.newInstance();
				for (Integer index : titleIndex.keySet()) {
					Method m = titleIndex.get(index);
					Class pType = m.getParameterTypes()[0];
					Object value = getValue(sheet, i, index, pType);
					titleIndex.get(index).invoke(o, value);
				}
				result.add(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 到出数据到excel
	 * 
	 * @param data
	 *            待导出的数据
	 * @param os
	 *            导出的excel输出流
	 * @param titleMap
	 *            导出的数据的标题与属性名映射
	 */
	public static void writeExcel(List data, OutputStream os, Map<String, String> titleMap) {
		styleMap.clear();
		try {
			// 创建Excel的工作书册 Workbook,对应到一个excel文档
			Workbook wb = new HSSFWorkbook();
			Sheet sheet = null;
			Set<String> titles = titleMap.keySet();
			List<Method> methods = new ArrayList<Method>();
			Object o = data.get(0);
			Class<?> clazz = o.getClass();
			for (String title : titles) {
				Method getMethod = clazz.getMethod("get" + firstCharToUpperCase(titleMap.get(title)));
				methods.add(getMethod);
			}
			for (int i = 0, j = 0, k = 0; i < data.size(); i++, j++, k = 0) {
				if (i == 0 || i % 65000 == 0) {
					sheet = wb.createSheet("sheet" + ((int) i / 65000 + 1));
					for (String title : titles) {
						setValue(sheet, 0, k++, title);
					}
					k = 0;
					j = 1;
				}
				o = data.get(i);
				for (Method method : methods) {
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
	 * 
	 * @param data
	 *            待导出的数据
	 * @param temp
	 *            模板文件
	 * @param os
	 *            导出的excel输出流
	 * @param startRow
	 *            数据开始写入的行
	 * @param startColumn
	 *            数据开始写入的列
	 */
	public static void writeExcel(List data, File temp, OutputStream os, int startRow, int startColumn) {
		styleMap.clear();
		try {
			Workbook wb = null;
			String filename = temp.getName();
			boolean is2007 = filename.indexOf(".xlsx") >= 0;
			if (is2007) {
				// wb = new XSSFWorkbook(new FileInputStream(temp));
			} else {
				wb = new HSSFWorkbook(new FileInputStream(temp));
			}
			Sheet sheet = wb.getSheetAt(0);
			Row titleRow = sheet.getRow(0);
			Row tempRow = sheet.getRow(startRow);
			Map<String, CellStyle> formats = new LinkedHashMap<String, CellStyle>();
			int c = startColumn;
			for (Cell tempCell : tempRow) {
				String attr = tempCell.getStringCellValue();
				CellStyle format = tempCell.getCellStyle();
				formats.put(attr, format);
			}
			for (int i = 0, j = startRow; i < data.size(); i++, j++) {
				if (i != 0 && i % 65000 == 0) {
					sheet = wb.createSheet("sheet" + ((int) i / 65000 + 1));
					for (int k = 0; k < titleRow.getPhysicalNumberOfCells(); k++) {
						setValue(sheet, 0, k, titleRow.getCell(k).getStringCellValue(),
								titleRow.getCell(k).getCellStyle(), -1);
					}
					j = startRow;
				}
				Object o = data.get(i);
				Class<?> clazz = o.getClass();
				for (String key : formats.keySet()) {
					Method getMethod = clazz.getMethod("get" + firstCharToUpperCase(key));
					Object value = getMethod.invoke(o);
					setValue(sheet, j, c++, value, formats.get(key), -1);
				}
				c = startColumn;
			}
			wb.write(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取指定单元格指定类型的值
	 * 
	 * @param cell
	 *            单元格对象
	 * @param type
	 *            需要的返回值类型
	 * @return 单元格值
	 */
	public static Object getValue(Cell cell, Class<?> type) {
		Object value = getValue(cell);
		if (type == null)
			return value;
		if (type.equals(String.class)) {
			return String.valueOf(value);
		} else if (type.equals(Date.class)) {
			if (value instanceof Date) {
				return value;
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				try {
					return sdf.parse((String) value);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} else if (type.equals(Byte.class)) {
			if (value instanceof String) {
				return Byte.parseByte((String) value);
			} else {
				return ((Double) value).byteValue();
			}
		} else if (type.equals(Integer.class)) {
			if (value instanceof String) {
				return Integer.parseInt((String) value);
			} else {
				return ((Double) value).intValue();
			}
		} else if (type.equals(Double.class)) {
			if (value instanceof String) {
				return Double.parseDouble((String) value);
			} else {
				return value;
			}
		}
		return value;
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 *            单元格对象
	 * @return 单元格值
	 */
	public static Object getValue(Cell cell) {

		switch (cell.getCellType()) {
		case BLANK:
			return null;
		case BOOLEAN:
			return cell.getBooleanCellValue();
		case ERROR:
			return cell.getErrorCellValue();
		case FORMULA:
			return cell.getCellFormula();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}
		case STRING:
			return cell.getStringCellValue();
		default:
			return cell.getStringCellValue();
		}
	}

	public static void removeCell(Sheet sheet, int r, int c) {
		Row row = getRow(sheet, r);
		Cell cell = getCell(row, c);
		row.removeCell(cell);
	}

	/**
	 * 设置单元格的值
	 * 
	 * @param sheet
	 *            工作表
	 * @param r
	 *            行
	 * @param c
	 *            列
	 * @param value
	 *            值
	 * @param style
	 *            单元格样式
	 */
	public static void setValue(Sheet sheet, int r, int c, Object value, CellStyle style, int cellType,
			float rowHeight) {
		Cell cell = getCell(getRow(sheet, r), c);
		if (rowHeight != 0) {
			cell.getRow().setHeightInPoints(rowHeight);
		}
		if (style != null) {
			cell.setCellStyle(style);
		}
		if (value == null) {
			return;
		} else if (value instanceof Number) {
			cell.setCellValue(((Number) value).doubleValue());
			if (style == null) {
				cell.setCellStyle(getFormat(sheet.getWorkbook(), "#,##0.00"));
			}
		} else if (value instanceof Date) {
			cell.setCellValue((Date) value);
			if (style == null)
				cell.setCellStyle(getFormat(sheet.getWorkbook(), "yyyy年MM月dd日"));
		} else if (value instanceof String && cellType == CellType.FORMULA.getCode()) {
			cell.setCellFormula((String) value);
		} else {
			cell.setCellValue(String.valueOf(value));
		}
	}

	public static void setValue(Sheet sheet, int r, int c, Object value, CellStyle style, int cellType) {
		setValue(sheet, r, c, value, null, cellType, 0);
	}

	/**
	 * 设置单元格的值
	 * 
	 * @param sheet
	 *            工作表
	 * @param r
	 *            行
	 * @param c
	 *            列
	 * @param value
	 *            值
	 */
	public static void setValue(Sheet sheet, int r, int c, Object value) {
		setValue(sheet, r, c, value, null, -1);
	}

	/**
	 * 获取excel行
	 * 
	 * @param rowCounter
	 *            行序号
	 * @param sheet
	 *            工作表
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
	 * 
	 * @param row
	 *            行对象
	 * @param column
	 *            纵序号
	 * @return 单元格对象
	 */
	public static Cell getCell(Row row, int column) {
		Cell cell = row.getCell(column);
		if (cell == null) {
			cell = row.createCell(column);
		}
		return cell;
	}

	public static Cell getCell(Sheet sheet, int r, int c) {
		return getCell(getRow(sheet, r), c);
	}

	/**
	 * 获取单元格值
	 * 
	 * @param sheet
	 *            工作表对象
	 * @param row
	 *            行序号
	 * @param column
	 *            纵序号
	 * @param type
	 *            值类型
	 * @return 单元格的值
	 */
	public static Object getValue(Sheet sheet, int row, int column, Class<?> type) {
		Cell cell = getCell(getRow(sheet, row), column);
		return getValue(cell, type);
	}

	public static CellStyle getFormat(Workbook wb, String p) {
		Short index = styleMap.get(p);
		CellStyle style = null;
		if (index == null) {
			style = wb.createCellStyle();
			DataFormat df = wb.createDataFormat();
			style.setDataFormat(df.getFormat(p));
			style.setWrapText(true);
			styleMap.put(p, style.getIndex());
		} else {
			style = wb.getCellStyleAt(index);
		}
		return style;
	}

	/**
	 * poi样式示例
	 */
	public void writeExcel() {
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
//		font.setBold((short) 100);
		font.setFontHeight((short) 300);
//		font.setColor(HSSFColor.BLUE.index);

		// 创建单元格样式
		HSSFCellStyle style = wb.createCellStyle();
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// 设置边框
//		style.setBottomBorderColor(HSSFColor.RED.index);
//		style.setBorderBottom(CellType.BORDER_THIN);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);

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
		HSSFHyperlink link = cell.getHyperlink();
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

	public static void main(String[] args) {
		new ExcelUtil2().writeExcel();
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

	/*****************************************************************************************/
	/*****************************************************************************************/
	/*****************************************************************************************/

	/**
	 * 
	 * @param title
	 *            Sheet名字
	 * @param pojoClass
	 *            Excel对象Class
	 * @param dataSet
	 *            Excel对象数据List
	 * @param out
	 *            输出流
	 */
	public static void exportExcel(String title, Class<?> pojoClass, Collection<?> dataSet, OutputStream out) {
		// 使用userModel模式实现的，当excel文档出现10万级别的大数据文件可能导致OOM内存溢出
		exportExcelInUserModel(title, pojoClass, dataSet, out);
		// 使用eventModel实现，可以一边读一边处理，效率较高，但是实现复杂，暂时未实现
	}

	private static void exportExcelInUserModel(String title, Class<?> pojoClass, Collection<?> dataSet,
			OutputStream out) {
		try {
			// 首先检查数据看是否是正确的
			if (dataSet == null || dataSet.size() == 0) {
				throw new Exception("导出数据为空！");
			}
			if (title == null || out == null || pojoClass == null) {
				throw new Exception("传入参数不能为空！");
			}
			// 声明一个工作薄
			Workbook workbook = new HSSFWorkbook();
			// 生成一个表格
			Sheet sheet = workbook.createSheet(title);

			// 标题
			List<String> exportFieldTitle = new ArrayList<String>();
			List<Integer> exportFieldWidth = new ArrayList<Integer>();
			// 拿到所有列名，以及导出的字段的get方法
			List<Method> methodObj = new ArrayList<Method>();
			Map<String, Method> convertMethod = new HashMap<String, Method>();
			// 得到所有字段
			Field fileds[] = pojoClass.getDeclaredFields();
			// 遍历整个filed
			for (int i = 0; i < fileds.length; i++) {
				Field field = fileds[i];
				Excel excel = field.getAnnotation(Excel.class);
				// 如果设置了annottion
				if (excel != null) {
					// 添加到标题
					exportFieldTitle.add(excel.exportName());
					// 添加标题的列宽
					exportFieldWidth.add(excel.exportFieldWidth());
					// 添加到需要导出的字段的方法
					String fieldname = field.getName();
					// System.out.println(i+"列宽"+excel.exportName()+" "+excel.exportFieldWidth());
					StringBuffer getMethodName = new StringBuffer("get");
					getMethodName.append(fieldname.substring(0, 1).toUpperCase());
					getMethodName.append(fieldname.substring(1));

					Method getMethod = pojoClass.getMethod(getMethodName.toString(), new Class[] {});

					methodObj.add(getMethod);
					if (excel.exportConvertSign() == 1) {
						StringBuffer getConvertMethodName = new StringBuffer("get");
						getConvertMethodName.append(fieldname.substring(0, 1).toUpperCase());
						getConvertMethodName.append(fieldname.substring(1));
						getConvertMethodName.append("Convert");
						// System.out.println("convert: "+getConvertMethodName.toString());
						Method getConvertMethod = pojoClass.getMethod(getConvertMethodName.toString(), new Class[] {});
						convertMethod.put(getMethodName.toString(), getConvertMethod);
					}
				}
			}
			int index = 0;
			// 产生表格标题行
			Row row = sheet.createRow(index);
			for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
				Cell cell = row.createCell(i);
				// cell.setCellStyle(style);
				RichTextString text = new HSSFRichTextString(exportFieldTitle.get(i));
				cell.setCellValue(text);
			}

			// 设置每行的列宽
			for (int i = 0; i < exportFieldWidth.size(); i++) {
				// 256=65280/255
				sheet.setColumnWidth(i, 256 * exportFieldWidth.get(i));
			}
			Iterator its = dataSet.iterator();
			// 循环插入剩下的集合
			while (its.hasNext()) {
				// 从第二行开始写，第一行是标题
				index++;
				row = sheet.createRow(index);
				Object t = its.next();
				for (int k = 0, methodObjSize = methodObj.size(); k < methodObjSize; k++) {
					Cell cell = row.createCell(k);
					Method getMethod = methodObj.get(k);
					Object value = null;
					if (convertMethod.containsKey(getMethod.getName())) {
						Method cm = convertMethod.get(getMethod.getName());
						value = cm.invoke(t, new Object[] {});
					} else {
						value = getMethod.invoke(t, new Object[] {});
					}
					cell.setCellValue(value.toString());
				}
			}

			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param title
	 *            Sheet名字
	 * @param pojoClass
	 *            Excel对象Class
	 * @param dataSet
	 *            Excel对象数据List
	 * @param out
	 *            输出流
	 */
	public static HSSFWorkbook exportExcel(String title, Class<?> pojoClass, Collection<?> dataSet) {
		// 使用userModel模式实现的，当excel文档出现10万级别的大数据文件可能导致OOM内存溢出
		return exportExcelInUserModel2File(title, pojoClass, dataSet);
	}

	@SuppressWarnings("unchecked")
	private static HSSFWorkbook exportExcelInUserModel2File(String title, Class<?> pojoClass, Collection<?> dataSet) {
		// 声明一个工作薄
		HSSFWorkbook workbook = null;
		try {
			// 首先检查数据看是否是正确的
			// if (dataSet == null || dataSet.size() == 0) {
			// throw new Exception("导出数据为空！");
			// }
			// if (title == null || pojoClass == null) {
			// throw new Exception("传入参数不能为空！");
			// }
			// 声明一个工作薄
			workbook = new HSSFWorkbook();
			// 生成一个表格
			Sheet sheet = workbook.createSheet(title);

			// 标题
			List<String> exportFieldTitle = new ArrayList<String>();
			List<Integer> exportFieldWidth = new ArrayList<Integer>();
			// 拿到所有列名，以及导出的字段的get方法
			List<Method> methodObj = new ArrayList<Method>();
			Map<String, Method> convertMethod = new HashMap<String, Method>();
			// 得到所有字段
			Field fileds[] = pojoClass.getDeclaredFields();
			// 遍历整个filed
			for (int i = 0; i < fileds.length; i++) {
				Field field = fileds[i];
				Excel excel = field.getAnnotation(Excel.class);
				// 如果设置了annottion
				if (excel != null) {
					// 添加到标题
					exportFieldTitle.add(excel.exportName());
					// 添加标题的列宽
					exportFieldWidth.add(excel.exportFieldWidth());
					// 添加到需要导出的字段的方法
					String fieldname = field.getName();
					// System.out.println(i+"列宽"+excel.exportName()+" "+excel.exportFieldWidth());
					StringBuffer getMethodName = new StringBuffer("get");
					getMethodName.append(fieldname.substring(0, 1).toUpperCase());
					getMethodName.append(fieldname.substring(1));

					Method getMethod = pojoClass.getMethod(getMethodName.toString(), new Class[] {});

					methodObj.add(getMethod);
					if (excel.exportConvertSign() == 1) {
						StringBuffer getConvertMethodName = new StringBuffer("get");
						getConvertMethodName.append(fieldname.substring(0, 1).toUpperCase());
						getConvertMethodName.append(fieldname.substring(1));
						getConvertMethodName.append("Convert");
						// System.out.println("convert: "+getConvertMethodName.toString());
						Method getConvertMethod = pojoClass.getMethod(getConvertMethodName.toString(), new Class[] {});
						convertMethod.put(getMethodName.toString(), getConvertMethod);
					}
				}
			}
			int index = 0;
			// 产生表格标题行
			Row row = sheet.createRow(index);
			for (int i = 0, exportFieldTitleSize = exportFieldTitle.size(); i < exportFieldTitleSize; i++) {
				Cell cell = row.createCell(i);
				// cell.setCellStyle(style);
				RichTextString text = new HSSFRichTextString(exportFieldTitle.get(i));
				cell.setCellValue(text);
			}

			// 设置每行的列宽
			for (int i = 0; i < exportFieldWidth.size(); i++) {
				// 256=65280/255
				sheet.setColumnWidth(i, 256 * exportFieldWidth.get(i));
			}
			Iterator its = dataSet.iterator();
			// 循环插入剩下的集合
			while (its.hasNext()) {
				// 从第二行开始写，第一行是标题
				index++;
				row = sheet.createRow(index);
				Object t = its.next();
				for (int k = 0, methodObjSize = methodObj.size(); k < methodObjSize; k++) {
					Cell cell = row.createCell(k);
					Method getMethod = methodObj.get(k);
					Object value = null;
					if (convertMethod.containsKey(getMethod.getName())) {
						Method cm = convertMethod.get(getMethod.getName());
						value = cm.invoke(t, new Object[] {});
					} else {
						value = getMethod.invoke(t, new Object[] {});
					}
					cell.setCellValue(value == null ? "" : value.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}
	/*****************************************************************************************/
	/*****************************************************************************************/
	/*****************************************************************************************/

	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/
	/************************************************************************************************/
}
