package com.jun.plugin.dbutils.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * 读取*.properties配置文件
 */
public class PropertiesConfig {

	// 配置文件的map key:propertiesName value:PropertiesUtil对象
	private static HashMap<String, PropertiesConfig> propertiesMap = new HashMap<String, PropertiesConfig>();

	// 属性文件
	private Properties properties;

	private PropertiesConfig() {

	}
	
	/**
	 * jdbc配置文件
	 * @return
	 */
	public static PropertiesConfig getApplicationConfig() {
		return PropertiesConfig.getInstance("/jdbc.properties");
	}

	public static void TestProperties(String[] args) {
		PropertiesConfig config = PropertiesConfig.getApplicationConfig();
		System.out.println(config.getProperty("jdbc.url"));
	}
	
	public synchronized static PropertiesConfig getInstance(String propertiesName) {

		PropertiesConfig configUtil = propertiesMap.get(propertiesName);

		if (configUtil == null) {
			configUtil = new PropertiesConfig();
			configUtil.analysisXml(propertiesName);
			propertiesMap.put(propertiesName, configUtil);
		}

		return configUtil;
	}

	private void analysisXml(String propertiesName) {
		InputStream ins = getClass().getResourceAsStream(propertiesName);
		properties = new Properties();
		try {
			properties.load(ins);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

}
