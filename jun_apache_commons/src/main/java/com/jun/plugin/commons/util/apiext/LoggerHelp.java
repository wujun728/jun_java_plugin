package com.jun.plugin.commons.util.apiext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerHelp implements java.io.Serializable {
	private static final long serialVersionUID = 7616481079297589315L;

	/****
	 * 得到commons-util的根目录日志
	 * 
	 * @return
	 */
	public static final Logger getUtilLogger() {
		return LoggerFactory.getLogger("cn.rjzjh.commons.util");
	}

	/****
	 * 跟据logger的名字得到日志
	 * 
	 * @param name
	 * @return
	 */
	public static final Logger getJspLog(String name) {
		return LoggerFactory.getLogger(name);
	}

	/***
	 * 通过类得到日志
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static final Logger getLoggerByClass(Class c) {
		return LoggerFactory.getLogger(c);
	}

}
