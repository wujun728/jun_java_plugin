package com.opensource.nredis.proxy.monitor.string.utils;

public class NumberUtil {
	
	/**

	 * 校验为int型

	 * 

	 * @param args

	 * @return true/flase

	 */
	public static boolean isInt(String... args) {
		for (String a : args) {
			try {
				Integer.valueOf(a);
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	/**

	 * 校验非int型

	 * 

	 * @param args

	 * @return true/flase

	 */
	public static boolean isNotInt(String... args) {
		if (!isInt(args)) {
			return true;
		} else {
			return false;
		}
	}

	/**

	 * 转化为int

	 * 

	 * @param value

	 * @return 0

	 */
	public static int toInt(String value) {
		int num = 0;
		try {
			num = Integer.valueOf(value);
		} catch (Exception e) {
			num = 0;
		}
		return num;
	}

	/**

	 * 转化为long

	 * 

	 * @param value

	 * @return

	 */
	public static long toLong(String value) {
		long num = 0;
		try {
			num = Long.valueOf(value);
		} catch (Exception e) {
			num = 0;
		}
		return num;
	}
}
