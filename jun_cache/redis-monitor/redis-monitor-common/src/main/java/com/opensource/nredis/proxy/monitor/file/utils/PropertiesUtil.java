package com.opensource.nredis.proxy.monitor.file.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Properties;

/**
 * 
 * @author liubing
 *
 */
public class PropertiesUtil {
	
	private static HashMap<File, Properties>	propertiesCache	= new HashMap<File, Properties>();

	/**
	 * 解析 Properties 文件
	 * 
	 * @param file
	 * @return
	 */
	public static Properties getProperties(File file) {
		

		try {
			if (!propertiesCache.containsKey(file)) {
				Properties properites = new Properties();
				String content = new String(FileUtil.loadFile(file));
				properites.load(new StringReader(content));
				propertiesCache.put(file, properites);
			}
			
			return propertiesCache.get(file);
			
		} catch (IOException e) {
			//Logger.error(e);
			return null;
		}
	}

	/**
	 * 从Properties文件读取字符串
	 * 
	 * @param file
	 * @param name
	 * @return
	 */
	public static String getString(File file, String name) {
		Properties properites = getProperties(file);
		return ObjectUtil.nullDefault(properites.getProperty(name), null);
	}

	/**
	 * 从Properties文件读取整形
	 * 
	 * @param file
	 * @param name
	 * @return
	 */
	public static int getInt(File file, String name) {
		String value = getString(file, name);
		return ObjectUtil.nullDefault(Integer.valueOf(value), 0);
	}

	/**
	 * 从Properties文件读取浮点数
	 * 
	 * @param file
	 * @param name
	 * @return
	 */
	public static float getFloat(File file, String name) {
		String value = getString(file, name);
		return ObjectUtil.nullDefault(Float.valueOf(value.trim()), 0).floatValue();
	}

	/**
	 * 从Properties读取双精度浮点数
	 * 
	 * @param file
	 * @param name
	 * @return
	 */
	public static double getDouble(File file, String name) {
		String value = getString(file, name);
		return ObjectUtil.nullDefault(Double.valueOf(value.trim()), 0).doubleValue();
	}

	/**
	 * 清空 Properites 缓存
	 */
	public void clear(){
		 propertiesCache.clear();
	}
}
