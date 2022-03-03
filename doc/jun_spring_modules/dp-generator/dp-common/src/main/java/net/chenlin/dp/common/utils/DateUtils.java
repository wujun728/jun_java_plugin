package net.chenlin.dp.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月8日 下午12:02:46
 */
public class DateUtils {
	
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/** 时间格式(yyyy年M月dd日 ah:mm:ss) 代码生成器使用 */
	public final static String DATE_TIME_CHN_PATTERN = "yyyy年M月dd日 ah:mm:ss";

	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}
	
}
