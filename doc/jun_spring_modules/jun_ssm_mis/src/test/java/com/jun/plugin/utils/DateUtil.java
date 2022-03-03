package com.jun.plugin.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

/**
 * 
 * 功能描述�?
 * 
 * @author Administrator
 * @Date Jul 19, 2008
 * @Time 9:47:53 AM
 * @version 1.0
 */
public class DateUtil {

	public static Date date = null;

	// public static DateFormat dateFormat = null;

	public static Calendar calendar = null;

	/**
	 * 功能描述：格式化日期
	 * 
	 * @param dateStr
	 *            String 字符型日�?
	 * @param format
	 *            String 格式
	 * @return Date 日期
	 */
	public static Date parseDate(String dateStr, String format) {
		try {
			dateFormat = new SimpleDateFormat(format);
			String dt = dateStr.replaceAll("-", "/");
			if ((!dt.equals("")) && (dt.length() < format.length())) {
				dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]", "0");
			}
			date = (Date) dateFormat.parse(dt);
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 功能描述：格式化日期
	 * 
	 * @param dateStr
	 *            String 字符型日期：YYYY-MM-DD 格式
	 * @return Date
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy/MM/dd");
	}

	/**
	 * 功能描述：格式化输出日期
	 * 
	 * @param date
	 *            Date 日期
	 * @param format
	 *            String 格式
	 * @return 返回字符型日�?
	 */
	public static String format3(Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				dateFormat = new SimpleDateFormat(format);
				result = dateFormat.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 功能描述�?
	 * 
	 * @param date
	 *            Date 日期
	 * @return
	 */
	public static String format2(Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 功能描述：返回年�?
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回年份
	 */
	public static int getYear(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 功能描述：返回月�?
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回月份
	 */
	public static int getMonth(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 功能描述：返回日�?
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回日份
	 */
	public static int getDay(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 功能描述：返回小�?
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 功能描述：返回分�?
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 功能描述：返回毫�?
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 功能描述：返回字符型日期
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日�?yyyy/MM/dd 格式
	 */
	public static String getDate(Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 功能描述：返回字符型时间
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回字符型时�?HH:mm:ss 格式
	 */
	public static String getTime(Date date) {
		return format(date, "HH:mm:ss");
	}

	/**
	 * 功能描述：返回字符型日期时间
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回字符型日期时�?yyyy/MM/dd HH:mm:ss 格式
	 */
	public static String getDateTime(Date date) {
		return format(date, "yyyy/MM/dd HH:mm:ss");
	}

	/**
	 * 功能描述：日期相�?
	 * 
	 * @param date
	 *            Date 日期
	 * @param day
	 *            int 天数
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int day) {
		calendar = Calendar.getInstance();
		long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	/**
	 * 功能描述：日期相�?
	 * 
	 * @param date
	 *            Date 日期
	 * @param date1
	 *            Date 日期
	 * @return 返回相减后的日期
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 功能描述：取得指定月份的第一�?
	 * 
	 * @param strdate
	 *            String 字符型日�?
	 * @return String yyyy-MM-dd 格式
	 */
	public static String getMonthBegin(String strdate) {
		date = parseDate(strdate);
		return format(date, "yyyy-MM") + "-01";
	}

	/**
	 * 功能描述：取得指定月份的�?���?��
	 * 
	 * @param strdate
	 *            String 字符型日�?
	 * @return String 日期字符�?yyyy-MM-dd格式
	 */
	public static String getMonthEnd(String strdate) {
		date = parseDate(getMonthBegin(strdate));
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDate(calendar.getTime());
	}

	/**
	 * 功能描述：常用的格式化日�?
	 * 
	 * @param date
	 *            Date 日期
	 * @return String 日期字符�?yyyy-MM-dd格式
	 */
	public static String formatDate(Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 功能描述：以指定的格式来格式化日�?
	 * 
	 * @param date
	 *            Date 日期
	 * @param format
	 *            String 格式
	 * @return String 日期字符�?
	 */
	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static void main2(String[] args) {
		Date d = new Date();
		// System.out.println(d.toString());
		// System.out.println(formatDate(d).toString());
		// System.out.println(getMonthBegin(formatDate(d).toString()));
		// System.out.println(getMonthBegin("2008/07/19"));
		// System.out.println(getMonthEnd("2008/07/19"));
		System.out.println(addDate(d, 15).toString());

		/*
		 * Date t = DateUtil.parseStringToDate("2012-12-20 10:30:12",
		 * DateUtil.DATE_FORMAT_FULL);
		 * System.out.println(t);DateUtil.getAfterDateTime(30*24)
		 * System.out.println(parseDateToString(t));
		 */
		// System.out.println(DateUtil.getAfterDateTime(2,"HH:mm:ss"));
		/*
		 * System.out.println(DateUtil.isBefore_int("2014-12-20", "2014-12-20",
		 * DATE_FORMAT_SHORT)/86400000);
		 *//*
			 * Date d = DateUtil.parseStringToDate("2014-12-20 10:30:12",
			 * DATE_FORMAT_FULL); Date d1 = DateUtil.parseStringToDate(
			 * "2014-12-10 10:30:12", DATE_FORMAT_FULL); Long n = d.getTime() -
			 * d1.getTime();
			 * System.out.println(DateUtil.changeTime(n.intValue()/1000));
			 */
		// System.out.println(DateUtil.getAfterDateTime(30*24).toLocaleString());
		System.out.println((long) 9 / 10);
		boolean dateArr = isLegalOfSsqj("2008Q1", "2");
		System.out.println(dateArr);
	}

	// ~ Static fields/initializers
	// =============================================

	private static String datePattern = "MM/dd/yyyy";

	private static String timePattern = datePattern + " HH:MM a";

	// ~ Methods
	// ================================================================

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	public static final String date2Str(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	public static final String date2Str(String pattern, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(pattern);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			return null;
		}

		return (date);
	}

	public static final Date str2Date2(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			return null;
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday1() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(datePattern);

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			System.out.print("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(datePattern, aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		try {

			aDate = convertStringToDate(datePattern, strDate);
		} catch (ParseException pe) {
			// log.error("Could not convert '" + strDate
			// + "' to a date, throwing exception");
			pe.printStackTrace();
			return null;

		}
		return aDate;
	}

	// ���ڸ�ʽת����ʱ���
	public static long getTimeStamp(String pattern, String strDate) {
		long returnTimeStamp = 0;
		Date aDate = null;
		try {
			aDate = convertStringToDate(pattern, strDate);
		} catch (ParseException pe) {
			aDate = null;
		}
		if (aDate == null) {
			returnTimeStamp = 0;
		} else {
			returnTimeStamp = aDate.getTime();
		}
		return returnTimeStamp;
	}

	// ��ȡ��ǰ���ڵ��ʴ�
	public static long getNowTimeStamp() {
		long returnTimeStamp = 0;
		Date aDate = null;
		try {
			aDate = convertStringToDate("yyyy-MM-dd HH:mm:ss", getNowDateTime());
		} catch (ParseException pe) {
			aDate = null;
		}
		if (aDate == null) {
			returnTimeStamp = 0;
		} else {
			returnTimeStamp = aDate.getTime();
		}
		return returnTimeStamp;
	}

	/**
	 * �õ���ʽ�����ϵͳ��ǰ����
	 * 
	 * @param strScheme
	 *            ��ʽģʽ�ַ�
	 * @return ��ʽ�����ϵͳ��ǰʱ�䣬������쳣�����ؿմ�""
	 * @see java.util.SimpleDateFormat
	 */
	public static final String getNowDateTime(String strScheme) {
		String strReturn = null;
		Date now = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(strScheme);
			strReturn = sdf.format(now);
		} catch (Exception e) {
			strReturn = "";
		}
		return strReturn;
	}

	public static final String getNowDateTime() {
		String strReturn = null;
		Date now = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strReturn = sdf.format(now);
		} catch (Exception e) {
			strReturn = "";
		}
		return strReturn;
	}

	/**
	 * ת�����ڸ�ʽ"MM/dd/YY��MM.dd.YY��MM-dd-YY��MM/dd/YY"�������Ϊ��ĸ�ʽyyyy-MM-dd
	 * 
	 * @param strDate
	 *            ��ת�������ڸ�ʽ
	 * @return ��ʽ��������ڣ�������쳣�����ؿմ�""
	 * @see java.util.SimpleDateFormat
	 */
	public static final String convertNormalDate(String strDate) {
		String strReturn = null;
		// �ȰѴ������ָ��ת����java��ʶ�ķָ��
		String[] date_arr = strDate.split("\\.|\\/|\\-");
		try {
			if (date_arr.length == 3) {
				if (date_arr[2].length() != 4) {
					String nowYear = getNowDateTime("yyyy");
					date_arr[2] = nowYear.substring(0, 2) + date_arr[2];
				}
				strReturn = DateUtil.getDateTime("yyyy-MM-dd", convertStringToDate(combineStringArray(date_arr, "/")));
			}

		} catch (Exception e) {
			return strReturn;
		}
		return strReturn;
	}

	/**
	 * ���ַ�����ʹ��ָ���ķָ���ϲ���һ���ַ�
	 * 
	 * @param array
	 *            �ַ�����
	 * @param delim
	 *            �ָ���Ϊnull��ʱ��ʹ��""��Ϊ�ָ���û�зָ���
	 * @return �ϲ�����ַ�
	 * @since 0.4
	 */
	public static final String combineStringArray(String[] array, String delim) {
		int length = array.length - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	public static final int getWeekNum(String strWeek) {
		int returnValue = 0;
		if (strWeek.equals("Mon")) {
			returnValue = 1;
		} else if (strWeek.equals("Tue")) {
			returnValue = 2;
		} else if (strWeek.equals("Wed")) {
			returnValue = 3;
		} else if (strWeek.equals("Thu")) {
			returnValue = 4;
		} else if (strWeek.equals("Fri")) {
			returnValue = 5;
		} else if (strWeek.equals("Sat")) {
			returnValue = 6;
		} else if (strWeek.equals("Sun")) {
			returnValue = 0;
		} else if (strWeek == null) {
			returnValue = 0;
		}

		return returnValue;
	}

	/**
	 * ��ȡ�����ַ��е�����ʱ���ʾ�ַ�
	 * 
	 * @param strDate
	 * @return
	 */
	public static final String getSabreTime(String strDate) {
		String strReturn = "";
		try {

			Date d = DateUtil.str2Date("yyyy-MM-dd HH:mm:ss", StringUtil.replace(strDate, "T", " "));
			strReturn = DateUtil.date2Str("hh:mm aaa", d);

		} catch (Exception e) {
			return strReturn;
		}
		return strReturn;
	}

	/**
	 * ��ȡ�����ַ��е��������ڱ�ʾ�ַ�
	 * 
	 * @param strDate
	 * @return
	 */
	public static final String getSabreDate(String strDate) {
		String strReturn = "";
		try {
			String p = null;
			if (strDate.length() > 10)
				p = "yyyy-MM-dd HH:mm:ss";
			else
				p = "yyyy-MM-dd";
			Date d = DateUtil.str2Date(p, StringUtil.replace(strDate, "T", " "));
			strReturn = DateUtil.date2Str("EEE d-MMM", d);

		} catch (Exception e) {
			return strReturn;
		}
		return strReturn;
	}

	/**
	 * ��ȡ�����ַ����������ʱ���ʾ
	 * 
	 * @param strDate
	 * @return
	 */
	public static final String getSabreDateTime(String strDate) {
		String strReturn = "";
		try {
			String p = null;
			if (strDate.length() > 10)
				p = "yyyy-MM-dd HH:mm:ss";
			else
				p = "yyyy-MM-dd";
			Date d = DateUtil.str2Date(p, StringUtil.replace(strDate, "T", " "));
			strReturn = DateUtil.date2Str("EEE d-MMM hh:mm aaa", d);

		} catch (Exception e) {
			return strReturn;
		}
		return strReturn;
	}

	/**
	 * �õ���ʽ�����ָ������
	 * 
	 * @param strScheme
	 *            ��ʽģʽ�ַ�
	 * @param date
	 *            ָ��������ֵ
	 * @return ��ʽ�����ָ�����ڣ�������쳣�����ؿմ�""
	 * @see java.util.SimpleDateFormat
	 */
	public static final String getDateTime(Date date, String strScheme) {
		String strReturn = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(strScheme);
			strReturn = sdf.format(date);
		} catch (Exception e) {
			strReturn = "";
		}

		return strReturn;
	}

	/**
	 * ��ȡ����
	 * 
	 * @param timeType
	 *            ʱ�����ͣ�Ʃ�磺Calendar.DAY_OF_YEAR
	 * @param timenum
	 *            ʱ�����֣�Ʃ�磺-1 ���죬0 ���죬1 ����
	 * @return ����
	 */
	public static final Date getDateFromNow(int timeType, int timenum) {
		Calendar cld = Calendar.getInstance();
		cld.set(timeType, cld.get(timeType) + timenum);
		return cld.getTime();
	}

	/**
	 * ��ȡ����
	 * 
	 * @param timeType
	 *            ʱ�����ͣ�Ʃ�磺Calendar.DAY_OF_YEAR
	 * @param timenum
	 *            ʱ�����֣�Ʃ�磺-1 ���죬0 ���죬1 ����
	 * @param format_string
	 *            ʱ���ʽ��Ʃ�磺"yyyy-MM-dd HH:mm:ss"
	 * @return �ַ�
	 */
	public static final String getDateFromNow(int timeType, int timenum, String format_string) {
		if ((format_string == null) || (format_string.equals("")))
			format_string = "yyyy-MM-dd HH:mm:ss";
		Calendar cld = Calendar.getInstance();
		Date date = null;
		DateFormat df = new SimpleDateFormat(format_string);
		cld.set(timeType, cld.get(timeType) + timenum);
		date = cld.getTime();
		return df.format(date);
	}

	/**
	 * ��ȡ��ǰ���ڵ��ַ�
	 * 
	 * @param format_string
	 *            ʱ���ʽ��Ʃ�磺"yyyy-MM-dd HH:mm:ss"
	 * @return �ַ�
	 */
	public static final String getDateNow(String format_string) {
		if ((format_string == null) || (format_string.equals("")))
			format_string = "yyyy-MM-dd HH:mm:ss";
		Calendar cld = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(format_string);
		return df.format(cld.getTime());
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat();;

	/**
	 * 将时间转换成数据
	 * 
	 * @param date
	 *            需要转换的时间
	 * @param format
	 *            转换得格式 例如"yyyy-MM-dd hh:mm:ss"
	 * @return String
	 */
	public static String dateToString(Timestamp date, String format) {
		// 附加时间格式
		dateFormat.applyPattern(format);
		// 将时间转换成字符串
		return dateFormat.format(date);
	}

	/**
	 * 将时间转换成数据
	 * 
	 * @param date
	 *            需要转换的时间
	 * @param format
	 *            转换得格式 例如"yyyy-MM-dd hh:mm:ss"
	 * @return String
	 */
	public static String dateToString(java.util.Date date, String format) {
		// 附加时间格式
		dateFormat.applyPattern(format);
		// 将时间转换成字符串
		return dateFormat.format(date);
	}

	/**
	 * 将时间转换成时间
	 * 
	 * @param date
	 *            需要转换的时间
	 * @param format
	 *            转换得格式 例如"yyyy-MM-dd hh:mm:ss"
	 * @return Date
	 */
	public static java.util.Date dateToDate(java.util.Date date, String format) {
		// 附加时间格式
		dateFormat.applyPattern(format);
		// 将时间转换成字符串
		try {
			return dateFormat.parse(dateToString(date, format));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将字符串转换成时间
	 * 
	 * @param dateString
	 *            需要转换的时间字符串
	 * @param format
	 *            转换得格式 例如"yyyy-MM-dd hh:mm:ss"
	 * @return Date
	 */
	public static java.util.Date stringToDate(String dateString, String format) {
		// 附加时间格式
		dateFormat.applyPattern(format);
		// 将时间转换成字符串
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 比较两个时间的差值(以秒为单位)
	 * 
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return long
	 */
	public static long dateDiff(java.util.Date date1, java.util.Date date2) {
		// return date1.getTime() / (24*60*60*1000) - date2.getTime() /
		// (24*60*60*1000);
		return date2.getTime() / 1000 - date1.getTime() / 1000; // 用立即数，减少乘法计算的开销
	}

	/**
	 * 作用：获取当前日期 格式 2007－3－4 返回类型：Date 参数：null
	 */
	public static Date date() {
		// 获取当前日期
		String strDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		// 将字符串日期转换成 java.sql.Date 日期类型
		// return Date.valueOf(strDate);
		return null;
	}

	/**
	 * 获取当前日期的前几天或者后几天日期
	 * 
	 * @param day
	 *            天数 负数代表前几天，正数代表后几天
	 * @return
	 */
	public static Date getDateByDay(int day) {
		// 获取当前日期
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, date.get(Calendar.DATE) + day);
		String strDate = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
		// 将字符串日期转换成 java.sql.Date 日期类型
		// return Date.valueOf(strDate);
		return null;
	}

	/**
	 * 作用：获取当前日期 格式 2007－3－4 12：10：20 返回类型：Date 参数：null
	 */
	public static Timestamp datetime() {
		// 获取当前日期
		String strTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		// 将字符串日期转换成 java.sql.Date 日期类型
		return Timestamp.valueOf(strTimestamp);
	}

	/**
	 * 作用：获取当前日期 格式 2007－3－4 12：10：20 返回类型：Date 参数：null
	 */
	public static String datetimeByString() {
		// 获取当前日期
		String strTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		// 将字符串日期转换成 java.sql.Date 日期类型
		return strTimestamp;
	}

	/**
	 * 作用：获取当前时间 格式 12：10：20 返回类型：Date 参数：null
	 */
	public static Time time() {
		// 获取当前日期
		String strTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		// 将字符串日期转换成 java.sql.Date 日期类型
		return Time.valueOf(strTime);
	}

	public static final String PARENT_ONLYDATE = "yyyy-MM-dd";
	public static final String PARENT_ALL = "yyyy-MM-dd HH:mm:ss";
	public static final String PARENT_SIMPLE_ALL = "yyyyMMddHHmmss";

	public static String format4(Date date, String paren) {
		SimpleDateFormat format = new SimpleDateFormat(paren);
		return format.format(date);
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String getDate1() {
		return sdf.format(new Date());
	}


	/**
	 * 验证数据是否为空
	 * 
	 * @param str
	 * @return true 不为空 false 为空
	 */
	public static boolean IsNull(String str) {
		if (str != null && str.trim().length() > 0)
			return true;
		else
			return false;
	}

	/**
	 * 验证数据长度
	 * 
	 * @param str
	 * @param statlength
	 *            字符串的最小长度
	 * @param endlength
	 *            字符串的最大长度
	 * @return true 数据长度合法 false 数据长度不合法
	 */
	public static boolean IsLength(String str, int maxlength) {
		if (IsNull(str)) {
			if (str.trim().length() <= maxlength)
				return true;
			else
				return false;
		}
		return false;
	}

	/**
	 * 验证数据长度
	 * 
	 * @param str
	 * @param statlength
	 *            字符串的最小长度
	 * @param endlength
	 *            字符串的最大长度
	 * @return true 数据长度合法 false 数据长度不合法
	 */
	public static boolean IsLength(String str, int minlength, int maxlength) {
		if (IsNull(str)) {
			if (str.trim().length() >= minlength && str.trim().length() <= maxlength)
				return true;
			else
				return false;
		}
		return false;
	}

	public static void main22(String[] args) {
		String strTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

		String str = DateUtil.dateToString(DateUtil.datetime(), "HH:mm:ss");

		System.out.println(strTimestamp);
		// Date.valueOf(strTimestamp);
		System.out.println(DateUtil.stringToDate(strTimestamp, "yyyy-MM-dd HH:mm:ss"));
	}

	/**********************************************************************************************/
	/**********************************************************************************************/

	private static char DAY_DELIMITER = '-';
	public static int YEAR = 1;
	public static int MONTH = 2;
	public static int DAY = 5;
	public static int HOUR = 11;
	public static int MINUTE = 12;
	public static int SECOND = 13;
	public static int SUNDAY = 1;
	public static int MONDAY = 2;
	public static int TUESDAY = 3;
	public static int WEDNESDAY = 4;
	public static int THURSDAY = 5;
	public static int FRIDAY = 6;
	public static int SATURDAY = 7;

	public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_FORMAT_SHORT = "yyyy-MM-dd";

	public static final String DATE_FORMAT_COMPACT = "yyyyMMdd";

	public static final String DATE_FORMAT_COMPACTFULL = "yyyyMMddHHmmss";

	public static final String DATE_FORMAT_FULL_MSEL = "yyyyMMddHHmmssSSSS";

	public static final String DATE_YEAR_MONTH = "yyyyMM";

	/**
	 * 获取系统当前日期
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 得到当前日期
	 * 
	 * @return String 当前日期 yyyy-MM-dd HH:mm:ss格式
	 */
	public static String getCurDateTime() {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		// String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		DateFormat sdf = new SimpleDateFormat(DATE_FORMAT_FULL);
		sdf.setTimeZone(TimeZone.getDefault());
		return (sdf.format(now.getTime()));
	}

	/**
	 * 得到当前日期
	 * 
	 * @return String 当前日期 yyyyMMddHHmmss格式
	 */
	public static String getCurDateTime1() {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		DateFormat sdf = new SimpleDateFormat(DATE_FORMAT_COMPACTFULL);
		sdf.setTimeZone(TimeZone.getDefault());
		return (sdf.format(now.getTime()));
	}

	/**
	 * 得到当前日期YYYYMM格式
	 * 
	 * @return String 当前日期 yyyyMM格式
	 */
	public static String getCurDateYYYYMM() {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());

		DateFormat sdf = new SimpleDateFormat(DATE_YEAR_MONTH);
		sdf.setTimeZone(TimeZone.getDefault());
		return (sdf.format(now.getTime()));
	}

	/**
	 * 得到下个月日期YYYYMM格式
	 * 
	 * @return String 当前日期 yyyyMM格式
	 */
	public static String getNextMonthDateYYYYMM() {
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		cal.add(Calendar.MONTH, 1);
		DateFormat sdf = new SimpleDateFormat(DATE_YEAR_MONTH);
		sdf.setTimeZone(TimeZone.getDefault());
		return (sdf.format(cal.getTime()));
	}

	/**
	 * 得到当前日期
	 * 
	 * @return String 当前日期 yyyyMMdd格式
	 */
	public static String getCurDateYYYYMMDD() {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());

		DateFormat sdf = new SimpleDateFormat(DATE_FORMAT_COMPACT);
		sdf.setTimeZone(TimeZone.getDefault());
		return (sdf.format(now.getTime()));
	}

	/**
	 * 判断否是今天
	 * 
	 * @param strDate
	 *            yyyy-MM-dd
	 * @return
	 */
	public static boolean isCurrentDay(String strDate) {
		boolean bRet = false;
		try {
			Calendar now = Calendar.getInstance(TimeZone.getDefault());
			// String DATE_FORMAT = "yyyy-MM-dd";
			DateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SHORT);
			sdf.setTimeZone(TimeZone.getDefault());
			Date date1 = sdf.parse(strDate);
			String strDate1 = sdf.format(date1);
			String strDate2 = sdf.format(now.getTime());
			bRet = strDate1.equalsIgnoreCase(strDate2);
		} catch (ParseException e) {
			e.printStackTrace();

		}
		return bRet;
	}

	/**
	 * 判断否是今天
	 * 
	 * @param Date
	 * 
	 * @return
	 */
	public static boolean isCurrentDay(Date dt) {
		boolean bRet = false;
		Calendar now = Calendar.getInstance(TimeZone.getDefault());

		DateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SHORT);
		sdf.setTimeZone(TimeZone.getDefault());
		String strDate1 = sdf.format(dt);
		String strDate2 = sdf.format(now.getTime());
		bRet = strDate1.equalsIgnoreCase(strDate2);
		return bRet;
	}

	/**
	 * 获取几小时后的时间
	 * 
	 * @param hour
	 *            小时
	 * @param format
	 *            hh:mm:ss
	 * @return HH:MM:SS
	 */
	public static String getAfterDateTime(int hour, String format) {
		long lTime = new Date().getTime() + hour * 60 * 60 * 1000;
		Calendar calendar = new GregorianCalendar();
		java.util.Date date_now = new java.util.Date(lTime);
		calendar.setTime(date_now);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		java.util.Date date = calendar.getTime();
		return sdf.format(date);
	}

	/**
	 * 获取几小时后的时间
	 * 
	 * @param hour
	 *            小时
	 * @param format
	 *            hh:mm:ss
	 * @return java.util.Date
	 */
	public static java.util.Date getAfterDateTime(long hour) {
		long lTime = new Date().getTime() + hour * 60 * 60 * 1000;
		Calendar calendar = new GregorianCalendar();
		java.util.Date date_now = new java.util.Date(lTime);
		calendar.setTime(date_now);
		java.util.Date date = calendar.getTime();
		return date;
	}

	/**
	 * 得到当前日期
	 * 
	 * @param format日期格式
	 * @return String 当前日期 format日期格式
	 */
	public static String getCurDateTime(String format) {
		try {
			Calendar now = Calendar.getInstance(TimeZone.getDefault());
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setTimeZone(TimeZone.getDefault());
			return (sdf.format(now.getTime()));
		} catch (Exception e) {
			return getCurDateTime(); // 如果无法转化，则返回默认格式的时间。
		}
	}

	/**
	 * 得到时间戳
	 * 
	 * @param null
	 * @return String 当前日期时间戳(yyyyMMddHHmmssSSSS)
	 */
	public static String getTimeStamp() {
		try {
			Calendar now = Calendar.getInstance(TimeZone.getDefault());
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_FULL_MSEL);
			sdf.setTimeZone(TimeZone.getDefault());
			return (sdf.format(now.getTime()));
		} catch (Exception e) {
			return getCurDateTime(); // 如果无法转化，则返回默认格式的时间。
		}
	}

	/**
	 * 日期转字符串
	 * 
	 * @return String
	 */
	public static String parseDateToString(Date thedate, String format) {
		DateFormat df = new SimpleDateFormat(format);
		if (thedate != null) {
			return df.format(thedate.getTime());
		}
		return null;
	}

	/**
	 * 日期转字符串
	 * 
	 * @param thedate
	 * @return
	 */
	public static String parseDateToString(Date thedate) {
		// String format = "yyyy-MM-dd";
		return parseDateToString(thedate, DATE_FORMAT_SHORT);
	}

	/**
	 * 字符串转日期
	 * 
	 * @return Date
	 */
	public static Date parseStringToDate(String thedate, String format) {
		DateFormat sdf = new SimpleDateFormat(format);
		Date dd1 = null;
		try {
			dd1 = sdf.parse(thedate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dd1;
	}

	/**
	 * 由String型日期转成format形式String
	 * 
	 * @param format1原先格式
	 * @param format2转化格式
	 * @return String
	 */
	public static String changeFormatDateString(String format1, String format2, String strDate) {
		if (strDate == null)
			return "";
		if (strDate.length() >= format1.length() && format1.length() >= format2.length()) {
			return parseDateToString(parseStringToDate(strDate, format1), format2);
		}
		return strDate;
	}

	// 得到当前日期的前一天时间 yyyymmdd
	public static String beforeNDaysDate(String format) {
		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		SimpleDateFormat sdf = new SimpleDateFormat(format); // 设置时间格式
		String timed = sdf.format(dBefore); // 格式化前一天
		return timed;
	}

	/**
	 * 获得N个月后的日期
	 * 
	 * theDate 日期
	 * 
	 * int month 月数
	 * 
	 * format 格式
	 * 
	 */
	public static String afterNMonthDate(String theDate, int month, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		Date dd1 = null;
		try {
			dd1 = sdf.parse(theDate);
			cal.setTime(dd1);
			cal.add(Calendar.MONTH, month);
			sdf.setTimeZone(TimeZone.getDefault());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (sdf.format(cal.getTime()));
	}

	/**
	 * 得到N天后的日期
	 * 
	 * @param theDate某日期
	 *            格式 yyyy-MM-dd
	 * @param nDayNum
	 *            N天
	 * @return String N天后的日期
	 */
	public static String afterNDaysDate(String theDate, String nDayNum, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date dd1 = sdf.parse(theDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dd1);
			cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(nDayNum));
			sdf.setTimeZone(TimeZone.getDefault());
			return (sdf.format(cal.getTime()));
		} catch (Exception e) {
			return getCurDateTime(format); // 如果无法转化，则返回默认格式的时间。
		}
	}

	/**
	 * 得到N小时后的日期
	 * 
	 * @param theDate某日期
	 *            格式传入传出格式都是 format格式
	 * @param nDayNum
	 *            N小时
	 * @return String N小时后的日期
	 */
	public static String afterNHoursDate(String theDate, String nHourNum, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date dd1 = sdf.parse(theDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dd1);
			cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(nHourNum));
			sdf.setTimeZone(TimeZone.getDefault());
			return (sdf.format(cal.getTime()));
		} catch (Exception e) {
			return getCurDateTime(format); // 如果无法转化，则返回默认格式的时间。
		}
	}

	/**
	 * 得到N分钟后的日期
	 * 
	 * @param theDate某日期
	 *            格式 yyyy-MM-dd
	 * @param nDayNum
	 *            N分钟
	 * @return String N分钟后的日期
	 */
	public static String afterNMinsDate(String theDate, String nMinNum, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date dd1 = sdf.parse(theDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dd1);
			cal.add(Calendar.MINUTE, Integer.parseInt(nMinNum));
			sdf.setTimeZone(TimeZone.getDefault());
			return (sdf.format(cal.getTime()));
		} catch (Exception e) {
			return getCurDateTime(format); // 如果无法转化，则返回默认格式的时间。
		}
	}

	/**
	 * 得到N秒后的日期
	 * 
	 * @param theDate某日期
	 *            格式 yyyy-MM-dd
	 * @param nDayNum
	 *            N分钟
	 * @return String N分钟后的日期
	 */
	public static String afterNSecondsDate(String theDate, String nMinNum, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date dd1 = sdf.parse(theDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dd1);
			cal.add(Calendar.MILLISECOND, Integer.parseInt(nMinNum));
			sdf.setTimeZone(TimeZone.getDefault());
			return (sdf.format(cal.getTime()));
		} catch (Exception e) {
			return getCurDateTime(format); // 如果无法转化，则返回默认格式的时间。
		}
	}

	// 比较两个字符串格式日期大小,带格式的日期
	public static boolean isBefore(String strdat1, String strdat2, String format) {
		try {
			Date dat1 = parseStringToDate(strdat1, format);
			Date dat2 = parseStringToDate(strdat2, format);
			return dat1.before(dat2);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 比较两个字符串格式日期大小,带格式的日期,返回int
	public static long isBefore_int(String strdat1, String strdat2, String format) {
		long result = 0;
		try {
			Date dat1 = parseStringToDate(strdat1, format);
			Date dat2 = parseStringToDate(strdat2, format);
			return dat2.getTime() - dat1.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 比较两个字符串格式日期大小,默认转换格式:yyyy-MM-dd
	public static boolean isBefore(String strdat1, String strdat2) {
		// String format = "yyyy-MM-dd";
		return isBefore(strdat1, strdat2, DATE_FORMAT_SHORT);
	}

	/**
	 * 获取休息时间
	 * 
	 * @param strTime
	 *            strTime=" 3:00:00"; 需要休息的时间，注意有空格
	 * @return 需要休息的时间
	 * @throws ParseException
	 */
	public static long getSleepTime(String strTime) throws ParseException {
		long p = 1;
		long l_date = System.currentTimeMillis();
		java.util.Date date_now = new java.util.Date(l_date);
		SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT_SHORT);
		String strDate = fmt.format(date_now) + strTime;
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_FULL);
		if (date_now.before(df.parse(strDate)))
			p = (df.parse(strDate)).getTime() - l_date;
		else {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date_now);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			java.util.Date date = calendar.getTime();
			strDate = fmt.format(date) + strTime;
			p = (df.parse(strDate)).getTime() - l_date;
		}
		return p;
	}

	public static Timestamp getCurrentTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String getHourMinute(String fullTime) {
		return fullTime.substring(11, 16);
	}

	// 得到日期数组中最小日期
	public static String getMinDateOfArray(String dateArray[]) {
		String dateTmp = "";
		if (dateArray != null) {
			dateTmp = dateArray[0];
			for (int i = 1; i < dateArray.length; i++) {
				if (isBefore(dateArray[i], dateTmp, DATE_FORMAT_SHORT)) {
					dateTmp = dateArray[i];
				}
			}
		}
		return dateTmp;
	}

	// 得到日期数组中最大日期
	public static String getMaxDateOfArray(String dateArray[]) {
		String dateTmp = "";
		if (dateArray != null) {
			dateTmp = dateArray[0];
			for (int i = 1; i < dateArray.length; i++) {
				if (isBefore(dateTmp, dateArray[i], DATE_FORMAT_SHORT)) {
					dateTmp = dateArray[i];
				}
			}
		}
		return dateTmp;
	}

	public static boolean hasNextDayInArray(String dateArray[], String dateTmp) {
		int index = 0;
		if (dateArray != null) {
			Arrays.sort(dateArray);
			for (int i = 0; i < dateArray.length; i++) {
				if (dateArray[i].equals(dateTmp)) {
					index = i;
				}
			}
			System.out.println(index);
			return index + 1 != dateArray.length;
		}
		return false;
	}

	public static boolean hasPreviousDayInArray(String dateArray[], String dateTmp) {
		int index = 0;
		if (dateArray != null) {
			Arrays.sort(dateArray);
			for (int i = 0; i < dateArray.length; i++) {
				if (dateArray[i].equals(dateTmp)) {
					index = i;
				}
			}
			return index != 0;
		}
		return false;
	}

	/*
	 * 得到上一个月或者下一个月的日期
	 */
	public static String getDayafterMonth(String theDate, int month, String formatStr) {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		Date dat1 = parseStringToDate(theDate, formatStr);
		now.setTime(dat1);
		sdf.setTimeZone(TimeZone.getDefault());
		now.add(Calendar.MONTH, month);
		return sdf.format(now.getTime());
	}

	/**
	 * <p>
	 * 获取当前系统时间的小时数
	 * </p>
	 * 
	 * @return
	 */
	public static int getCurrentHour() {
		Calendar calendar = new GregorianCalendar();
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * <p>
	 * 获取当前系统时间的分钟数
	 * </p>
	 * 
	 * @return
	 */
	public static int getCurrentMinutes() {
		Calendar calendar = new GregorianCalendar();
		return calendar.get(Calendar.MINUTE);
	}

	public static Date getFristDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		return calendar.getTime();
	}

	public static Date getLastDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		return calendar.getTime();
	}

	/**
	 * 返回N年后的时间
	 * 
	 * @param year
	 * @return
	 */
	public static Date getLastYEAR(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}

	/**
	 * 将秒转换为小时分秒等
	 * 
	 * @param sec
	 * @return
	 */
	public static String changeTimetohm(int sec) {
		String temp = "";
		if (sec < 60) {
			temp = "" + sec + ":";
		} else if (sec < 3600) {
			temp = "" + sec / 60 + ":" + sec % 60 + ":";
		} else {
			temp = "" + sec / 3600 + ":" + (sec % 3600) / 60 + ":" + sec % 60 + "";
		}
		return temp;
	}

	/**
	 * 将秒转换为小时分秒等
	 * 
	 * @param sec
	 * @return
	 */
	public static String changeTime(int sec) {
		String temp = "";
		if (sec < 60) {
			temp = "" + sec + "秒";
		} else if (sec < 3600) {
			temp = "" + sec / 60 + "分" + sec % 60 + "秒";
		} else {
			temp = "" + sec / 3600 + "小时" + (sec % 3600) / 60 + "分" + sec % 60 + "秒";
		}
		return temp;
	}

	/**
	 * 获取12小时制当前日期字符串
	 * 
	 * @return
	 */
	public static String getStrDate_12() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date currentTime = new Date();
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	/**
	 * 获取24小时制当前日期字符串
	 * 
	 * @return
	 */
	public static String getStrDate_24() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	/**
	 * 获取格式化当前时间、毫秒字符串
	 * 
	 * @return
	 */
	public static String getStrDateS() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
		Date currentTime = new Date();
		String strDate = formatter.format(currentTime);
		return strDate;
	}

