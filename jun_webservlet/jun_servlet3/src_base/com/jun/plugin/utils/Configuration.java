package com.jun.plugin.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 读取 配置文件。
 *
 */
public class Configuration {
    
    private static final String CONF_CLASSPATH = "/config.properties";
    
    private static Configuration instance = new Configuration();
    
    private volatile Properties configuration = new Properties();
    
    FileOutputStream os = null;
    
    
    /**
     * 构造方法。
     */
    private Configuration() {
        
        InputStream is = this.getClass().getResourceAsStream(CONF_CLASSPATH);
        
        if (is != null) {
            try {
                this.configuration.clear();
                this.configuration.load(is);
            } catch (IOException e) {
            } finally {
                try {
                    is.close();
                } catch (Throwable t) {}
            }
        } else {
        }
    }
    
    
    /**
     * 获得Configuration实例。
     * @return    Configuration实例
     */
    public static Configuration getInstance() {
        
        return instance;
    }
    
    /**
     * 获得配置项。
     * @param     key        配置关键字
     * @return    配置项
     */
    public String getConfiguration(String key) {
        
        return this.configuration.getProperty(key);
    }
    
    /**
     * 取得String类型的值，如果配置项不存在，则返回defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
	public String getStringValue(String key, String defaultValue) {
		String value = this.getConfiguration(key);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}
    
    public void setConfiguration(String key, String value){
    	this.configuration.setProperty(key, value);
    	
    	
    }
    
    public void store(String comments) {
        if (configuration != null) {
            try {
                String path = getClass().getResource(CONF_CLASSPATH).getPath();
                os = new FileOutputStream(path);
                configuration.store(os, comments);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (Exception ee) {
                    }
                }
            }
        }
    }
    
    /**
     * 获得整型配置项。
     * @param     key        配置关键字
     * @return    配置项
     */
    public Integer getIntegerConfiguration(String key) {
        
        Integer result = null;
        
        String value = this.configuration.getProperty(key);
        if ((value != null) && !"".equals(value)) {
            try {
                result = new Integer(Integer.parseInt(value));
            } catch (NumberFormatException nfe) {}
        }
        
        return result;
    }
    
    /**
     * 获得短整型配置项。
     * @param     key        配置关键字
     * @return    配置项
     */
    public Short getShortConfiguration(String key) {
        Short result = null;
        String value = this.configuration.getProperty(key);
        if ((value != null) && !"".equals(value)) {
            try {
                result = new Short(Short.parseShort(value));
            } catch (NumberFormatException nfe) {}
        }
        
        return result;
    }
    
    /**
     * 获得长整型配置项。
     * @param     key        配置关键字
     * @return    配置项
     */
    public Long getLongConfiguration(String key) {
        
        Long result = null;
        
        String value = this.configuration.getProperty(key);
        if ((value != null) && !"".equals(value)) {
            try {
                result = new Long(Long.parseLong(value));
            } catch (NumberFormatException nfe) {}
        }
        
        return result;
    }
    
    /**
     * 获得单精度浮点型配置项。
     * @param     key        配置关键字
     * @return    配置项
     */
    public Float getFloatConfiguration(String key) {
        
        Float result = null;
        
        String value = this.configuration.getProperty(key);
        if ((value != null) && !"".equals(value)) {
            try {
                result = new Float(Float.parseFloat(value));
            } catch (NumberFormatException nfe) {}
        }
        
        return result;
    }
    
    /**
     * 获得双精度浮点型配置项。
     * @param     key        配置关键字
     * @return    配置项
     */
    public Double getDoubleConfiguration(String key) {
        
        Double result = null;
        
        String value = this.configuration.getProperty(key);
        if ((value != null) && !"".equals(value)) {
            try {
                result = new Double(Double.parseDouble(value));
            } catch (NumberFormatException nfe) {}
        }
        
        return result;
    }
    
    /**
     * 获得布尔型配置项。
     * @param     key        配置关键字
     * @return    配置项
     */
    public Boolean getBooleanConfiguration(String key) {
        
        Boolean result = null;
        
        String value = this.configuration.getProperty(key);
        if ((value != null) && !"".equals(value)) {
            if ("true".equalsIgnoreCase(value)) {
                result = new Boolean(true);
            } else if ("false".equalsIgnoreCase(value)) {
                result = new Boolean(false);
            }
        }
        
        return result;
    }
    
    /**
     * 获得日期型配置项。
     * @param     key        配置关键字
     * @param     pattern    日期字符串格式
     * @return    配置项
     */
    public Date getDateConfiguration(String key, String pattern) {
        
        Date result = null;
        
        String value = this.configuration.getProperty(key);
        if ((value != null) && !"".equals(value)) {
            try {
                if ((pattern != null) && !"".equals(pattern)) {
                    result = (new SimpleDateFormat(pattern)).parse(value);
                } else {
                    result = (new SimpleDateFormat()).parse(value);
                }
            } catch (ParseException pe) {}
        }
        
        return result;
    }
}