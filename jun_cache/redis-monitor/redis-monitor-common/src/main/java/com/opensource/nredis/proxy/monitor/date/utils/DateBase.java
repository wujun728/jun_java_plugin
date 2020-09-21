package com.opensource.nredis.proxy.monitor.date.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * 日期基础工具类
 * @author liubing
 *
 */
public class DateBase {
	/** 系统默认 日期类型 yyyy-MM-dd */
	public static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd";
	
	/** 时间  日期类型  HH:mm:ss */
	public static final String DATE_PATTERN_TIME = "HH:mm:ss";
	
	/** 日期时间 日期类型  yyyy-MM-dd HH:mm:ss */
	public static final String DATE_PATTERN_DATETIME = "yyyy-MM-dd HH:mm";
	
	/**  时期格式  yyyy-MM-dd*/
	public static DateFormat dateformater;

	/**  时间格式 HH:mm:ss*/
	public static DateFormat timeformater;

	/** 日期时间格式  yyyy-MM-dd HH:mm*/
	public static DateFormat dateTimeformater;

	static {
		if (dateformater == null) {
			dateformater = new SimpleDateFormat(DATE_PATTERN_DEFAULT);
		}
		if (timeformater == null) {
			timeformater = new SimpleDateFormat(DATE_PATTERN_TIME);
		}

		if (dateTimeformater == null) {
			dateTimeformater = new SimpleDateFormat(DATE_PATTERN_DATETIME);
		}
	}

	/** 一天毫秒数 */
	public static final long DAY_IN_MILLISECOND = 1000 * 3600 * 24;

	/** 一小时毫秒数 */
	public static final long HOUR_IN_MILLISECOND = 1000 * 3600 ;

	/** 构造方法私有化 */
	private DateBase() {}

