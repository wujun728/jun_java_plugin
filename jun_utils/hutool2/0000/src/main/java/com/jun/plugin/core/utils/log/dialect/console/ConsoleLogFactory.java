package com.jun.plugin.core.utils.log.dialect.console;

import com.jun.plugin.core.utils.log.Log;
import com.jun.plugin.core.utils.log.LogFactory;

/**
 * 利用System.out.println()打印日志
 * @author Looly
 *
 */
public class ConsoleLogFactory extends LogFactory {
	
	public ConsoleLogFactory() {
		super("Hutool Console Logging");
	}

	@Override
	public Log getLog(String name) {
		return new ConsoleLog(name);
	}

	@Override
	public Log getLog(Class<?> clazz) {
		return new ConsoleLog(clazz);
	}

}
