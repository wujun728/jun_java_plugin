package com.baijob.commonTools;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {
	/**
	 * 日期测试
	 */
	@Test
	public void dateTest() {
		String dateStr = "2013-01-13";
		Date date = DateUtil.parseDate(dateStr);
		String dateStr_after = DateUtil.formatDate(date);
		Assert.assertEquals(dateStr_after, dateStr);
	}
	
	/**
	 * 日期时间测试
	 */
	@Test
	public void datetimeTest() {
		String dateStr = "2013-01-13 11:33:20";
		Date date = DateUtil.parseDateTime(dateStr);
		String dateStr_after = DateUtil.formatDateTime(date);
		Assert.assertEquals(dateStr_after, dateStr);
	}
	
	/**
	 * 时间偏移测试
	 */
	@Test
	public void getOffsiteDateTest() {
		Date offsiteDate = DateUtil.getOffsiteDate(DateUtil.parseDateTime("2013-01-13 11:33:20"), Calendar.DAY_OF_MONTH, 1);
		Assert.assertEquals("2013-01-14 11:33:20", DateUtil.formatDateTime(offsiteDate));
	}
	
	public static void main(String[] args) {
		//当前时间
		System.out.println("Now: " + DateUtil.now());
		//当天日期
		System.out.println("Today: " + DateUtil.today());
	}
}
