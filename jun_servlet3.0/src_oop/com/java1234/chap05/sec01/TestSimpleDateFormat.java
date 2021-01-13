package com.java1234.chap05.sec01;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSimpleDateFormat {

	/**
	 * 将日期字符串转换成一个日期对象
	 * @param dateStr 日期字符串
	 * @param format 格式
	 * @return
	 * @throws ParseException
	 */
	public static Date formatDateStr(String dateStr,String format) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(dateStr);
	}
	
	
	/**
	 * 将日期对象格式化为指定格式的日期字符串
	 * @param date 传入的日期对象
	 * @param format 格式
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	public static void main(String[] args) throws ParseException {
		Date date=new Date();
		/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(date));*/
		
		System.out.println(formatDate(date,"yyyy-MM-dd"));
		System.out.println(formatDate(date,"yyyy-MM-dd HH:mm:ss"));
		System.out.println(formatDate(date,"yyyy年MM月dd日 HH时mm分ss秒"));
		
		String dateStr="1989-11-02 10:04:07";
		Date date2=formatDateStr(dateStr,"yyyy-MM-dd HH:mm:ss");
		System.out.println(formatDate(date2,"yyyy-MM-dd HH:mm:ss"));
	}
}
