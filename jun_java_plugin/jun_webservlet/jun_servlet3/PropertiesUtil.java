package com.dcf.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import com.eos.runtime.core.ApplicationContext;
import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.logging.Logger;

public class PropertiesUtil {
	
	private static final Logger logger = TraceLoggerFactory.getLogger(PropertiesUtil.class);
	
	private static final String CONFIG_FILE_NAME = "custom_config.properties";
	
	private static final Properties properties = new Properties();
	
	/**
	 * 通过key，获取config.properties指定属性的值
	 * 
	 * @param propertyKey 属性Key
	 * @return 返回String类型属性值
	 */
	public static String getPropertyValue(String propertyKey) {
		if(properties.isEmpty()){
			init();
		}
		String propertyValue = null;
		propertyValue = properties.getProperty(propertyKey);
		return propertyValue;
	}
	public static void init(){
		InputStream inputStream = null;
//		Reader reader = null;
		try {
			String configPath = ApplicationContext.getInstance().getApplicationConfigPath();
			inputStream = new FileInputStream(configPath + "/" + CONFIG_FILE_NAME);
//			reader = new InputStreamReader(inputStream, "UTF-8");
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
			logger.error("custom_config.properties属性配置文件未找到！");
		} catch (IOException e) {
			logger.error("获取custom_config.properties属性时文件读取IO异常！");
		} finally {
			if (inputStream != null) {
				try {
//					reader.close();
					inputStream.close();
				} catch (IOException t) {
					logger.error("关闭custom_config.properties 的输入流时发生异常！");
				}
			}
		}
	}

}
