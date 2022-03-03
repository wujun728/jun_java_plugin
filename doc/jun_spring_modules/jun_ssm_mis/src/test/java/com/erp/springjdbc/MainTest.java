package com.erp.springjdbc;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jun.plugin.utils.Configuration;
import com.jun.plugin.utils.DateUtil;
import com.jun.plugin.utils.biz.SpringContextUtil;

public class MainTest {
	
	public static void main(String[] args) {
		function1();
	}

	public static void function3() {
		Date startDate = new Date();
		Date endDate = new Date();
		System.out.println(DateUtil.getTimeInMillis(startDate, endDate));
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	public static void function0() {
		Date startDate = new Date();
		Date endDate = new Date();
		System.out.println(DateUtil.getTimeInMillis(startDate, endDate));
	}
	@SuppressWarnings({ "unused", "rawtypes" })
	public static void function1() {
		Date startDate = new Date();
		JdbcTemplate jt = (JdbcTemplate) SpringContextUtil.getBean("jdbcTemplate");
		System.out.println("info=1");
		String sql = " SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME ='T_AREA_INFO'   AND COLUMN_KEY<>'PRI' ";
		System.out.println("info=2");
		List list = jt.queryForList(sql);
		System.out.println("info=3");
		Object obj[] = list.toArray();
		System.out.println(list.size());
		Object obj2[] = new Object[list.size()];
		for (Object o : obj) {
			System.out.println(o);
			System.out.println(((Map) o).get("COLUMN_NAME"));
		}
		for (int i = 0; i < list.size(); i++) {
			// obj2[i]=
		}
		Date endDate = new Date();
		System.out.println(DateUtil.getTimeInMillis(startDate, endDate));
	}

	public static void function2() {
		Date startDate = new Date();
		System.out.println("info=" + Configuration.getInstance().getConfiguration("info"));
		Date endDate = new Date();
		System.out.println(DateUtil.getTimeInMillis(startDate, endDate));
	}

}
