package com.kvn.poi.exp.function;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * <pre>
 * 内部函数库，使用EL表达式在excel模板中调用。
 * 示例：
 *  #{ T(com.kvn.poi.function.InternalUtils).fmtDate(beginTime,'yyyy-MM-dd') }
 * </pre>
 * 
 * @author wzy
 * @date 2017年7月6日 下午5:47:03
 */
public class InternalUtils {

	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String fmtDate(Date date, String pattern) {
		DateTime dt = new DateTime(date.getTime());
		return dt.toString(pattern);
	}

}
