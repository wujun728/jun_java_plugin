package org.myframework.util;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

public abstract class FieldUtil {
	
	public static boolean hasField(Object target, String fieldName, Class<?> type) {
		return ReflectionUtils.findField(target.getClass(), fieldName, type) == null;
	}

	public static Object getFieldValue(Object target, String fieldName) {
		Field field = ReflectionUtils.findField(target.getClass(), fieldName);
		ReflectionUtils.makeAccessible(field);
		return ReflectionUtils.getField(field, target);
	}
	
	public static void setFieldValue(Object target, String fieldName, Object value) {
		Field field = ReflectionUtils.findField(target.getClass(), fieldName);
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, target, value);
	}
}
