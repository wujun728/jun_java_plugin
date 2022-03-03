package com.jun.admin.test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jun.admin.util.Configuration;
import com.jun.admin.util.DateUtil;
import com.jun.admin.util.SpringUtil;

public class TestLongToBin {
	
	public static void main(String[] args) {
		function3();
	}

	public static void function3() {
		Date startDate = new Date();
		//**************************************************************************************
		System.out.println(Long.MAX_VALUE);
		System.out.println(Long.toBinaryString(Long.MAX_VALUE));
		System.out.println(String.valueOf(Long.MAX_VALUE).length());
		System.out.println(String.valueOf(Long.toBinaryString(Long.MAX_VALUE)).length());
		//**************************************************************************************
		Date endDate = new Date();
		System.out.println(DateUtil.getTimeInMillis(startDate, endDate));
	}
	 
}
