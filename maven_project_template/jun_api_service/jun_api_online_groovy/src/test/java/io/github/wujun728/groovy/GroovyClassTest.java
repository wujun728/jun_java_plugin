package io.github.wujun728.groovy;

import groovy.lang.GroovyClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.codehaus.groovy.control.CompilerConfiguration;
public class GroovyClassTest {
	public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException {
	   //字符串转java
	   //groovy提供了一种将字符串文本代码直接转换成Java Class对象的功能
		CompilerConfiguration config = new CompilerConfiguration();
        config.setSourceEncoding("UTF-8");
        // 设置该GroovyClassLoader的父ClassLoader为当前线程的加载器(默认)
       System.out.print("Thread.currentThread().getContextClassLoader()="+Thread.currentThread().getContextClassLoader());
	   GroovyClassLoader groovyClassLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), config);
	   GroovyClassLoader groovyClassLoaderObj = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), config);
	   //里面的文本是Java代码,但是我们可以看到这是一个字符串我们可以直接生成对应的Class<?>对象,而不需要我们写一个.java文件
	   Class<?> clazz = groovyClassLoader.parseClass(" package io.github.wujun728.groovy;\r\n"
	   		+ "import  io.github.wujun728.groovy.Param;\r\n"
	   		+ "public class Main {\r\n"
	   		+ "	\r\n"
	   		+ "	public int age = 666;\r\n"
	   		+ "\r\n"
	   		+ "	public void sayHello() {\r\n"
	   		+ "		System.out.println(\"年龄是:\" + age);\r\n"
	   		+ "	}\r\n"
	   		+ "	public void sayHello(Param p) {\r\n"
	   		+ "		System.out.println(\"年龄是:\" + p.getMsg());\r\n"
	   		+ "	}\r\n"
	   		+ "}\r\n"
	   		+ " ");
	   Class<?> clazzObj = groovyClassLoaderObj.parseClass(" package io.github.wujun728.groovy;\r\n"
	   		+ "\r\n"
	   		+ "import java.io.Serializable;\r\n"
	   		+ "\r\n"
	   		+ "public class Param implements Serializable{\r\n"
	   		+ "	\r\n"
	   		+ "	String msg;\r\n"
	   		+ "\r\n"
	   		+ "	public String getMsg() {\r\n"
	   		+ "		return msg;\r\n"
	   		+ "	}\r\n"
	   		+ "\r\n"
	   		+ "	public void setMsg(String msg) {\r\n"
	   		+ "		this.msg = msg;\r\n"
	   		+ "	}\r\n"
	   		+ "} ");
	   Method[] methods = clazz.getDeclaredMethods();
	   Method method = clazz.getDeclaredMethod("sayHello",Param.class);
	   try {
		   
	      method.invoke(clazz.newInstance(),clazzObj.newInstance());
	   } catch (InvocationTargetException e) {
	      e.printStackTrace();
	   }
	} 
	
	
	public static void Testmain(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException {
		   //字符串转java
		   //groovy提供了一种将字符串文本代码直接转换成Java Class对象的功能
		   GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
		   //里面的文本是Java代码,但是我们可以看到这是一个字符串我们可以直接生成对应的Class<?>对象,而不需要我们写一个.java文件
		   Class<?> clazz = groovyClassLoader.parseClass("package com.xxl.job.core.glue;\n" +
		      "\n" +
		      "public class Main {\n" +
		      "\n" +
		      "    public int age = 22;\n" +
		      "    \n" +
		      "    public void sayHello() {\n" +
		      "        System.out.println(\"年龄是:\" + age);\n" +
		      "    }\n" +
		      "}\n");

		   Method method = clazz.getDeclaredMethod("sayHello");
		   try {
		      method.invoke(clazz.newInstance());
		   } catch (InvocationTargetException e) {
		      e.printStackTrace();
		   }
		} 
}
