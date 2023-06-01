package com.springboot.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 编号生成
 * 
 * @author lenovo
 *
 */
public class IdKit {

	/**
	 * getUUID
	 * 
	 * @return String
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

	/**
	 * <pre>
	 * 时间编号
	 * 取时间的 年月日时分秒(yyyyMMddHHmmss)
	 * </pre>
	 * 
	 * @return
	 */
	public static String getDateId(String suffix) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(currentTime) + suffix;
	}

	public static void main(String[] args) {
		System.out.println(getDateId(""));
	}

}
