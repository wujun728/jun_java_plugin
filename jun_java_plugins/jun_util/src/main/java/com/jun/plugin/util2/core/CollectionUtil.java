package com.jun.plugin.util2.core;

import java.util.Collection;

/**
 * 
 * @author 罗季晖
 * @email  bigtiger02@gmail.com
 * @date   2013-8-21
 */
public class CollectionUtil {

	/**
	 * 判断一个集合是否为空
	 * @param c
	 * @return 为空返回true
	 */
	public static <T> boolean isEmpty(Collection<T> c){
		return c == null || c.size() == 0;
	}
	public static <T> boolean isEmpty(T[] c){
		return c == null || c.length == 0;
	}
	public static boolean isEmpty(boolean[] c){
		return c == null || c.length == 0;
	}
	public static boolean isEmpty(int[] c){
		return c == null || c.length == 0;
	}
	public static boolean isEmpty(short[] c){
		return c == null || c.length == 0;
	}
	public static boolean isEmpty(long[] c){
		return c == null || c.length == 0;
	}
	public static boolean isEmpty(float[] c){
		return c == null || c.length == 0;
	}
	public static boolean isEmpty(double[] c){
		return c == null || c.length == 0;
	}
	public static boolean isEmpty(char[] c){
		return c == null || c.length == 0;
	}
	public static boolean isEmpty(byte[] c){
		return c == null || c.length == 0;
	}
	
	/**
	 * 判断一个集合不为空
	 * @param c
	 * @return 不为空返回true
	 */
	public static <T> boolean isNotEmpty(Collection<T> c){
		return c != null && c.size() > 0;
	}
	public static <T> boolean isNotEmpty(T[] c){
		return c != null && c.length > 0;
	}
	public static boolean isNotEmpty(boolean[] c){
		return c != null && c.length > 0;
	}
	public static boolean isNotEmpty(int[] c){
		return c != null && c.length > 0;
	}
	public static boolean isNotEmpty(short[] c){
		return c != null && c.length > 0;
	}
	public static boolean isNotEmpty(long[] c){
		return c != null && c.length > 0;
	}
	public static boolean isNotEmpty(float[] c){
		return c != null && c.length > 0;
	}
	public static boolean isNotEmpty(double[] c){
		return c != null && c.length > 0;
	}
	public static boolean isNotEmpty(char[] c){
		return c != null && c.length > 0;
	}
	public static boolean isNotEmpty(byte[] c){
		return c != null && c.length > 0;
	}
}
