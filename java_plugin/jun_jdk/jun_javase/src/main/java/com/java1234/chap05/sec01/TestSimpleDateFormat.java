package com.java1234.chap05.sec01;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSimpleDateFormat {

	/**
	 * 将日期对象格式为指定格式的日期字符串
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
	
	/**
	 * 将日期字符串转换成一个日期对象
	 * @param dataStr 日期字符串
	 * @param format 格式
	 * @return
	 * @throws ParseException 
	 */
	public static Date formatDate(String dataStr,String format) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(dataStr);
	}
	
	public static void main(String[] args) throws ParseException {
		Date date=new Date();
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(date));*/
		
		String dataStr="1989-10-08 09:01:07";
		Date data2=formatDate(dataStr,"yyyy-MM-dd HH:mm:ss");
		
		System.out.println(formatDate(date,"yyyy-MM-dd HH:mm:ss"));
		System.out.println(formatDate(date,"yyyy年MM月dd日 HH:mm:ss"));
		System.out.println(formatDate(date,"yyyy年MM月dd日"));
		
		System.out.println(formatDate(data2,"yyyy-MM-dd HH:mm:ss"));
		
		
	}
}
