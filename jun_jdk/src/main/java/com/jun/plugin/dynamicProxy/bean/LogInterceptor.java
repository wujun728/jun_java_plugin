package com.jun.plugin.dynamicProxy.bean;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class LogInterceptor implements MethodInterceptor {
	
	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("方法执行前");
		Object invokeSuper = methodProxy.invokeSuper(o, objects);
		System.out.println("方法执行后");
		return invokeSuper;
	}

}
