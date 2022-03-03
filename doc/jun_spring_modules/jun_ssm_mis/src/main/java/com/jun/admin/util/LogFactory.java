package com.jun.admin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogFactory {

	private static LogFactory logFactory = new LogFactory();
	
	private LogFactory() {
	}

	public static LogFactory getInstance() {
		return logFactory;
	}
	
	/**
	 * 获取默认的Logger，默认为控制台
	 * 
	 * @return Logger
	 */
	public static Logger getLogger() {
		return LoggerFactory.getLogger("stdout");
	}

}
