package com.bing.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shizhongtao
 * 
 * @date 2016-2-17 Description:
 */
public class DataTypeDetect {
	private static final Pattern DATE_PTRN = Pattern
			.compile("^(?:(?:(?:(?:1[6-9]|[2-9]\\d)\\d{2})-(?:0[13578]|1[02])-(?:0[1-9]|[12]\\d|3[01]))"
					+ "|(?:(?:(?:1[6-9]|[2-9]\\d)\\d{2})-(?:0[13456789]|1[012])-(?:0[1-9]|[12]\\d|30))"
					+ "|(?:(?:(?:1[6-9]|[2-9]\\d)\\d{2})-02-(?:0[1-9]|1\\d|2[0-9])))"
					+ "\\s+(?:20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$");
	private static final Pattern DATE_PTRN1 = Pattern
			.compile("^(?:(?:(?:(?:1[6-9]|[2-9]\\d){0,1}\\d{2})[-/\\\\年_](?:0?[13578]|1[02])[-/\\\\月_](0?[1-9]|[12]\\d|3[01]))"
					+ "|(?:(?:(?:1[6-9]|[2-9]\\d){0,1}\\d{2})[-/\\\\年_](?:0?[13456789]|1[012])[-/\\\\月_](0?[1-9]|[12]\\d|30))"
					+ "|(((1[6-9]|[2-9]\\d){0,1}\\d{2})[-/\\\\年_]0?2[-/\\\\月_](?:0?[1-9]|1\\d|2[0-9])))"
					+ "(?:\\s+|[日号]\\s*)(?:20|21|22|23|[0-1]?\\d)[:时点][0-5]?\\d(?:[:分]([0-5]?\\d秒?)|分?){0,1}\\s*$");
	private static final Pattern DATE_PTRN2 = Pattern
			.compile("^(?:(?:(?:(?:1[6-9]|[2-9]\\d){0,1}\\d{2})[-/\\\\年_](?:0?[13578]|1[02])[-/\\\\月_](0?[1-9]|[12]\\d|3[01])[日号]{0,1})"
					+ "|(?:(?:(?:1[6-9]|[2-9]\\d){0,1}\\d{2})[-/\\\\年_](?:0?[13456789]|1[012])[-/\\\\月_](0?[1-9]|[12]\\d|30)[日号]{0,1})"
					+ "|(?:(?:(?:1[6-9]|[2-9]\\d){0,1}\\d{2})[-/\\\\年_]0?2[-/\\\\月_](?:0?[1-9]|1\\d|2[0-9])[日号]{0,1}))"
					+ "(?:\\s*|(?:\\s+0{1,2}:0{1,2}:0{1,2}))$");
	/*private static final Pattern DATE_PTRN3 = Pattern
			.compile("^(?:(?:(?:(?:1[6-9]|[2-9]\\d)\\d{2})(?:0[13578]|1[02])(?:0[1-9]|[12]\\d|3[01]))"
					+ "|(?:(?:(?:1[6-9]|[2-9]\\d)\\d{2})(?:0[13456789]|1[012])(?:0[1-9]|[12]\\d|30))"
					+ "|(?:(?:(?:1[6-9]|[2-9]\\d)\\d{2})02(?:0[1-9]|1\\d|2[0-9])))"
					+ "$");*/
	private static final Pattern NUMBER_PTRN = Pattern
			.compile("^(?:0|[1-9]\\d*)(?:\\.\\d*)?$");
	private static final Pattern INTEGER_PTRN = Pattern
			.compile("^(?:0|[1-9]\\d*)(?:\\.0*)?$");
	private static final Pattern BOOLEAN_PTRN = Pattern.compile("^[01是否真假]$");

	/**
	 * @author shizhongtao
	 * @param arg
	 * @return
	 */
	public static boolean isYMDT(String arg) {
		Matcher m = DATE_PTRN1.matcher(arg);
		return m.matches();
	}

	/**
	 * 如果所给字符串中 时分秒都是0，也返回true
	 * 
	 * @author shizhongtao
	 * @return
	 */
	public static boolean isYMD(String arg) {
		boolean re = false;
		/*Matcher m3 = DATE_PTRN3.matcher(arg);
		if (m3.matches()) {
			re = true;
		}*/
		if (!re) {
			Matcher m = DATE_PTRN2.matcher(arg);
			re=m.matches();
		}
		return re;
	}

	/**
	 * 判读参数是不是日期类型<br>
	 * 支持的格式为 （年月日时分秒）顺序的字符串。<br>
	 * 例如：1900年1月12日,1900-1-12 12:12:10等
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isDateType(String arg) {
		boolean result = false;
		Matcher m = DATE_PTRN.matcher(arg);
		result = m.matches();
		if (!result) {
			result = isYMDT(arg);
		}
		if (!result) {
			result = isYMD(arg);
		}
		return result;
	}

	public static boolean isNumType(String arg) {
		Matcher m = NUMBER_PTRN.matcher(arg);
		return m.matches() ;
	}

	/**
	 * 只要可以无损转换为整形的都认为是int类型，没有考虑int范围例如：12.0000
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isIntegerType(String arg) {
		Matcher m = INTEGER_PTRN.matcher(arg);
		if(!m.matches()){
			return false;
		}
		String str = arg.replaceFirst("\\.\\d*", "");
		
		
		return ( str.length()<=String.valueOf(Integer.MAX_VALUE).length()&& ("2147483647".compareTo(str)>0));

	}

	/**
	 * @param arg
	 * @return
	 */
	public static boolean isBooleanType(String arg) {
		Matcher m = BOOLEAN_PTRN.matcher(arg);
		return m.matches();

	}
}
