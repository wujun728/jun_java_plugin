package com.kulv.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

	/**
	 * 
	 * @return 数据格式:2014-14-19 12:30:30  
	 */
	public static String getCurrentDateTime(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
	
	/**
	 * 
	 * @return 数据格式:20141419123030 
	 */
	public static String getCurrentDateTimeSeria(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date());
	}
	
	/**
	 * 生成当前时间
	 *  格式：12:30:30
	 * @return
	 */
	public static String getCurrentTime(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(new Date());
	}
	
	/**
	 * 
	 * @return 数据格式:2014-14-19
	 */
	public static String getCurrentDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date());
	}
	
	/**
	 * 根据初始时间，间隔数生成时间段
	 * @param startDate
	 * @param days
	 * @return
	 * @throws Exception
	 */
	public static List<String> getDates(String startDate,int days) throws Exception{
		List<String> dates = new ArrayList<String>();
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(sdf.parse(startDate));
		for(int i=0;i<days;i++){
			dates.add(sdf.format(c.getTime()));
			c.add(Calendar.DATE, 1);
		}
		return dates;
	}
	/**
	 * 根据初始时间，间隔数生成最后时间
	 * @param startDate
	 * @param days
	 * @return
	 * @throws Exception
	 */
	public static String getDate(String startDate,int days) throws Exception{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(sdf.parse(startDate));
		c.add(Calendar.DATE, days);
		return sdf.format(c.getTime());
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}
	/**
	 * 获取指定日期的前一天
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}
	/**
	 * 根据开始日期和结束日期，计算期间的日期
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public static List<String> getListDateBystartEnd(String startDate,String endDate) throws Exception{
		List<String> dates = new ArrayList<String>();
		
		String trueEndDate = getSpecifiedDayBefore(endDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date flagDate = dateFormat.parse(trueEndDate);
		
		while (true) {
			dates.add(startDate);
			startDate = getSpecifiedDayAfter(startDate);
			Date date = dateFormat.parse(startDate);
			if (date.after(flagDate)) {
				break;
			}
		}
		return dates;
	}
	
	public static void main(String[] args) throws ParseException {
		try{
			List<String> dates = getListDateBystartEnd("2014-08-21","2014-08-24");
			for(int i=0;i<dates.size();i++){
				System.out.println(dates.get(i));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
