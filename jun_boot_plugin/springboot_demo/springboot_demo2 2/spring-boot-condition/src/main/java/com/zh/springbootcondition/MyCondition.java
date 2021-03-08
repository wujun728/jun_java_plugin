package com.zh.springbootcondition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 此处我们可以根据自己的特定逻辑来返回true/false
 * @author Wujun
 * @date 2019/5/29
 */
@Slf4j
public class MyCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //获取bean定义的注册类
        BeanDefinitionRegistry registry = conditionContext.getRegistry();
        //获取ioc使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        //获取当前环境信息
        Environment environment = conditionContext.getEnvironment();
        //获得当前系统名
        String property = environment.getProperty("os.name");
        log.info("------------------------当前系统:{}------------------------------",property);
        //获取资源resourceLoader
        ResourceLoader resourceLoader = conditionContext.getResourceLoader();
        //获取类加载器
        ClassLoader classLoader = conditionContext.getClassLoader();

        return false;
    }
}
