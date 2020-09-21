package com.github.ghsea.dbtracer.sample;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {
	private static ApplicationContext ctx;

	private SpringUtil() {

	}

	public static <T> T getBean(Class<T> type) {
		return ctx.getBean(type);
	}

	public static Object getBean(String name) {
		return ctx.getBean(name);
	}

	public void setApplicationContext(ApplicationContext ctx0) throws BeansException {
		ctx = ctx0;
	}
}
