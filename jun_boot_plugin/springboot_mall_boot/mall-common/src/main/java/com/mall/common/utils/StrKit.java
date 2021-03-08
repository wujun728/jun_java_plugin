package com.mall.common.utils;


/**
 * 字符串处理工具类
 * @author Wujun
 * @version 1.0
 * @create_at 2017年6月18日 下午10:02:37
 */
public class StrKit {

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return str == null || str.trim().length() == 0 ? true : false;
	}

	public static boolean isbank(String str){
		return str == null ? true : false;
	}

	public static boolean isEmpty(String[] properties) {
		return properties == null || properties.length == 0 ? true : false;
	}

	public static boolean isNotbank(String str){
		return str != null && str.length() > 0 ? true : false;
	}

	public static boolean isEmpty(Object object){
		return object == null || object.toString().trim().length() == 0 ? true : false;
	}

	public static boolean isNotEmpty(String str){
		return str != null && str.trim().length() > 0 ? true : false;
	}

	public static boolean isNotEmpty(Object object){
		return object == null ? false : true;
	}

	/**
	 * 将字符串转换成数组
	 * @param str
	 * @return
	 */
	public static String[] spilt(String str){
		if(str == null)
			throw new NullPointerException("str can not be null");
		return str.split(",");
	}

	public static String[] spilt(String str,String separator){
		if(str == null)
			throw new NullPointerException("str can not be null");
		return str.split(separator);
	}

	/**
	 * 将首字母变成小写
	 * @param keyName
	 * @return
	 */
	public static String toLowerCaseFirst(String keyName){
		return keyName.substring(0, 1).toLowerCase();
	}

	/**
	 * 将首字母变成大写
	 * @param keyName
	 * @return
	 */
	public static String totoUpperCaseFirst(String keyName){
		return keyName.substring(0, 1).toUpperCase() + keyName.substring(1, keyName.length());
	}
}
