package com.jun.plugin.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ListProxy {
	public static void main(String[] args) throws Exception {
		final List list = new ArrayList();
		//声明代理
		Object oo = Proxy.newProxyInstance(
				ListProxy.class.getClassLoader(),
				new Class[]{List.class}
				,
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						//执行被代理类的方法
						System.err.println("正在执行的方法是："+method.getName());
						if(args!=null){
							for(Object arg:args){
								System.err.println("参数："+arg);
							}
						}
						Object returnVal = method.invoke(list,args);
						if(method.getName().equals("size")){//调用的是否是size方法
							System.err.println("其实是:"+returnVal);
							return 1000;
						}
						return returnVal;
					}
				});
		
		List list2 = (List) oo;
//		list2.add("Jack");
//		
//		int size = list2.size();
//		System.err.println("大小是："+size);
//		
		System.err.println("-----------");
		list.add("Rose");//1
		list.add("Jack");
		System.err.println("=====================");
		int size = list2.size();
		System.err.println("大小是："+size);//1
	}
}
