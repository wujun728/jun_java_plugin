package com.opensource.nredis.proxy.monitor.date.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 日期工具类（String 类型）
 * @author liubing
 *
 */
public class DateUtil {
	
	/** 构造方法私有化 */
	private DateUtil() {
	}

	/**

	 * 根据传入的日期格式 获取系统的前一天时间

	 * 

	 * @param pattern

	 *            日期格式 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @return 系统的前一天时间

	 */
	public static String getPreviousDate(String pattern) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date date = cal.getTime();
		DateFormat dateFormat = DateBase.getDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**

	 * 根据传入的日期格式 获取当前系统时间

	 * 

	 * @param pattern

	 *            日期格式 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @return 对应格式的当前系统时间

	 */
	public static String getCurrentDate(String pattern) {
		Calendar cal = GregorianCalendar.getInstance();
		Date date = cal.getTime();
		DateFormat dateFormat = DateBase.getDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**

	 * 根据传入的日期格式 获取系统的明天时间

	 * 

	 * @param pattern

	 *            日期格式 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @return 系统的明天时间

	 */
	public static String getNextDate(String pattern) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date date = cal.getTime();
		DateFormat dateFormat = DateBase.getDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**

	 * 获取指定日期的前或后推N天（字符串类型），如果传入的参数为空，则返回空

	 * 

	 * @param date

	 *            日期

	 * @param n

	 *            跳转天数 负数就是往前推，正数即往后推

	 * @param pattern

	 *            日期的格式 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * 

	 * @return 返回字符型日期

	 */
	public static String moveDate(String date, int n, String pattern) {
		if (n == 0) {
			return date;
		}
		if (date == null || date.trim().equals("")) {
			throw new IllegalArgumentException("传入的日期不能为空！");
		}
		String str = "";
		DateFormat dateFormat = DateBase.getDateFormat(pattern);
		try {
			Date ddate = dateFormat.parse(date);
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(ddate);
			cal.add(Calendar.DAY_OF_MONTH, n);
			Date tmp = cal.getTime();
			str = dateFormat.format(tmp);
		} catch (ParseException e) {
			e.printStackTrace();
			return str;
		}
		return str;
	}

	/**

	 * 返回指定日期和其后n天日期list

	 * 

	 * @param date

	 *            日期

	 * @param n

	 *            天数 负数就是往前推，正数即往后推

	 * @param pattern

	 *            格式化字符串 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @return 日期集合

	 */
	public static List<String> getDates(String date, int n, String pattern) {
		List<String> dayList = new ArrayList<String>();
		String toDate = moveDate(date, n, pattern);
		dayList = DateUtil.getDates(date, toDate, pattern);
		return dayList;
	}

	/**

	 * 取得两个日期之间的所有日期集合，包含起始日期和结束日期， 日期不分前后顺序，支持跨年。

	 * 

	 * @param dateO

	 *            起始日期

	 * @param dateT

	 *            结束日期

	 * @param pattern

	 *            格式化字符串 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * 

	 * @return 日期集合

	 */
	public static List<String> getDates(String dateO, String dateT, String pattern) {
		if ((dateO == null) || (dateT == null) || dateO.trim().equals("")
				|| dateT.trim().equals("")) {
			throw new IllegalArgumentException("传入的日期不能为空！");
		}
		List<String> list = new ArrayList<String>();
		try {
			DateFormat format = DateBase.getDateFormat(pattern);
			Date dO = format.parse(dateO);
			Date dT = format.parse(dateT);
			Calendar calO = GregorianCalendar.getInstance();
			Calendar calT = GregorianCalendar.getInstance();
			if (dO.after(dT)) {
				calO.setTime(dT);
				calT.setTime(dO);
			} else {
				calO.setTime(dO);
				calT.setTime(dT);
			}
			while (!calO.after(calT)) {
				list.add(format.format(calO.getTime()));
				calO.add(GregorianCalendar.DATE, +1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	/**

	 * 获取两个日期之间的天数， 日期不分前后顺序，支持跨年。

	 * 

	 * @param dateO

	 *            日期1

	 * @param dateT

	 *            日期2

	 * @param pattern

	 *            格式化字符串 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @return 两个日期之间的天数

	 */
	public static int getDayCountBetween2Days(String dateO, String dateT, String pattern) {
		if ((dateO == null) || (dateT == null) || dateO.trim().equals("")
				|| dateT.trim().equals("")) {
			throw new IllegalArgumentException("传入的日期不能为空！");
		}
		int count = 0;
		try {
			DateFormat format = DateBase.getDateFormat(pattern);
			Date dO = format.parse(dateO);
			Date dT = format.parse(dateT);
			Calendar calO = GregorianCalendar.getInstance();
			Calendar calT = GregorianCalendar.getInstance();
			if (dO.after(dT)) {
				calO.setTime(dT);
				calT.setTime(dO);
			} else {
				calO.setTime(dO);
				calT.setTime(dT);
			}
			while (!calO.after(calT)) {
				count++;
				calO.add(GregorianCalendar.DATE, +1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return count;
		}
		return count;
	}

	/***

	 * 获取传入日期的同类型日， 正数为未来周中，负数为历史周中

	 * 

	 * @param date

	 *            传入日期

	 * @param n

	 *            正数为未来周中，负数为历史周中

	 * @param pattern

	 *            日期的格式 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * 

	 * @return 同类型日

	 */
	public static String getSimilarDate(String date, int n, String pattern) {
		if (n == 0) {
			return date;
		}
		if (date == null || date.equals("")) {
			throw new IllegalArgumentException("传入的日期不能为空！");
		}
		String str = "";
		DateFormat dateFormat = DateBase.getDateFormat(pattern);
		try {
			Date ddate = dateFormat.parse(date);
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(ddate);
			int count = n * 7;
			cal.add(Calendar.DAY_OF_MONTH, count);
			Date tmp = cal.getTime();
			str = dateFormat.format(tmp);
		} catch (ParseException e) {
			e.printStackTrace();
			return str;
		}
		return str;
	}
	
	/**

	 * 获取一段时间内，对应日期的所有同类型日 日期不分前后顺序，支持跨年。<br>

	 * 

	 * @param dateO

	 *            日期1

	 * @param dateT

	 *            日期2

	 * @param day_of_week

	 *            目标日期

	 * @param pattern

	 *            目标日期格式化字符串 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @return 目标日期的同类型日列表（pattern格式）

	 */
	public static List<String> getSimilarDates(String dateO, String dateT, String day_of_week,
			String pattern) {
		if ((null == dateO) || (null == dateT) || (null == day_of_week) || dateO.trim().equals("")
				|| dateT.trim().equals("") || day_of_week.trim().equals("")) {
			throw new IllegalArgumentException("传入的日期不能为空！");
		}
		List<String> list = new ArrayList<String>();
		try {
			DateFormat format = DateBase.getDateFormat(pattern);
			Date targetDate = format.parse(day_of_week);
			Calendar targetCal = GregorianCalendar.getInstance();
			targetCal.setTime(targetDate);
			int _target = targetCal.get(Calendar.DAY_OF_WEEK);
			Date dO = format.parse(dateO);
			Date dT = format.parse(dateT);
			Calendar calO = GregorianCalendar.getInstance();
			Calendar calT = GregorianCalendar.getInstance();
			if (dO.after(dT)) {
				calO.setTime(dT);
				calT.setTime(dO);
			} else {
				calO.setTime(dO);
				calT.setTime(dT);
			}
			while (!calO.after(calT)) {
				if (calO.get(Calendar.DAY_OF_WEEK) == _target) {
					list.add(format.format(calO.getTime()));
				}
				calO.add(GregorianCalendar.DATE, +1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	/**

	 * 获取一段时间内，对应日期的所有同类型日 日期不分前后顺序，支持跨年。

	 * 

	 * @param dateO

	 *            日期1

	 * @param dateT

	 *            日期2

	 * @param day_of_week

	 *            目标日期 Calendar.DAY_OF_WEEK;

	 * @param pattern

	 *            格式化字符串 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @return 目标日期的同类型日列表（pattern格式）

	 */
	public static List<String> getSimilarDates(String dateO, String dateT, int day_of_week,
			String pattern) {
		if ((null == dateO) || (null == dateT) || dateO.trim().equals("")
				|| dateT.trim().equals("")) {
			throw new IllegalArgumentException("传入的日期不能为空！");
		}
		if (0 == day_of_week) {
			throw new IllegalArgumentException("目标日起录入错误，目标日期为 1~7 的整数");
		}

		List<String> list = new ArrayList<String>();
		try {
			DateFormat format = DateBase.getDateFormat(pattern);
			Date dO = format.parse(dateO);
			Date dT = format.parse(dateT);
			Calendar calO = GregorianCalendar.getInstance();
			Calendar calT = GregorianCalendar.getInstance();
			if (dO.after(dT)) {
				calO.setTime(dT);
				calT.setTime(dO);
			} else {
				calO.setTime(dO);
				calT.setTime(dT);
			}
			while (!calO.after(calT)) {
				if (calO.get(Calendar.DAY_OF_WEEK) == day_of_week) {
					list.add(format.format(calO.getTime()));
				}
				calO.add(GregorianCalendar.DATE, +1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	/** 工作日 */
	private static final String DAY_TYPE_WORKDAY = "workday";

	/** 周末 */
	private static final String DAY_TYPE_WEEKEND = "weekend";

	/**

	 * 获取一段时间内，所有双休日 日期不分前后顺序，支持跨年。

	 * 

	 * @param dateO

	 *            日期1

	 * @param dateT

	 *            日期2

	 * @param pattern

	 *            格式化字符串 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @param type

	 *            日期类型 -- DAY_TYPE_WORKDAY（工作日）、 DAY_TYPE_WEEKEND（周末）

	 * @return pattern格式的日期列表

	 */
	private static List<String> getDates(String dateO, String dateT, String pattern, String type) {
		if ((null == dateO) || (null == dateT) || dateO.trim().equals("")
				|| dateT.trim().equals("")) {
			throw new IllegalArgumentException("传入的日期不能为空！");
		}
		List<String> list = new ArrayList<String>();
		try {
			DateFormat format = DateBase.getDateFormat(pattern);
			Date dO = format.parse(dateO);
			Date dT = format.parse(dateT);
			Calendar calO = GregorianCalendar.getInstance();
			Calendar calT = GregorianCalendar.getInstance();
			if (dO.after(dT)) {
				calO.setTime(dT);
				calT.setTime(dO);
			} else {
				calO.setTime(dO);
				calT.setTime(dT);
			}
			while (!calO.after(calT)) {
				if (type.equalsIgnoreCase(DAY_TYPE_WEEKEND)) {
					if ((calO.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
							|| (calO.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
						list.add(format.format(calO.getTime()));
					}
				} else if (type.equalsIgnoreCase(DAY_TYPE_WORKDAY)) {
					if ((calO.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
							&& (calO.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
						list.add(format.format(calO.getTime()));
					}
				}
				calO.add(GregorianCalendar.DATE, +1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	/**

	 * 获取一段时间内，所有双休日 日期不分前后顺序，支持跨年。

	 * 

	 * @param dateO

	 *            日期1

	 * @param dateT

	 *            日期2

	 * @param pattern

	 *            格式化字符串 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @return 所有双休日列表

	 */
	public static List<String> getWeekends(String dateO, String dateT, String pattern) {
		return getDates(dateO, dateT, pattern, DAY_TYPE_WEEKEND);
	}

	/**

	 * 获取一段时间内，所有工作日 日期不分前后顺序，支持跨年。

	 * 

	 * @param dateO

	 *            日期1

	 * @param dateT

	 *            日期2

	 * @param pattern

	 *            格式化字符串 如果传入格式为空，则为默认格式 yyyy-MM-dd

	 * @return 所有工作日列表

	 */
	public static List<String> getWorkdays(String dateO, String dateT, String pattern) {
		return getDates(dateO, dateT, pattern, DAY_TYPE_WORKDAY);
	}

}
