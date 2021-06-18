package com.dcf.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * <h1>时间类型处理类</h1><br>
 * <p>提供时间格式的转换、字符串类型转时间类型等方法。</p>
 *
 */
public class DateUtil {
	
	/**
	 * 获取指定格式的当前日期字符串
	 * @param pattern 日期格式
	 * @return String 返回指定格式的时间字符串
	 */
	public static String getCurrDate(String pattern){
		return new SimpleDateFormat(pattern).format(new Date());
	}
	
	/**
	 * 获取当前日期指定格式的日期
	 * @param pattern 日期格式
	 * @return Date 转换后的日期
	 */
	public static Date getCurrentDate(String pattern){
		return stringToDate(getCurrDate(pattern),pattern);
	}
	
	/**
	 * 将日期转换为指定格式字符串
	 * @param date 要转化的日期
	 * @param pattern 转化格式
	 * @return String 转换后的日期格式字符串
	 */
	public static String dateToString(Date date,String pattern){
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 将字符串转化为指定格式的日期
	 * @param dateString 要转化的字符串
	 * @param pattern 转化格式
	 * @return Date 转换后的日期
	 */
	public static Date stringToDate(String dateString, String pattern){
		return com.eos.foundation.common.utils.DateUtil.parse(dateString, pattern);
	}
	
	/**
	 * 将日期转化为指定格式的日期
	 * @param date 要转换的日期
	 * @param pattern 转换的格式
	 * @return Date 转换后的日期
	 */
	public static Date dateToDate(Date date,String pattern){
		return stringToDate(dateToString(date,pattern),pattern);
	}
	
	
	/**
	 * 获取指定日期月份的第一天
	 * @param date 获取月份的日期
	 * @return Date 返回指定日期月份的第一天的日期
	 */
	public static Date getFirstDayByMonth(Date date) {

		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
	
		gc.setTime(date);
	
		gc.set(Calendar.DAY_OF_MONTH, 1);
	
		return dateToDate(gc.getTime(), "yyyy-MM-dd");

	}
	
	/**
	 * 获取指定日期月份的最后一天
	 * @param date 获取月份的日期
	 * @return Date 返回最后一天的日期
	 */
	public static Date getLastDayByMonth(Date date){
		
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.set(Calendar.DATE, 1);// 设为当前月的1号

		cal.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号

		cal.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		return dateToDate(cal.getTime(), "yyyy-MM-dd");

	}

	/**
	 * 获取指定日期的月份
	 * @param date 指定日期
	 * @return int 返回int型月份值
	 */
	public static int getMonthByDate(Date date) {

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		return cal.get(Calendar.MONTH);

	}
	
	/**
	 * 获取指定日期加减n天后的日期
	 * @param date 指定的日期
	 * @return Date 返回Date型加减n天后的日期
	 */
	public static Date nextDates(Date date,int n){
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(date);
		
		cal.add(Calendar.DAY_OF_MONTH, +n);

        return cal.getTime();

	}
	
	/**
	 * 获取当前时间毫秒数
	 * @return Long 返回Long型毫秒值
	 */
	public static Long getJVMTimeMillis(){
		return com.eos.foundation.common.utils.DateUtil.getJVMTimeMillis();
	}
	
}
