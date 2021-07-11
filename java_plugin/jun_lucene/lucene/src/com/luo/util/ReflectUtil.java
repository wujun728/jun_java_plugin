package com.luo.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectUtil {

	@SuppressWarnings("rawtypes")
	public static Class getGenericParmeterType(Class clazz) {
		Type type = clazz.getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		Type[] types = pt.getActualTypeArguments();
		return (Class<?>) types[0];
	}
}
