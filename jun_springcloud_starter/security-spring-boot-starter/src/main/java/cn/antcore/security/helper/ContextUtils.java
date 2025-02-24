package cn.antcore.security.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.*;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Spring上下文工具类
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/24</p>
 **/
@Component
public class ContextUtils implements ApplicationContextAware, ApplicationEventPublisherAware {

    /** Spring 上下文 **/
    private static ApplicationContext applicationContext;
    private static ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextUtils.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        ContextUtils.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 获取上下文
     * @return Spring上下文
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取Bean
     * @param tClass Bean class
     * @param <T>
     * @return Bean实例
     */
    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    /**
     * 获取Bean集合
     * @param tClass Bean class
     * @param <T>
     * @return Bean实例集合
     */
    public static <T> Collection<T> getBeans(Class<T> tClass) {
        return applicationContext.getBeansOfType(tClass).values();
    }

    /**
     * 发布事件
     * @param event
     */
    public static void publishEvent(ApplicationEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
