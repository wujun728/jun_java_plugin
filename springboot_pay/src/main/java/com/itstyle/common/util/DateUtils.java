package com.itstyle.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期操作类
 * 创建者 科帮网
 * 创建时间	2017年7月31日
 */
public class DateUtils {

	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	/**
	 * 获取YYYY-MM-DD hh:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	public static String getTimestamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
}
