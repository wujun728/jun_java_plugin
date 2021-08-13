package com.mycompany.myproject.spring.postProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;

// InstantiationAwareBeanPostProcessor 代表了Spring的另外一段生命周期：实例化， 继承了BeanPostProcessor接口
// 先区别一下Spring Bean 的实例化和初始化两个阶段的主要作用：
// 1、实例化—-实例化的过程是一个创建Bean的过程，即调用Bean的构造函数，单例的Bean放入单例池中 （InstantiationAwareBeanPostProcessor）
// 2、初始化—-初始化的过程是一个赋值的过程，即调用Bean的setter，设置Bean的属性 （BeanPostProcessor）
//
@Component
public class MyInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    private final static Logger logger = LoggerFactory.getLogger(MyInstantiationAwareBeanPostProcessor.class);

    public MyInstantiationAwareBeanPostProcessor(){
        logger.debug( "custom MyInstantiationAwareBeanPostProcessor instantiate" );
    }

    /*
        如果有多个InstantiationAwareBeanPostProcessor 实现 , 只要有一个result 不为 null；
        后面的所有 后置处理器的方法就不执行了，直接返回(所以执行顺序很重要)
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        logger.debug("实例化 前 : bean=[" + beanName + " : " + beanClass.toString() + "]");

        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        logger.debug("实例化 后 : bean=[" + beanName + " : " + bean.getClass().toString() + "]");

        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        logger.debug("属性定制");

        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.debug("后置处理器处理 前 bean=[" + beanName + " : " + bean.getClass().toString() + "]");

        return bean;
    }

    /*
        如果返回null， 后面的所有 后置处理器的方法就不执行，直接返回(所以执行顺序很重要)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.debug("后置处理器处理 后 bean=[" + beanName + " : " + bean.getClass().toString() + "]");

        return bean;
    }
}
