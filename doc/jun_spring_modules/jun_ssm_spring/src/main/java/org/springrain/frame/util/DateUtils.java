package org.springrain.frame.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 日期,时间工具类
 */
public class DateUtils {
	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	//public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	//public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIMEZONE="GMT+8";
	
	
    /** 年月 */
	public final static String MONTH_FORMAT = "yyyy-MM";
	/** 日期 */
	public final static String DATE_FORMAT = "yyyy-MM-dd";
	/** 日期时间 */
	public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/** 时间 */
	public final static String TIME_FORMAT = "HH:mm:ss";
	/**
	 * 每天的毫秒数
	 */
	//private final static long MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
	

	private DateUtils(){
		throw new IllegalAccessError("工具类不能实例化");
	}
	
	
	/**
	 * Get the previous time, from how many days to now.
	 * 
	 * @param days
	 *            How many days.
	 * @return The new previous time.
	 */
	public static Date previous(int days) {
		return new Date(System.currentTimeMillis() - days * 3600000L * 24L);
	}

	/**
	 * Convert date and time to string like "yyyy-MM-dd HH:mm".
	 */
	public static String formatDateTime(Date d) {
		return new SimpleDateFormat(DATETIME_FORMAT).format(d);
	}
	public static String formatDateTime(Date d,String fmt) {
		return new SimpleDateFormat(fmt).format(d);
	}
	/**
	 * Convert date and time to string like "yyyy-MM-dd HH:mm".
	 */
	public static String formatDateTime(long d) {
		return new SimpleDateFormat(DATETIME_FORMAT).format(d);
	}

	public static String formatDate(long d) {
		return new SimpleDateFormat(DATE_FORMAT).format(d);
	}

