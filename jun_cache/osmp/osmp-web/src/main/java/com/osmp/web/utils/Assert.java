package com.osmp.web.utils;

/**
 * 包含一些判断方法 
 * @author sunjian  
 * @version 1.0 2013-2-26
 */
public abstract class Assert {
	/**
	 * 判断字符串是否为
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return str==null||"".equals(str);
	}
	
	/**
	 * 判断多个字符串是否为空,只有全不为空的情况才返回false否则返回true
	 * @param strings
	 * @return
	 */
	public static boolean isEmpty(String... strings){
		for(String str:strings){
			if(str==null||"".equals(str)) return true;
		}
		
		return false;
	}
	
}
