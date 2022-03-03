package com.jun.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jun.common.Globarle;

/**
 * 日期时间工具类
 * 
 * @author Wujun
 * @createTime 2011-6-5 上午09:52:44
 */
public class DateTimeUtils {
	// 返回时间字符串(yyyy-MM-dd)
	public static String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				Globarle.DEFAULT_DATE_FORMAT);
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 日期大小比较
	 */
	public static boolean dateCompare(Date date1, Date date2) {
		boolean dateComPareFlag = true;
		if (date2.compareTo(date1) != 1) {
			dateComPareFlag = false;
		}
		return dateComPareFlag;
	}
}
