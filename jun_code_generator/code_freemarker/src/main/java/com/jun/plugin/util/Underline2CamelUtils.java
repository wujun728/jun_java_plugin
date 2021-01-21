package com.jun.plugin.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 下划线和驼峰命名方法之间的转换
 *
 * @author Wujun
 */
public class Underline2CamelUtils {

	/**
	 * 字段名
	 *
	 * @param column
	 * @return
	 */
//	private static String underline2Camel2(String column) {
//		if (StringUtils.isBlank(column)) {
//			return "";
//		}
//		StringBuilder sb = new StringBuilder(column.length());
//		// 当前的下标
//		int idx = 0;
//		int length = column.length();
//		for (int index = 0; index < length; index++) {
//			if (column.charAt(index) == underlineChar) {
//				// 判断后面是否还有_
//				while (column.charAt(++index) == underlineChar) {
//				}
//				idx = index;// i所对应的字符需要转换为大写字符
//				char c = column.charAt(idx);
//				if (sb.length() == 0) {
//
//				} else {
//					for (int index2 = 0; index2 < a2z.length; index2++) {
//						if (a2z[index2] == c) {
//							c = A2Z[index2];
//							break;
//						}
//					}
//				}
//				sb.append(c);
//			} else {
//				sb.append(column.charAt(index));
//			}
//		}
//		return sb.toString();
//	}

	/**
	 * @param columnName
	 * @return
	 */
	public static String underline2Camel(String columnName) {

		// 快速检查
		if (StringUtils.isBlank(columnName)) {
			// 没必要转换
			return "";
		} else if (StringUtils.containsNone(columnName, underlineStr)) {
			// 不含下划线，仅将首字母小写
			return StringUtils.uncapitalize(columnName);
		} else {
			// 用下划线将原始字符串分割
			String[] columns = StringUtils.split(columnName, underlineStr);
			StringBuilder result = new StringBuilder();
			for (String columnSplit : columns) {
				// 跳过原始字符串中开头、结尾的下换线或双重下划线
				if (StringUtils.isBlank(columnSplit)) {
					continue;
				}
				// 处理真正的驼峰片段
				if (result.length() == 0) {
					// 第一个驼峰片段，全部字母都小写
					result.append(StringUtils.uncapitalize(columnSplit));
				} else {
					// 其他的驼峰片段，首字母大写
					result.append(StringUtils.capitalize(columnSplit));
				}
			}
			return result.toString();
		}
	}

	/**
	 * 驼峰转换下划线
	 *
	 * @param property
	 * @return
	 */
//	private static String camel2Underline(String property) {
//		if (property == null || property.isEmpty()) {
//			return "";
//		}
//		StringBuilder column = new StringBuilder();
//		column.append(property.substring(0, 1).toLowerCase());
//		for (int index = 1; index < property.length(); index++) {
//			String s = property.substring(index, index + 1);
//			// 在小写字母前添加下划线
//			if (!Character.isDigit(s.charAt(0)) && s.equals(s.toUpperCase())) {
//				column.append(underlineStr);
//			}
//			// 其他字符直接转成小写
//			column.append(s.toLowerCase());
//		}
//
//		return column.toString();
//	}

//	static char underlineChar = '_';
	private static final String underlineStr = "_";
//	private static char[] a2z = "abcdefghijklmnopqrstwvuxyz".toCharArray();
//	private static char[] A2Z = "abcdefghijklmnopqrstwvuxyz".toUpperCase().toCharArray();
}
