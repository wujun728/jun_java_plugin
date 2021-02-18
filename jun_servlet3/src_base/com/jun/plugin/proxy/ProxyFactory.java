package com.jun.plugin.proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * 此类不但是工具类，且还是执行句柄
 *
 */
public class ProxyFactory implements InvocationHandler {
	/**
	 * 声明被代理类
	 */
	private Object src;
	/**
	 * 构造 中接收这个被代理的对象
	 */
	private ProxyFactory(Object src){
		this.src=src;
	}
	/**
	 * 提供一个静态方法返回代理对象
	 */
	public static Object factory(Object src){
		Object proxyedObj = 
				Proxy.newProxyInstance(
						ProxyFactory.class.getClassLoader(),
						src.getClass().getInterfaces(),
						new ProxyFactory(src));
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
	
	
	
	
	
	
	/**
	 * 提供一个static方法可以返回代理类(被代理以后的对象)
	 * 接收被代理的对象
	 * @return
	 */
	public static Object getProxy(final Object src){
		Object proxedObject = //生成被代理类的代理对象
				Proxy.newProxyInstance(
						ProxyFactory.class.getClassLoader(),
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
	
	//**************************************************************************************************
	//**************************************************************************************************
	//**************************************************************************************************
	@Test
	public void proxyDemo(){
		List list = new ArrayList();
		Map  mm = new HashMap();
		
//		List list2 = (List)MyProxy.getProxy(list);
//		list2.add("ddd");
		
		Map mm2 =(Map) ProxyFactory.factory(mm);
		mm2.put("ddd", "sfds");
		mm2.remove("sfsd");
		
		P p = new P();
		IP p2 = (IP) ProxyFactory.factory(p);
		p2.run();
	}
	
	class P implements IP{
		public void run(){
			System.err.println("run...");
		}
	}
	interface IP{
		void run();
	}
	
	
	
	public static void main(String[] args) throws Exception {
		//声明被代理类
		final List list = new ArrayList();
		final Map  mm = new HashMap();
		//生成代理类
		Object obj = Proxy.newProxyInstance(
				ProxyFactory.class.getClassLoader(),
				new Class[]{Map.class},
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						System.err.println("执行某个方法了:"+method.getName());
						//执行被代理类
						Object returnValue = method.invoke(mm, args);
						return returnValue;
					}
				});
		//将代理类转换成接口的对象
//		List list2 = (List) obj;
//		list2.add("ddd");
//		System.err.println(list2.get(0));
	
	    Map m2 = (Map) obj;
	    m2.put("dddd","ddd");
	}
	//**************************************************************************************************
	//**************************************************************************************************
	//**************************************************************************************************
}
