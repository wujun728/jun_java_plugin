package com.jun.plugin.util2.core;

import java.text.NumberFormat;

/**
 * 常用数字工具类
 * @author bigtiger
 * @email  bigtiger02@gmail.com
 * @date   2013-8-17
 */
public class NumberUtil {
	
	/**
	 * 得到double数值
	 * 传入值为空或发生异常，返回0
	 * @param value
	 * @return
	 */
	
	public static double getDouble(Object value){
		double tmp = 0;
		if(value == null){
			return tmp;
		}
		try {
			tmp = Double.parseDouble(value.toString());
		} catch (NumberFormatException e) {
		}
		return tmp;
	}
	
	/**
	 * 数字格式化
	 * @param value        需要格式化的值
	 * @param maxFragDigit 最大小数位数
	 * @param minFragDigit 最小小树位数
	 * @return
	 */
	public static String doubleFormat(double value,int maxFragDigit, int minFragDigit){
		return doubleFormat(value, maxFragDigit, minFragDigit, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	
	/**
	 * 数字格式化
	 * @param value
	 * @param maxFragDigit
	 * @param minFragDigit
	 * @param intMaxDigit
	 * @param intMinDigit
	 * @return
	 */
	public static String doubleFormat(double value,int maxFragDigit, int minFragDigit,int intMaxDigit,int intMinDigit){
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(maxFragDigit);
		nf.setMinimumFractionDigits(minFragDigit);
		nf.setMaximumIntegerDigits(intMaxDigit);
		nf.setMinimumIntegerDigits(intMinDigit);
		return nf.format(value);
	}
}
