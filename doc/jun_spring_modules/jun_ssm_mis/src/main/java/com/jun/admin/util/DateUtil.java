package com.jun.admin.util;

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

public class DateUtil {

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
	 * @param strDate  yyyy-MM-dd
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
	 *  获得N个月后的日期
	 *  
	 *  theDate 日期
	 *  
	 *  int month 月数
	 *  
	 *  format 格式
	 * 
	 */
	public static String afterNMonthDate(String theDate, int month ,String format){
		
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
	
	

	public DateUtil()
	{
	}

	public static String getDate()
	{
		return getDateTime().substring(0, 10);
	}

 

	public static String getDateTime()
	{
		return getDateTime(((Calendar) (new GregorianCalendar())));
	}

	private static String getDateTime(Calendar calendar)
	{
		StringBuffer buf = new StringBuffer("");
		buf.append(calendar.get(1));
		buf.append(DAY_DELIMITER);
		buf.append(calendar.get(2) + 1 <= 9 ? (new StringBuilder()).append("0").append(calendar.get(2) + 1).toString() : (new StringBuilder()).append(calendar.get(2) + 1).append("").toString());
		buf.append(DAY_DELIMITER);
		buf.append(calendar.get(5) <= 9 ? (new StringBuilder()).append("0").append(calendar.get(5)).toString() : (new StringBuilder()).append(calendar.get(5)).append("").toString());
		buf.append(" ");
		buf.append(calendar.get(11) <= 9 ? (new StringBuilder()).append("0").append(calendar.get(11)).toString() : (new StringBuilder()).append(calendar.get(11)).append("").toString());
		buf.append(":");
		buf.append(calendar.get(12) <= 9 ? (new StringBuilder()).append("0").append(calendar.get(12)).toString() : (new StringBuilder()).append(calendar.get(12)).append("").toString());
		buf.append(":");
		buf.append(calendar.get(13) <= 9 ? (new StringBuilder()).append("0").append(calendar.get(13)).toString() : (new StringBuilder()).append(calendar.get(13)).append("").toString());
		return buf.toString();
	}

	public static long[] diff(String startTime, String endTime)
	{
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

	public static String getPreDateTime(String datetime, int type, int step)
	{
		Calendar calendar = new GregorianCalendar(Integer.parseInt(datetime.substring(0, 4)), Integer.parseInt(datetime.substring(5, 7)) - 1, Integer.parseInt(datetime.substring(8, 10)), Integer.parseInt(datetime.substring(11, 13)), Integer.parseInt(datetime.substring(14, 16)), Integer.parseInt(datetime.substring(17, 19)));
		calendar.add(type, step);
		return getDateTime(calendar);
	}

	public static Calendar getCalendar(String datetime)
	{
		return new GregorianCalendar(Integer.parseInt(datetime.substring(0, 4)), Integer.parseInt(datetime.substring(5, 7)) - 1, Integer.parseInt(datetime.substring(8, 10)), Integer.parseInt(datetime.substring(11, 13)), Integer.parseInt(datetime.substring(14, 16)), Integer.parseInt(datetime.substring(17, 19)));
	}

	public static Calendar getCalendar()
	{
		return getCalendar(getDateTime());
	}

	public static String getPreDate(String date, int type, int step)
	{
		Calendar calendar = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date.substring(8, 10)), 0, 0, 0);
		calendar.add(type, step);
		return getDateTime(calendar).substring(0, 10);
	}

	public static int getDateInt()
	{
		String date = getDate();
		return Integer.parseInt((new StringBuilder()).append(date.substring(0, 4)).append(date.substring(5, 7)).append(date.substring(8, 10)).toString());
	}

	public static int getTimeInt()
	{
		String date = getDateTime();
		return Integer.parseInt((new StringBuilder()).append(date.substring(11, 13)).append(date.substring(14, 16)).append(date.substring(17, 19)).toString());
	}

	public static long getTimeStamp(String datetime)
	{
		return getCalendar(datetime).getTime().getTime();
	}


