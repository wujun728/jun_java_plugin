package com.jun.plugin.utils;

import org.apache.log4j.Logger;

public class Log4jTest {

	//logger�����������ǣ�com.bjsxt.log4j.test
	private static Logger logger = Logger.getLogger(Log4jTest.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.debug("DEBUG��Ϣ");
		logger.info("INFO��Ϣ");
		logger.warn("WARN��Ϣ");
		logger.error("ERROR��Ϣ");
		logger.fatal("FATAL��Ϣ");
	}

}
