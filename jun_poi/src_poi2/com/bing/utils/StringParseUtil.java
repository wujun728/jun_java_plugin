package com.bing.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;

import com.google.common.base.Strings;

/**
 * @author shizhongtao
 *
 * @date 2016-3-21
 * Description: MayBe is's useful 
 */
public class StringParseUtil {
	

	/**
	 * Parses the string argument as a boolean. The {@code boolean} returned
	 * represents the value {@code true} if the string argument is not
	 * {@code null} and is equal, ignoring case, to the string {@code "true"},
	 * {@code "yes"},{@code "是"},{@code "a"} etc.
	 * <p>
	 * Example: {@code Boolean.parseBoolean("True")} returns {@code true}.<br>
	 * Example: {@code Boolean.parseBoolean("N")} returns {@code false}.
	 * Example: {@code Boolean.parseBoolean("No")} returns {@code false}.
	 * 
	 * @param s
	 *            the {@code String} containing the boolean representation to be
	 *            parsed
	 * @return the boolean represented by the string argument
	 */
	public static boolean parseBoolean(String s) {

		return ((!Strings.isNullOrEmpty(s) )&& toBoolean(s));
	}

	private static boolean toBoolean(String name) {
		if (name.equalsIgnoreCase("false") || name.equalsIgnoreCase("no")) {
			return false;
		}

		if (ArrayUtils.contains(new String[] { "否", "假", "N", "n", "0" }, name)) {
			return false;
		}

		return true;
	}

	static final Pattern FLOATING_POINT_PATTERN = fpPattern();
	static final Pattern FLOATING_POINT_PATTERN1 = Pattern
			.compile("0[xX](?:\\p{XDigit}++(?:\\.\\p{XDigit}*+)?|\\.\\p{XDigit}++)");
	static final Pattern CURRENCY_POINT_PATTERN = Pattern
			.compile("(?<=^[$￥])(?:\\d++(?:\\.\\d*+)?|\\.\\d++)$");
	static final Pattern FRACTION_POINT_PATTERN = Pattern
			.compile("^-?(?:0|[1-9]\\d*)/-?[1-9]\\d*$");
	static final Pattern PERCENT_POINT_PATTERN = Pattern
			.compile("^(?:[1-9]\\d*(?:\\.\\d*+)?|0?\\.\\d++)(?=%$)");

	private static Pattern fpPattern() {
		String decimal = "(?:\\d++(?:\\.\\d*+)?|\\.\\d++)";
		String completeDec = decimal + "(?:[eE][+-]?\\d++)?[fFdD]?";
		String hex = "(?:\\p{XDigit}++(?:\\.\\p{XDigit}*+)?|\\.\\p{XDigit}++)";
		String completeHex = "0[xX]" + hex + "[pP][+-]?\\d++[fFdD]?";
		String fpPattern = "[+-]?(?:NaN|Infinity|" + completeDec + "|"
				+ completeHex + ")";
		return Pattern.compile(fpPattern);
	}

	/**
	 * Parses the specified string as a double-precision floating point value.
	 * The ASCII character {@code '-'} (<code>'&#92;u002D'</code>) is recognized
	 * as the minus sign.
	 * 
	 * @param string
	 *            the string representation of a {@code double} value
	 * @return the floating point value represented by {@code string}, or
	 *         {@code null} if {@code string} has a length of zero or cannot be
	 *         parsed as a {@code double} value
	 * @throws ParseException 
	 */
	public static Double parseDouble(String string) throws ParseException {
		if (StringUtils.isBlank(string)) {
			return null;
		}
		if (FLOATING_POINT_PATTERN.matcher(string).matches()) {
			// TODO(user): could be potentially optimized, but only with
			// extensive testing
			
				return Double.parseDouble(string);
			
		}
		// currency formater
		Matcher matcher = CURRENCY_POINT_PATTERN.matcher(string);
		if (matcher.find()) {
			
				return Double.parseDouble(matcher.group());
			
		}
		// percent formater
		 matcher = PERCENT_POINT_PATTERN.matcher(string);
		if (matcher.find()) {
			
				char[] charArray = matcher.group().toCharArray();
				int pIndex=charArray.length;
				StringBuilder sb=new StringBuilder();
				for (int i=0;i<charArray.length;i++) {
					char temp=charArray[i];
					if(temp=='.'){
						pIndex=i;
					}else{
						sb.append(temp);
					}
				}
				pIndex-=2;
				while(pIndex<0){
					sb.insert(0,'0');
					pIndex++;
				}
				sb.insert(pIndex,'.');
				 return parseDouble(sb.toString());
			
		}
		// fraction formater
		if (FRACTION_POINT_PATTERN.matcher(string).matches()) {
			
				return parseFraction2Double(string);
			
		}

		if (DataTypeDetect.isDateType(string)) {
		
				Date date = convertYMDT2Date(string);
				return convertToDaouble(date);
			
		}

		boolean b = FLOATING_POINT_PATTERN1.matcher(string).matches();
		if (b) {
			string += "p0";
				return Double.parseDouble(string);
		}
		return null;
	}

