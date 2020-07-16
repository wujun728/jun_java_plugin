package com.jun.plugin.utils2.log.dialect.commons;

import com.jun.plugin.utils2.log.Log;
import com.jun.plugin.utils2.log.LogFactory;

/**
 *  Apache Commons Logging
 * @author Looly
 *
 */
public class ApacheCommonsLogFactory extends LogFactory{
	
	public ApacheCommonsLogFactory() {
		super("Apache Common Logging");
	}

	@Override
	public Log getLog(String name) {
		try {
			return new ApacheCommonsLog4JLog(name);
		} catch (Exception e) {
			return new ApacheCommonsLog(name);
		}
	}

	@Override
	public Log getLog(Class<?> clazz) {
		try {
			return new ApacheCommonsLog4JLog(clazz);
		} catch (Exception e) {
			return new ApacheCommonsLog(clazz);
		}
	}

}
