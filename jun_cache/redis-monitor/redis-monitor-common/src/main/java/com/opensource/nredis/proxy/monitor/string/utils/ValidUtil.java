package com.opensource.nredis.proxy.monitor.string.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUtil {
	
	/**
	 * 判断是否为手机号
	 * 
	 * @param str
	 * @return true/false
	 */
	public static boolean isMobile(String str) {
		Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 判断是否为邮箱地址
	 * @param str
	 * @return true/false
	 */
	public static boolean isEmail(String str) {
		Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

}
