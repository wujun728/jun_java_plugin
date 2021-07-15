package com.dcf.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.eos.runtime.core.ApplicationContext;
import com.eos.system.logging.Logger;

/**
 * <h1>Properties工具类</h1><br>
 * <p>提供Properties相关值获取的方法</p>
 *
 */
public class PropertiesHelper {
	private static Logger log = DcfUtil.getLogger(PropertiesHelper.class);
	
	/**
	 * 获取指定属性的值
	 * @param propertiesName 属性名
	 * @return  返回String类型属性值
	 */
	public static String getProperties(String propertiesName) {
		Properties prop = new Properties();
        InputStream input = null;
        String property = null;
        try {
             String configPath = ApplicationContext.getInstance()
                       .getApplicationConfigPath();
             input = new FileInputStream(configPath + "/config.properties");
             prop.load(input);
             property = prop.getProperty(propertiesName);
             log.debug("config.properties 属性【" + propertiesName + "】的值为【" + property + "】");
        } catch (Throwable t) {
        	log.error(t.getMessage(), t); 
        	t.printStackTrace();
        } finally {
        	if(input!=null) {
        		try {
        			input.close();
        		} catch (Throwable t) { }
        	}
        }
		return property;
	}
}
