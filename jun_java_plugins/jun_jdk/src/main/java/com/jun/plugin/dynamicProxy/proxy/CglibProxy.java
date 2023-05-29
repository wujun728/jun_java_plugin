package com.jun.plugin.dynamicProxy.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {
	
	
	public Object getInstance(Object target) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(this);
		//通过字节码技术动态创建子类实例  
		return enhancer.create();
	}

	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("方法执行前");
		System.out.println(method);
		Object invokeSuper = methodProxy.invokeSuper(o, objects);
		System.out.println("方法执行后");
		return invokeSuper;
	}

}
