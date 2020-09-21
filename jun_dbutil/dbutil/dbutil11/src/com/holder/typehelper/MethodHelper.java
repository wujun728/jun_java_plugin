package com.holder.typehelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodHelper {
	public static Object invoke(Method method, Object instance, Object[] params) {
		try {
			return method.invoke(instance, params);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
