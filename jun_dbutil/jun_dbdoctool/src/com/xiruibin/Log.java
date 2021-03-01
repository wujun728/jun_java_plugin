package com.xiruibin;

import java.util.logging.Logger;

public class Log {
	private static final Logger log = Logger.getLogger("Log");
	
	public static void info(String msg) {
		log.info(msg);
	}
	
	public static void warning(String msg) {
		log.warning(msg);
	}
	
	public static void severe(String msg) {
		log.severe(msg);
	}
}