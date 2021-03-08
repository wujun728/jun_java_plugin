package org.smartboot.shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * 提供了常用的日期处理功能
 *
 * @author Wujun
 * @version DateUtils.java, v 0.1 2015年11月4日 下午6:51:28 Seer Exp.
 */
public final class DateUtils {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_FORMAT_NOHOUR = "yyyy-MM-dd";
	/**
	 * 获取当前时间,格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return getDate(new Date(), DEFAULT_FORMAT);
	}

	public static String getDefaultDate(Date date) {
		return getDate(date, DEFAULT_FORMAT);
	}

	public static String getDate(Date date, String formate) {
		SimpleDateFormat sf = new SimpleDateFormat(formate);
		return sf.format(date);
	}

	public static Date getDate(String dateStr, String formate) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat(formate);
		return sf.parse(dateStr);
	}

	public static long getTime(String dateStr, String formate) {
		Date date;
		try {
			date = getDate(dateStr, formate);
			return date.getTime();
		} catch (ParseException e) {
			return -1;
		}
	}

	/**
	 * @param dateStr
	 * 
	 *            字符串日期数据 格式为 yyyy-MM-dd HH:mm:ss
	 * @return
	 * 
	 */
	public static long getTime(String dateStr) {
		return getTime(dateStr, DEFAULT_FORMAT);
	}

	/**
	 * 获取当天凌晨时间
	 * 
	 * @return
	 */
	public static Date getWeeHours() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	public static String getCurrentTimeNoHour() {
		return getDate(new Date(), DEFAULT_FORMAT_NOHOUR);
	}
	public static String getPreviousOrNextDay(String time,int dayNum) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT_NOHOUR);
		Date date = df.parse(time);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTime(date);
		currentDate.add(GregorianCalendar.DATE, dayNum);

		Date monday = currentDate.getTime();
		String preMonday = df.format(monday);
		return preMonday;
	}
}