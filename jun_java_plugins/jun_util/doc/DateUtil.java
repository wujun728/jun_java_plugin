package org.myframework.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 封装日期处理相关方法
 * 增加ORACLE中的ADD_MONTH ,SYSDATE 等类似功能的方法
 * @author Administrator
 * 
 */
public class DateUtil {

	private static Log log = LogFactory.getLog(DateUtil.class);

	/** 采用Singleton设计模式而具有的唯一实例 */
	private static DateUtil instance = new DateUtil();
	private static String defaultPattern = "yyyy-MM-dd";
	/** 格式化器存储器 */
	private static Map<String, SimpleDateFormat> formats;

	private DateUtil() {
		resetFormats();
	}

	/**
	 * 通过缺省日期格式得到的工具类实例
	 * 
	 * @return <code>DateUtilities</code>
	 */
	public static DateUtil getInstance() {
		return instance;
	}

	/** Reset the supported formats to the default set. */
	public void resetFormats() {
		formats = new HashMap<String, SimpleDateFormat>();

		// alternative formats
		formats.put("yyyy-MM-dd HH:mm:ss", new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss"));
		formats.put("yyyyMMddHHmmssms", new SimpleDateFormat(
		"yyyyMMddHHmmssms"));
		
		// alternative formats
		formats.put("yyyy-MM-dd", new SimpleDateFormat("yyyy-MM-dd"));

		// XPDL examples format
		formats.put("MM/dd/yyyy HH:mm:ss a", new SimpleDateFormat(
				"MM/dd/yyyy HH:mm:ss a"));

		// alternative formats
		formats.put("yyyy-MM-dd HH:mm:ss a", new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss a"));

		// ISO formats
		formats.put("yyyy-MM-dd'T'HH:mm:ss'Z'", new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'"));
		formats.put("yyyy-MM-dd'T'HH:mm:ssZ", new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssZ"));
		formats.put("yyyy-MM-dd'T'HH:mm:ssz", new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssz"));
	}

	/**
	 * 格式化日期字符串
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (!formats.containsKey(pattern))
			pattern = defaultPattern;
		DateFormat format = formats.get(pattern);
		return format.format(date);
	}

	/**
	 * 自动判断格式化类型
	 * 
	 * @param date
	 *            需格式化的日期
	 * @return 格式化后的字符串
	 */
	public static String format(Date date) {
		Iterator<SimpleDateFormat> iter = formats.values().iterator();
		while (iter.hasNext()) {
			return iter.next().format(date);
		}
		return null;
	}

	/**
	 * 解析日期
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		Date resultDate = null;
		try {
			if (!formats.containsKey(pattern))
				pattern = defaultPattern;
			resultDate = formats.get(pattern).parse(date);
		} catch (ParseException e) {
			log.error(e);
		}
		return resultDate;
	}

	/**
	 * 解析字符串到日期型
	 * 
	 * @param dateString
	 *            日期字符串
	 * @return 返回日期型对象
	 */
	public static Date parse(String dateString) {
		Iterator<SimpleDateFormat> iter = formats.values().iterator();
		while (iter.hasNext()) {
			try {
				return iter.next().parse(dateString);
			} catch (ParseException e) {
				log.error(e);
			}
		}
		return null;
	}

	/**
	 * 取得当前日期
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * @param offsetYear
	 * @return 当前时间 + offsetYear
	 */
	public static Timestamp getTimestampExpiredYear(int offsetYear) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, offsetYear);
		return new Timestamp(now.getTime().getTime());
	}

	/**
	 * @param offsetMonth
	 * @return 当前时间 + offsetMonth
	 */
	public static Timestamp getCurrentTimestampExpiredMonth(int offsetMonth) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, offsetMonth);
		return new Timestamp(now.getTime().getTime());
	}

	/**
	 * @param offsetDay
	 * @return 当前时间 + offsetDay
	 */
	public static Timestamp getCurrentTimestampExpiredDay(int offsetDay) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, offsetDay);
		return new Timestamp(now.getTime().getTime());
	}

	/**
	 * @param offsetSecond
	 * @return 当前时间 + offsetSecond
	 */
	public static Timestamp getCurrentTimestampExpiredSecond(int offsetSecond) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, offsetSecond);
		return new Timestamp(now.getTime().getTime());
	}

	/**
	 * @param offsetDay
	 * @return 指定时间 + offsetDay
	 */
	public static Timestamp getTimestampExpiredDay(Date givenDate, int offsetDay) {
		Calendar date = Calendar.getInstance();
		date.setTime(givenDate);
		date.add(Calendar.DATE, offsetDay);
		return new Timestamp(date.getTime().getTime());
	}

	/**
	 * 实现ORACLE中ADD_MONTHS函数功能
	 * 
	 * @param offsetMonth
	 * @return 指定时间 + offsetMonth
	 */
	public static Timestamp getTimestampExpiredMonth(Date givenDate,
			int offsetMonth) {
		Calendar date = Calendar.getInstance();
		date.setTime(givenDate);
		date.add(Calendar.MONTH, offsetMonth);
		return new Timestamp(date.getTime().getTime());
	}

	/**
	 * @param offsetSecond
	 * @return 指定时间 + offsetSecond
	 */
	public static Timestamp getTimestampExpiredSecond(Date givenDate,
			int offsetSecond) {
		Calendar date = Calendar.getInstance();
		date.setTime(givenDate);
		date.add(Calendar.SECOND, offsetSecond);
		return new Timestamp(date.getTime().getTime());
	}

	/**
	 * @param offsetSecond
	 * @return 指定时间 + offsetSecond
	 */
	public static Timestamp getTimestampExpiredHour(Date givenDate,
			int offsetHour) {
		Calendar date = Calendar.getInstance();
		date.setTime(givenDate);
		date.add(Calendar.HOUR, offsetHour);
		return new Timestamp(date.getTime().getTime());
	}

	/**
	 * @return 当前日期 yyyy-MM-dd
	 */
	public static String getCurrentDay() {
		return format(new Date(), defaultPattern);
	}

	/**
	 * @return 当前的时间戳 yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowTime() {
		return format(new Date(),"yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @return 给出指定日期的月份的第一天
	 */
	public static Date getMonthFirstDay(Date givenDate) {
		Date date = DateUtil.parse(DateUtil.format(givenDate, "yyyy-MM"),
				"yyyy-MM");
		return date;
	}

	/**
	 * @return 给出指定日期的月份的最后一天
	 */
	public static Date getMonthLastDay(Date givenDate) {
		Date firstDay = getMonthFirstDay(givenDate);
		Date lastMonthFirstDay = DateUtil.getTimestampExpiredMonth(firstDay, 1);
		Date lastDay = DateUtil.getTimestampExpiredDay(lastMonthFirstDay, -1);
		return lastDay;
	}
}
