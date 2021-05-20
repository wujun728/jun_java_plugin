package com.ibeetl.admin.core.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * 常用工具类方法
 * 
 * @author Wujun
 *
 */
public class Tool {
	static final String DATE_FORAMT = "yyyy-MM-dd";
	static final String DATETIME_FORAMT = "yyyy-MM-dd HH:mm:ss";

	public static Date[] parseDataRange(String str) {
		//查询范围
		String[] arrays = str.split("至");
		Date min = parseDate(arrays[0]);
		Date max = parseDate(arrays[1]);
	
		return new Date[] { min,max };
	}

	public static Date[] parseDataTimeRange(String str) {
		//查询范围
		String[] arrays = str.split("至");
		Date min = parseDateWithPattern(arrays[0], DATETIME_FORAMT);
		Date max = parseDateWithPattern(arrays[1], DATETIME_FORAMT);

		return new Date[] { min,max };
	}

	public static Date parseDate(String str) {
	    return parseDateWithPattern(str, DATE_FORAMT);
	}

	public static Date parseDateWithPattern(String str, String pattern) {
		try {
			return DateUtils.parseDate(str.trim(), pattern);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
