package net.oschina.quartzutils.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtils implements ApplicationContextAware {
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		context = arg0;

	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static Object getBean(String beanId) {
		try {
			return getContext().getBean(beanId);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T getBean(String beanId, Class<T> clazz) {
		try {
			return getContext().getBean(beanId, clazz);
		} catch (Exception e) {
			return null;
		}
	}
}
