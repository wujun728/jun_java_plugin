/**
 * 
 */
package com.opensource.nredis.proxy.monitor.api.utils;

import java.util.Properties;

/**
 * @author liubing
 *
 */
public class ConfigUtils {
	
	public static Properties getProperties() throws Exception{
		Properties config = new Properties();
        config.load(ConfigUtils.class.getClassLoader().getResourceAsStream("sys-config.properties"));
        return config;
	}
}
