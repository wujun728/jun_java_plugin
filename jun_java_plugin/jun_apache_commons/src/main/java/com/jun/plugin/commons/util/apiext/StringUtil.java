package com.jun.plugin.commons.util.apiext;

import java.math.BigDecimal;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.jun.plugin.commons.util.callback.ValueEncoder;

/**
 * @ClassName: StringUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wujun
 * @date 2010-10-29 下午01:36:36
 *
 */
public abstract class StringUtil {
	/**
	 * 把为空的字符按指定字符返回，<br>
	 * 如果inputStr[0]为null或"" 则取inputStr[1]值<br>
	 *
	 * @param inputStr
	 *            输入要转换的数组
	 * @return String
	 * */
	public static String hasNull(String... inputStr) {
		if (inputStr == null)
			return "";
		String returnStr;
		switch (inputStr.length) {
		case 0:
			returnStr = "";
			break;
		case 1:
			returnStr = trimSpace(inputStr[0]);
			break;
		default:
			if (inputStr[0] == null || inputStr[0].trim().length() <= 0)
				returnStr = inputStr[1];
			else
				returnStr = inputStr[0].trim();
			break;
		}
		return returnStr;
	}

	/**
	 * 判断字符串是否为空
	 *
	 * @param inputStr
	 *            输入要转换的数组,如果inputStr[0]为null或"" 则取inputStr[1]值
	 * @return String
	 * */
	public static boolean isNull(String input) {
		if (input == null || "null".equals(input) || input.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否不为空
	 *
	 * @param inputStr
	 *            输入要转换的数组,如果inputStr[0]为null或"" 则取inputStr[1]值
	 * @return String
	 * */
	public static boolean isNotNull(String input) {
		return !isNull(input);
	}

	/**
	 * 去掉字符串前后的空格(半角,全角空格)
	 *
	 * @param str
	 *            要处理的字符串
	 * @return String
	 */
	public static String trimSpace(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		}

		int len = str.length();
		int first = 0;
		int last = str.length() - 1;
		char c;

		for (c = str.charAt(first); (first < len)
				&& (c == '\u3000' || c == ' '); first++) {
			c = str.charAt(first);
		}
		if (first > 0) {
			first--;
		}
		if (len > 0) {
			c = str.charAt(last);
			while ((last > 0) && (c == '\u3000' || c == ' ')) {
				last--;
				c = str.charAt(last);
			}
			last = last + 1;
		}
		if (first >= last) {
			return "";
		}
		return ((first > 0) || (last < len)) ? str.substring(first, last) : str;
	}

	/**
	 * 获取字符串中含数字和字母的个数<br>
	 */
	public static int sumOfNumLet(String src) {
		String figures = "0123456789";
		String letters = "abcdefghijklmnopqrstuvwxyz";
		int sum = 0;
		for (int i = 0; (src != null) && (i < src.length()); i++) {
			char ch = src.charAt(i);
			if ((figures.indexOf(ch) != -1) || (letters.indexOf(ch) != -1))
				sum++;
		}
		return sum;
	}

	/**
	 * tapestry输出变量 时要填此格式化对象
	 * */
	public final static Format formatCommon = new Format() {
		private static final long serialVersionUID = -8271124584977967310L;

		@Override
		public Object parseObject(String source, ParsePosition pos) {
			return source;
		}

		@Override
		public StringBuffer format(Object obj, StringBuffer toAppendTo,
				FieldPosition pos) {
			return toAppendTo.append(obj);
		}
	};

	public static String combo(String[] paramArrayOfString, String paramString) {
		String str1 = paramString;
		if ((paramArrayOfString == null) || (paramArrayOfString.length < 1))
			return "";
		if ((paramString == null) || (paramString.trim().equals("")))
			str1 = ",";
		String str2 = trimSpace(paramArrayOfString[0]);
		int i = paramArrayOfString.length;
		for (int j = 1; j < i; ++j)
			str2 = new StringBuilder().append(str2).append(str1)
					.append(trimSpace(paramArrayOfString[j])).toString();
		return str2;
	}

	/**
	 * 首字母转成大写
	 *
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (isNull(s)) {
			return "";
		} else if (Character.isUpperCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
		}
	}

	/***
	 * String转为基本数据类型
	 *
	 * @param type
	 * @param v
	 * @param handler
	 * @return
	 */
	public static final <T> T str2Object(Class<T> type, String v,
			ValueEncoder<T> handler) {
		Object param = null;
		if (handler != null)
			return handler.toValue(v);

		if (type != String.class
				&& org.apache.commons.lang3.StringUtils.isEmpty(v)) {
			return null;
		}
		if (type == String.class)
			param = v;
		else if (type == int.class || type == Integer.class)
			param = Integer.parseInt(v);
		else if (type == long.class || type == Long.class)
			param = Long.parseLong(v);
		else if (type == byte.class || type == Byte.class)
			param = Byte.parseByte(v);
		else if (type == char.class || type == Character.class)
			param = v.charAt(0);
		else if (type == float.class || type == Float.class)
			param = Float.parseFloat(v);
		else if (type == double.class || type == Double.class)
			param = Double.parseDouble(v);
		else if (type == short.class || type == Short.class)
			param = Short.parseShort(v);
		else if (type == boolean.class || type == Boolean.class)
			param = Boolean.parseBoolean(v);
		else if (Date.class.isAssignableFrom(type))
			param = DateUtil.objToDate(v);
		else if (Enum.class.isAssignableFrom(type)) {
			try {
				param = type.getField(v).get(null);
			} catch (Exception e) {
			}
		} else if (type == BigDecimal.class) {
			try {
				param = new BigDecimal(v);
			} catch (Exception e) {
			}
		} else
			throw new IllegalArgumentException(String.format(
					"object type '%s' not valid", type));
		return (T) param;
	}

	public static final <T> T str2Object(Class<T> type, String v) {
		return str2Object(type, v, null);
	}
}