	/**
	 * 转换日期为字符串格式
	 * <p>
	 * 
	 * @param Date
	 * @return
	 */
	public static String DateToStr(java.util.Date Date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = formatter.format(Date);
		return strDate;
	}

	/**
	 * 转换日期为格式化字符串
	 * 
	 * @param Date
	 * @param format
	 * @return
	 */
	public static String DateToFormatStr(java.util.Date Date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String strDate = formatter.format(Date);
		return strDate;
	}

	/**
	 * 获取当前日期 格式为 yyyy-MM-dd
	 * 
	 * @return strDate
	 */
	public static String getNowStrDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * 获取当间时间字符串 yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getLongDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * 获取当间时间字符串 yyyyMMddHHmmssSS
	 * 
	 * @return
	 */
	public static String getLongDateS() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSS");
		Date date = new Date();
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * 比较二个日期
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean Compare_Date(java.util.Date date1, java.util.Date date2) {
		return date1.equals(date2);
	}

	/**
	 * 将字符串类型的时间转化为Date型
	 * 
	 * @param strDate
	 * @param formatDate
	 * @return Date
	 * @throws ParseException
	 */
	public static Date str2Date(String strDate, String formatDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
		return sdf.parse(strDate);
	}

	/**
	 * 将字符串类型的时间转化为Date型，并将在此时间上进行增加或减少相应天
	 * 
	 * @param strDate
	 * @param formatDate
	 * @return Date
	 * @throws ParseException
	 */
	public static Date otherDate(String strDate, String formatDate, int num) throws ParseException {
		Calendar c = new GregorianCalendar();

		Date date = str2Date(strDate, formatDate);

		c.setTime(date);

		c.add(Calendar.DATE, num);

		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);

