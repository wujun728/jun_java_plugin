package com.jun.admin.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jun.admin.util.Configuration;
import com.jun.admin.util.DateUtil;
import com.jun.admin.util.SpringUtil;

public class TestJavaSE {
	
	public static void main(String[] args) {
//		function1();
		function2();
	}

	public static void function2() {
//		Runtime.getRuntime().gc();
		System.out.println("Runtime.getRuntime().maxMemory()"+Runtime.getRuntime().maxMemory()/1024/1024+"M");
		System.out.println("Runtime.getRuntime().totalMemory()"+Runtime.getRuntime().totalMemory()/1024/1024+"M");
		System.out.println("="+Double.valueOf(String.valueOf(Runtime.getRuntime().totalMemory()))/Runtime.getRuntime().maxMemory()+"M");
		String s="0";
		for(int i=0;i<28;i++){
			s=s+s;
		}
		System.out.println("Runtime.getRuntime().maxMemory()"+Runtime.getRuntime().maxMemory()/1024/1024+"M");
		System.out.println("Runtime.getRuntime().totalMemory()"+Runtime.getRuntime().totalMemory()/1024/1024+"M");
		System.out.println("="+Double.valueOf(String.valueOf(Runtime.getRuntime().totalMemory()))/Runtime.getRuntime().maxMemory()+"M");
		Runtime.getRuntime().gc();
		System.out.println("Runtime.getRuntime().maxMemory()"+Runtime.getRuntime().maxMemory()/1024/1024+"M");
		System.out.println("Runtime.getRuntime().totalMemory()"+Runtime.getRuntime().totalMemory()/1024/1024+"M");
		System.out.println("="+Double.valueOf(String.valueOf(Runtime.getRuntime().totalMemory()))/Runtime.getRuntime().maxMemory()+"M");
	}
		
	public static void function1() {
		Date startDate = new Date();
		//**************************************************************************************
		String s[] =new String[3];
		s[0]="0";
		s[1]="1";
		s[2]="2";
		for(String t : s){
			System.out.println(t);
		}
		
		
		List list=new ArrayList();
		list.add("1");
		list.add("2");
		for(Object t : list){
			System.out.println(t);
		}

		//**************************************************************************************
		Date endDate = new Date();
		System.out.println(DateUtil.getTimeInMillis(startDate, endDate));
	}
	 
}
