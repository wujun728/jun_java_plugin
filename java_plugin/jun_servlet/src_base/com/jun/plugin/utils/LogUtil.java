package com.jun.plugin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class LogUtil {

	private static Logger logger = Logger.getLogger(LogUtil.class);

	/**
	 * @param args
	 */
	@Test
	public void Test() {
		logger.debug("DEBUG_MSG");
		logger.info("INFO_MSG");
		logger.warn("WARN_MSG");
		logger.error("ERROR_MSG");
		logger.fatal("FATAL_MSG");
	}

	/**
	 * 获取日志对象 LogUtil.getLogger()
	 * 
	 * @return
	 * @return Logger Author：WSK 2013-4-26 下午03:49:31
	 */
	public static org.slf4j.Logger getLogger1() {
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		return LoggerFactory.getLogger(stacks[2].getClassName());
	}

	public static void e(String title, String msg) {
		System.err.println(title + ":" + msg);
	}

	public static void i(String title, String msg) {
		System.out.println(title + ":" + msg);
	}

	@Test
	public void tast() {
		LogUtil.getLogger1().error("info");
	}

	// private static BaseLogUtil objLog = new BaseLogUtil();
	private static final String LOGCONFIG = "log4j.properties";
	private static Logger objLog;

	public static Logger getLogger() {
		if (objLog == null) {
			// DOMConfigurator.configure(getConfigFile());
			objLog = LogManager.getLogger(LogUtil.class);
		}
		return objLog;
	}

	private static String getConfigFile() {
		String s = LogUtil.class.getClassLoader().getResource("").toString();
		String filePath = s + LOGCONFIG;
		return filePath;
	}

	// Start Info
	// /
	// / 记录信息
	// /
	// / 信息内容
	public static void info(String message, Exception exception) {
		try {
			log("INFO", message, exception);
		} catch (Exception ex) {

		}
	}

	// /
	// / 记录信息
	// /
	// / 信息内容
	public static void info(String message) {
		log("INFO", message);
	}
	// end Info

	// Start Error
	public static void trace(String message) {
		try {
			log("TRACE", message);
		} catch (Exception ex) {
		}
	}

	public static void trace(String message, Exception exception) {
		try {
			log("TRACE", message, exception);
		} catch (Exception ex) {
		}
	}

	// /
	// / 记录一个错误信息
	// /
	// / 信息内容
	// / 异常对象
	public static void error(String message, Exception exception) {
		try {
			log("ERROR", message, exception);
		} catch (Exception ex) {

		}
	}

	// /
	// / 记录一个错误信息
	// /
	// / 信息内容
	public static void error(String message) {
		try {
			log("ERROR", message);
		} catch (Exception ex) {

		}
	}

	// end Error

	// Start Warning

	// /
	// / 记录一个警告信息
	// /
	// / 信息内容
	// / 异常对象
	public static void warning(String message, Exception exception) {
		try {
			log("WARN", message, exception);
		} catch (Exception ex) {

		}
	}

	// /
	// / 记录一个警告信息
	// /
	// / 信息内容
	public static void warning(String message) {
		try {
			log("WARN", message);
		} catch (Exception ex) {

		}
	}

	// end Warning

	// Start Fatal

	// /
	// / 记录一个程序致命性错误
	// /
	// / 信息内容
	// / 异常对象
	public static void fatal(String message, Exception exception) {
		try {
			log("FATAL", message, exception);
		} catch (Exception ex) {

		}
	}

	// /
	// / 记录一个程序致命性错误
	// /
	// / 信息内容
	public static void fatal(String message) {
		try {
			log("FATAL", message);
		} catch (Exception ex) {

		}
	}

	// end Fatal

	// Start Debug

	// /
	// / 记录调试信息
	// /
	// / 信息内容
	// / 异常对象
	public static void debug(String message, Exception exception) {
		try {
			log("DEBUG", message, exception);
		} catch (Exception ex) {

		}
	}

	// /
	// / 记录调试信息
	// /
	// / 信息内容
	public static void debug(String message) {
		try {
			log("DEBUG", message);
		} catch (Exception ex) {

		}
	}
	// end Debug

	public static void log(String level, Object msg) {
		log(level, msg, null);
	}

	public static void log(String level, Throwable e) {
		log(level, null, e);
	}

	public static void log(String level, Object msg, Throwable e) {
		try {
			StringBuilder sb = new StringBuilder();
			Throwable t = new Throwable();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			String input = sw.getBuffer().toString();
			StringReader sr = new StringReader(input);
			BufferedReader br = new BufferedReader(sr);
			for (int i = 0; i < 4; i++)
				br.readLine();
			String line = br.readLine();
			// at
			// com.media.web.action.DicManageAction.list(DicManageAction.java:89)
			int paren = line.indexOf("at ");
			line = line.substring(paren + 3);
			paren = line.indexOf('(');
			String invokeInfo = line.substring(0, paren);
			int period = invokeInfo.lastIndexOf('.');
			sb.append('[');
			sb.append(invokeInfo.substring(0, period));
			sb.append(':');
			sb.append(invokeInfo.substring(period + 1));
			sb.append("():");
			paren = line.indexOf(':');
			period = line.lastIndexOf(')');
			sb.append(line.substring(paren + 1, period));
			sb.append(']');
			sb.append(" - ");
			sb.append(msg);
			getLogger().log((Priority) Level.toLevel(level), sb.toString(), e);
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		}
	}
	
	
	/*******************************************************************************************/
	/*******************************************************************************************/
	/*******************************************************************************************/
	

	private static LogUtil logFactory = new LogUtil();
	

	public static LogUtil getInstance() {
		return logFactory;
	}
	
	/**
	 * 获取默认的Logger，默认为控制台
	 * 
	 * @return Logger
	 */
	public static org.slf4j.Logger getLogger331() {
		return LoggerFactory.getLogger("stdout");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private PrintWriter logPrint;
	private String logFile = "";
	private String logName = "";

//	public LogFactory() {
//		checkDate();
//	}

//	public LogFactory(String logName) {
//		this.logName = logName;
//		checkDate();
//	}

	private String getLogFile() {
		String date = "";
		Calendar cd = Calendar.getInstance();
		int y = cd.get(Calendar.YEAR);
		int m = cd.get(Calendar.MONTH) + 1;
		int d = cd.get(Calendar.DAY_OF_MONTH);

		date = "./log/" + logName + y + "-";

		if (m < 10)
			date += 0;

		date += m + "-";

		if (d < 10)
			date += 0;

		date += d + ".log";

		return date;
	}

	private void newLog() {
		logFile = getLogFile();

		try {
			logPrint = new PrintWriter(new FileWriter(logFile, true), true);
		} catch (IOException e) {
			(new File("./log")).mkdir();

			try {
				logPrint = new PrintWriter(new FileWriter(logFile, true), true);

			} catch (IOException ex) {
				System.err.println("�޷�����־�ļ���" + logFile);
				logPrint = new PrintWriter(System.err);
			}
		}
	}

	private void checkDate() {
		if (logFile == null || logFile.trim().equals("") || !logFile.equals(getLogFile())) {
			newLog();
		}
	}

	public void log(String msg) {
		checkDate();
		logPrint.println(new Date() + ": " + msg);
	}

	public void log(Throwable e, String msg) {
		checkDate();
		logPrint.println(new Date() + ": " + msg);
		e.printStackTrace(logPrint);
	}
	
	
	
	
	/*******************************************************************************************/
	/*******************************************************************************************/
	/*******************************************************************************************/
}
