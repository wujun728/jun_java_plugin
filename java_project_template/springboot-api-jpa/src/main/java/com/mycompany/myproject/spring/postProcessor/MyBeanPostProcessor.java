package com.mycompany.myproject.spring.postProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * bean  后置处理器
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    private final static Logger logger = LoggerFactory.getLogger(MyBeanPostProcessor.class);

    public MyBeanPostProcessor(){
        logger.debug( "custom MyBeanPostProcessor instantiate" );
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

//        if("spring.resources-org.springframework.boot.autoconfigure.web.ResourceProperties".equals(beanName)){
//            logger.debug("pause pause");
//        }
//
//        if(beanName.contains(".")){
//            logger.debug("后置处理器处理bean=["+beanName+"]开始");
//        }else
//        {
//            logger.debug("后置处理器处理bean=[" + beanName + " : " + bean.getClass().toString() + "]开始");
//        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

//        if(beanName.contains(".")){
//            logger.debug("后置处理器处理bean=["+beanName+"]完毕!");
//        }else
//        {
//            logger.debug("后置处理器处理bean=[" + beanName + " : " + bean.getClass().toString() + "]完毕!");
//        }

        return bean;
    }
}