	/**
	 * Parse date like "yyyy-MM-dd".
	 */
	public static Date parseDate(String d) {
		try {
			return new SimpleDateFormat(DATE_FORMAT).parse(d);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * Parse date and time like "yyyy-MM-dd hh:mm".
	 */
	public static Date parseDateTime(String dt) {
		try {
			return new SimpleDateFormat(DATETIME_FORMAT).parse(dt);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}


	/**
	 * 转换日期字符串得到指定格式的日期类型
	 * 
	 * @param formatString
	 *            需要转换的格式字符串
	 * @param targetDate
	 *            需要转换的时间
	 * @return
	 * @throws ParseException
	 */
	public static final Date convertString2Date(String formatString,
			String targetDate) throws ParseException {
		if (StringUtils.isBlank(targetDate))
			return null;
		SimpleDateFormat format =  new SimpleDateFormat(formatString);
		Date result = null;
		try {
			result = format.parse(targetDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return result;
	}

	

	/**
	 * 转换字符串得到默认格式的日期类型
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date convertString2Date(String strDate) throws ParseException {
		Date result = null;
		try {
			result = convertString2Date(DATE_FORMAT, strDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return result;
	}

	/**
	 * 转换日期得到指定格式的日期字符串
	 * 
	 * @param formatString
	 *            需要把目标日期格式化什么样子的格式。例如,yyyy-MM-dd HH:mm:ss
	 * @param targetDate
	 *            目标日期
	 * @return
	 */
	public static String convertDate2String(String formatString, Date targetDate) {
		SimpleDateFormat format = null;
		String result = null;
		if (targetDate != null) {
			format = new SimpleDateFormat(formatString);
			result = format.format(targetDate);
		} else {
			return null;
		}
		return result;
	}

	/**
	 * 转换日期,得到默认日期格式字符串
	 * 
	 * @param targetDate
	 * @return
	 */
	public static String convertDate2String(Date targetDate) {
		return convertDate2String(DATE_FORMAT, targetDate);
	}

	/**
	 * 比较日期大小
	 * 
	 * @param src
	 * @param src
	 * @return int; 1:DATE1>DATE2;
	 */
	public static int compare_date(Date src, Date src1) {

		String date1 = convertDate2String(DATETIME_FORMAT, src);
		String date2 = convertDate2String(DATETIME_FORMAT, src1);
		DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return 0;
	}

	/**
	 * 日期比较
	 * 
	 * 判断时间date1是否在时间date2之前 <br/>
	 * 时间格式 2005-4-21 16:16:34 <br/>
	 * 添加人：胡建国
	 * 
	 * @param targetDate
	 * @return
	 */
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 日期比较
	 * 
	 * 判断当前时间是否在时间date2之前 <br/>
	 * 时间格式 2005-4-21 16:16:34 <br/>
	 * 添加人：胡建国
	 * 
	 * @param targetDate
	 * @return
	 */
	public static boolean isDateBefore(String date2) {
		if (date2 == null) {
			return false;
		}
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 比较当前时间与时间date2的天相等 时间格式 2008-11-25 16:30:10 如:当前时间是2008-11-25
	 * 16:30:10与传入时间2008-11-25 15:31:20 相比较,返回true即相等
	 * 
	 * @param date1
	 * @param date2
	 * @return boolean; true:相等
	 * @author zhangjl
	 */
	public static boolean equalDate(String date2) {
		try {
			String date1 = convertDate2String(DATETIME_FORMAT,
					new Date());
			//date1.equals(date2);
			Date d1 = convertString2Date(DATE_FORMAT, date1);
			Date d2 = convertString2Date(DATE_FORMAT, date2);
			return d1.equals(d2);
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
			return false;
		}
	}

	/**
	 * 比较时间date1与时间date2的天相等 时间格式 2008-11-25 16:30:10
	 * 
	 * @param date1
	 * @param date2
	 * @return boolean; true:相等
	 * @author zhangjl
	 */
	public static boolean equalDate(String date1, String date2) {
		if(StringUtils.isEmpty(date1)||StringUtils.isEmpty(date2)){
			return false;
		}
		try {

			Date d1 = convertString2Date(DATE_FORMAT, date1);
			Date d2 = convertString2Date(DATE_FORMAT, date2);

			return d1.equals(d2);
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 比较时间date1是否在时间date2之前 时间格式 2008-11-25 16:30:10
	 * 
	 * @param date1
	 * @param date2
	 * @return boolean; true:在date2之前
	 * @author 胡建国
	 */
	public static boolean beforeDate(String date1, String date2) {
		if(StringUtils.isEmpty(date1)||StringUtils.isEmpty(date2)){
			return false;
		}
		try {
			Date d1 = convertString2Date(DATE_FORMAT, date1);
			Date d2 = convertString2Date(DATE_FORMAT, date2);
			return d1.before(d2);
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 获取上个月开始时间
	 * 
	 * @param currentDate
	 *            当前时间
	 * @return 上个月的第一天
	 */
	public static Date getBoferBeginDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		result.set(currentDate.get(Calendar.YEAR), (currentDate
				.get(Calendar.MONTH)) - 1, result
				.getActualMinimum(Calendar.DATE), 0, 0, 0);
		return result.getTime();
	}

	/**
	 * 获取指定月份的第一天
	 * 
	 * @param currentDate
	 * @return
	 */
	public static Date getBeginDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		result.set(currentDate.get(Calendar.YEAR), (currentDate
				.get(Calendar.MONTH)), result.getActualMinimum(Calendar.DATE));
		return result.getTime();
	}


	/**
	 * 获取上个月结束时间
	 * 
	 * @param currentDate
	 *            当前时间
	 * @return 上个月最后一天
	 */
	public static Date getBoferEndDate(Calendar currentDate) {
		Calendar result = currentDate;
		// result.set(currentDate.get(Calendar.YEAR), currentDate
		// .get(Calendar.MONTH) - 1);
		result.set(Calendar.DATE, 1);
		result.add(Calendar.DATE, -1);
		return result.getTime();
	}

	/**
	 * 获取两个时间的时间间隔
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static int getDaysBetween(Calendar beginDate, Calendar endDate) {
		if (beginDate.after(endDate)) {
			Calendar swap = beginDate;
			beginDate = endDate;
			endDate = swap;
		}
		int days = endDate.get(Calendar.DAY_OF_YEAR)
				- beginDate.get(Calendar.DAY_OF_YEAR) + 1;
		int year = endDate.get(Calendar.YEAR);
		if (beginDate.get(Calendar.YEAR) != year) {
			beginDate = (Calendar) beginDate.clone();
			do {
				days += beginDate.getActualMaximum(Calendar.DAY_OF_YEAR);
				beginDate.add(Calendar.YEAR, 1);
			} while (beginDate.get(Calendar.YEAR) != year);
		}
		return days;
	}

	/**
	 * 获取两个时间的时间间隔(月份)
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getMonthsBetween(Date beginDate, Date endDate) {
		if (beginDate.after(endDate)) {
			Date swap = beginDate;
			beginDate = endDate;
			endDate = swap;
		}
		int months = endDate.getMonth() - beginDate.getMonth();
		int years = endDate.getYear() - beginDate.getYear();

		months += years * 12;

		return months;
	}

	/**
	 * 获取两个时间内的工作日
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static int getWorkingDay(Calendar beginDate, Calendar endDate) {
		int result = -1;
		if (beginDate.after(endDate)) {
			Calendar swap = beginDate;
			beginDate = endDate;
			endDate = swap;
		}
		int charge_start_date = 0;
		int charge_end_date = 0;
		int stmp;
		int etmp;
		stmp = 7 - beginDate.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - endDate.get(Calendar.DAY_OF_WEEK);
		if (stmp != 0 && stmp != 6) {
			charge_start_date = stmp - 1;
		}
		if (etmp != 0 && etmp != 6) {
			charge_end_date = etmp - 1;
		}
		result = (getDaysBetween(getNextMonday(beginDate),
				getNextMonday(endDate)) / 7)
				* 5 + charge_start_date - charge_end_date;
		return result;
	}

	/**
	 * 根据当前给定的日期获取当前天是星期几(中国版的)
	 * 
	 * @param date
	 *            任意时间
	 * @return
	 */
	public static String getChineseWeek(Calendar date) {
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		return dayNames[dayOfWeek - 1];

	}

	/**
	 * 获得日期的下一个星期一的日期
	 * 
	 * @param date
	 *            任意时间
	 * @return
	 */
	public static Calendar getNextMonday(Calendar date) {
		Calendar result = null;
		do {
			result = (Calendar) date.clone();
			result.add(Calendar.DATE, 1);
		} while (result.get(Calendar.DAY_OF_WEEK) != 2);
		return result;
	}

	/**
	 * 获取两个日期之间的休息时间
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static int getHolidays(Calendar beginDate, Calendar endDate) {
		return getDaysBetween(beginDate, endDate)
				- getWorkingDay(beginDate, endDate);

	}

	public static boolean isDateEnable(Date beginDate, Date endDate,
			Date currentDate) {
		// 开始日期
		long beginDateLong = beginDate.getTime();
		// 结束日期
		long endDateLong = endDate.getTime();
		// 当前日期
		long currentDateLong = currentDate.getTime();
		if (currentDateLong >= beginDateLong && currentDateLong <= endDateLong) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 获取当前月份的第一天
	 * 
	 * @param currtenDate
	 *            当前时间
	 * @return
	 */
	public static Date getMinDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		result.set(currentDate.get(Calendar.YEAR), currentDate
				.get(Calendar.MONTH), currentDate
				.getActualMinimum(Calendar.DATE));
		return result.getTime();
	}

	public static Calendar getDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date);
		return calendar;
	}

	public static Calendar getDate(int year, int month) {
		return getDate(year, month, 0);
	}

	public static Date getCountMinDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, calendar.getActualMinimum(Calendar.DATE));
		return calendar.getTime();
	}

	public static Date getCountMaxDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH), 0);
		return calendar2.getTime();
	}

