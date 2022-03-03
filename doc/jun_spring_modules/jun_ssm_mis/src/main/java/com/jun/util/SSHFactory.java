package com.jun.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Bean工厂类（使用spring依赖注入实现）
 * @author Wujun
 * @createTime   Jul 30, 2011 5:03:53 PM
 */
public class SSHFactory implements ApplicationContextAware{
	private ApplicationContext ctx;
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}
	
	private  ApplicationContext getContext(){
		return ctx;
	}
	
	/**
	 * 获取bean实例
	 * @param 接口的class
	 * @return 返回接口实现类
	 */
	public <T>T getBean(Class<T> clazz){
		String simpleName = clazz.getSimpleName();
		return (T)getContext().getBean(simpleName);
	}
}
