package org.myframework.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 提供不同的结构类型之间的数据转换成JSON (JSON,POJO,HttpServletRequest ) -- >MAP
 * 
 * @author Administrator
 * 
 */
public class MapUtil {
	private static Log log = LogFactory.getLog(MapUtil.class);

	/**
	 * @param request
	 * @param emptyToNull
	 * @return
	 */
	public static Map<String, Object> getMapFromRequest(
			HttpServletRequest request, boolean emptyToNull) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Enumeration elements = request.getParameterNames(); elements
				.hasMoreElements();) {
			String key = (String) elements.nextElement();
			String value = request.getParameter(key);
			// 将空字符串转化为NULL
			if (emptyToNull)
				value = "".equals(value) ? null : value;
			map.put(key, value);
		}
		log.debug(map);
		return map;
	}

	/**
	 * @param request
	 * @return
	 */
	public static Map getMapFromRequest(HttpServletRequest request) {
		return getMapFromRequest(request, true);
	}

	/**
	 * 将JSON对象转换为Map
	 * 
	 * @param jo
	 * @return
	 */
	public static Map<String, Object> getMapFromJSONObject(JSONObject jo) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator<String> i = jo.keys(); i.hasNext();) {
			String key = (String) i.next();
			Object value = jo.get(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 将JSON字符串转换为Map
	 * 
	 * @param jo
	 * @return
	 */
	public static Map<String, Object> getMapFromJSONString(String joString) {
		JSONObject jo = JSONObject.fromObject(joString);
		return getMapFromJSONObject(jo);
	}

	/**
	 * 将POJO转换为Map
	 * 
	 * @param jo
	 * @return
	 */
	public static Map<String, Object> getMapFromPojo(Object pojo) {
		JSONObject jo = JSONObject.fromObject(pojo);
		return getMapFromJSONObject(jo);
	}

}
