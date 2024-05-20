package com.jun.plugin.generate.config;

import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.jun.plugin.db.DataSourcePool;
import com.jun.plugin.db.record.Db;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.jun.plugin.db.DataSourcePool.main;

/**
 * @author wujun
 * @date 2021/3/19
 */
@Slf4j
@Configuration
public class GeneratorAutoConfig implements ApplicationContextAware, InitializingBean {
	private ConfigurableApplicationContext applicationContext;
	private BeanDefinitionRegistry registry;
	private String packages[] = {"com.jun.plugin.common","com.jun.plugin.generate","com.jun.plugin.generate.modular"};

	private List<Class> annotationClasss = Arrays.asList(Configuration.class,/*Mapper.class,*/ Service.class, Component.class,
			Repository.class, Controller.class,ConfigurationProperties.class);


	@Override
	public void afterPropertiesSet() {
//		initBeans();
	}

	private void initBeans() {
		String url = SpringUtil.getProperty("project.config.packages");
		String [] pks =  packages;
		if(StringUtils.isNotEmpty(url)){
			pks =  ArrayUtil.addAll(packages,url.split(","));
		}
		for(String p : pks){
			annotationClasss.forEach(clazz->{
				Set<Class<?>> classes = ClassScanner.scanPackageByAnnotation(p, clazz);
				classes.forEach(c->{
					String beanName = StrUtil.lowerFirst(NamingCase.toCamelCase(c.getSimpleName()));
					if(!applicationContext.containsBean(beanName) && !applicationContext.containsBeanDefinition(beanName) ){
						registerBean(beanName,c);
					}
				});
			});
		}
		//applicationContext.refresh();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = (ConfigurableApplicationContext) applicationContext;
	}

	public void registerBean(String beanName, Class clazz) {
		this.registry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		BeanDefinition beanDefinition = builder.getBeanDefinition();
		registry.registerBeanDefinition(beanName, beanDefinition);
	}




}