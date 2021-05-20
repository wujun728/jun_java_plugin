package com.java1234.chap05.sec01;

import java.util.Calendar;

public class TestCalendar {

	public static void main(String[] args) {
		Calendar calendar=Calendar.getInstance();
		System.out.println(calendar.get(Calendar.YEAR));
		System.out.println(calendar.get(Calendar.MONTH)+1);
		
		System.out.println("现在是："+calendar.get(Calendar.YEAR)+"年"
				+(calendar.get(Calendar.MONTH)+1)+"年"
				+calendar.get(Calendar.DAY_OF_MONTH)+"日"
				+calendar.get(Calendar.HOUR_OF_DAY)+"时"
				+calendar.get(Calendar.MINUTE)+"分"
				+calendar.get(Calendar.SECOND)+"秒");
	}
}
