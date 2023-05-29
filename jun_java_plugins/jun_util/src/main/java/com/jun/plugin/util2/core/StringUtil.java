package com.jun.plugin.util2.core;

import java.util.regex.Pattern;

/**
 * 一些常用的string转换工具
 * @author 罗季晖
 * @email  bigtiger02@gmail.com
 * @date   2013年8月21日
 */
public class StringUtil {

	//邮件正则表达式
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	
	/**
	 * 判断是不是一个合法的电子邮件地址
	 * @param email
	 * @return 是合法地址返回true
	 */
	public static boolean isEmail(String email){
		if(isBlank(email)) 
			return false;
	    return emailer.matcher(email).matches();
	}
	
	/**
	 * 判断是否为空   对于空串判断为true
	 * @param str
	 * @return 为空则返回true
	 */
	public static boolean isEmpty(String str){
		return str == null;
	}
	/**
	 * 判断字符串是否有数值 
	 * @param str
	 * @return 若没有则返回true
	 */
	public static boolean isBlank(String str){
		return str == null || str.trim().length() == 0;
	}
	
	/**
	 * 对象字符串安全类型转换
	 * @param obj  需要转换的对象
	 * @return     返回对象的字符串
	 */
	public static String toSafeString(Object obj){
		if(obj == null){
			return "";
		}
		return obj.toString();
	}
}