	/**

	 * 根据 yyyy-MM-dd 字符串解析成相应的日期

	 * @param strDate yyyy-MM-dd 格式的日期

	 * @return 转换后的日期

	 */
	public static Date parseDate(String strDate) {
		Date date = null;
		try {
			date = dateformater.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
		return date;
	}

	/**

	 * 根据传入的日期格式 将字符串解析成 日期类型

	 * @param strDate 日期字符串

	 * @param pattern 日期格式 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @return 日期类型

	 */
	public static Date parseDate(String strDate, String pattern) {
		Date date = null;
		try {
			DateFormat format = getDateFormat(pattern);
			date = format.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
		return date;
	}

	/**

	 * 根据 HH:mm:ss 字符串解析成相应的时间格式

	 * @param strTime HH:mm:ss 格式的时间格式

	 * @return 转换后的时间

	 */
	public static Date parseTime(String strTime) {
		Date date = null;
		try {
			date = timeformater.parse(strTime);
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
		return date;
	}

	/**

	 * 根据yyyy-MM-dd HH:mm字符串解析成相应的日期时间

	 * @param strDateTime 以"yyyy-MM-dd HH:mm"为格式的时间字符串

	 * @return 转换后的日期

	 */
	public static Date parseDateTime(String strDateTime) {
		Date date = null;
		try {
			date = dateTimeformater.parse(strDateTime);
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
		return date;
	}

	/**

	 * 获取系统当前时间

	 * @return 系统当前时间

	 */
	public static Date getCurrentDate() {
		Calendar gc = GregorianCalendar.getInstance();
		return gc.getTime();
	}
	
	/**

	 * 得到日期的起始日期，例如2004-1-1 15:12，转换后为 2004-1-1 00:00 

	 * @param date 需要转换的日期

	 * @return 该日期的零点

	 */
	public static Date getTodayStart(Date date) {
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MILLISECOND, 0);
		return gc.getTime();
	}

	/**

	 * 得到日期的结束日期，例如2004-1-1 15:12，转换后为2004-1-2 00:00，注意为第二天的0点整

	 * @param date 所要转换的日期

	 * @return 为第二天的零点整

	 */
	public static Date getTodayEnd(Date date) {
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTime(dateDayAdd(date, 1));
		return getTodayStart(gc.getTime());
	}

	/**

	 * 得到日期所在月份的开始日期（第一天的开始日期），例如2004-1-15 15:10，转换后为2004-1-1 00:00

	 * @param date 需要转换的日期

	 * @return 日期所在月份的开始日期

	 */
	public static Date getMonthBegin(Date date) {
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTime(date);
		int year = gc.get(Calendar.YEAR);
		int mon = gc.get(Calendar.MONTH);
		Calendar gCal = new GregorianCalendar(year, mon, 1);
		return gCal.getTime();
	}

	/**

	 * 根据年、月返回由年、月构成的日期的月份开始日期

	 * @param year 所在的年

	 * @param month 所在的月份，从1月到12月

	 * @return 由年、月构成的日期的月份开始日期

	 */
	public static Date getMonthBegin(int year, int month) {
		if (month <= 0 || month > 12) {
			throw new IllegalArgumentException("month must from 1 to 12");
		}
		Calendar gc = new GregorianCalendar(year, month - 1, 1);
		return gc.getTime();
	}

	/**

	 * 根据日期所在的月份，得到下个月的第一天零点整

	 * @param date 需要转换的日期

	 * @return 对应日期的下个月的第一天零点整

	 */
	public static Date getMonthEnd(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		return getTodayEnd(cal.getTime());
	}

	/**

	 * 根据日期所在的星期，得到这个星期的开始日期，注意，每周从星期日开始计算

	 * @param date 需要转换的日期

	 * @return 传入日期所在周的第一天的零点整

	 */
	public static Date getWeekBegin(Date date) {
		Calendar gCal = GregorianCalendar.getInstance();
		gCal.setTime(date);
		int startDay = gCal.getActualMinimum(Calendar.DAY_OF_WEEK);
		gCal.set(Calendar.DAY_OF_WEEK, startDay);
		return gCal.getTime();
	}

	/**

	 * 根据日期所在的星期，得到下周开始第一天的零点整

	 * @param date 需要转换的日期

	 * @return 传入日期的下周开始第一天的零点整

	 */
	public static Date getWeekEnd(Date date) {
		Calendar gCal = GregorianCalendar.getInstance();
		gCal.setTime(date);
		int lastDay = gCal.getActualMaximum(Calendar.DAY_OF_WEEK);
		gCal.set(Calendar.DAY_OF_WEEK, lastDay);
		return getTodayEnd(gCal.getTime());
	}

	/**

	 * 根据年、月返回由年、月构成的日期的下一个月第一天零点整

	 * @param year 所在的年

	 * @param month 所在的月份，从1月到12月

	 * @return 下一个月第一天零点整

	 */
	public static Date getMonthEnd(int year, int month) {
		Date start = getMonthBegin(year, month);
		return getMonthEnd(start);
	}

	/**

	 * 根据日期所在的年份，得到当年的开始日期，为每年的1月1日零点整

	 * @param date 需要转换的日期

	 * @return 当年的开始日期，为每年的1月1日零点整

	 */
	public static Date getYearBegin(Date date) {
		Calendar gCal = GregorianCalendar.getInstance();
		gCal.setTime(date);
		gCal.set(Calendar.DAY_OF_YEAR, 1);
		return getTodayStart(gCal.getTime());
	}

	/**

	 * 根据日期所在的年份，得到当年的结束日期，为来年的1月1日零点整

	 * @param date 需要转换的日期

	 * @return 来年的1月1日零点整

	 */
	public static Date getYearEnd(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		int lastday = cal.getActualMaximum(GregorianCalendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, lastday);
		return getTodayEnd(cal.getTime());
	}

	/**

	 * 转换日期为 yyyy-MM-dd 格式的字符串

	 * @param date 需要转换的日期

	 * @return 转换后的日期字符串

	 */
	public static String formatDate(Date date) {
		return dateformater.format(date);
	}

	/**

	 * 转换指定日期成时间格式 HH:mm:ss 格式的字符串

	 * @param date 指定的时间

	 * @return 转换后的字符串

	 */
	public static String formatTime(Date date) {
		return timeformater.format(date);
	}

	/**

	 * 转换指定日期成 yyyy-MM-dd HH:mm:ss 格式的字符串

	 * @param date 需要转换的日期

	 * @return 转换后的字符串

	 */
	public static String formatDateTime(Date date) {
		return dateTimeformater.format(date);
	}

	/**

	 * 根据指定的转化模式，转换日期成字符串

	 * @param date 需要转换的日期

	 * @param pattern 日期格式 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @return 转换后的字符串

	 */
	public static String formatDate(Date date, String pattern) {
		DateFormat formater = getDateFormat(pattern);
		return formater.format(date);
	}
	
	/**

	 * 在指定日期的基础上，增加或是减少月份信息，如1月31日，增加一个月后，则为2月28日（非闰年）

	 * @param date 指定的日期

	 * @param months 增加或是减少的月份数，正数为增加，负数为减少

	 * @return 增加或是减少后的日期

	 */
	public static Date dateMonthAdd(Date date, int months) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		int m = cal.get(Calendar.MONTH) + months;
		if (m < 0) {
			m += -12;
		}
		cal.roll(Calendar.YEAR, m / 12);
		cal.roll(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**

	 * 在指定的日期基础上，增加或是减少天数

	 * @param date 指定的日期

	 * @param days 需要增加或是减少的天数，正数为增加，负数为减少

	 * @return 增加或是减少后的日期

	 */
	public static Date dateDayAdd(Date date, int days) {
		long now = date.getTime() + days * DAY_IN_MILLISECOND;
		return (new Date(now));
	}

	/**

	 * 在指定的日期基础上，增加或是减少年数

	 * @param date 指定的日期

	 * @param year 需要增加或是减少的年数，正数为增加，负数为减少 

	 * @return 增加或是减少后的日期

	 */
	public static Date dateYearAdd(Date date, int year) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.roll(Calendar.YEAR, year);
		return cal.getTime();
	}

	/**

	 * 得到日期的年数

	 * @param date 指定的日期 

	 * @return 返回指定日期的年数

	 */
	public static int getDateYear(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**

	 * 得到指定日期的月份数

	 * @param date 指定的日期

	 * @return 返回指定日期的月份数

	 */
	public static int getDateMonth(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONDAY);
	}

	/**

	 * 得到制定日期在当前天数，例如2004-5-20日，返回141

	 * @param date 指定的日期

	 * @return 返回的天数

	 */
	public static int getDateYearDay(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**

	 * 得到制定日期在当前月中的天数，例如2004-5-20日，返回20

	 * @param date 指定的日期

	 * @return 返回天数

	 */
	public static int getDateMonthDay(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**

	 * 得到指定日期的年份

	 * @param date 指定的日期

	 * @return 日期的年份

	 */
	public static int getYear(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**

	 * 得到指定日期在当在一年中的月份数，从1开始

	 * @param date 指定的日期

	 * @return 指定日期在当在一年中的月份数

	 */
	public static int getMonth(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**

	 * 得到指定日期在当在一月中的号数，从1开始

	 * @param date 指定的日期

	 * @return 日期在当在一月中的号数

	 */
	public static int getDay(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**

	 * 得到指定日期在当前星期中的天数，例如2004-5-20日，返回5，

	 * 每周以周日为开始按1计算，所以星期四为5

	 * @param date 指定的日期

	 * @return 返回天数

	 */
	public static int getDateWeekDay(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**

	 * 得到指定日期在当前周内是第几天 (周一开始)

	 * @param date 指定日期 

	 * @return 周内天书

	 */
	public static int getWeek(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		return ((cal.get(Calendar.DAY_OF_WEEK) - 1) + 7) % 7;
	}
	
	/**

	 * 根据传入的格式，获取日期格式化实例，如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @param pattern 日期格式

	 * @return 格式化实例

	 */
	public static DateFormat getDateFormat(String pattern){
		if(null == pattern || "".equals(pattern.trim())){
			return new SimpleDateFormat(DateBase.DATE_PATTERN_DEFAULT);
		}else{
			return new SimpleDateFormat(pattern);
		}
	}
	
	/**

	 * 计算两个时间之间的时间差

	 * @param from 开始

	 * @param to 结束

	 * @return 时间差

	 */
	public static long calculateTimeInMillis(Date from, Date to){
		Calendar fromCal = getCalendar(from);
		Calendar toCal = getCalendar(to);
		if(fromCal.after(toCal)){
			fromCal.setTime(to);
			toCal.setTime(from);
		}
		return toCal.getTimeInMillis() - fromCal.getTimeInMillis();
	}
	
	/**

	 * 获取Calendar实例

	 * @param date 日期类型

	 * @return

	 */
	public static Calendar getCalendar(Date date){
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTime(date);
		return gc;
	}
	
    /**

	 * 根据 yyyyMMdd HH:mm 日期格式，转换成数据库使用的时间戳格式

	 * @param strTimestamp 以 yyyyMMdd HH:mm 格式的时间字符串

	 * @return 转换后的时间戳

	 */
	public static java.sql.Timestamp parseTimestamp(String strTimestamp) {
		return new java.sql.Timestamp(DateBase.parseDateTime(strTimestamp).getTime());
	}
}
