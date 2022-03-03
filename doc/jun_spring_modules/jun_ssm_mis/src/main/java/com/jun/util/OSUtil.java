package com.jun.util;

/**
 * @author Wujun
 * @createTime 2011-9-15 上午01:29:11
 */
public class OSUtil {
	private static final String WINDOWS = "WINDOWS";

	/**
	 * 判断当前操作系统的类型
	 * 
	 * @return false means window system ,true means unix like system
	 */
	public static boolean isUnixLikeSystem() {
		String name = System.getProperty("os.name");
		if (name != null) {
			if (name.toUpperCase().indexOf(WINDOWS) == -1) { // it means it's unix like system
				return true;
			}
		}
		return false;
	}

}
