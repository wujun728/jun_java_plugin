/** 
 * Project Name:busmgr 
 * File Name:CommonUtil.java 
 * Package Name:com.spsoft.busmgr.util 
 * Date:2013-5-8����03:37:44 
 * Copyright (c) 2013, Guangzhou Sea Power Software Technology co.,ltd. All Rights Reserved. 
 * 
 */

package com.jun.admin.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * ClassName:CommonUtil <br/>
 * Function: ��ͨ�����Ĵ��� <br/>
 * Reason: ʵ�ֹ�ͨ�����Ĺ��� <br/>
 * Date: 2013-5-8 ����03:37:44 <br/>
 * 
 * @author Wujun
 * @version
 * @since JDK 1.5
 * @see
 */
public class CommonUtil {
    
    public static String getDateStr(String pt) {
        if (pt == null || pt.trim().length() == 0) {
            pt = "yyyy-MM-dd";
        }
        
        SimpleDateFormat fm = new SimpleDateFormat();
        fm.applyPattern(pt);
        return fm.format(new Date());
    }
    
    public static String getDateStr(Object dateObj, String pt) {
        Date date = null;
        if (dateObj instanceof Date) {
            if (dateObj == null) {
                return "";
            }
            date = (Date) dateObj;
        } else {
            if (dateObj == null || dateObj.toString().length() == 0) {
                return "";
            }
            
            java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(dateObj
                    .toString());
            date = new Date(sqlDate.getTime());
        }
        
        if (pt == null || pt.trim().length() == 0) {
            pt = "yyyy-MM-dd";
        }
        
        SimpleDateFormat fm = new SimpleDateFormat();
        fm.applyPattern(pt);
        return fm.format(date);
    }
    
    public static Date getDate(String str, String pt) throws ParseException {
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        
        if (pt == null || pt.trim().length() == 0) {
            pt = "yyyy-MM-dd";
        }
        
        SimpleDateFormat fm = new SimpleDateFormat();
        fm.applyPattern(pt);
        return fm.parse(str);
    }
    
    public static java.sql.Timestamp getSqlDate() {
        long dateTime = new Date().getTime();
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(dateTime);
        return sqlDate;
    }
    
    public static java.sql.Timestamp getSqlDate(String timeValue) {
        java.sql.Timestamp sqlDate = null;
        Long dateTime = CommonUtil.getDateTime(timeValue);
        if (dateTime != null) {
            sqlDate = new java.sql.Timestamp(dateTime.longValue());
        }
        return sqlDate;
    }
    
    public static Long getDateTime(String timeValue) {
        Long dateTime = null;
        
        if (timeValue != null && timeValue.trim().length() > 0) {
            timeValue = rightPadTo(timeValue, "1900-01-01 00:00:00");
            timeValue = timeValue.replace("/", "-");
            
            try {
                Date date = CommonUtil
                        .getDate(timeValue, "yyyy-MM-dd HH:mm:ss");
                dateTime = new Long(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        return dateTime;
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> parmsMap = new HashMap<String, String>();
        
        Map<String, String[]> properties = request.getParameterMap();
        Object obj = "";
        String value = "";
        String[] values = null;
        
        for (String key : properties.keySet()) {
            obj = properties.get(key);
            if (null == obj) {
                value = "";
            } else if (obj instanceof String[]) {
                value = "";
                values = (String[]) obj;
                for (int i = 0; i < values.length; i++) {
                    value += "," + values[i];
                }
                value = value.length() > 0 ? value.substring(1) : value;
            } else {
                value = obj.toString();
            }
            
            parmsMap.put(key, value);
        }
        
        return parmsMap;
    }
    
    
    
    
    
    public static String rightPadTo(String src, String dec) {
        String retStr = src;
        int len = src.length();
        if (dec.length() - len > 0) {
            retStr += dec.substring(len);
        }
        return retStr;
    }
}
