package com.abc.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogHandler implements InvocationHandler {

	private Object target;

	public LogHandler(Object target) {
		super();
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("访问方法前");
		Object invoke = method.invoke(target, args);
		System.out.println("访问方法后");
		
		return invoke;
		
	}

}