	public static String formatDate(String datetime, String pattern)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(pattern);
		return simpleDateFormat.format(getCalendar(datetime).getTime());
	}

	public static int getdDatePart(String datetime, int part)
	{
		Calendar calendar = getCalendar(datetime);
		return calendar.get(part);
	}

	public static int getdDatePart(int part)
	{
		Calendar calendar = getCalendar(getDateTime());
		return calendar.get(part);
	}

	public static String getTimeInMillis(Date sDate, Date eDate)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sDate);
		long timethis = calendar.getTimeInMillis();
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(eDate);
		long timeend = calendar2.getTimeInMillis();
		long thedaymillis = timeend - timethis;
		return thedaymillis >= 1000L ? (new StringBuilder()).append(thedaymillis / 1000L).append("秒!").toString() : (new StringBuilder()).append(thedaymillis).append("毫秒!").toString();
	}

	public static String formatDateTime(String dTime)
	{
		String dateTime = "";
		if (dTime != null && !"".equals(dTime) && !dTime.startsWith("1900-01-01"))
		{
			Timestamp t = Timestamp.valueOf(dTime);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateTime = formatter.format(t);
		}
		return dateTime;
	}

	public static String formatDateTime(String dTime, String format)
	{
		if ("".equals(format.trim()))
			format = "yyyy-MM-dd";
		String dateTime = "";
		if (dTime != null && !"".equals(dTime) && !dTime.startsWith("1900-01-01"))
		{
			Timestamp t = Timestamp.valueOf(dTime);
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			dateTime = formatter.format(t);
		}
		return dateTime;
	}

	public static String formatDateStr(String dTime, int lastIndex)
	{
		if (dTime.equals("") || dTime.substring(0, 10).equals("1900-01-01"))
			dTime = "";
		else
			dTime = dTime.substring(0, lastIndex);
		return dTime;
	}

	public static int getLastDayOfMonth(int year, int month)
	{
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
			return 31;
		if (month == 4 || month == 6 || month == 9 || month == 11)
			return 30;
		if (month == 2)
			return (year % 4 != 0 || year % 100 == 0) && year % 400 != 0 ? 28 : 29;
		else
			return 0;
	}

	public static String[] getPeriodBeginEnd(String tperiod, String cyccode)
	{
		String period = tperiod;
		int cyc = Integer.parseInt(cyccode);
		String periodbelong[] = new String[2];
		String year = "";
		year = period.substring(0, 4);
		switch (cyc)
		{
		case 0: // '\0'
			periodbelong[0] = tperiod;
			periodbelong[1] = tperiod;
			break;

		case 1: // '\001'
			periodbelong[0] = (new StringBuilder()).append(period).append("01").toString();
			periodbelong[1] = (new StringBuilder()).append(period).append(getLastDayOfMonth(Integer.parseInt(year), Integer.parseInt(period.substring(4)))).toString();
			break;

		case 2: // '\002'
			switch (Integer.parseInt(period.substring(5)))
			{
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
			switch (Integer.parseInt(period.substring(5, 6)))
			{
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

	public static boolean isLegalOfSsqj(String ssqj, String cycCode)
	{
		boolean flag = false;
		int cyc = Integer.parseInt(cycCode);
		switch (cyc)
		{
		default:
			break;

		case 0: // '\0'
			if (ssqj.matches("(\\d{3}[^0]|[^0]\\d{3})(0[^0]0[1-9]|0[^0]1[0-9]|0[^0]2[0-8]|1[0-2]0[1-9]|1[0-2]1[0-9]|1[0-2]2[0-8]|0[13-9]29|1[0-2]29|0[13-9]30|1[0-2]30|0[13578]31|1[02]31)|\\d{2}0[48]0229|\\d{2}[2468][048]0229|\\d{2}[13579][26]0229|0[48]000229|[13579][26]000229|[2468][048]000229"))
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

	public static String getCurSsqjs()
	{
		String curSsqjs = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = df.format(new Date());
		int year = Integer.parseInt(curDate.substring(0, 4));
		int month = Integer.parseInt(curDate.substring(5, 7));
		if (month == 1)
		{
			String curY = String.valueOf(year - 1);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("12','").append(curY).append("Q4','").append(curY).append("'").toString();
		} else
		if (month == 2)
		{
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("01'").toString();
		} else
		if (month == 3)
		{
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("02'").toString();
		} else
		if (month == 4)
		{
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("03','").append(curY).append("Q1'").toString();
		} else
		if (month == 5)
		{
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("04'").toString();
		} else
		if (month == 6)
		{
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("05'").toString();
		} else
		if (month == 7)
		{
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("06','").append(curY).append("Q2'").toString();
		} else
		if (month == 8)
		{
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("07'").toString();
		} else
		if (month == 9)
		{
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("08'").toString();
		} else
		if (month == 10)
		{
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("09','").append(curY).append("Q3'").toString();
		} else
		if (month == 11)
		{
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("10'").toString();
		} else
		{
			String curY = String.valueOf(year);
			curSsqjs = (new StringBuilder()).append("'").append(curY).append("11'").toString();
		}
		return curSsqjs;
	}

	public static String getPreSSQJ(String curSSQJ)
	{
		SimpleDateFormat fmt = null;
		if (curSSQJ.length() == 4)
			fmt = new SimpleDateFormat("yyyy");
		else
		if (curSSQJ.length() == 6)
			fmt = new SimpleDateFormat("yyyyMM");
		Calendar cal = Calendar.getInstance();
		try
		{
			cal.setTime(fmt.parse(curSSQJ));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		if (curSSQJ.length() == 4)
			cal.add(1, -1);
		else
		if (curSSQJ.length() == 6)
			cal.add(2, -1);
		String preSSQJ = fmt.format(cal.getTime());
		return preSSQJ;
	}

	public static boolean isDate(String strDate, String pattern)
	{
		boolean back = true;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try
		{
			sdf.parse(strDate);
		}
		catch (ParseException e)
		{
			back = false;
		}
		return back;
	}

	
	

	public static void main(String args[]) {
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
			 * DATE_FORMAT_FULL); Date d1 =
			 * DateUtil.parseStringToDate("2014-12-10 10:30:12",
			 * DATE_FORMAT_FULL); Long n = d.getTime() - d1.getTime();
			 * System.out.println(DateUtil.changeTime(n.intValue()/1000));
			 */
		// System.out.println(DateUtil.getAfterDateTime(30*24).toLocaleString());
		System.out.println((long) 9 / 10);
		boolean dateArr = isLegalOfSsqj("2008Q1", "2");
		System.out.println(dateArr);
	}

}
