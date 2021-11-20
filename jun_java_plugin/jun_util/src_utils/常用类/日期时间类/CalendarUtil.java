package org.zxjava.test.other;

import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarUtil {

	public static void main(String args[]) {

		System.out.println("First day of week is : "
				+ new SimpleDateFormat("yyyy-MM-dd")
						.format(getFirstDateByWeek(new Date())));

		System.out.println("Last day of week is : "
				+ new SimpleDateFormat("yyyy-MM-dd")
						.format(getLastDateByWeek(new Date())));
		
		System.out.println("First day of month is : "
				+ new SimpleDateFormat("yyyy-MM-dd")
						.format(getFirstDateByMonth(new Date())));
		
		System.out.println("Last day of month is : "
				+ new SimpleDateFormat("yyyy-MM-dd")
						.format(getLastDateByMonth(new Date())));
	}

	/**
	 * 获得所在星期的第一天
	 */
	public static Date getFirstDateByWeek(Date date) {

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期一
		now.set(now.DATE, first_day_of_week);
		return now.getTime();
	}

	/**
	 * 获得所在星期的最后一天
	 */
	public static Date getLastDateByWeek(Date date) {

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int today = now.get(Calendar.DAY_OF_WEEK);
		int first_day_of_week = now.get(Calendar.DATE) + 2 - today; // 星期一
		int last_day_of_week = first_day_of_week + 6; // 星期日
		now.set(now.DATE, last_day_of_week);
		return now.getTime();
	}

	/**
	 * 获得所在月份的最后一天
	 * @param 当前月份所在的时间
	 * @return 月份的最后一天
	 */
	public static Date getLastDateByMonth(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + 1);
		now.set(Calendar.DATE, 1);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
		now.set(Calendar.HOUR, 11);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		return now.getTime();
	}

	/**
	 * 获得所在月份的第一天
	 * @param 当前月份所在的时间 
	 * @return 月份的第一天
	 */
	public static Date getFirstDateByMonth(Date date) {
		
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, 0);
		now.set(Calendar.HOUR, 12);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now.getTime();
	}
}
