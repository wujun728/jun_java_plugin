package com.erp.service.impl;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class SpringWiredBean extends SpringBeanAutowiringSupport {
		 
	     /**
	      * 自动装配注解会让Spring通过类型匹配为beanFactory注入示例
	      */
	     @Autowired
	     private BeanFactory beanFactory;
	 
	     private SpringWiredBean() {
	     }
	 
	     private static SpringWiredBean instance;
	 
	     static {
	         // 静态块，初始化实例
	         instance = new SpringWiredBean();
	     }
	 
	     /**
	      * 实例方法
	      * 使用的时候先通过getInstance方法获取实例
	      * 
	      * @param beanId
	      * @return
	      */
	     public Object getBeanById(String beanId) {
	         return beanFactory.getBean(beanId);
	     }
	 
	     public static SpringWiredBean getInstance() {
	         return instance;
	     }
}