	/**
	 * 获取当前月份的第一天
	 * 
	 * @param currtenDate
	 *            当前时间
	 * @return
	 */
	public static Date getMinDate() {
		Calendar currentDate = Calendar.getInstance();
		return getMinDate(currentDate);
	}

	/**
	 * 获取当前月分的最大天数
	 * 
	 * @param currentDate
	 *            当前时间
	 * @return
	 */
	public static Date getMaxDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		result.set(currentDate.get(Calendar.YEAR), currentDate
				.get(Calendar.MONTH), currentDate
				.getActualMaximum(Calendar.DATE));
		return result.getTime();
	}

	/**
	 * 获取当前月分的最大天数
	 * 
	 * @param currentDate
	 *            当前时间
	 * @return
	 */
	public static Date getMaxDate() {
		Calendar currentDate = Calendar.getInstance();
		return getMaxDate(currentDate);
	}

	/**
	 * 获取今天最大的时间
	 * 
	 * @return
	 */
	public static String getMaxDateTimeForToDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar
				.getMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
		return convertDate2String(DATETIME_FORMAT, calendar.getTime());
	}

	/**
	 * 获取日期最大的时间
	 * 
	 * @return
	 */
	public static Date getMaxDateTimeForToDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar
				.getMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
		return calendar.getTime();
	}

	/**
	 * 获取今天最小时间
	 * 
	 * @return
	 */
	public static String getMinDateTimeForToDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar
				.getMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
		return convertDate2String(DATETIME_FORMAT, calendar.getTime());
	}

	/**
	 * 获取 date 最小时间
	 * 
	 * @return
	 */
	public static Date getMinDateTimeForToDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar
				.getMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
		return calendar.getTime();
	}

	/**
	 * 获取发生日期的结束时间 根据用户设置的日期天数来判定这这个日期是什么(例如 (getHappenMinDate = 2008-10-1) 的话
	 * 那么 (getHappenMaxDate = 2008-11-1) 号)
	 * 
	 * @return
	 */
	public static Date getHappenMaxDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 加减天数
	 * 
	 * @param num
	 * @param Date
	 * @return
	 */
	public static String addDay(int num, String dateStr) {
		Date date = parseDate(dateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, num);// 把日期往后增加 num 天.整数往后推,负数往前移动
		return formatDate(calendar.getTime().getTime()); // 这个时间就是日期往后推一天的结果
	}
	
	/**
	 * 加减天数
	 * 
	 * @param num
	 * @param Date
	 * @return
	 */
	public static Date addDay(int num, Date Date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Date);
		calendar.add(Calendar.DATE, num);// 把日期往后增加 num 天.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推一天的结果
	}

	/**
	 * 加减小时
	 * @param num 正数：时间往后推num小时；负数：往前推num小时。
	 * @param date
	 * @return
	 */
	public static Date addHour(int num,Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		calendar.add(Calendar.HOUR, num);
		return calendar.getTime();
	}
	
	
	  public static void main(String[] args) {
		  
//	  System.out.println(getMaxDateTimeForToDay());
//	  System.out.println(getMinDateTimeForToDay());
		try {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		  
		  Calendar c = Calendar.getInstance();
	      c.set(Calendar.MONTH, 0);
	      Date resultDate = c.getTime();
	      String stDate = sdf.format(resultDate);
		  Date startDate = sdf.parse(stDate);
		  
		  Date nowDate = new Date();
		  String eDate = sdf.format(nowDate);
		  Date endDate = sdf.parse(eDate);
		
		  getDateBetweenTwo(startDate,endDate);
		} catch (ParseException e) {
		
			e.printStackTrace();
		}
		  
		 
	  }
	 

	/**
	 * 计算两端时间的小时差
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getHour(Date begin, Date end) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(begin);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(end);
		Long millisecond = c2.getTimeInMillis() - c1.getTimeInMillis();
		Long hour = millisecond / 1000 / 60 / 60;
		Long minute = (millisecond / 1000 / 60) % 60;
		if (minute >= 30) {
			hour++;
		}

		return hour.intValue();
	}

	/**
	 * 格式化日期
	 */
	public static String dateFormat(Date date) {
		if (date == null) {
			return null;
		}
		return DateFormat.getDateInstance().format(date);
	}

	

	/*****************************************
	 * @功能 计算某年某月的结束日期
	 * @return interger
	 * @throws ParseException
	 ****************************************/
	public static String getYearMonthEndDay(int yearNum, int monthNum)
			throws ParseException {

		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(monthNum);
		String tempDay = "31";
		if (tempMonth.equals("1") || tempMonth.equals("3")
				|| tempMonth.equals("5") || tempMonth.equals("7")
				|| tempMonth.equals("8") || tempMonth.equals("10")
				|| tempMonth.equals("12")) {
			tempDay = "31";
		}
		if (tempMonth.equals("4") || tempMonth.equals("6")
				|| tempMonth.equals("9") || tempMonth.equals("11")) {
			tempDay = "30";
		}
		if (tempMonth.equals("2")) {
			if (isLeapYear(yearNum)) {
				tempDay = "29";
			} else {
				tempDay = "28";
			}
		}
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return tempDate;// setDateFormat(tempDate,"yyyy-MM-dd");
	}

	/*****************************************
	 * @功能 判断某年是否为闰年
	 * @return boolean
	 * @throws ParseException
	 ****************************************/
	public static boolean isLeapYear(int yearNum) {
		boolean isLeep = false;
		/** 判断是否为闰年，赋值给一标识符flag */
		if ((yearNum % 4 == 0) && (yearNum % 100 != 0)) {
			isLeep = true;
		} else if (yearNum % 400 == 0) {
			isLeep = true;
		} else {
			isLeep = false;
		}
		return isLeep;
	}

	/**
	 * 格式化日期
	 * 
	 * @throws ParseException
	 * 
	 *             例: DateUtils.formatDate("yyyy-MM-dd HH",new Date())
	 *             "yyyy-MM-dd HH:00:00"
	 */
	public static Date formatDate(String formatString, Date date)
			throws ParseException {
		if (date == null) {
			date = new Date();
		}
		if (StringUtils.isBlank(formatString))
			formatString = DateUtils.DATE_FORMAT;

		date = DateUtils.convertString2Date(formatString, DateUtils
				.convertDate2String(formatString, date));

		return date;
	}

	/**
	 * 格式化日期 yyyy-MM-dd
	 * 
	 * @throws ParseException
	 *             例： DateUtils.formatDate(new Date()) "yyyy-MM-dd 00:00:00"
	 */
	public static Date formatDate(Date date) throws ParseException {
		date = formatDate(DateUtils.DATE_FORMAT, date);
		return date;
	}

	/**
	 * @throws ParseException
	 *             根据日期获得 星期一的日期
	 * 
	 */
	public static Date getMonDay(Date date) throws ParseException {

		Calendar cal = Calendar.getInstance();
		if (date == null)
			date = new Date();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			cal.add(Calendar.WEEK_OF_YEAR, -1);

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		date = formatDate(cal.getTime());

		return date;
	}

	/**
	 * @throws ParseException
	 *             根据日期获得 星期日 的日期
	 * 
	 */
	public static Date getSunDay(Date date) throws ParseException {

		Calendar cal = Calendar.getInstance();
		if (date == null)
			date = new Date();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
			cal.add(Calendar.WEEK_OF_YEAR, 1);

		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		date = formatDate(cal.getTime());
		return date;
	}

	/**
	 * 获得 下个月的第一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getNextDay(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1);
		return formatDate(cal.getTime());
	}
	
	
	public static String getDatePoor(Date endDate, Date startDate) {	 
	    long nd = 1000 * 24 * 60 * 60;
	    long nh = 1000 * 60 * 60;
	    long nm = 1000 * 60;
	    long ns = 1000;
	    // long ns = 1000;
	    // 获得两个时间的毫秒时间差异
	    long diff = endDate.getTime() - startDate.getTime();
	    // 计算差多少天
	    long day = diff / nd;
	    // 计算差多少小时
	    long hour = diff % nd / nh;
	    // 计算差多少分钟
	    long min = diff % nd % nh / nm;
	    // 计算差多少秒
	    long sec = diff % nd % nh % nm / ns;  
	    
	    StringBuffer bf = new StringBuffer();
	    
	    if(day!=0){
	    	bf.append(day).append("天");
	    }
	    if(hour!=0){
	    	bf.append(hour).append("小时");
	    }
	    if(min!=0){
	    	bf.append(min).append("分钟");
	    }
	    if(sec!=0){
	    	bf.append(sec).append("秒");
	    }
	    return bf.toString();
	}

	
	public static String getDateSec(Date endDate, Date startDate) {	 
	    long nd = 1000 * 24 * 60 * 60;
	    long nh = 1000 * 60 * 60;
	    long nm = 1000 * 60;
	    long ns = 1000;
	    // 获得两个时间的毫秒时间差异
	    long diff = endDate.getTime() - startDate.getTime();
	    // 计算差多少秒
	    long sec = diff % nd % nh % nm / ns;  
	    
	    StringBuffer bf = new StringBuffer();
	    if(sec!=0){
	    	bf.append(sec);
	    }
	    return bf.toString();
	}


	/**
	 * 月份加减
	 * @param month
	 * @param date
	 * @return
	 */
	public static Date addMonth(int num, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, num);// 把日期往后增加 num 月.整数往后推,负数往前移动
		return calendar.getTime();
	}
	
	/**
	 * 获得一个范围日期里面的每一个月份
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getDateBetweenTwo(Date startDate, Date endDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		List<String> dateList = new ArrayList<String>();
		
		Calendar dd = Calendar.getInstance();// 定义日期实例
		dd.setTime(startDate);// 设置日期起始时间

		while (dd.getTime().before(endDate)) {// 判断是否到结束日期
			String str = sdf.format(dd.getTime());
			dateList.add(str);
			dd.add(Calendar.MONTH, 1);// 进行当前日期月份加1
		}
		return dateList;
	}
	/**
     * 根据传入的时间 和当前的时间进行比较.
     * 
     * @param microsecond
     *            1分钟=60*1000 60分钟=1小时=60*60*1000 10小时=24*60*60*1000
     *            5天=5*24*60*60*1000
     * @return
     */
    public static String getTimeConversion(long microsecond) {
        long mDurtionTime = System.currentTimeMillis() - microsecond;
        if (mDurtionTime < 60 * 1000) {
            return String.valueOf(Math.abs(mDurtionTime / 1000)) + "秒前";
        } else if (mDurtionTime < 60 * 60 * 1000) {
            return String.valueOf(mDurtionTime / (60 * 1000)) + "分钟前";
        } else if (mDurtionTime < 24 * 60 * 60 * 1000) {
            return String.valueOf(mDurtionTime / (60 * 60 * 1000)) + "小时前";
        } else if (mDurtionTime < 10 * 24 * 60 * 60 * 1000) {
            return String.valueOf(mDurtionTime / (24 * 60 * 60 * 1000)) + "天前";
        } else {
            return getDateTime(microsecond);
        }

    }
    
    public static String getDateTime(long microsecond) {
        return detailFormat(new Date(microsecond));
    }
    
    /**
     * 返回日期的详细格式：yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     *            传入一个日期
     * @return
     */
    public static String detailFormat(Date date) {
        if (date == null) {
            return "";
        } else {
            return new SimpleDateFormat(DATETIME_FORMAT).format(date);
        }
    }
    
    /**
     * 1分钟=60*1000 1小时=60分钟=60*60*1000 1天=24*60*60*1000
     * *如果时间戳相差十分钟就显示刚刚，一小时之内的显示分钟数，一天内显示小时数，5天之内的显示天数，否则显示简短日期
     * 
     * @param microsecond
     * @return
     */
    public static String getTimeSocial(long microsecond) {
        long mDurtionTime = System.currentTimeMillis() - microsecond;
        if (mDurtionTime < 10 * 60 * 1000) {
            return "刚刚";
        } else if (mDurtionTime < 60 * 60 * 1000) {
            return String.valueOf(mDurtionTime / (60 * 1000)) + "分钟前";
        } else if (mDurtionTime < 24 * 60 * 60 * 1000) {
            return String.valueOf(mDurtionTime / (60 * 60 * 1000)) + "小时前";
        } else if (mDurtionTime < 5 * 24 * 60 * 60 * 1000) {
            return String.valueOf(mDurtionTime / (24 * 60 * 60 * 1000)) + "天前";
        } else {
            return getshortDateTime(microsecond);
        }

    }
    
    /**
     * 当时间相差过长时设置简短的年份显示
     * 
     * @param microsecond
     * @return
     */
    public static String getshortDateTime(long microsecond) {
        return new SimpleDateFormat("yy-MM-d HH:mm").format(new Date(microsecond));
    }
    
    /**
     * 根据传入的月份返回开始日期与结束日期
     * @param selectTime 格式　2017-01　，有特殊值sevenDay 代表近７天
     * @return 第一个值为startTime　第二个值为endTime
     * @throws Exception
     */
    public static List<Date> getStartAndEndTimeByMonth(String selectTime) throws Exception{
    	List<Date> dateList = new ArrayList<Date>();
    	
    	Date createStartTime = null;
    	Date createEndTime = null;
    	if("sevenDay".equals(selectTime)) {
    		Calendar c = Calendar.getInstance();
    		c.set(Calendar.HOUR_OF_DAY, 24);
    		c.set(Calendar.MINUTE, 0);
    		c.set(Calendar.SECOND, 0);
    		c.set(Calendar.MILLISECOND, 0);
    		
    		// 近七天
    		createEndTime = c.getTime();
    		c.add(Calendar.DATE, - 7);  
    		createStartTime = c.getTime();
    	}else {
    		// 单独某个月份
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    		createStartTime = sdf.parse(selectTime);
    		Calendar c = Calendar.getInstance();
    		c.setTime(createStartTime);
    		c.add(Calendar.MONTH, 1);
    		createEndTime = c.getTime();
    	}
    	
    	dateList.add(0, createStartTime);
		dateList.add(1, createEndTime);
    	return dateList;
    }
    

}
