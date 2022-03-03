package com.jun.common.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * Json日期格式化接口
 * @author Wujun
 * @createTime   2011-5-14 下午09:04:52
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";      
    private DateFormat dateFormat;      
     
    /**    
     * 构造方法.    
     *    
     * @param datePattern 日期格式    
     */     
    public DateJsonValueProcessor(String datePattern) {      
        try {      
            dateFormat = new SimpleDateFormat(datePattern);      
        } catch (Exception ex) {      
            dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);      
        }      
    }      
     
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {      
        return process(value);      
    }      
     
    public Object processObjectValue(String key, Object value,      
        JsonConfig jsonConfig) {      
        return process(value);      
    }      
     
    private Object process(Object value) {   
        if (value==null) {   
            return "";   
        }else if (value instanceof Date) {  
            return dateFormat.format((Date)value);   
        }else {   
            return value;   
        }   
    }      
}
