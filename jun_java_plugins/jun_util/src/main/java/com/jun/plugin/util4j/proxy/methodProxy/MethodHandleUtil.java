package com.jun.plugin.util4j.proxy.methodProxy;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 方法句柄代理
 * JDK7以上支持
 * @author juebanlin
 */
public class MethodHandleUtil{
	
	/**
	 * JDK 7及以上的方法句柄方式调用
	 * @throws Throwable
	 */
	protected void demo() throws Throwable {
	    MethodHandles.Lookup lookup = MethodHandles.lookup();
	    MethodType type = MethodType.methodType(String.class, int.class, int.class);
	    MethodHandle mh = lookup.findVirtual(String.class, "substring", type);
	    String str = (String) mh.invokeExact("Hello World", 1, 3);
	    System.out.println(str);
	}
	
	/**
	 * 代理对象方法
	 * 会根据方法名和方法参数严格匹配
	 * @param target 代理对象
	 * @param methodName 对象方法名
	 * @param args 对象方法参数
	 * @return
	 */
	public static MethodHandleProxy proxyMethod(Object target,String methodName,Object ...args)
	{
		MethodHandle mh=findMethodHandle(target, methodName, args);
		if(mh!=null)
		{
			return new MethodHandleProxy(target, mh, args);
		}
		return null;
	}
	
	/**
	 * 获取对象方法句柄
	 * @param target 对象
	 * @param methodName 方法名
	 * @param ptypes 方法参数列表
	 * @return
	 */
	public static MethodHandle findMethodHandle(Object target,String methodName,Object ...args)
	{
		Class<?>[] ptypes = new Class<?>[args.length];
		for(int i=0;i<args.length;i++)
		{
			ptypes[i]=args[i].getClass();
		}
		return findMethodHandle(target, methodName, ptypes);
	}
	
	/**
	 * 获取对象方法句柄
	 * @param target 对象
	 * @param methodName 方法名
	 * @param ptypes 方法参数类型列表
	 * @return
	 */
	public static MethodHandle findMethodHandle(Object target,String methodName,Class<?> ...ptypes)
	{
		MethodHandle mh=null;
		MethodHandles.Lookup lookup = MethodHandles.lookup();
	    try {
	    	MethodType type = MethodType.methodType(target.getClass(),ptypes);
	    	mh = lookup.findVirtual(target.getClass(),methodName, type);
		} catch (Throwable e) {
		}
		return mh;
	}
}
