package com.jun.plugin.core.utils.log.dialect.log4j2;

import com.jun.plugin.core.utils.log.Log;
import com.jun.plugin.core.utils.log.LogFactory;

/**
 * <a href="http://logging.apache.org/log4j/2.x/index.html">Apache Log4J 2</a> log.<br>
 * @author Looly
 *
 */
public class Log4j2LogFactory extends LogFactory{
	
	public Log4j2LogFactory() {
		super("Log4j2");
	}

	@Override
	public Log getLog(String name) {
		return new Log4j2Log(name);
	}

	@Override
	public Log getLog(Class<?> clazz) {
		return new Log4j2Log(clazz);
	}

}
