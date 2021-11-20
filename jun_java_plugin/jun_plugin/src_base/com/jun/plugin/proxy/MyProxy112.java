package com.jun.plugin.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 可以对所有的类进行代理
 * @author 王健
 */
public class MyProxy112 {
	/**
	 * 提供一个static方法可以返回代理类(被代理以后的对象)
	 * 接收被代理的对象
	 * @return
	 */
	public static Object getProxy(final Object src){
		Object proxedObject = //生成被代理类的代理对象
				Proxy.newProxyInstance(
						MyProxy.class.getClassLoader(),
						src.getClass().getInterfaces(),//获取这个字节码的实现的接口娄组
						new InvocationHandler() {
							public Object invoke(Object proxy, Method method, Object[] args)
									throws Throwable {
								System.err.println(">>>:正在执行："+method.getName());
								return method.invoke(src, args);
							}
						});
		return proxedObject;
	}
}