	/**
	 * Parses the string argument as a signed decimal integer.
	 * 
	 * @param s
	 *            a {@code String} containing the {@code int} representation to
	 *            be parsed
	 * @return Long or null if can't to be parsed;
	 * @throws ParseException 
	 */
	public static Long parseInteger(String s) throws ParseException {

		return parseInteger(s, 10);
	}

	/**
	 * Parses the string argument as a signed {@code Date}.
	 * 
	 * @param s
	 *            a {@code String} containing the {@code int} representation to
	 *            be parsed
	 * @return  Date or null if can't be parsed
	 * @throws ParseException 
	 */
	public static Date parseDate(String s) throws ParseException {
		if (DataTypeDetect.isDateType(s)) {
				Date date = convertYMDT2Date(s);
				return date;
		}
		if(DataTypeDetect.isNumType(s)){
			Double d = parseDouble(s);
				Date date=convertToDate(d);
				return date;
		}
		return null;
	}

	/**
	 * 必须保证arg为日期类型，可用{@link DataTypeDetect#parseDouble(String)}
	 * 
	 * @param arg
	 * @return
	 * @throws ParseException
	 */
	public static Date convertYMDT2Date(String arg) throws ParseException {
		String temp;
		temp = arg.replaceAll("[/\\\\年月_.]", "-");
		temp = temp.replaceAll("[日号]", " ");
		temp = temp.replaceAll("[点时分秒]", ":");

		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		try {
			Date date = format.parse(temp);
			return date;
		} catch (ParseException e) {
			try {
				format.applyPattern("yy-MM-dd HH:mm");
				Date date = format.parse(temp);
				return date;
			} catch (ParseException e1) {
				try {
					format.applyPattern("yy-MM-dd HH");
					Date date = format.parse(temp);
					return date;
				} catch (ParseException e2) {
					format = new SimpleDateFormat("yy-MM-dd");
					Date date = format.parse(temp);
					return date;
				}
			}
		}

	}

	public static Long parseInteger(String string, int radix) throws ParseException {
		if (Strings.isNullOrEmpty(string)) {
			return null;
		}
		if (radix == 10) {
			Double d = parseDouble(string);
			d.longValue();
		} else {
			// TODO the others neet to do
		}
		return null;
	}

	/**
	 * 只能用于excel数据类型
	 * 
	 * @author shizhongtao
	 * @param date
	 * @return
	 */
	static double convertToDaouble(Date date) {
		return DateUtil.getExcelDate(date);
	}

	static Double parseFraction2Double(String string) {
		char[] array = string.toCharArray();
		int numerator = 0;
		int denominator = 0;
		StringBuilder bd = new StringBuilder();
		boolean limit = true;
		try {
			for (char c : array) {
				if (c != '/') {
					bd.append(c);

				} else {
					if (limit) {
						numerator = Integer.valueOf(bd.toString());
					}
					bd.setLength(0);
					limit = false;
				}
			}
			denominator = Integer.valueOf(bd.toString());
			if(numerator==0){
				return 0d;
			}
			if (denominator != 0) {
				return numerator / (double) denominator;
			}
		} catch (NumberFormatException e) {

		}
		return null;
	}

	/**
	 * @author shizhongtao
	 * @param date
	 * @return
	 */
	static long convertToLong(Date date) {
		return ((Double) convertToDaouble(date)).longValue();
	}

	/**
	 * @author shizhongtao
	 * @param d
	 * @return
	 */
	static Date convertToDate(double d) {
		return DateUtil.getJavaDate(d);
	}

	/**
	 * @author shizhongtao
	 * @param l
	 * @return
	 */
	static Date convertToDate(long l) {
		return convertToDate((double) l);
	}
}
