package com.jun.plugin.poi;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import com.sun.media.sound.InvalidFormatException;

/**
 * @author Yu Jou 2014年7月11日
 */
public class ExcelUtils {

	/**
	 * Executes the specified command and arguments in a separate process, and waits for the
	 * process to finish.
	 * <p>
	 * Output from the process is expected to be text in the system's default character set.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param cmdarray array containing the command to call and its arguments.
	 * @return The results of the execution in an ExcelUtils object.
	 * @throws SecurityException if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException - if an I/O error occurs
	 * @throws NullPointerException - if cmdarray is null
	 * @throws IndexOutOfBoundsException - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtils exec(String[] cmdarray) throws IOException {
		return new ExcelUtils(Runtime.getRuntime().exec(cmdarray), null);
	}

	/**
	 * Executes the specified command and arguments in a separate process, and waits for the
	 * process to finish.
	 * <p>
	 * Output from the process is expected to be text in the system's default character set.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param cmdarray array containing the command to call and its arguments.
	 * @param envp array of strings, each element of which has environment variable settings in format name=value.
	 * @return The results of the execution in an ExcelUtils object.
	 * @throws SecurityException if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException - if an I/O error occurs
	 * @throws NullPointerException - if cmdarray is null
	 * @throws IndexOutOfBoundsException - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtils exec(String[] cmdarray, String[] envp) throws IOException {
		return new ExcelUtils(Runtime.getRuntime().exec(cmdarray, envp), null);
	}

	/**
	 * Executes the specified command and arguments in a separate process, and waits for the
	 * process to finish.
	 * <p>
	 * Output from the process is expected to be text in the system's default character set.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param cmdarray array containing the command to call and its arguments.
	 * @param envp array of strings, each element of which has environment variable settings in format name=value.
	 * @param dir the working directory of the subprocess, or null if the subprocess should inherit the working directory of the current process.
	 * @return The results of the execution in an ExcelUtils object.
	 * @throws SecurityException if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException - if an I/O error occurs
	 * @throws NullPointerException - if cmdarray is null
	 * @throws IndexOutOfBoundsException - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtils exec(String[] cmdarray, String[] envp, File dir) throws IOException {
		return new ExcelUtils(Runtime.getRuntime().exec(cmdarray, envp), null);
	}

	/**
	 * Executes the specified command and arguments in a separate process, and waits for the
	 * process to finish.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param cmdarray array containing the command to call and its arguments.
	 * @param charset Output from the executed command is expected to be in this character set.
	 * @return The results of the execution in an ExcelUtils object.
	 * @throws SecurityException if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException - if an I/O error occurs
	 * @throws NullPointerException - if cmdarray is null
	 * @throws IndexOutOfBoundsException - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtils exec(String[] cmdarray, String charset) throws IOException {
		return new ExcelUtils(Runtime.getRuntime().exec(cmdarray), charset);
	}

	/**
	 * Executes the specified command and arguments in a separate process, and waits for the
	 * process to finish.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param cmdarray array containing the command to call and its arguments.
	 * @param envp array of strings, each element of which has environment variable settings in format name=value.
	 * @param charset Output from the executed command is expected to be in this character set.
	 * @return The results of the execution in an ExcelUtils object.
	 * @throws SecurityException if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException - if an I/O error occurs
	 * @throws NullPointerException - if cmdarray is null
	 * @throws IndexOutOfBoundsException - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtils exec(String[] cmdarray, String[] envp, String charset) throws IOException {
		return new ExcelUtils(Runtime.getRuntime().exec(cmdarray, envp), charset);
	}

	/**
	 * Executes the specified command and arguments in a separate process, and waits for the
	 * process to finish.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param cmdarray array containing the command to call and its arguments.
	 * @param envp array of strings, each element of which has environment variable settings in format name=value.
	 * @param dir the working directory of the subprocess, or null if the subprocess should inherit the working directory of the current process.
	 * @param charset Output from the executed command is expected to be in this character set.
	 * @return The results of the execution in an ExcelUtils object.
	 * @throws SecurityException if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException - if an I/O error occurs
	 * @throws NullPointerException - if cmdarray is null
	 * @throws IndexOutOfBoundsException - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtils exec(String[] cmdarray, String[] envp, File dir, String charset) throws IOException {
		return new ExcelUtils(Runtime.getRuntime().exec(cmdarray, envp), charset);
	}

	/**
	 * Executes the specified command using a shell.  On windows uses cmd.exe or command.exe.
	 * On other platforms it uses /bin/sh.
	 * <p>
	 * A shell should be used to execute commands when features such as file redirection, pipes,
	 * argument parsing are desired.
	 * <p>
	 * Output from the process is expected to be text in the system's default character set.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param command String containing a command to be parsed by the shell and executed.
	 * @return The results of the execution in an ExcelUtils object.
	 * @throws SecurityException if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException - if an I/O error occurs
	 * @throws NullPointerException - if command is null
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtils execUsingShell(String command) throws IOException {
		return execUsingShell(command, null);
	}

	/**
	 * Executes the specified command using a shell.  On windows uses cmd.exe or command.exe.
	 * On other platforms it uses /bin/sh.
	 * <p>
	 * A shell should be used to execute commands when features such as file redirection, pipes,
	 * argument parsing are desired.
	 * <p>
	 * No input is passed to the process on STDIN.
	 *
	 * @param command String containing a command to be parsed by the shell and executed.
	 * @param charset Output from the executed command is expected to be in this character set.
	 * @return The results of the execution in an ExcelUtils object.
	 * @throws SecurityException if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException - if an I/O error occurs
	 * @throws NullPointerException - if command is null
	 *
	 * @since ostermillerutils 1.06.00
	 */
	public static ExcelUtils execUsingShell(String command, String charset) throws IOException {
		if (command == null) throw new NullPointerException();
		String[] cmdarray;
		String os = System.getProperty("os.name");
		if (os.equals("Windows 95") || os.equals("Windows 98") || os.equals("Windows ME")){
			cmdarray = new String[]{"command.exe", "/C", command};
		} else if (os.startsWith("Windows")){
			cmdarray = new String[]{"cmd.exe", "/C", command};
		} else {
			cmdarray = new String[]{"/bin/sh", "-c", command};
		}
		return new ExcelUtils(Runtime.getRuntime().exec(cmdarray), charset);
	}

