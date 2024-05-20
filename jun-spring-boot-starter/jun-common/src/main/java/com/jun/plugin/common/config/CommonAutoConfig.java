package com.jun.plugin.common.config;

import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.jun.plugin.common.base.interfaces.IPluginRunner;
import com.jun.plugin.common.filter.EncodingFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngineManager;
import java.util.*;

/**
 * @author wujun
 * @date 2021/3/19
 */
@Slf4j
@Configuration
public class CommonAutoConfig implements ApplicationContextAware, InitializingBean {
	private ConfigurableApplicationContext applicationContext;
	private BeanDefinitionRegistry registry;
	private String packages[] = {"com.jun.plugin.common","com.jun.plugin.rest"};

	private List<Class> annotationClasss = Arrays.asList(Configuration.class,/*Mapper.class,*/ Service.class, Component.class,
			Repository.class, Controller.class,ConfigurationProperties.class);

	@Override
	public void afterPropertiesSet() {
		callRunners();
//		initBeans();
	}


	private void callRunners() {
		//将实现ApplicationRunner和CommandLineRunner接口的类，存储到集合中
		List<Object> runners = new ArrayList<>();
		runners.addAll(applicationContext.getBeansOfType(IPluginRunner.class).values());
		//按照加载先后顺序排序
		AnnotationAwareOrderComparator.sort(runners);
		for (Object runner : new LinkedHashSet<>(runners)) {
			if (runner instanceof IPluginRunner) {
				// callRunner((CommandLineRunner) runner, args);
				try {
					//调用各个实现类中的逻辑实现
					//((IInitRunner)runner).run(args.getSourceArgs());
					((IPluginRunner)runner).run();
				}
				catch (Exception ex) {
					throw new IllegalStateException("Failed to execute CommandLineRunner", ex);
				}
			}
		}
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

	@Bean
	@ConditionalOnMissingBean
	public ScriptEngineManager scriptEngineManager() {
		return new ScriptEngineManager();
	}


}