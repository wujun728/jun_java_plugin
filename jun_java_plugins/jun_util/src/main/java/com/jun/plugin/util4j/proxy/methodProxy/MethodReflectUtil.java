package com.jun.plugin.util4j.proxy.methodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 方法反射代理
 * @author juebanlin
 */
public class MethodReflectUtil{
	
	/**
	 * 根据方法名称获取对象的方法调用句柄
	 * @param target
	 * @param methodName
	 * @param args
	 * @return
	 */
	public static MethodInvokeProxy proxyByName(Object target,String methodName,Object ...args)
	{
		Method m=findMethodByName(target, methodName);
		if(m!=null)
		{
			return new MethodInvokeProxy(target, m, args);
		}
		return null;
	}
	
	/**
	 * 根据注解获取对象的方法调用句柄
	 * 优先获取第一个匹配到的方法
	 * @param target
	 * @param annotationClass
	 * @param args
	 * @return
	 */
	public static <T extends Annotation> MethodInvokeProxy proxyByAnnotation(Object target,Class<T> annotationClass,Object ...args)
	{
		Method m=findMethodByAnnotation(target,annotationClass);
		if(m!=null)
		{
			return new MethodInvokeProxy(target, m, args);
		}
		return null;
	}
	
	/**
	 * 根据指定注解获取
	 * @param target
	 * @param tag
	 * @param args
	 * @return
	 */
	public static <T extends Annotation> MethodInvokeProxy proxyByAnnotationTag(Object target,String tag,Object ...args)
	{
		Method m=findMethodByAnnotationTag(target,tag);
		if(m!=null)
		{
			return new MethodInvokeProxy(target, m, args);
		}
		return null;
	}
	
	public static Method findMethodByName(Object target,String methodName)
	{
		Method[] methods=target.getClass().getDeclaredMethods();
		for(Method m:methods)
		{
			if(m.getName().equals(methodName))
			{
				return m;
			}
		}
		return null;
	}
	
	public static <T extends Annotation> Method findMethodByAnnotation(Object target,Class<T> annotationClass)
	{
		Method[] methods=target.getClass().getDeclaredMethods();
		for(Method m:methods)
		{
			T function=m.getAnnotation(annotationClass);
			if(function!=null)
			{
				return m;
			}
		}
		return null;
	}
	
	/**
	 * 根据 {@link com.jun.plugin.util4j.proxy.methodProxy.AnnotationTag} 属性匹配方法
	 * @param target
	 * @param tag
	 * @return
	 */
	public static  Method findMethodByAnnotationTag(Object target,String tag)
	{
		Method[] methods=target.getClass().getDeclaredMethods();
		for(Method m:methods)
		{
			AnnotationTag function=m.getAnnotation(AnnotationTag.class);
			if(function!=null && function.tag().equals(tag))
			{
				return m;
			}
		}
		return null;
	}
}
