package com.itheima.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Client1 {

	public static void main(String[] args) {
		final Human sb = new SpringBrother();
		
		//代理人:如何动态产生代理人
		
		/*
		ClassLoader loader:动态代理，必须有字节码class。加到内存中运行，必须有类加载器。固定：和被代理人用的是一样的
        Class<?>[] interfaces:代理类要实现的接口，要和被代理对象有着相同的行为。固定：和被代理人用的是一样的
        InvocationHandler h:如何代理。他是一个接口。策略设计模式。
        
		 */
		//产生代理类，得到他的实例
		Human proxyMan = (Human)Proxy.newProxyInstance(sb.getClass().getClassLoader(), 
				sb.getClass().getInterfaces(), 
				new InvocationHandler() {
					//匿名内部类，完成具体的代理策略
					//调用代理类的任何方法，都会经过该方法。  拦截
			
					/*
					 Object proxy:对代理对象的引用。
					 Method method:当前执行的方法
					 Object[] args:当前方法用到的参数
					 
					 
					 返回值：当前调用的方法的返回值
					 */
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						//判断出场费
						if("sing".equals(method.getName())){
							//唱歌
							float money = (Float)args[0];
							if(money>10000){
								method.invoke(sb, money/2);
							}
						}
						if("dance".equals(method.getName())){
							//唱歌
							float money = (Float)args[0];
							if(money>20000){
								method.invoke(sb, money/2);
							}
						}
						return null;
					}
				}
		);
		proxyMan.sing(20000);
		proxyMan.dance(100000);
	}

}
