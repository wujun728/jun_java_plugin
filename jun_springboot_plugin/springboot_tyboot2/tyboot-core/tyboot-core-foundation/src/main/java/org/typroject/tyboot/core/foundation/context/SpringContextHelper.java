package org.typroject.tyboot.core.foundation.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: SpringContextHelper.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: SpringContextHelper.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
@Component
public class SpringContextHelper implements ApplicationContextAware
{
	private static final Logger logger = LoggerFactory.getLogger(SpringContextHelper.class);
	private static ApplicationContext context = null;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		context = applicationContext;
	}


	public static Object getBean(String name)
	{
		Object bean = null;
		try
		{
			bean = context.getBean(name);
		}catch (Exception e)
		{
			logger.error(e.getMessage(),e);
		}
		return bean;
	}


	public static Object getBean(Class<?> beanClass){
		Object bean = null;
		try
		{
			bean = context.getBean(beanClass);
		}catch (Exception e)
		{
			logger.error(e.getMessage(),e);
		}
		return bean;
	}
}

/*
 * $Log: av-env.bat,v $
 */