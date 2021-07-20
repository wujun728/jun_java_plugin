package com.mycompany.myproject.spring.postProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	private final static Logger logger = LoggerFactory.getLogger(MyBeanFactoryPostProcessor.class);

	public MyBeanFactoryPostProcessor(){
		logger.debug( "custom MyBeanFactoryPostProcessor instantiate" );
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		logger.debug("Custom ProcessBeanFactory MyBeanFactoryPostProcessor");
	}
}
