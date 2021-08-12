package com.jun.plugin.servlet.guice.core.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
	public static final String yyyyMM = "yyyyMM";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String yyyyMMddHHmm = "yyyyMMddHHmm";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String HHmm = "HHmm";
	public static final String HHmmss = "HHmmss";

	public static final String yyyy_MM = "yyyy-MM";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String HH_mm = "HH:mm";
	public static final String HH_mm_ss = "HH:mm:ss";

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String timeToString(java.util.Date date, String format) {
		String rString = "";
		if (null != date) {
			DateFormat dateFormat = new SimpleDateFormat(null == format ? yyyy_MM_dd_HH_mm_ss : format);
			rString = dateFormat.format(date);
		}
		return rString;
	}

	/**
	 * 格式化日期 yyyyMM
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString_yyyyMM(java.util.Date date) {
		return timeToString(date, yyyyMM);
	}

	/**
	 * 格式化日期 yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString_yyyyMMdd(java.util.Date date) {
		return timeToString(date, yyyyMMdd);
	}

	/**
	 * 格式化日期 yyyyMMddHHmm
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString_yyyyMMddHHmm(java.util.Date date) {
		return timeToString(date, yyyyMMddHHmm);
	}

	/**
	 * 格式化日期 yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString_yyyyMMddHHmmss(java.util.Date date) {
		return timeToString(date, yyyyMMddHHmmss);
	}

	/**
	 * 格式化日期 HHmm
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString_HHmm(java.util.Date date) {
		return timeToString(date, HHmm);
	}

	/**
	 * 格式化日期 HHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString_HHmmss(java.util.Date date) {
		return timeToString(date, HHmmss);
	}

	/**
	 * 格式化日期 yyyy-MM
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString_yyyy_MM(java.util.Date date) {
		return timeToString(date, yyyy_MM);
	}

	/**
	 * 格式化日期 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString_yyyy_MM_dd(java.util.Date date) {
		return timeToString(date, yyyy_MM_dd);
	}

	/**
	 * 格式化日期 yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString_yyyy_MM_dd_HH_mm(java.util.Date date) {
		return timeToString(date, yyyy_MM_dd_HH_mm);
	}

	/**
	 * 格式化日期 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString_yyyy_MM_dd_HH_mm_ss(java.util.Date date) {
		return timeToString(date, yyyy_MM_dd_HH_mm_ss);
	}

	/**
	 * 格式化日期 HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString_HH_mm(java.util.Date date) {
		return timeToString(date, HH_mm);
	}

	/**
	 * 格式化日期 HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString_HH_mm_ss(java.util.Date date) {
		return timeToString(date, HH_mm_ss);
	}

	/**
	 * 格式化日期 yyyyMM
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timeToString_yyyyMM(java.security.Timestamp timestamp) {
		if (null == timestamp) {
			return "";
		}
		return timeToString(timestamp.getTimestamp(), yyyyMM);
	}

	/**
	 * 格式化日期 yyyyMMdd
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timeToString_yyyyMMdd(java.security.Timestamp timestamp) {
		if (null == timestamp) {
			return "";
		}
		return timeToString(timestamp.getTimestamp(), yyyyMMdd);
	}

	/**
	 * 格式化日期 yyyyMMddHHmmss
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timeToString_yyyyMMddHHmmss(java.security.Timestamp timestamp) {
		if (null == timestamp) {
			return "";
		}
		return timeToString(timestamp.getTimestamp(), yyyyMMddHHmmss);
	}

	/**
	 * 格式化日期 HHmm
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timeToString_HHmm(java.security.Timestamp timestamp) {
		if (null == timestamp) {
			return "";
		}
		return timeToString(timestamp.getTimestamp(), HHmm);
	}

	/**
	 * 格式化日期 HHmmss
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timeToString_HHmmss(java.security.Timestamp timestamp) {
		if (null == timestamp) {
			return "";
		}
		return timeToString(timestamp.getTimestamp(), HHmmss);
	}

	/**
	 * 格式化日期 yyyy-MM
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timeToString_yyyy_MM(java.security.Timestamp timestamp) {
		if (null == timestamp) {
			return "";
		}
		return timeToString(timestamp.getTimestamp(), yyyy_MM);
	}

	/**
	 * 格式化日期 yyyy-MM-dd
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timeToString_yyyy_MM_dd(java.security.Timestamp timestamp) {
		if (null == timestamp) {
			return "";
		}
		return timeToString(timestamp.getTimestamp(), yyyy_MM_dd);
	}

	/**
	 * 格式化日期 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timeToString_yyyy_MM_dd_HH_mm_ss(java.security.Timestamp timestamp) {
		if (null == timestamp) {
			return "";
		}
		return timeToString(timestamp.getTimestamp(), yyyy_MM_dd_HH_mm_ss);
	}

	/**
	 * 格式化日期 HH:mm
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timeToString_HH_mm(java.security.Timestamp timestamp) {
		if (null == timestamp) {
			return "";
		}
		return timeToString(timestamp.getTimestamp(), HH_mm);
	}

	/**
	 * 格式化日期 HH:mm:ss
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timeToString_HH_mm_ss(java.security.Timestamp timestamp) {
		if (null == timestamp) {
			return "";
		}
		return timeToString(timestamp.getTimestamp(), HH_mm_ss);
	}

	/**
	 * 字符串转时间
	 * 
	 * @param dateStr
	 *            时间字符串
	 * @return
	 */
	public static java.util.Date stringToTime(String dateStr) {
		return stringToTime(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 字符串转时间
	 * 
	 * @param dateStr
	 *            时间字符串
	 * @param format
	 *            字符串格式 例 yyyy-MM-dd
	 * @return
	 */
	public static java.util.Date stringToTime(String dateStr, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		java.util.Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * java.security.Timestamp 转Date
	 * 
	 * @param timestamp
	 * @return
	 */
	public static java.sql.Date timestampToDate(java.security.Timestamp timestamp) {
		if (null == timestamp) {
			return null;
		}
		return new java.sql.Date(timestamp.getTimestamp().getTime());
	}

	/**
	 * java.sql.Timestamp 转Date
	 * 
	 * @param timestamp
	 * @return
	 */
	public static java.sql.Date timestampToDate(java.sql.Timestamp timestamp) {
		if (null == timestamp) {
			return null;
		}
		return new java.sql.Date(timestamp.getTime());
	}

	/**
	 * Date 转 java.sql.Timestamp
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Timestamp dateToSqlTimestamp(java.util.Date date) {
		if (null == date) {
			return null;
		}
		return new java.sql.Timestamp(date.getTime());
	}
	/*
	 * public static java.security.Timestamp
	 * datetampToSecurityTimestamp(java.util.Date date) { if(null==date) {
	 * return null; } return new java.security.Timestamp(date, null); }
	 */

	/**
	 * 日期调控
	 * 
	 * @param date
	 *            日期
	 * @param year
	 *            数字为正表示增加多少年 为负减少多少年 0为不改变
	 * @param month
	 *            数字为正表示增加多少月 为负减少多少月 0为不改变
	 * @param day
	 *            数字为正表示增加多少日 为负减少多少日 0为不改变
	 * @param hour
	 *            数字为正表示增加多少小时 为负减少多少小时 0为不改变
	 * @param minute
	 *            数字为正表示增加多分钟 为负减少多少分钟 0为不改变
	 * @param second
	 *            数字为正表示增加多少秒 为负减少多少秒 0为不改变
	 * @return
	 */
	public static java.util.Date dateControl(java.util.Date date, int year, int month, int day, int hour, int minute,
			int second) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		calendar.add(Calendar.MONTH, month);
		calendar.add(Calendar.DATE, day);
		calendar.add(Calendar.HOUR, hour);
		calendar.add(Calendar.MINUTE, minute);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 日期调控
	 * 
	 * @param date
	 *            日期
	 * @param year
	 *            数字为正表示增加多少年 为负减少多少年 0为不改变
	 * @param month
	 *            数字为正表示增加多少月 为负减少多少月 0为不改变
	 * @param day
	 *            数字为正表示增加多少日 为负减少多少日 0为不改变
	 * @return
	 */
	public static java.util.Date dateControlDate(java.util.Date date, int year, int month, int day) {
		return dateControl(date, year, month, day, 0, 0, 0);
	}

	/**
	 * 日期调控
	 * 
	 * @param date
	 *            日期
	 * @param hour
	 *            数字为正表示增加多少小时 为负减少多少小时 0为不改变
	 * @param minute
	 *            数字为正表示增加多分钟 为负减少多少分钟 0为不改变
	 * @param second
	 *            数字为正表示增加多少秒 为负减少多少秒 0为不改变
	 * @return
	 */
	public static java.util.Date dateControlTime(java.util.Date date, int hour, int minute, int second) {
		return dateControl(date, 0, 0, 0, hour, minute, second);
	}

	/**
	 * 功能描述：得到两个日期之间的相差天数
	 * 
	 * @param date
	 *            java.util.Date 日期
	 * @param date1
	 *            java.util.Date 日期
	 * @return 返回相减后的日期
	 */
	public static int dateControl(java.util.Date date, java.util.Date date1) {
		return (int) ((date.getTime() - date1.getTime()) / (24 * 3600 * 1000));
	}

	/**
	 * 计算两个日期相隔的天数
	 * 
	 * @param firstString
	 * @param secondString
	 * @return
	 */
	public static int dateControl(String starTtime, String endTime) {
		java.util.Date firstDate = stringToTime(starTtime);
		java.util.Date secondDate = stringToTime(endTime);
		int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
		return nDay;
	}

	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String 字符型日期
	 * @return String yyyy-MM-dd 格式
	 */
	public static java.util.Date getMonthBeginToDate(int year, int month) {
		return stringToTime((year < 10 ? "0" + year : year) + "-" + (month < 10 ? "0" + month : month) + "-01",
				"yyyy-MM-dd");
	}

	/**
	 * 取得指定日期的第一天
	 */
	public static java.util.Date getMonthBeginToDate(java.util.Date date) {
		return stringToTime(getMonthBeginToString(date), "yyyy-MM-dd");
	}

	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String 字符型日期
	 * @return String yyyy-MM-dd 格式
	 */
	public static String getMonthBeginToString(int year, int month) {
		return (year < 10 ? "0" + year : year) + "-" + (month < 10 ? "0" + month : month) + "-01";
	}

	/**
	 * 取得指定时间的该月份的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonthBeginToString(java.util.Date date) {
		return timeToString_yyyy_MM(date) + "-01";
	}

	/**
	 * 取得指定日期的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getMonthEndToDate(int year, int month) {

		return stringToTime(getMonthEndToString(year, month), "yyyy-MM-dd");
	}

	/**
	 * 取得指定日期的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getMonthEndToDate(java.util.Date date) {
		return stringToTime(getMonthEndToString(date), "yyyy-MM-dd");
	}

	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String 字符型日期
	 * @return String 日期字符串 yyyy-MM-dd格式
	 */
	public static String getMonthEndToString(int year, int month) {
		java.util.Date da = stringToTime(
				(year < 10 ? "0" + year : year) + "-" + (month < 10 ? "0" + month : month) + "-01");
		return getMonthEndToString(da);
	}

	/**
	 * 取得指定日期的当月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonthEndToString(java.util.Date date) {
		String rString = "";
		if (null != date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DATE, 1);
			calendar.add(Calendar.MONTH, +1);
			calendar.add(Calendar.DATE, -1);
			rString = timeToString_yyyy_MM_dd(calendar.getTime());
		}
		return rString;
	}

	/**
	 * 判断当前时间是否在指定日期内
	 * 
	 * @param starttime
	 * @param endtime
	 * @param isMinute(是否带时分秒)
	 * @return
	 */
	public static boolean dateisNowBetweenDates(String startTime, String endTime, boolean isMinute) {
		return dateisNowBetweenDates(new java.util.Date(), startTime, endTime, isMinute);
	}

	/**
	 * 判断指定时间是否在指定日期内
	 * 
	 * @param date
	 * @param starttime
	 * @param endtime
	 * @param isMinute(是否带时分秒)
	 * @return
	 */
	public static boolean dateisNowBetweenDates(String data, String startTime, String endTime, boolean isMinute) {
		return dateisNowBetweenDates(stringToTime(data), startTime, endTime, isMinute);
	}

	/**
	 * 判断指定时间是否在指定日期内
	 * 
	 * @param date
	 * @param starttime
	 * @param endtime
	 * @param isMinute(是否带时分秒)
	 * @return
	 */
	public static boolean dateisNowBetweenDates(java.util.Date data, String startTime, String endTime,
			boolean isMinute) {
		java.util.Date sT = null;// 起始时间
		java.util.Date eT = null;// 终止时间
		if (isMinute) {
			sT = stringToTime(startTime + " 00:00:01");
			eT = stringToTime(endTime + " 23:59:59");
		} else {
			sT = stringToTime(startTime);
			eT = stringToTime(endTime);
		}
		Calendar scalendar = Calendar.getInstance();
		scalendar.setTime(sT);// 起始时间
		Calendar ecalendar = Calendar.getInstance();
		ecalendar.setTime(eT);// 终止时间
		Calendar calendarnow = Calendar.getInstance();
		calendarnow.setTime(data);
		return calendarnow.after(scalendar) && calendarnow.before(ecalendar);
	}

	/**
	 * 判断日期是否在指定日期之后 (date是否在date2之后)
	 * 
	 * @param date
	 * @param date2
	 * @return
	 */
	public static boolean dateisAfter(String date, String date2) {
		java.util.Date sT = stringToTime(date);
		java.util.Date eT = stringToTime(date2);
		return sT.after(eT);
	}

	/**
	 * 判断日期是否在指定日期之前 (date是否在date2之前)
	 * 
	 * @param date
	 * @return
	 */
	public static boolean dateisBefore(String date, String date2) {
		java.util.Date sT = stringToTime(date);
		java.util.Date eT = stringToTime(date2);
		return sT.before(eT);
	}

}