	/**
	 * Take a process, record its standard error and standard out streams, wait for it to finish
	 *
	 * @param process process to watch
	 * @throws SecurityException if a security manager exists and its checkExec method doesn't allow creation of a subprocess.
	 * @throws IOException - if an I/O error occurs
	 * @throws NullPointerException - if cmdarray is null
	 * @throws IndexOutOfBoundsException - if cmdarray is an empty array (has length 0).
	 *
	 * @since ostermillerutils 1.06.00
	 */
	private ExcelUtils(Process process, String charset) throws IOException {
		StringBuffer output = new StringBuffer();
		StringBuffer error = new StringBuffer();

		Reader stdout;
		Reader stderr;

		if (charset == null){
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
		while (!done){
			boolean readSomething = false;
			// read from the process's standard output
			if (!stdoutclosed && stdout.ready()){
				readSomething = true;
				int read = stdout.read(buffer, 0, buffer.length);
				if (read < 0){
					readSomething = true;
					stdoutclosed = true;
				} else if (read > 0){
					readSomething = true;
					output.append(buffer, 0, read);
				}
			}
			// read from the process's standard error
			if (!stderrclosed && stderr.ready()){
				int read = stderr.read(buffer, 0, buffer.length);
				if (read < 0){
					readSomething = true;
					stderrclosed = true;
				} else if (read > 0){
					readSomething = true;
					error.append(buffer, 0, read);
				}
			}
			// Check the exit status only we haven't read anything,
			// if something has been read, the process is obviously not dead yet.
			if (!readSomething){
				try {
					this.status = process.exitValue();
					done = true;
				} catch (IllegalThreadStateException itx){
					// Exit status not ready yet.
					// Give the process a little breathing room.
					try {
						Thread.sleep(100);
					} catch (InterruptedException ix){
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
	public String getOutput(){
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
	public String getError(){
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
	public int getStatus(){
		return status;
	}
	/*public static String checkExcel(File execelFile, Integer rowIndex, List<ETL_Rule> constraints ) {
		Workbook wb = null;
		Sheet sheet = null;
		Integer lastRowNum = null;
		Integer numberOfCells = null;
		List<ETL_Property_Column> pcs = new ArrayList<ETL_Property_Column>();
		try {
			for (ETL_Rule rule : constraints) {
				pcs.addAll(rule.getPropertyColumns());
			}
			wb = WorkbookFactory.create(execelFile);
			sheet = wb.getSheetAt(0);
			lastRowNum = sheet.getLastRowNum();
			numberOfCells = sheet.getRow(rowIndex).getPhysicalNumberOfCells();
		} catch (Exception e) {
			return "文件异常，请重新上传";
		}
		Row row = null;
		Cell cell = null;
		for (int i = rowIndex; i <= lastRowNum; i++) {
			row = sheet.getRow(i);
			for (int j = 0; j < numberOfCells; j++) {
				cell = row.getCell(j);
				String cellValue = getCellFormatValue(cell);
				for (ETL_Property_Column pc : pcs) {
					ETL_Column column = pc.getColumn();
					if(column == null){
						continue;
					}
					Integer cellIndex = column.getColumn_cellIndex();
					if(cellIndex == j) {
						String classType = pc.getProperty().getProperty_classType();
						try {
							if(pc.getIsRepeat() && StringUtils.isBlank(cellValue)){
								return column.getColumn_name() + "不能为空";
							} else if ("java.sql.Timestamp".equals(classType)) {
								try {
									Class<?> clzz = Class.forName(classType);
									Method method = clzz.getMethod("valueOf", String.class);
									method.invoke(clzz, cellValue);
								} catch (Exception ee) {
									return "第" + (i + 1) +"行 [" +column.getColumn_name() + "] 日期格式有误,请重新上传";
								}
							}
						} catch (Exception e) {
							return "第" + (i + 1) +"行 [" +column.getColumn_name() + "] 数据错格式有误,请重新上传";
						}
					}
				}
			}
		}
		return null;
	}*/
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
	/*public static Map<Integer, String> readExcelTitle(File execelFile, Integer rowIndex) throws InvalidFormatException, IOException {

		Workbook wb = WorkbookFactory.create(execelFile);
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(rowIndex - 1);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		Map<Integer, String> tileMap = new HashMap<Integer, String>(colNum);

		for (int i = 0; i < colNum; i++) {
			String content = getCellFormatValue(row.getCell(i));
			if (content != null && !content.trim().isEmpty()) {
				tileMap.put(i, getCellFormatValue(row.getCell(i)));
			}
		}

		return tileMap;
	}*/

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
	/*public static List<Map<Integer, String>> readExcelContentList(File execelFile, Integer index) throws InvalidFormatException, IOException {

		List<Map<Integer, String>> data = new ArrayList<Map<Integer, String>>();
		Map<Integer, String> content = null;

		Workbook wb = WorkbookFactory.create(execelFile);
		Sheet sheet = wb.getSheetAt(0);
		Integer lastRowNum = sheet.getLastRowNum();
		Integer numberOfCells = sheet.getRow(index).getPhysicalNumberOfCells();

		Row row = null;
		Cell cell = null;
		for (int i = index; i <= lastRowNum; i++) {
			row = sheet.getRow(i);
			content = new HashMap<Integer, String>();
			for (int j = 0; j < numberOfCells; j++) {
				cell = row.getCell(j);
				String cellValue = getCellFormatValue(cell);
				if (cellValue != null && !cellValue.isEmpty()) {
					content.put(j, cellValue.trim());
				}
			}
			data.add(content);
		}
		return data;
	}*/

	/*public static List<Map<Integer, String>> readExcelContent(File execelFile, Integer... indexs) throws Exception {
		List<Map<Integer, String>> datas = new ArrayList<Map<Integer, String>>();
		Workbook wb = WorkbookFactory.create(execelFile);
		Sheet sheet = wb.getSheetAt(0);
		for (Integer index : indexs) {
			index --;
			Integer numberOfCells = sheet.getRow(index).getPhysicalNumberOfCells();
			Row row = sheet.getRow(index);
			Map<Integer, String> content = new HashMap<Integer, String>();
			for (int j = 0; j < numberOfCells; j++) {
				Cell cell = row.getCell(j);
				String cellValue = getCellFormatValue(cell);
				if (StringUtils.isNotBlank(cellValue)) {
					content.put(j, cellValue.trim());
				}
			}
			datas.add(content);
		}
		return datas;
	}
	 */

	private static String getCellFormatValue(Cell cell) {
		String cellValue = null;
		if (cell == null) {
			return cellValue;
		}
		int cellType = cell.getCellType();
		switch (cellType) {
		case Cell.CELL_TYPE_STRING: {
			cellValue = cell.getRichStringCellValue().getString().trim();
			break;
		}
		case Cell.CELL_TYPE_FORMULA: {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cellValue = cell.getRichStringCellValue().getString().trim();
			break;
		}
		case Cell.CELL_TYPE_NUMERIC: {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				cellValue = sdf.format(date);

			} else {// 如果是纯数字
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String value = cell.getRichStringCellValue().getString().trim();
				;
				if (value.endsWith(".0")) {
					int index = value.lastIndexOf(".0");
					if (index > -1) {
						value = value.substring(0, index);
					}
				}
				cellValue = value;
			}
			break;
		}
		case Cell.CELL_TYPE_BOOLEAN: {
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		}
		default:
			break;
		}
		return cellValue;
	}

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
	/*public static void colorExcel(File sourcelFile, Integer index, List<Integer> rows) {

		if (rows == null || rows.size() <= 0) {
			return;
		}
		FileOutputStream fileOut = null;
		try {
			String destFile = sourcelFile.getAbsolutePath() + "_temp";

			Workbook wb = WorkbookFactory.create(sourcelFile);
			CellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			Sheet sheet = wb.getSheetAt(0);
			Integer numberOfCells = sheet.getRow(index).getPhysicalNumberOfCells();
			System.out.println(numberOfCells);
			Row row = null;
			Cell cell = null;
			for (Integer rowIndex : rows) {
				row = sheet.getRow(index + rowIndex);
				for (int j = 0; j < numberOfCells; j++) {
					if (row != null) {
						cell = row.getCell(j);
						if (cell != null) {
							cell.setCellStyle(style);
						}
					}
				}
			}
			fileOut = new FileOutputStream(destFile);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * 获取指定行内容
	 * 
	 * @param execelFile
	 * @param rows
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	/*public static List<Map<Integer, String>> readExcelRowsContentList(File execelFile, List<Integer> rows) throws InvalidFormatException, IOException {

		List<Map<Integer, String>> data = new ArrayList<Map<Integer, String>>();
		Map<Integer, String> content = null;

		Workbook wb = WorkbookFactory.create(execelFile);
		Sheet sheet = wb.getSheetAt(0);
		Integer numberOfCells = sheet.getRow(0).getPhysicalNumberOfCells();
		Row row = null;
		Cell cell = null;
		for (Integer rowIndex : rows) {
			row = sheet.getRow(rowIndex);
			content = new HashMap<Integer, String>();
			for (int j = 0; j < numberOfCells; j++) {
				cell = row.getCell(j);
				String cellValue = getCellFormatValue(cell);
				if (cellValue != null && !cellValue.isEmpty()) {
					content.put(j, cellValue.trim());
				}
			}
			data.add(content);
		}
		return data;
	}*/
}
