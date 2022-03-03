package com.jun.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.util.Assert;

/**
 * @author Wujun
 * @createTime 2011-8-25 上午01:46:13
 * 
 * 访问在当前类声明的private/protected成员变量及private/protected函数的BeanUtil. 注意,因为必须为当前类声明的变量,通过继承获得的protected变量将不能访问, 需要转型到声明该变量的类才能访问.
 * 反射的其他功能请使用Apache Jarkarta Commons BeanUtils
 */
public class BeanUtils {
	/**
	 * 获取当前类声明的private/protected变量
	 */
	public static Object getPrivateProperty(Object object, String propertyName) throws IllegalAccessException,
			NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		return field.get(object);
	}

	/**
	 * 设置当前类声明的private/protected变量
	 */
	public static void setPrivateProperty(Object object, String propertyName, Object newValue)
			throws IllegalAccessException, NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		field.set(object, newValue);
	}

	/**
	 * 调用当前类声明的private/protected函数
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object[] params)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}
		Method method = object.getClass().getDeclaredMethod(methodName, types);
		method.setAccessible(true);
		return method.invoke(object, params);
	}

	/**
	 * 调用当前类声明的private/protected函数
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object param) 
			throws NoSuchMethodException,IllegalAccessException, InvocationTargetException {
		return invokePrivateMethod(object, methodName, new Object[] { param });
	}
}
