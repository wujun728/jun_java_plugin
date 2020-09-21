/*   
 * Project: OSMP
 * FileName: GlobalUtils.java
 * version: V1.0
 */
package com.osmp.utils.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年9月10日 下午2:11:19
 */

public class GlobalUtils {
	private static final Logger logger = LoggerFactory.getLogger(GlobalUtils.class);
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static String currentDBUser;

	/**
	 * Bean属性Copy时应该排除的惯性
	 */
	public static final String[] DOMAINBUSINESS_IGNOREPROPERTIES = { "createDate", "isDeleted" };

	private GlobalUtils() {

	}

	/**
	 * pattern : yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formartDate(Date date) {
		if (null == date) {
			return null;
		}
		return dateFormat.format(date);
	}

	/**
	 * pattern : yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formartDateTime(Date date) {
		if (null == date) {
			return null;
		}
		return dateTimeFormat.format(date);
	}

	/**
	 * pattern : yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formartTime(Date date) {
		if (null == date) {
			return null;
		}
		return timeFormat.format(date);
	}

	public static String getCurrentDBUser() {
		return currentDBUser;
	}

	public static void setCurrentDBUser(String currentDBUser) {
		GlobalUtils.currentDBUser = currentDBUser;
	}

	public static boolean invokeShellCommand(String... command) {
		if (null == command || command.length == 0) {
			return false;
		}
		StringBuilder builderCommand = new StringBuilder();
		for (String cmd : command) {
			builderCommand.append(cmd + " ");
		}
		logger.info("Execute shell commad : " + builderCommand.toString());
		BufferedReader readMessage = null;
		BufferedReader readError = null;
		try {
			Process process = Runtime.getRuntime().exec(command);

			readMessage = new BufferedReader(new InputStreamReader(process.getInputStream()));
			readError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String data = null;
			StringBuilder execInfo = new StringBuilder();
			if (null != readMessage) {
				try {
					data = readMessage.readLine();
					while (null != data) {
						execInfo.append(data);
						data = readMessage.readLine();
					}
					readMessage.close();
				} catch (IOException ex) {
					logger.warn("Invoke command (" + builderCommand.toString() + ") ,read input steam error,msg is "
							+ ex.getMessage(), ex);
				}
			}
			if (null != readError) {
				try {
					data = readError.readLine();
					while (null != data) {
						execInfo.append(data);
						data = readError.readLine();
					}
					readError.close();
				} catch (IOException ex) {
					logger.warn("Invoke command (" + builderCommand.toString() + ") ,read error steam error,msg is "
							+ ex.getMessage(), ex);
				}
			}

			process.waitFor();
			process.destroy();
			int result = process.exitValue();
			logger.warn("\nInvoke command : " + builderCommand.toString() + "\n exitValue : " + result
					+ ", out info : \n" + execInfo.toString());
			if (result != 0) {
				throw new RuntimeException("Invoke command (" + builderCommand.toString() + ") error, exitValue : "
						+ result + ", out info : \n" + execInfo.toString());
			}
			return true;
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
			return false;
		} catch (InterruptedException e) {
			logger.warn(e.getMessage(), e);
			return false;
		} finally {
			if (null != readMessage) {
				try {
					readMessage.close();
				} catch (IOException e) {
					logger.warn(e.getMessage(), e);
				}
			}
			if (null != readError) {
				try {
					readError.close();
				} catch (IOException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}
	}

}
