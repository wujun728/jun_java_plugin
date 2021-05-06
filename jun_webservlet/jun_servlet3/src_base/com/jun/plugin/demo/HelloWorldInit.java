package com.jun.plugin.demo;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *  servlet的生命周期：init(ServletConfig)、service(ServletRequest,ServletResponse)、destroy()
 *  * 服务器
 *  * 第一次请求
 *  	* Class clazz = Class.forName("cn.itcast.HelloWorld2");
 *  	* Object obj = clazz.newInstance();
 *  	* 获得init
 *  	* 调用init，服务器创建ServletConfig，作为实参传递
 *  	* 获得service
 *  	* 调用service，服务器创建两个参与，作为实参
 *  * 第二次请求 。。。
 *  	* 服务器缓存了clazz
 *  	* 获得service
 *  	* 调用
 *  * 服务器关闭
 *  	* 获得clazz
 *  	* 获得destroy方法
 *  	* 调用
 *  
 */
public class HelloWorldInit implements Servlet {
	
	public HelloWorldInit(){
//		System.out.println("hello2 默认构造");
	}
	
	
	/**
	 * servlet的初始化方法
	 * 	* 执行次数：1次
	 */
	public void init(ServletConfig config) throws ServletException {
		//接口：javax.servlet.ServletConfig
		//实现类：org.apache.catalina.core.StandardWrapperFacade
		
		//public final class StandardWrapperFacade implements ServletConfig {
		
		//1、返回当前servlet的名称：<servlet><servlet-name>配置
		//System.out.println(config.getServletName());
		//2、返回指定初始化参数名称的值
//		String str = config.getInitParameter("gf");
//		System.out.println(str);
//		
//		String lang = config.getInitParameter("lang");
//		System.out.println(lang);
		
		//3、返回当前servlet的所有的初始化参数的名称
//		Enumeration<String> names = config.getInitParameterNames();
//		while(names.hasMoreElements()){
//			String name = names.nextElement();
//			// name = gf  value = fengjie
//			System.out.println(name + ":" + config.getInitParameter(name));
//		}
		
		
		//4、ServletConfig保存了当前web项目的 ServletContext的引用
//		ServletContext sc = config.getServletContext();
//		System.out.println(sc);
		//实现类：org.apache.catalina.core.ApplicationContextFacade
		
	}

	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
//		System.out.println("service2");
		
	}

	public void destroy() {
		System.out.println("destroy2");
		
	}

	public ServletConfig getServletConfig() {
		return null;
	}

	public String getServletInfo() {
		return null;
	}



	
	
	
	
}
