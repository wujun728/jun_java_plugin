/*   
 * Project: OLWEB 433
 * FileName: PropertiesUtils.java
 * Company: Chengdu osmp Technology Co.,Ltd
 * version: V1.0
 */
package com.osmp.web.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Description: 资源文件工具类
 * 
 * @author: wangkaiping
 * @date: 2014年11月24日 下午4:38:48
 */
public class PropertiesUtils {

	/**
	 * 根据properties文件路径加载并转换为Map形式返回
	 * 
	 * @param name
	 * @return
	 */
	public static Map<String, String> getMap(String name) {
		Map<String, String> temp = new HashMap<String, String>();
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(name));
			// prop.load(PropertiesUtils.class.getResourceAsStream(name));
			Set<String> keyNames = prop.stringPropertyNames();
			if (keyNames != null && keyNames.size() > 0) {
				for (String key : keyNames) {
					String value = prop.getProperty(key);
					temp.put(key, value);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 根据资源文件URL加载为Properties
	 * 
	 * @param name
	 * @return
	 */
	public static Properties getProperties(String name) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
