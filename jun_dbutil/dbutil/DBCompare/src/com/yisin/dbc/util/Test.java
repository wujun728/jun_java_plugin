package com.yisin.dbc.util;

import java.lang.reflect.Method;

import com.yisin.dbc.entity.CreAttr;

public class Test {

	public static void main(String[] args) {
	    System.out.println(Test.class.getPackage().getName());
	}

	public static void test(Object obj, String idfeild) {
		String methodName = "get" + idfeild.substring(0, 1).toUpperCase() + idfeild.substring(1);
		Method method;
		try {
			method = obj.getClass().getMethod(methodName);
			Object value = method.invoke(obj, null);
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
