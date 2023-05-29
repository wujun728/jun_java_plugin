package com.jun.plugin.util2.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 常用日期函数工具类
 * @author 罗季晖
 * @email  bigtiger02@gmail.com
 * @date   2013-8-21
 */
public class DateUtil {
	
	/**
	 * 日期格式化
	 * @param reg   日期格式化参数
	 * @param date  日期
	 * @return
	 */
	public static String dateFormat(String reg,Date date){
		DateFormat sdf = getDateFormat(reg);
		return sdf.format(date);
	}
	/**
	 * 默认日期格式化  格式为yyyy-MM-dd 
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date){
		return dateFormat("yyyy-MM-dd",date);
	}
	
	public static String dateFormatForTime(Date date){
		return dateFormat("yyyy-MM-dd HH:mm:ss", date);
	}
	/**
	 * 以友好的方式显示时间
	 * @param sdate
	 * @return
	 */
	public static String friendlyTime(Date time) {
		if(time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = getDateFormat();
		//判断是否是同一天
		String curDate = dateFormat.format(cal.getTime());
		String paramDate = dateFormat.format(time);
		if(curDate.equals(paramDate)){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0){
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			}else{ 
				ftime = hour+"小时前";
			}
			return ftime;
		}
		
		long lt = time.getTime()/86400000;
		long ct = cal.getTimeInMillis()/86400000;
		int days = (int)(ct - lt);		
		if(days == 0){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0){
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			}else{ 
				ftime = hour+"小时前";
			}
		}
		else if(days == 1){
			ftime = "昨天";
		}
		else if(days == 2){
			ftime = "前天";
		}
		else if(days > 2 && days <= 10){ 
			ftime = days+"天前";			
		}
		else if(days > 10){			
			ftime = dateFormat.format(time);
		}
		return ftime;
	}
	
	/**
	 * 判断给定字符串时间是否为今日
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(Date time){
		boolean b = false;
		Date today = new Date();
		DateFormat dateFormat = getDateFormat();
		if(time != null){
			String nowDate = dateFormat.format(today);
			String timeDate = dateFormat.format(time);
			if(nowDate.equals(timeDate)){
				b = true;
			}
		}
		return b;
	}
	
	/**
	 * 获取日期格式化对象
	 * @return
	 */
	public static DateFormat getDateFormat(){
		return getDateFormat("yyyy-MM-dd");
	}
	/**
	 * 获取日期格式化对象
	 * @param reg
	 * @return
	 */
	public static DateFormat getDateFormat(String reg){
		return new SimpleDateFormat(reg);
	}
    
    /**
     * 获取毫秒
	 * @param reg
	 * @param date
	 * @return
	 */
	public static long getMilliseconds(String reg,String date){
		long milliseconds = 0;
		DateFormat df = getDateFormat(reg);
		try {
			Date time = df.parse(date);
			milliseconds = time.getTime();
		} catch (ParseException e) {
			milliseconds = 0;
		}
		return milliseconds;
	}
}