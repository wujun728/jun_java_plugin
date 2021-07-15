package com.jun.plugin.proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * 此类不但是工具类，且还是执行句柄
 *
 */
public class MyProxy222 implements InvocationHandler {
	/**
	 * 声明被代理类
	 */
	private Object src;
	/**
	 * 构造 中接收这个被代理的对象
	 */
	private MyProxy222(Object src){
		this.src=src;
	}
	/**
	 * 提供一个静态方法返回代理对象
	 */
	public static Object factory(Object src){
		Object proxyedObj = 
				Proxy.newProxyInstance(
						MyProxy2.class.getClassLoader(),
						src.getClass().getInterfaces(),
						new MyProxy222(src));
		return proxyedObj;
	}
	/**
	 * 实现执行拦截方法
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.err.println("执行的方法是>>>>:+"+method.getName());
		Object rVlaue = method.invoke(src,args);
		return rVlaue;
	}
}
