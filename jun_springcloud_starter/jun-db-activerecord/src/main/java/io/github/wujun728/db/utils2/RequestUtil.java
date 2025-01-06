package io.github.wujun728.db.utils2;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class RequestUtil {

	public static Map<String, Object> convertDataMap(HttpServletRequest request) {
		Map<String, String[]> properties = request.getParameterMap();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Iterator<?> entries = properties.entrySet().iterator();
		Entry<?, ?> entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Entry<?, ?>) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	public static String getValue(HttpServletRequest request, String paramName) {
		if(request == null) {
		    return "";
        }

	    String obj = request.getParameter(paramName);
		if (obj == null) {
			obj = "";
		}
		return obj.trim();
	}

	public static int getIntValue(HttpServletRequest request, String paramName) {
		int returnVal = 0;
		String obj = request.getParameter(paramName);
		if (StrUtil.isNotBlank(obj) && isInteger(obj)) {
			returnVal = Integer.parseInt(obj);
		}
		return returnVal;
	}
	/**
	 * 判断是否为整数
	 * @param str 传入的字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	public static String[] getValues(HttpServletRequest request, String paramName) {
		String[] value;
		value = request.getParameterValues(paramName);
		if (value == null)
			value = new String[] {};
		return value;
	}

	public static String getValuesString(HttpServletRequest request, String paramName) {
		return getValuesString(request, paramName, ",");
	}

	public static String getValuesString(HttpServletRequest request, String paramName,
			String delims) {
		String temp = "";
		String[] values = request.getParameterValues(paramName);
		if (values == null) {
			return "";
		} else {
			for (int i = 0; i < values.length; i++) {
				if (i == values.length - 1) {
					temp += values[i].trim();
				} else {
					temp += values[i].trim() + delims;
				}
			}
		}
		return temp;
	}
}