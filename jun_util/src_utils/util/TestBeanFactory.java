/**
 * Program  : TestBeanFactory.java
 * Author   : wangyin
 * Create   : 2007-3-11 ÏÂÎç01:52:32
 * @version    %I%, %G%
 * @since      1.0
 * Copyright 2006 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with Embedded Internet Solutions Inc.
 *
 */

package cn.ipanel.apps.portalBackOffice.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestBeanFactory {
	private static ApplicationContext applicationContext;

	static {
		applicationContext = new FileSystemXmlApplicationContext(new String[] {
				getFilePath("dao-hibernate-config.xml"),
				getFilePath("service-config.xml") });
		
	}

	public static Object getBeanByName(String beanName) {
		return applicationContext.getBean(beanName);
	}
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}

	private static String getFilePath(String xmlName) {
		return ClassLoader.getSystemResource(
				xmlName).toString();
	}
	
	
}
