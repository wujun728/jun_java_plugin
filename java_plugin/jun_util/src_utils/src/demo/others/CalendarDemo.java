package demo.others;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * 日历类简单例子,Calendar类不稳定,有延时性和容错性
 */
public class CalendarDemo {
	public static void main(String[] args) {
		simpleDemo();
		showCalendar();
		numberOfDays();
	}

	// Calendar常用方法示例
	public static void simpleDemo() {
		//当前日期
		Calendar c = new GregorianCalendar();
		//c.setTime(new Date());
		StringBuilder str = new StringBuilder();
		//获取当前日期的信息
		str.append("year:" + c.get(Calendar.YEAR));
		str.append("   month:" + (c.get(Calendar.MONTH) + 1));
		str.append("   day:" + c.get(Calendar.DAY_OF_MONTH));
		str.append("   week:" + (c.get(Calendar.DAY_OF_WEEK) - 1));
		str.append("   hour:" + c.get(Calendar.HOUR_OF_DAY));
		str.append("   minute:" + c.get(Calendar.MINUTE));
		str.append("   second:" + c.get(Calendar.SECOND));
		System.out.println(str);

		// 转换成Date对象
		Date d = Calendar.getInstance().getTime();
		System.out.println(d);
	}

	// 输出当前月的日历
	public static void showCalendar() {
		// 获得当前时间
		Calendar c = Calendar.getInstance();

		// 设置代表的日期为1号

		c.set(Calendar.DATE, 1);

		// 获得1号是星期几

		int start = c.get(Calendar.DAY_OF_WEEK);

		// 获得当前月的最大日期数

		int maxDay = c.getActualMaximum(Calendar.DATE);

		// 输出标题

		System.out.println("星期日 星期一 星期二 星期三 星期四 星期五   星期六");

		// 输出开始的空格

		for (int i = 1; i < start; i++) {

			System.out.print("      ");

		}

		// 输出该月中的所有日期

		for (int i = 1; i <= maxDay; i++) {

			// 输出日期数字

			System.out.print(" " + i);

			// 输出分隔空格

			System.out.print("   ");

			if (i < 10) {

				System.out.print(' ');

			}

			// 判断是否换行

			if ((start + i - 1) % 7 == 0) {

				System.out.println();

			}

		}

		// 换行

		System.out.println();

	}

	// 计算两个日期之间相差的天数
	public static void numberOfDays() {

		// 设置两个日期

		// 日期：2009年3月11号

		Calendar c1 = Calendar.getInstance();

		c1.set(2009, 3 - 1, 11);

		// 日期：2010年4月1号

		Calendar c2 = Calendar.getInstance();

		c2.set(2010, 4 - 1, 1);

		// 转换为相对时间

		long t1 = c1.getTimeInMillis();

		long t2 = c2.getTimeInMillis();

		// 计算天数

		long days = (t2 - t1) / (24 * 60 * 60 * 1000);

		System.out.println(days);
	}

}
