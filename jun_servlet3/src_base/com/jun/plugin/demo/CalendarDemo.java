package com.jun.plugin.demo;

import java.util.Calendar;
import java.util.Date;

public class CalendarDemo {
	public static void main(String args[]) throws InterruptedException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		long start = calendar.getTimeInMillis();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String date = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		System.out.println("CalendarDemo");
		System.out.println("" + year + "年" + month + "月" + date + "日 " + "星期" + day);
		System.out.println("" + hour + ":" + minute + ":" + second + "");
//		calendar.set(1949, 9, 1);
//		calendar.setTime(new Date());
//		calendar.set(2004, 9, 1);
		calendar.setTime(new Date());
		long end = calendar.getTimeInMillis();
		long interdays = (end - start) ;
		System.out.println("end - start=" + interdays );
	}
}