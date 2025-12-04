/**
 * @filename:Const 2018年06月01日
 * @project OnlineGame    边鹏  V1.0
 * Copyright(c) 2018 BianP Co. Ltd. 
 * All right reserved. 
 */
package com.item.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据接收与传递专用工具类
 * @create Jun 1, 2018 12:16:49 PM
 * @update Jun 6, 2018 3:07:09 PM
 * @author 边鹏
 * @version v1.1.0
 * @instructions
 * 		WEB参数接收：Record record=new Record(httpServletRequest);
 * 		MYBATIS:resultType="Record"
 */
@SuppressWarnings("rawtypes")
public class Record extends HashMap implements Map {

	private static final long serialVersionUID = 1L;

	Map map = null;
	HttpServletRequest request;

	@SuppressWarnings("unchecked")
	public Record(HttpServletRequest request) {
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
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
		map = returnMap;
	}

	public Record() {
		map = new HashMap();
	}

	@Override
	public Object get(Object key) {
		Object obj = null;
		if (map.get(key) instanceof Object[]) {
			Object[] arr = (Object[]) map.get(key);
			obj = request == null ? arr : (request.getParameter((String) key) == null ? arr : arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}

	public String getString(String key) {
		try {
			String result= get(key).toString();
			if (!"".equals(result)&&null!=result) {
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public Integer getInteger(Object key) {
		try {
			return Integer.parseInt(get(key).toString());
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public Long getLong(Object key) {
		try {
			return Long.parseLong(get(key).toString());
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return map.containsValue(value);
	}

	public Set entrySet() {
		// TODO Auto-generated method stub
		return map.entrySet();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	public Set keySet() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map t) {
		// TODO Auto-generated method stub
		map.putAll(t);
	}

	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	public Collection values() {
		// TODO Auto-generated method stub
		return map.values();
	}

}
