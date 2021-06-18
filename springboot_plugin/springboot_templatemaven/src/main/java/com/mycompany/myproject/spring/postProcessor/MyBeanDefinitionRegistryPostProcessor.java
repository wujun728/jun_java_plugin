package com.mycompany.myproject.spring.postProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor  implements BeanDefinitionRegistryPostProcessor {

	private final static Logger logger = LoggerFactory.getLogger(MyBeanDefinitionRegistryPostProcessor.class);

	public MyBeanDefinitionRegistryPostProcessor(){
		logger.debug( "custom MyBeanDefinitionRegistryPostProcessor instantiate" );
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		logger.debug("Custom MyBeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		logger.debug("Custom MyBeanDefinitionRegistryPostProcessor.postProcessBeanFactory");

	}
}
