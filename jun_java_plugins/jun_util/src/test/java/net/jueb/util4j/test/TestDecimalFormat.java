package net.jueb.util4j.test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class TestDecimalFormat {

	//获取DecimalFormat的方法DecimalFormat.getInstance();

	public static void test1(DecimalFormat df) {
			//默认显示3位小数
			double d = 1.5555555;
			System.out.println(df.format(d));//1.556
			//设置小数点后最大位数为5
			df.setMaximumFractionDigits(5);
			df.setMinimumIntegerDigits(15);
			System.out.println(df.format(d));//1.55556
			df.setMaximumFractionDigits(2);
			System.out.println(df.format(d));//1.56
			//设置小数点后最小位数，不够的时候补0
			df.setMinimumFractionDigits(10);
			System.out.println(df.format(d));//1.5555555500
			//设置整数部分最小长度为3，不够的时候补0
			df.setMinimumIntegerDigits(3);
			System.out.println(df.format(d));
			//设置整数部分的最大值为2，当超过的时候会从个位数开始取相应的位数
			df.setMaximumIntegerDigits(2);
			System.out.println(df.format(d));
		}
		
		public static void test2(DecimalFormat df) {
			int number = 155566;
			//默认整数部分三个一组，
			System.out.println(number);//输出格式155,566
			//设置每四个一组
			df.setGroupingSize(4);
			System.out.println(df.format(number));//输出格式为15,5566
			DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
			//设置小数点分隔符
			dfs.setDecimalSeparator(';');
			//设置分组分隔符
			dfs.setGroupingSeparator('a');
			df.setDecimalFormatSymbols(dfs);
			System.out.println(df.format(number));//15a5566
			System.out.println(df.format(11.22));//11;22
			//取消分组
			df.setGroupingUsed(false);
			System.out.println(df.format(number));
		}
		
		public static void test3(DecimalFormat df) {
			double a = 1.220;
			double b = 11.22;
			double c = 0.22;
			//占位符可以使用0和#两种，当使用0的时候会严格按照样式来进行匹配，不够的时候会补0，而使用#时会将前后的0进行忽略
			//按百分比进行输出
//			df.applyPattern("00.00%");
			df.applyPattern("##.##%");
			System.out.println(df.format(a));//122%
			System.out.println(df.format(b));//1122%
			System.out.println(df.format(c));//22%
			double d = 1.22222222;
			//按固定格式进行输出
			df.applyPattern("00.000");
			System.out.println(df.format(d));//01.222
			df.applyPattern("##.###");
			System.out.println(df.format(d));//1.222
		}
}
