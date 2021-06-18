/*
 * @(#)SpringMain.java 2014-12-16 下午1:57:32
 * Copyright 2014 鲍建明, Inc. All rights reserved. 8637.com
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.myschool.mima.server;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>File：SpringMain.java</p>
 * <p>Title: </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2014 2014-12-16 下午1:57:32</p>
 * <p>Company: 8637.com</p>
 * @author 鲍建明
 * @version 1.0
 */
public class SpringMain
{

	public static void main(String[] args) throws Exception
	{
		
		getApplicationContext();
		System.out.println("Listening ...");
	}

	public static ConfigurableApplicationContext getApplicationContext()
	{
		return new ClassPathXmlApplicationContext(
				"classpath:spring-mina.xml");
	}
	
}
