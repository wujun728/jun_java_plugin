package com.abc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy implements InvocationHandler {

	private Object target;

	public Object getInstance(Object target) {
		this.target = target;
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	@Override
	public Object  invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("这是开始前");
		Object invoke = method.invoke(target, args);
		System.out.println("这是执行后");
		return invoke;
	}
	
}
