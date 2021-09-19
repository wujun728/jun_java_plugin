package com.jun.plugin.demo.chap05.sec01;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSimpleDateFormat {

	/**
	 * �����ڶ����ʽΪָ����ʽ�������ַ���
	 * @param date ��������ڶ���
	 * @param format ��ʽ
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
	 * �������ַ���ת����һ�����ڶ���
	 * @param dataStr �����ַ���
	 * @param format ��ʽ
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
		System.out.println(formatDate(date,"yyyy��MM��dd�� HH:mm:ss"));
		System.out.println(formatDate(date,"yyyy��MM��dd��"));
		
		System.out.println(formatDate(data2,"yyyy-MM-dd HH:mm:ss"));
		
		
	}
}
