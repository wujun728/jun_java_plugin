package com.getall.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 支持slf4j标准的日志功能组件
 * LogUtil
 * Version：1.0
 * Author：WSK
 * 2013-4-26  下午03:49:08
 */
public class LogUtil {

	/**
	 * 获取日志对象
	 * LogUtil.getLogger()
	 * @return
	 * @return Logger
	 * Author：WSK
	 * 2013-4-26 下午03:49:31
	 */
	public static Logger getLogger() {
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		return LoggerFactory.getLogger(stacks[2].getClassName());
	}
}
