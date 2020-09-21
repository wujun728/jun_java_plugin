/*   
 * Project: OSMP
 * FileName: SpringBeanUtils.java
 * version: V1.0
 */
package com.osmp.tools.commons;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Description:SPRING bean 工具 主要实现通过Spring非注入方式获取配置Bean
 * 
 * @author: wangkaiping
 * @date: 2014年9月11日 上午11:02:31
 */

public final class SpringBeanUtils implements ApplicationContextAware {

	private static Log logger = LogFactory.getLog(SpringBeanUtils.class);

	private static ApplicationContext applicationContext; // Spring应用上下文环境

	public SpringBeanUtils() {
		logger.debug("SpringBeanUtils instance has be created");
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		setAppContext(context);
		logger.debug("Get applicationContext is ok,context id is " + applicationContext.getId());
	}

	private static void setAppContext(ApplicationContext context) {
		applicationContext = context;
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static String[] getAliases(String name) {
		return applicationContext.getAliases(name);
	}

	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(requiredType);
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz);
	}

	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	public static Object getBean(String name, Object... args) throws BeansException {
		return applicationContext.getBean(name, args);
	}

	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	public static boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isPrototype(name);
	}

	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	public static boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException {
		return applicationContext.isTypeMatch(name, targetType);
	}

}
