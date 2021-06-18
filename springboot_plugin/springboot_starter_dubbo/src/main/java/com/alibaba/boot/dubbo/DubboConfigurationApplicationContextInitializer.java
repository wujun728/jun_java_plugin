package com.alibaba.boot.dubbo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.alibaba.dubbo.config.spring.ServiceBean;

public class DubboConfigurationApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        Environment env = applicationContext.getEnvironment();
        String scan = env.getProperty("spring.dubbo.scan");
        if (scan != null) {
            AnnotationBean scanner = (AnnotationBean) registerAndInstance(scan);
            scanner.setPackage(scan);
            scanner.setApplicationContext(applicationContext);
            applicationContext.addBeanFactoryPostProcessor(scanner);
        }
    }

    private Object registerAndInstance(String scan) {
        if (!applicationContext.containsBean(AnnotationBeanConfiguration.class.getName())) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(AnnotationBeanConfiguration.class);
            beanDefinitionBuilder.addPropertyValue("package", scan);
            beanDefinitionBuilder.addPropertyValue("applicationContext", applicationContext);
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
            beanDefinitionRegistry.registerBeanDefinition(AnnotationBeanConfiguration.class.getName(),
                                                         beanDefinitionBuilder.getRawBeanDefinition());
        }
        return BeanUtils.instantiate(AnnotationBeanConfiguration.class);

    }

}
