package com.jun.admin.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

public class DataValidate {

	
	/**
	 * 处理object以下属性中,存在null值
	 * @param obj
	 * @return
	 */
	public static Object parseNull(Object obj) {
		try {
			PropertyUtilsBean b = new PropertyUtilsBean();
				Map map = b.describe(obj);
				Iterator it = map.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next().toString();
					if(map.get(key) == null){
						if(b.getPropertyType(obj, key) == String.class)
							b.setProperty(obj, key.toString(), "");
						else if(b.getPropertyType(obj, key) == Integer.class || b.getPropertyType(obj, key) == int.class)
							b.setProperty(obj, key.toString(), 0);
						else if(b.getPropertyType(obj, key) == Long.class || b.getPropertyType(obj, key) == long.class)
							b.setProperty(obj, key.toString(), 0l);
						else if(b.getPropertyType(obj, key) == Double.class || b.getPropertyType(obj, key) == double.class)
							b.setProperty(obj, key.toString(), 0.00);
						else 
							b.setProperty(obj, key.toString(), "");
					}
				}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
			
	/**
	 * 验证数据是否为空
	 * @param str
	 * @return true 不为空 false 为空
	 */
	public static boolean IsNull(String str) {
		if(str != null && str.trim().length() > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 验证数据长度
	 * @param str
	 * @param statlength 字符串的最小长度
	 * @param endlength 字符串的最大长度
	 * @return true 数据长度合法 false 数据长度不合法
	 */
	public static boolean IsLength(String str,int maxlength) {
		if(IsNull(str)){
			if(str.trim().length() <= maxlength)
				return true;
			else
				return false;
		}
		return false;
	}
	
	/**
	 * 验证数据长度
	 * @param str
	 * @param statlength 字符串的最小长度
	 * @param endlength 字符串的最大长度
	 * @return true 数据长度合法 false 数据长度不合法
	 */
	public static boolean IsLength(String str,int minlength,int maxlength) {
		if(IsNull(str)){
			if(str.trim().length() >= minlength && str.trim().length() <= maxlength)
				return true;
			else
				return false;
		}
		return false;
	}
}
