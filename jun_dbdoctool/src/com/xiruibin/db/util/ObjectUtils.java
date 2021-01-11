package com.xiruibin.db.util;

import java.util.Map;

/**
 * 对象公共工具集
 */
public final class ObjectUtils {
	/**
	 * 判断一个对象是否为null
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNull(Object o) {
		return o == null;
	}

	/**
	 * 判断map是否为空
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmptyMap(Map<?, ?> map) {
		return isNull(map) || map.size() == 0;
	}
}
