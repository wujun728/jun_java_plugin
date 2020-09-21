package com.opensource.nredis.proxy.monitor.array.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 数组工具类
 * @author liubing
 *
 */
public class ArrayUtil {
	
	/**

	 * 判断数组是否包含 元素t

	 * 

	 * @param array

	 *            数组

	 * @param element

	 *            元素

	 * @return 是否包含（true or false）

	 * @throws Exception

	 */
	public static <T> boolean isContains(T[] array, T element) throws Exception {
		if (array == null || element == null) {
			return false;
		}
		if (!array.getClass().getComponentType().equals(element.getClass())) {
			throw new IllegalArgumentException("数组与元素类型不符。");
		}
		List<T> list = Arrays.asList(array);
		return list.contains(element);
	}

	/**

	 * 数组转化为一个字符串

	 * 

	 * @param array 字符串数组

	 * @param linkStr

	 *            连接字符

	 * @return 通过连接字符串连接后 的字符串

	 */
	public static String join(Object[] array, String linkStr) {
		if (array != null && array.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (Object obj : array) {
				if (sb.length() > 0) {
					sb.append(linkStr);
				}
				sb.append(obj);
			}
			return sb.toString();
		}
		return "";
	}
}