		return str2Date(sdf.format(c.getTime()), formatDate);
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd HHmmss
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到现在小时
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat
	 *            yyyyMMddhhmmss
	 * @return
	 */
	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
	 */
	public static String getTwoHour(String st1, String st2) {
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
			return "0";
		else {
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
			if ((y - u) > 0)
				return y - u + "";
			else
				return "0";
		}
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {

		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 返回美国时间格式 26 Apr 2006
	 * 
	 * @param str
	 * @return
	 */
	public static String getEDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(str, pos);
		String j = strtodate.toString();
		String[] k = j.split(" ");
		return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
		String str = dat.substring(0, 8);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "30";
		} else {
			if (isLeapYear(dat)) {
				str += "29";
			} else {
				str += "28";
			}
		}
		return str;
	}

	/**
	 * 判断二个时间是否在同一个周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
	 * 
	 * @param sdate
	 * @param num
	 * @return
	 */
	public static String getWeek(String sdate, String num) {
		// 再转换为时间
		Date dd = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(dd);
		if (num.equals("1")) // 返回星期一所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		else if (num.equals("2")) // 返回星期二所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		else if (num.equals("3")) // 返回星期三所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		else if (num.equals("4")) // 返回星期四所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		else if (num.equals("5")) // 返回星期五所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		else if (num.equals("6")) // 返回星期六所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		else if (num.equals("0")) // 返回星期日所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static String getWeekStr(String sdate) {
		String str = "";
		str = DateUtil.getWeek(sdate);
		if ("1".equals(str)) {
			str = "星期日";
		} else if ("2".equals(str)) {
			str = "星期一";
		} else if ("3".equals(str)) {
			str = "星期二";
		} else if ("4".equals(str)) {
			str = "星期三";
		} else if ("5".equals(str)) {
			str = "星期四";
		} else if ("6".equals(str)) {
			str = "星期五";
		} else if ("7".equals(str)) {
			str = "星期六";
		}
		return str;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
	 * 此函数返回该日历第一行星期日所在的日期
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getNowMonth(String sdate) {
		// 取该时间所在月的一号
		sdate = sdate.substring(0, 8) + "01";

		// 得到这个月的1号是星期几
		Date date = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int u = c.get(Calendar.DAY_OF_WEEK);
		String newday = DateUtil.getNextDay(sdate, (1 - u) + "");
		return newday;
	}

	/**
	 * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
	 * 
	 * @param k
	 *            表示是取几位随机数，可以自己定
	 */

	public static String getNo(int k) {

		return getUserDate("yyyyMMddhhmmss") + getRandom(k);
	}

	/**
	 * 返回一个随机数
	 * 
	 * @param i
	 * @return
	 */
	public static String getRandom(int i) {
		Random jjj = new Random();
		// int suiJiShu = jjj.nextInt(9);
		if (i == 0)
			return "";
		String jj = "";
		for (int k = 0; k < i; k++) {
			jj = jj + jjj.nextInt(9);
		}
		return jj;
	}

	/**
	 * 
	 * @param args
	 */
	public static boolean RightDate(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		;
		if (date == null)
			return false;
		if (date.length() > 10) {
			sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			sdf.parse(date);
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}


	public static String getDate22() {
		return getDateTime().substring(0, 10);
	}

	public static String getDateTime() {
		return getDateTime(((Calendar) (new GregorianCalendar())));
	}

	private static String getDateTime(Calendar calendar) {
		StringBuffer buf = new StringBuffer("");
		buf.append(calendar.get(1));
		buf.append(DAY_DELIMITER);
		buf.append(calendar.get(2) + 1 <= 9 ? (new StringBuilder()).append("0").append(calendar.get(2) + 1).toString()
				: (new StringBuilder()).append(calendar.get(2) + 1).append("").toString());
		buf.append(DAY_DELIMITER);
		buf.append(calendar.get(5) <= 9 ? (new StringBuilder()).append("0").append(calendar.get(5)).toString()
				: (new StringBuilder()).append(calendar.get(5)).append("").toString());
		buf.append(" ");
		buf.append(calendar.get(11) <= 9 ? (new StringBuilder()).append("0").append(calendar.get(11)).toString()
				: (new StringBuilder()).append(calendar.get(11)).append("").toString());
		buf.append(":");
		buf.append(calendar.get(12) <= 9 ? (new StringBuilder()).append("0").append(calendar.get(12)).toString()
				: (new StringBuilder()).append(calendar.get(12)).append("").toString());
		buf.append(":");
		buf.append(calendar.get(13) <= 9 ? (new StringBuilder()).append("0").append(calendar.get(13)).toString()
				: (new StringBuilder()).append(calendar.get(13)).append("").toString());
		return buf.toString();
	}

	public static long[] diff(String startTime, String endTime) {
		long startTimeMillis = getCalendar(startTime).getTimeInMillis();
		long endTimeMillis = getCalendar(endTime).getTimeInMillis();
		long diff = endTimeMillis - startTimeMillis;
		long diffPart[] = new long[4];
		diffPart[0] = diff / 0x5265c00L;
		diffPart[1] = (diff % 0x5265c00L) / 0x36ee80L;
		diffPart[2] = (diff % 0x36ee80L) / 60000L;
		diffPart[3] = (diff % 60000L) / 1000L;
		return diffPart;
	}

	public static String getPreDateTime(String datetime, int type, int step) {
		Calendar calendar = new GregorianCalendar(Integer.parseInt(datetime.substring(0, 4)),
				Integer.parseInt(datetime.substring(5, 7)) - 1, Integer.parseInt(datetime.substring(8, 10)),
				Integer.parseInt(datetime.substring(11, 13)), Integer.parseInt(datetime.substring(14, 16)),
				Integer.parseInt(datetime.substring(17, 19)));
		calendar.add(type, step);
		return getDateTime(calendar);
	}

	public static Calendar getCalendar(String datetime) {
		return new GregorianCalendar(Integer.parseInt(datetime.substring(0, 4)),
				Integer.parseInt(datetime.substring(5, 7)) - 1, Integer.parseInt(datetime.substring(8, 10)),
				Integer.parseInt(datetime.substring(11, 13)), Integer.parseInt(datetime.substring(14, 16)),
				Integer.parseInt(datetime.substring(17, 19)));
	}

	public static Calendar getCalendar() {
		return getCalendar(getDateTime());
	}

	public static String getPreDate(String date, int type, int step) {
		Calendar calendar = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
				Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)), 0, 0, 0);
		calendar.add(type, step);
		return getDateTime(calendar).substring(0, 10);
	}

	public static int getDateInt() {
		String date = getDate22();
		return Integer.parseInt((new StringBuilder()).append(date.substring(0, 4)).append(date.substring(5, 7))
				.append(date.substring(8, 10)).toString());
	}

	public static int getTimeInt() {
		String date = getDateTime();
		return Integer.parseInt((new StringBuilder()).append(date.substring(11, 13)).append(date.substring(14, 16))
				.append(date.substring(17, 19)).toString());
	}

	public static long getTimeStamp(String datetime) {
		return getCalendar(datetime).getTime().getTime();
	}

	public static String formatDate(String datetime, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(pattern);
		return simpleDateFormat.format(getCalendar(datetime).getTime());
	}

	public static int getdDatePart(String datetime, int part) {
		Calendar calendar = getCalendar(datetime);
		return calendar.get(part);
	}

	public static int getdDatePart(int part) {
		Calendar calendar = getCalendar(getDateTime());
		return calendar.get(part);
	}

	public static String getTimeInMillis(Date sDate, Date eDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sDate);
		long timethis = calendar.getTimeInMillis();
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(eDate);
		long timeend = calendar2.getTimeInMillis();
		long thedaymillis = timeend - timethis;
		return thedaymillis >= 1000L ? (new StringBuilder()).append(thedaymillis / 1000L).append("秒!").toString()
				: (new StringBuilder()).append(thedaymillis).append("毫秒!").toString();
	}

	public static String formatDateTime(String dTime) {
		String dateTime = "";
		if (dTime != null && !"".equals(dTime) && !dTime.startsWith("1900-01-01")) {
			Timestamp t = Timestamp.valueOf(dTime);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateTime = formatter.format(t);
		}
		return dateTime;
	}

	public static String formatDateTime(String dTime, String format) {
		if ("".equals(format.trim()))
			format = "yyyy-MM-dd";
		String dateTime = "";
		if (dTime != null && !"".equals(dTime) && !dTime.startsWith("1900-01-01")) {
			Timestamp t = Timestamp.valueOf(dTime);
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			dateTime = formatter.format(t);
		}
		return dateTime;
	}

	public static String formatDateStr(String dTime, int lastIndex) {
		if (dTime.equals("") || dTime.substring(0, 10).equals("1900-01-01"))
			dTime = "";
		else
			dTime = dTime.substring(0, lastIndex);
		return dTime;
	}

	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
			return 31;
		if (month == 4 || month == 6 || month == 9 || month == 11)
			return 30;
		if (month == 2)
			return (year % 4 != 0 || year % 100 == 0) && year % 400 != 0 ? 28 : 29;
		else
			return 0;
	}

	public static String[] getPeriodBeginEnd(String tperiod, String cyccode) {
		String period = tperiod;
		int cyc = Integer.parseInt(cyccode);
		String periodbelong[] = new String[2];
		String year = "";
		year = period.substring(0, 4);
		switch (cyc) {
		case 0: // '\0'
			periodbelong[0] = tperiod;
			periodbelong[1] = tperiod;
			break;

		case 1: // '\001'
			periodbelong[0] = (new StringBuilder()).append(period).append("01").toString();
			periodbelong[1] = (new StringBuilder()).append(period)
					.append(getLastDayOfMonth(Integer.parseInt(year), Integer.parseInt(period.substring(4))))
					.toString();
			break;

		case 2: // '\002'
			switch (Integer.parseInt(period.substring(5))) {
			case 1: // '\001'
				periodbelong[0] = (new StringBuilder()).append(year).append("0101").toString();
				periodbelong[1] = (new StringBuilder()).append(year).append("0331").toString();
				break;

			case 2: // '\002'
				periodbelong[0] = (new StringBuilder()).append(year).append("0401").toString();
				periodbelong[1] = (new StringBuilder()).append(year).append("0630").toString();
				break;

			case 3: // '\003'
				periodbelong[0] = (new StringBuilder()).append(year).append("0701").toString();
				periodbelong[1] = (new StringBuilder()).append(year).append("0930").toString();
				break;

			case 4: // '\004'
				periodbelong[0] = (new StringBuilder()).append(year).append("1001").toString();
				periodbelong[1] = (new StringBuilder()).append(year).append("1231").toString();
				break;
			}
			break;

		case 3: // '\003'
			switch (Integer.parseInt(period.substring(5, 6))) {
			case 1: // '\001'
				periodbelong[0] = (new StringBuilder()).append(year).append("0101").toString();
				periodbelong[1] = (new StringBuilder()).append(year).append("0630").toString();
				break;

			case 2: // '\002'
				periodbelong[0] = (new StringBuilder()).append(year).append("0701").toString();
				periodbelong[1] = (new StringBuilder()).append(year).append("1231").toString();
				break;
			}
			break;

		case 4: // '\004'
			periodbelong[0] = (new StringBuilder()).append(year).append("0101").toString();
			periodbelong[1] = (new StringBuilder()).append(year).append("1231").toString();
			break;

		default:
			periodbelong = null;
			break;
		}
		return periodbelong;
	}

	public static boolean isLegalOfSsqj(String ssqj, String cycCode) {
		boolean flag = false;
		int cyc = Integer.parseInt(cycCode);
		switch (cyc) {
		default:
			break;

		case 0: // '\0'
			if (ssqj.matches(
					"(\\d{3}[^0]|[^0]\\d{3})(0[^0]0[1-9]|0[^0]1[0-9]|0[^0]2[0-8]|1[0-2]0[1-9]|1[0-2]1[0-9]|1[0-2]2[0-8]|0[13-9]29|1[0-2]29|0[13-9]30|1[0-2]30|0[13578]31|1[02]31)|\\d{2}0[48]0229|\\d{2}[2468][048]0229|\\d{2}[13579][26]0229|0[48]000229|[13579][26]000229|[2468][048]000229"))
				flag = true;
			break;

		case 1: // '\001'
			if (ssqj.matches("2\\d{3}(0[1-9]|1[0-2])"))
				flag = true;
			break;

		case 3: // '\003'
			if (ssqj.matches("2\\d{3}H[1-2]"))
				flag = true;
			break;

		case 2: // '\002'
			if (ssqj.matches("2\\d{3}Q[1-4]"))
				flag = true;
			break;

		case 4: // '\004'
			if (ssqj.matches("2\\d{3}"))
				flag = true;
			break;
		}
		return flag;
	}

	public static String getCurSsqjs() {
		String curSsqjs = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = df.format(new Date());
		int year = Integer.parseInt(curDate.substring(0, 4));
		int month = Integer.parseInt(curDate.substring(5, 7));
		if (month == 1) {
			String curY = String.valueOf(year - 1);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("12','").append(curY).append("Q4','")
					.append(curY).append("'").toString();
		} else if (month == 2) {
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("01'").toString();
		} else if (month == 3) {
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("02'").toString();
		} else if (month == 4) {
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("03','").append(curY).append("Q1'")
					.toString();
		} else if (month == 5) {
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("04'").toString();
		} else if (month == 6) {
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("05'").toString();
		} else if (month == 7) {
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("06','").append(curY).append("Q2'")
					.toString();
		} else if (month == 8) {
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("07'").toString();
		} else if (month == 9) {
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("08'").toString();
		} else if (month == 10) {
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("09','").append(curY).append("Q3'")
					.toString();
		} else if (month == 11) {
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("10'").toString();
		} else {
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("11'").toString();
		}
		return curSsqjs;
	}

	public static String getPreSSQJ(String curSSQJ) {
		SimpleDateFormat fmt = null;
		if (curSSQJ.length() == 4)
			fmt = new SimpleDateFormat("yyyy");
		else if (curSSQJ.length() == 6)
			fmt = new SimpleDateFormat("yyyyMM");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(fmt.parse(curSSQJ));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (curSSQJ.length() == 4)
			cal.add(1, -1);
		else if (curSSQJ.length() == 6)
			cal.add(2, -1);
		String preSSQJ = fmt.format(cal.getTime());
		return preSSQJ;
	}

	public static boolean isDate(String strDate, String pattern) {
		boolean back = true;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			sdf.parse(strDate);
		} catch (ParseException e) {
			back = false;
		}
		return back;
	}

	public static void main333(String args[]) {
		/*
		 * Date t = DateUtil.parseStringToDate("2012-12-20 10:30:12",
		 * DateUtil.DATE_FORMAT_FULL);
		 * System.out.println(t);DateUtil.getAfterDateTime(30*24)
		 * System.out.println(parseDateToString(t));
		 */
		// System.out.println(DateUtil.getAfterDateTime(2,"HH:mm:ss"));
		/*
		 * System.out.println(DateUtil.isBefore_int("2014-12-20", "2014-12-20",
		 * DATE_FORMAT_SHORT)/86400000);
		 *//*
			 * Date d = DateUtil.parseStringToDate("2014-12-20 10:30:12",
			 * DATE_FORMAT_FULL); Date d1 = DateUtil.parseStringToDate(
			 * "2014-12-10 10:30:12", DATE_FORMAT_FULL); Long n = d.getTime() -
			 * d1.getTime();
			 * System.out.println(DateUtil.changeTime(n.intValue()/1000));
			 */
		// System.out.println(DateUtil.getAfterDateTime(30*24).toLocaleString());
		System.out.println((long) 9 / 10);
		boolean dateArr = isLegalOfSsqj("2008Q1", "2");
		System.out.println(dateArr);
	}

	// ****************************************************************************************************
	// ****************************************************************************************************
	/** 定义常量 **/
	public static final String DATE_JFP_STR = "yyyyMM";
	public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_SMALL_STR = "yyyy-MM-dd";
	public static final String DATE_KEY_STR = "yyMMddHHmmss";

	/**
	 * 使用预设格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, DATE_FULL_STR);
	}

	/**
	 * 使用用户格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 两个时间比较
	 * 
	 * @param date
	 * @return
	 */
	public static int compareDateWithNow(Date date1) {
		Date date2 = new Date();
		int rnum = date1.compareTo(date2);
		return rnum;
	}

	/**
	 * 两个时间比较(时间戳比较)
	 * 
	 * @param date
	 * @return
	 */
	public static int compareDateWithNow(long date1) {
		long date2 = dateToUnixTimestamp();
		if (date1 > date2) {
			return 1;
		} else if (date1 < date2) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static String getNowTime() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
		return df.format(new Date());
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static String getNowTime(String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(new Date());
	}

	/**
	 * 获取系统当前计费期
	 * 
	 * @return
	 */
	public static String getJFPTime() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);
		return df.format(new Date());
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param String
	 *            date 需要转换的日期 yyyy-MM-dd HH:mm:ss
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp(String date) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param String
	 *            date 需要转换的日期 yyyy-MM-dd
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp(String date, String dateFormat) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * 将当前日期转换成Unix时间戳
	 * 
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp() {
		long timestamp = new Date().getTime();
		return timestamp;
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @param long
	 *            timestamp 时间戳
	 * @return String 日期字符串
	 */
	public static String unixTimestampToDate(long timestamp) {
		SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
		sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sd.format(new Date(timestamp));
	}
	// ****************************************************************************************************
	// ****************************************************************************************************

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @return 日期字符串
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @param pattern
	 *            字符串格式
	 * @return 日期字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return "null";
		}
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return new java.text.SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @return 日期类型
	 */
	public static Date format(String date) {
		return format(date, null);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @param pattern
	 *            格式
	 * @return 日期类型
	 */
	public static Date format(String date, String pattern) {
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (date == null || date.equals("") || date.equals("null")) {
			return new Date();
		}
		Date d = null;
		try {
			d = new java.text.SimpleDateFormat(pattern).parse(date);
		} catch (ParseException pe) {
		}
		return d;
	}

	/**********************************************************************************************/
	/**********************************************************************************************/

	/**
	 * Date Style
	 */
	public static final String DATESTYLE = "yyyyMMddHHmmss";

	/**
	 * Date Style for Extention
	 */
	public static final String DATESTYLE_EX = "yyyy-MM-dd_HH-mm-ss";

	/**
	 * Date Style for Extention
	 */
	public static final String DATESTYLE_ = "yyyy-MM-dd";

	/**
	 * Date Style for Year & Month
	 */
	public static final String DATESTYLE_YEAR_MONTH = "yyyyMM";

	/**
	 * Date Style for Short
	 */
	public static final String DATESTYLE_SHORT = "yyyyMMdd";

	/**
	 * Date Style for Extention
	 */
	public static final String DATESTYLE_SHORT_EX = "yyyy/MM/dd";

	/**
	 * Date Style for Year & Month Extention
	 */
	public static final String DATESTYLE_YEAR_MONTH_EX = "yyyy/MM";

	/**
	 * Date Style for detail
	 */
	public static final String DATESTYLE_DETAIL = "yyyyMMddHHmmssSSS";

	// static long now = System.currentTimeMillis();
	// public static Date CurrTime = new Date(now);

	/**
	 * 日期转化为字符串
	 * 
	 * @param date
	 *            时间
	 * @return yyyy-MM-dd HH:mm:ss 格式化的时间字符串
	 */
	public static String dateToString(Date date) {
		if (date == null)
			return "";
		return FormatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 日期转化为字符串
	 * 
	 * @param date
	 *            时间
	 * @return yyyy-MM-dd 格式化的时间字符串
	 */
	public static String dateToStringShort(Date date) {
		if (date == null)
			return "";
		return FormatDate(date, "yyyy-MM-dd");
	}

	/**
	 * 计算两个日期差（毫秒）
	 * 
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return 相差毫秒数
	 */
	public static long diffTwoDate(Date date1, Date date2) {
		long l1 = date1.getTime();
		long l2 = date2.getTime();
		return (l1 - l2);
	}

	/**
	 * 计算两个日期差（天）
	 * 
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return 相差天数
	 */
	public static int diffTwoDateDay(Date date1, Date date2) {
		long l1 = date1.getTime();
		long l2 = date2.getTime();
		int diff = Integer.parseInt("" + (l1 - l2) / 3600 / 24 / 1000);
		return diff;
	}

	/**
	 * 对日期进行格式化
	 * 
	 * @param date
	 *            日期
	 * @param sf
	 *            日期格式
	 * @return 字符串
	 */
	public static String FormatDate(Date date, String sf) {
		if (date == null)
			return "";
		SimpleDateFormat dateformat = new SimpleDateFormat(sf);
		return dateformat.format(date);
	}

	/**
	 * 取得当前系统日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getCurrDate() {
		Date date_time = new Date();
		return FormatDate(date_time, "yyyy-MM-dd");
	}

	// 取系统时间时一定要用这个方法，否则日期可能不动
	public static Date getCurrDateTime() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 取得当前系统时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrTime() {
		Date date_time = new Date();
		return FormatDate(date_time, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 10位时间转为8时间
	 * 
	 * @param str
	 * @return
	 */
	public static String getDate10to8(String str) {
		String y = str.substring(0, 4);
		String m = str.substring(5, 7);
		String d = str.substring(8, 10);
		return y + m + d;
	}

	/**
	 * 8位时间转为10时间
	 * 
	 * @param str
	 * @return
	 */
	public static String getDate8to10(String str) {
		String y = str.substring(0, 4);
		String m = str.substring(4, 6);
		String d = str.substring(6, 8);
		return y + "-" + m + "-" + d;
	}

	/**
	 * 根据年、月取得月末的日期
	 * 
	 * @param year
	 *            年
	 * @parm month 月
	 * @return time 返回日期格式"yyyy-mm-dd"
	 */
	public static String getTime(String year, String month) {
		String time = "";
		int len = 31;
		int iYear = Integer.parseInt(year);
		int iMonth = Integer.parseInt(month);
		if (iMonth == 4 || iMonth == 6 || iMonth == 9 || iMonth == 11)
			len = 30;
		if (iMonth == 2) {
			len = 28;
			if ((iYear % 4 == 0 && iYear % 100 == 0 && iYear % 400 == 0) || (iYear % 4 == 0 && iYear % 100 != 0)) {
				len = 29;
			}
		}
		time = year + "-" + month + "-" + String.valueOf(len);
		return time;
	}

	/**
	 * 字符串转换为日期
	 * 
	 * @param dateString
	 *            yyyy-MM-dd HH:mm:ss
	 * @return 日期
	 */
	public static Date stringToDate(String dateString) {
		if (dateString == null || dateString.trim().length() == 0)
			return null;
		String datestr = dateString.trim();

		String sf = "yyyy-MM-dd HH:mm:ss";
		Date dt = stringToDate(datestr, sf);
		if (dt == null)
			dt = stringToDate(datestr, "yyyy-MM-dd");
		if (dt == null)
			dt = stringToDate(datestr, "yyyyMMdd");
		return dt;
	}

	/**
	 * 字符串转换为日期
	 * 
	 * @param dateString
	 *            yyyy-MM-dd
	 * @return 日期
	 */
	public static Date stringToDateShort(String dateString) {
		String sf = "yyyy-MM-dd";
		Date dt = stringToDate(dateString, sf);
		return dt;
	}

	/**
	 * 取得日以上粒度起始时间
	 * 
	 * @param granularity
	 *            粒度
	 * @param statisticDate
	 *            结束时间
	 * @return 起始时间
	 */
	public String getBeginDate(String granularity, String statisticDate) {
		String beginDate = "";
		Date date = this.stringToDateShort(statisticDate);
		Date beginDateTemp = null;
		if (granularity.equals("1")) {// 日
			beginDateTemp = date;
		}
		if (granularity.equals("2")) {// 周
			beginDateTemp = this.getWeekBegin(date);
		}
		if (granularity.equals("3")) {// 旬
			beginDateTemp = this.getPeriodBegin(date);
		} else if (granularity.equals("4")) {// 月
			beginDateTemp = this.getMonthBegin(date);
		} else if (granularity.equals("5")) {// 季
			beginDateTemp = this.getSeasonBegin(date);
		} else if (granularity.equals("6")) {// 半年
			beginDateTemp = this.getHalfYearBegin(date);
		} else if (granularity.equals("7")) {// 年
			beginDateTemp = this.getYearBegin(date);
		}
		beginDate = this.dateToStringShort(beginDateTemp);
		return beginDate;
	}

	/**
	 *
	 * @param currentTime
	 *            计算日期
	 * @param type
	 *            偏移的类别
	 * @param iQuantity
	 *            偏移数量
	 * @return 偏移后的时间串
	 */
	public String getDateChangeALL(String currentTime, String type, int iQuantity) {
		Date curr = null;
		String newtype = "";
		if (currentTime.length() == 10) {
			curr = this.stringToDateShort(currentTime);
		}
		if (currentTime.length() > 10) {
			curr = this.stringToDate(currentTime);
		}

		// 日
		if (type.equals("1")) {
			iQuantity = iQuantity;
			newtype = "d";
		}
		// 周，按照7天计算
		else if (type.equals("2")) {
			iQuantity = iQuantity * 7;
			newtype = "d";
		}
		// 旬，按照10天计算
		else if (type.equals("3")) {
			iQuantity = iQuantity * 10;
			newtype = "d";
		}
		// 月
		else if (type.equals("4")) {
			iQuantity = iQuantity;
			newtype = "m";
		}
		// 旬，按照3个月计算
		else if (type.equals("5")) {
			iQuantity = iQuantity * 3;
			newtype = "m";
		}
		// 半年，按照六个月计算
		else if (type.equals("6")) {
			iQuantity = iQuantity * 6;
			newtype = "m";
		}
		// 年
		else if (type.equals("7")) {
			newtype = "y";
		} else {
			iQuantity = iQuantity;
			newtype = "d";
		}

		Date change = this.getDateChangeTime(curr, newtype, iQuantity);

		// if(!type.equals("d")){
		// change = this.getMonthEnd(change);
		// }

		return this.dateToStringShort(change);
	}

	/**
	 *
	 * @param currentTime
	 *            计算的日期
	 * @param type
	 *            偏移的类别
	 * @param iQuantity
	 *            偏移数量
	 * @return 偏移后的时间
	 */
	public Date getDateChangeTime(Date currentTime, String type, int iQuantity) {
		int year = Integer.parseInt(this.FormatDate(currentTime, "yyyy"));
		int month = Integer.parseInt(this.FormatDate(currentTime, "MM"));
		// 月份修正
		month = month - 1;
		int day = Integer.parseInt(this.FormatDate(currentTime, "dd"));
		int hour = Integer.parseInt(this.FormatDate(currentTime, "HH"));
		int mi = Integer.parseInt(this.FormatDate(currentTime, "mm"));
		int ss = Integer.parseInt(this.FormatDate(currentTime, "ss"));
		GregorianCalendar gc = new GregorianCalendar(year, month, day, hour, mi, ss);
		// 月份修正
		// gc.add(GregorianCalendar.MONTH, -1);
		if (type.equalsIgnoreCase("y")) {
			gc.add(GregorianCalendar.YEAR, iQuantity);
		} else if (type.equalsIgnoreCase("m")) {
			gc.add(GregorianCalendar.MONTH, iQuantity);
		} else if (type.equalsIgnoreCase("d")) {
			gc.add(GregorianCalendar.DATE, iQuantity);
		} else if (type.equalsIgnoreCase("h")) {
			gc.add(GregorianCalendar.HOUR, iQuantity);
		} else if (type.equalsIgnoreCase("mi")) {
			gc.add(GregorianCalendar.MINUTE, iQuantity);
		} else if (type.equalsIgnoreCase("s")) {
			gc.add(GregorianCalendar.SECOND, iQuantity);
		}
		return gc.getTime();
	}

	/**
	 *
	 * @param currentTime
	 *            计算的日期
	 * @param type
	 *            偏移的类别
	 * @param iQuantity
	 *            偏移数量
	 * @return 偏移后的时间串
	 */
	public String getDateChangeTime(String currentTime, String type, int iQuantity) {
		Date curr = this.stringToDate(currentTime);
		curr = this.getDateChangeTime(curr, type, iQuantity);
		return this.dateToString(curr);
	}

	/**
	 * 取得日以上粒度起始时间
	 * 
	 * @param granularity
	 *            粒度
	 * @param statisticDate
	 *            结束时间
	 * @return 起始时间
	 */
	public String getEndDate(String granularity, String statisticDate) {
		String beginDate = "";
		Date date = this.stringToDateShort(statisticDate);
		Date beginDateTemp = null;

		if (granularity.equals("1")) {// 日
			beginDateTemp = date;
		}
		if (granularity.equals("2")) {// 周
			beginDateTemp = this.getWeekEnd(date);
		}
		if (granularity.equals("3")) {// 旬
			beginDateTemp = this.getPeriodEnd(date);
		} else if (granularity.equals("4")) {// 月
			beginDateTemp = this.getMonthEnd(date);
		} else if (granularity.equals("5")) {// 季
			beginDateTemp = this.getSeasonEnd(date);
		} else if (granularity.equals("6")) {// 半年
			beginDateTemp = this.getHalfYearEnd(date);
		} else if (granularity.equals("7")) {// 年
			beginDateTemp = this.getYearEnd(date);
		}

		beginDate = this.dateToStringShort(beginDateTemp);
		return beginDate;
	}

	/**
	 * 取半年初
	 * 
	 * @param date
	 * @return
	 */
	public Date getHalfYearBegin(Date date) {
		int year = Integer.parseInt(this.FormatDate(date, "yyyy"));
		int month = Integer.parseInt(this.FormatDate(date, "MM"));
		String newDateStr = this.FormatDate(date, "yyyy") + "-";
		if (month <= 6) {
			newDateStr += "01-01";
		} else {
			newDateStr += "07-01";
		}
		return this.stringToDateShort(newDateStr);
	}

	/**
	 * 取半年末
	 * 
	 * @param date
	 * @return
	 */
	public Date getHalfYearEnd(Date date) {
		int year = Integer.parseInt(this.FormatDate(date, "yyyy"));
		int month = Integer.parseInt(this.FormatDate(date, "MM"));
		String newDateStr = this.FormatDate(date, "yyyy") + "-";
		if (month <= 6) {
			newDateStr += "06-30";
		} else {
			newDateStr += "12-31";
		}
		return this.stringToDateShort(newDateStr);
	}

	/**
	 * 取月初
	 * 
	 * @param date
	 * @return
	 */
	public Date getMonthBegin(Date date) {
		String newDateStr = this.FormatDate(date, "yyyy-MM") + "-01";
		// FormatDate(date, "yyyy-MM-dd");
		return this.stringToDateShort(newDateStr);
	}

	/**
	 * 取月末时间
	 * 
	 * @param date
	 *            日期
	 * @return date
	 */
	public Date getMonthEnd(Date date) {
		int year = Integer.parseInt(this.FormatDate(date, "yyyy"));
		int month = Integer.parseInt(this.FormatDate(date, "MM"));
		int day = Integer.parseInt(this.FormatDate(date, "dd"));

		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day, 0, 0, 0);
		int monthLength = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
		String newDateStr = this.FormatDate(date, "yyyy") + "-" + this.FormatDate(date, "MM") + "-";
		if (monthLength < 10)
			newDateStr += "0" + monthLength;
		else
			newDateStr += "" + monthLength;
		return this.stringToDateShort(newDateStr);
	}

	/**
	 * 取旬初
	 * 
	 * @param date
	 * @return
	 */
	public Date getPeriodBegin(Date date) {
		int days = Integer.parseInt(this.FormatDate(date, "dd"));
		String newDateStr = this.FormatDate(date, "yyyy-MM") + "-";
		if (days <= 10) {
			newDateStr += "01";
		} else if (days <= 20) {
			newDateStr += "11";
		} else {
			newDateStr += "21";
		}
		return this.stringToDateShort(newDateStr);
	}

	/**
	 * 取旬末
	 * 
	 * @param date
	 * @return
	 */
	public Date getPeriodEnd(Date date) {
		int days = Integer.parseInt(this.FormatDate(date, "dd"));
		String newDateStr = this.FormatDate(date, "yyyy-MM") + "-";
		if (days <= 10) {
			newDateStr += "10";
		} else if (days <= 20) {
			newDateStr += "20";
		} else {
			newDateStr = this.FormatDate(this.getMonthEnd(date), "yyyy-MM-dd");
		}
		return this.stringToDateShort(newDateStr);
	}

	/**
	 * 取季初
	 * 
	 * @param date
	 * @return
	 */
	public Date getSeasonBegin(Date date) {
		int year = Integer.parseInt(this.FormatDate(date, "yyyy"));
		int month = Integer.parseInt(this.FormatDate(date, "MM"));
		String newDateStr = this.FormatDate(date, "yyyy") + "-";
		if (month >= 1 && month <= 3) {
			newDateStr += "01-01";
		} else if (month >= 4 && month <= 6) {
			newDateStr += "04-01";
		} else if (month >= 7 && month <= 9) {
			newDateStr += "07-01";
		} else if (month >= 10 && month <= 12) {
			newDateStr += "10-01";
		}
		return this.stringToDateShort(newDateStr);
	}

	/**
	 * 取季度末时间
	 * 
	 * @param date
	 *            日期
	 * @return date
	 */
	public Date getSeasonEnd(Date date) {
		int year = Integer.parseInt(this.FormatDate(date, "yyyy"));
		int month = Integer.parseInt(this.FormatDate(date, "MM"));
		String newDateStr = this.FormatDate(date, "yyyy") + "-";
		if (month >= 1 && month <= 3) {
			newDateStr += "03-31";
		} else if (month >= 4 && month <= 6) {
			newDateStr += "06-30";
		} else if (month >= 7 && month <= 9) {
			newDateStr += "09-30";
		} else if (month >= 10 && month <= 12) {
			newDateStr += "12-31";
		}
		return this.stringToDateShort(newDateStr);
	}

	/**
	 * 取周初
	 * 
	 * @param date
	 * @return
	 */
	public Date getWeekBegin(Date date) {

		int year = Integer.parseInt(this.FormatDate(date, "yyyy"));
		int month = Integer.parseInt(this.FormatDate(date, "MM"));
		// 月份修正
		month = month - 1;
		int day = Integer.parseInt(this.FormatDate(date, "dd"));

		GregorianCalendar gc = new GregorianCalendar(year, month, day);

		int week = GregorianCalendar.DAY_OF_WEEK - 1;

		if (week == 0) {
			week = 7;
		}

		gc.add(GregorianCalendar.DATE, 0 - week + 1);

		return gc.getTime();
	}

	/**
	 * 取周末
	 * 
	 * @param date
	 * @return
	 */
	public Date getWeekEnd(Date date) {

		int year = Integer.parseInt(this.FormatDate(date, "yyyy"));
		int month = Integer.parseInt(this.FormatDate(date, "MM"));
		// 月份修正
		month = month - 1;
		int day = Integer.parseInt(this.FormatDate(date, "dd"));

		GregorianCalendar gc = new GregorianCalendar(year, month, day);

		int week = GregorianCalendar.DAY_OF_WEEK - 1;

		if (week == 0) {
			week = 7;
		}
		gc.add(GregorianCalendar.DATE, 7 - week);

		return gc.getTime();
	}

	/**
	 * 取得年初
	 * 
	 * @param date
	 * @return
	 */
	public Date getYearBegin(Date date) {
		String newDateStr = this.FormatDate(date, "yyyy") + "-01-01";
		return this.stringToDateShort(newDateStr);
	}

	/**
	 * 是否为年末
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	public Date getYearEnd(Date date) {
		String newDateStr = this.FormatDate(date, "yyyy") + "-12-31";
		return this.stringToDateShort(newDateStr);
	}

	/**
	 * 日期加N天
	 * 
	 * @param Sring
	 *            时间
	 * @return 加后的日期
	 */
	public static String addDay(String s, int n) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(s));
			cd.add(Calendar.DATE, n);// 增加一天
			// cd.add(Calendar.MONTH, n);//增加一个月

			return sdf.format(cd.getTime());

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 日期加N天
	 * 
	 * @param Sring
	 *            时间
	 * @return 加后的日期
	 */
	public static String delDay(String s, int n) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(s));
			cd.add(Calendar.DATE, -n);// 增加一天
			// cd.add(Calendar.MONTH, n);//增加一个月

			return sdf.format(cd.getTime());

		} catch (Exception e) {
			return null;
		}
	}

	/**********************************************************************************************/
	/**********************************************************************************************/

	// 返回时间字符串(yyyy-MM-dd)
	public static String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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

	/**********************************************************************************************/
	/**********************************************************************************************/

	public DateUtil() {
	}

	public static int openDay = 5;
	private String iDate = "";
	private int iYear;
	private int iMonth;
	private int iDay;

	// iDateTime = 2002-01-01 23:23:23
	public void setDate(String iDateTime) {
		this.iDate = iDateTime.substring(0, 10);
	}

	public String getDate() {
		return this.iDate;
	}

	public int getYear() {
		iYear = Integer.parseInt(iDate.substring(0, 4));
		return iYear;
	}

	public int getMonth() {
		iMonth = Integer.parseInt(iDate.substring(5, 7));
		return iMonth;
	}

	public int getDay() {
		iDay = Integer.parseInt(iDate.substring(8, 10));
		return iDay;
	}

	public static String subDate(String date) {
		return date.substring(0, 10);
	}

	/**
	 * �����Ƿ��Ǽ���ĩ
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isSeason(String date) {
		int getMonth = Integer.parseInt(date.substring(5, 7));
		boolean sign = false;
		if (getMonth == 3)
			sign = true;
		if (getMonth == 6)
			sign = true;
		if (getMonth == 9)
			sign = true;
		if (getMonth == 12)
			sign = true;
		return sign;
	}

	/**
	 * ��������ڿ�ʼ������ʱ��
	 * 
	 * @param afterDay
	 * @return
	 */
	public static String getDateFromNow(int afterDay) {
		GregorianCalendar calendar = new GregorianCalendar();
		Date date = calendar.getTime();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + afterDay);
		date = calendar.getTime();

		return df.format(date);
	}

	/**
	 * ���ʽ
	 * 
	 * @param afterDay
	 * @param format_string
	 * @return
	 */
	public static String getDateFromNow(int afterDay, String format_string) {
		Calendar calendar = Calendar.getInstance();
		Date date = null;

		DateFormat df = new SimpleDateFormat(format_string);

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + afterDay);
		date = calendar.getTime();

		return df.format(date);
	}

	/**
	 * �õ���ǰʱ�䣬�����ļ���û�������ַ�ʹ��yyyyMMddHHmmss��ʽ
	 * 
	 * @param afterDay
	 * @return by tim
	 */
	public static String getNowForFileName(int afterDay) {
		GregorianCalendar calendar = new GregorianCalendar();
		// Date date = calendar.getTime();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + afterDay);
		Date date = calendar.getTime();

		return df.format(date);
	}

	// ==============================================================================
	// �Ƚ����ڣ�������-N������ڶԱ� -1 0 1
	// ==============================================================================
	public int getDateCompare(String limitDate, int afterDay) {
		GregorianCalendar calendar = new GregorianCalendar();
		Date date = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + afterDay);
		date = calendar.getTime();// date������������������ȵ�����

		iDate = limitDate;
		calendar.set(getYear(), getMonth() - 1, getDay());
		Date dateLimit = calendar.getTime();
		return dateLimit.compareTo(date);
	}

	// ==============================================================================
	// �Ƚ����ڣ������ڵ����ڶԱ�
	// ==============================================================================
	public int getDateCompare(String limitDate) {
		iDate = limitDate;
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(getYear(), getMonth() - 1, getDay());
		Date date = calendar.getTime();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = new Date();
		return date.compareTo(nowDate);
	}

	// ==============================================================================
	// �Ƚ����ڣ������ڵ����ڶԱ� �õ�����
	// ==============================================================================
	public long getLongCompare(String limitDate) {

		iDate = limitDate;
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(getYear(), getMonth() - 1, getDay());
		Date date = calendar.getTime();
		long datePP = date.getTime();
		Date nowDate = new Date();
		long dateNow = nowDate.getTime();
		return ((dateNow - datePP) / (24 * 60 * 60 * 1000));

	}

	// ==============================================================================
	// �Ƚ����ڣ������ڵ����ڶԱ� �õ���Ϣ
	// ==============================================================================
	public String getStringCompare(String limitDate, int openDay) {
		iDate = limitDate;
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(getYear(), getMonth() - 1, getDay());
		Date date = calendar.getTime();
		long datePP = date.getTime();
		Date nowDate = new Date();
		long dateNow = nowDate.getTime();
		long overDay = ((dateNow - datePP) / (24 * 60 * 60 * 1000));
		String info = "";
		return info;

	}

	// ==============================================================================
	// �Ƚ����ڣ������ڵ����ڶԱ� �õ���Ϣ
	// ==============================================================================
	public String getPicCompare(String limitDate, int openDay) {

		iDate = limitDate;
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(getYear(), getMonth() - 1, getDay());
		Date date = calendar.getTime();
		long datePP = date.getTime();
		Date nowDate = new Date();
		long dateNow = nowDate.getTime();
		long overDay = ((dateNow - datePP) / (24 * 60 * 60 * 1000));
		String info = "";
		if (overDay > 0) {
			info = "plaint1.gif";
		}
		if (overDay == 0) {
			info = "plaint2.gif";
		}
		if (overDay < 0 && overDay >= -openDay) {
			info = "plaint2.gif";
		}
		if (overDay < -openDay) {
			info = "plaint3.gif";
		}
		if (overDay < -150) {
			info = "plaint3.gif";
		}
		return info;

	}

	/**
	 * method diffdate �����������ڼ����������
	 * 
	 * @param beforDate
	 *            ��ʽ:2005-06-20
	 * @param afterDate
	 *            ��ʽ:2005-06-21
	 * @return
	 */
	public static int diffDate(String beforeDate, String afterDate) {
		String[] tt = beforeDate.split("-");
		Date firstDate = new Date(Integer.parseInt(tt[0]), Integer.parseInt(tt[1]) - 1, Integer.parseInt(tt[2]));

		tt = afterDate.split("-");
		Date nextDate = new Date(Integer.parseInt(tt[0]), Integer.parseInt(tt[1]) - 1, Integer.parseInt(tt[2]));
		return (int) (nextDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * ��ȡ��������ڵ��ַ�
	 * 
	 * @return
	 */
	public static String getToday() {
		Calendar cld = Calendar.getInstance();
		java.util.Date date = new Date();
		cld.setTime(date);
		int intMon = cld.get(Calendar.MONTH) + 1;
		int intDay = cld.get(Calendar.DAY_OF_MONTH);
		String mons = String.valueOf(intMon);
		String days = String.valueOf(intDay);
		if (intMon < 10)
			mons = "0" + String.valueOf(intMon);
		if (intDay < 10)
			days = "0" + String.valueOf(intDay);
		return String.valueOf(cld.get(Calendar.YEAR)) + "-" + mons + "-" + days;
	}

	/**
	 * ��ȡ��ǰ�·�
	 * 
	 * @return �����ַ� ��ʽ����λ��
	 */
	public static String getCurrentMonth() {
		String strmonth = null;
		Calendar cld = Calendar.getInstance();
		java.util.Date date = new Date();
		cld.setTime(date);
		int intMon = cld.get(Calendar.MONTH) + 1;
		if (intMon < 10)
			strmonth = "0" + String.valueOf(intMon);
		else
			strmonth = String.valueOf(intMon);
		date = null;
		return strmonth;
	}

	// public static String getCurrMonth()
	// {
	// Calendar cld=Calendar.getInstance();
	// java.util.Date date=new Date();
	// cld.setTime(date);
	// int intMon=cld.get(Calendar.MONTH)+1;
	//
	// return String.valueOf(intMon).toString();
	// }

	/**
	 * ��ȡ��������ڵ��ַ�
	 */
	public static String getYestoday() {
		Calendar cld = Calendar.getInstance();
		java.util.Date date = new Date();
		cld.setTime(date);
		cld.add(Calendar.DATE, -1);
		int intMon = cld.get(Calendar.MONTH) + 1;
		int intDay = cld.get(Calendar.DAY_OF_MONTH);
		String mons = String.valueOf(intMon);
		String days = String.valueOf(intDay);
		if (intMon < 10)
			mons = "0" + String.valueOf(intMon);
		if (intDay < 10)
			days = "0" + String.valueOf(intDay);
		return String.valueOf(cld.get(Calendar.YEAR)) + "-" + mons + "-" + days;
	}

	/**
	 * �˺�����������Ա���Ĺ�����������ʹ���ں���ְ�ڸ��·ݵĹ�����
	 * ���루��ְ���ڣ�-1���ɵø��¹������� ʱ����2002-12-14Ϊ׼
	 * ���루��˾ʱ�䣬1���ɵĸ��¹�������
	 */
	public static int getWorkDay(String date, int sign) {
		int month = 0;
		int week = 0;
		int workDay = 0;
		Calendar rightNow = Calendar.getInstance();

		DateUtil dateOver = new DateUtil();
		dateOver.setDate(date);

		rightNow.set(rightNow.YEAR, dateOver.getYear());
		rightNow.set(rightNow.MONTH, dateOver.getMonth() - 1);
		rightNow.set(rightNow.DATE, dateOver.getDay());

		month = rightNow.get(rightNow.MONTH);

		while (rightNow.get(rightNow.MONTH) == month) {
			week = rightNow.get(Calendar.DAY_OF_WEEK);
			if (week == 1 || week == 7) {
			} else {
				workDay++;
				System.out.println(rightNow.get(rightNow.DATE));
			}
			rightNow.add(rightNow.DATE, sign);
		}
		return workDay;
	}

	public static void main(String args[]) {
		System.out.println(DateUtil.isSeason("2002-03-02"));
		// String cc ="100.123.342";
		// System.out.println(cc.indexOf(".",3));
		//
		// StringTokenizer st=new StringTokenizer(cc,".");
		//
		// if (st.countTokens()!=2) {
		//
		// String state = st.nextToken();
		// String event = st.nextToken();
		// System.out.println(""+event);
		String strDate = DateUtil.getDateFromNow(0, "yyyy-MM-dd HH:mm:ss");
		System.out.println("date:" + strDate);
		System.out.println("15:" + strDate.substring(0, 16));

		Date firstDate = new Date(2006, 11, 14, 18, 3, 0);
		Date nextDate = new Date(2006, 11, 15, 18, 2, 0);
		System.out.println("date's num: " + (int) (nextDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
		// }
		// System.out.println(DateUtil.getWorkDay("2002-11-14",-1));
	}

	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/
	/**********************************************************************************************/

}
