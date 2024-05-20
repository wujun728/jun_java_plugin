package io.github.wujun728.uidgenerator.config;

import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngineManager;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Configuration
@ComponentScan(basePackages = "io.github.wujun728.uidgenerator")
public class UidAutoConfig  implements ApplicationContextAware, InitializingBean {
	private ConfigurableApplicationContext applicationContext;

	private BeanDefinitionRegistry registry;

	/**
	 * //以下五个的结果都是为spring容器为这个类创建Bean.
	 * @Bean 注解告诉Spring这个方法将会返回一个对象，这个对象要注册为Spring应用上下文中的bean。 通常方法体中包含了最终产生bean实例的逻辑
	 * @Component 注解表明一个类会作为组件类，并告知Spring要为这个类创建bean
	 * @return
	 */
	List<Class> annotationClasss = Arrays.asList(Configuration.class,/*Mapper.class,*/ Service.class, Component.class, Repository.class, Controller.class,ConfigurationProperties.class);
	@Override
	public void afterPropertiesSet() throws Exception {
		annotationClasss.forEach(clazz->{
			Set<Class<?>> mappers = ClassScanner.scanPackageByAnnotation("io.github.wujun728.uidgenerator", clazz);
			mappers.forEach(c->{
				String beanName = NamingCase.toCamelCase(c.getSimpleName());
				beanName = StrUtil.lowerFirst(beanName);
				if(!applicationContext.containsBean(beanName) && !applicationContext.containsBeanDefinition(beanName) ){
					registerBean(beanName,c);
				}
			});
		});
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