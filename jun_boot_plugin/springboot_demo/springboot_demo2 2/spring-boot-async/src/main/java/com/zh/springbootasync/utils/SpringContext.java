package com.zh.springbootasync.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringContext管理类.
 * @author Wujun
 * @date 2019/06/17
 */
@Component
public class SpringContext implements ApplicationContextAware {
    /** Spring容器. */
    private static ApplicationContext applicationContext = null;
    
    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     *
     * @param applicationContext the new application context
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
       synchronized (applicationContext){
            SpringContext.applicationContext = applicationContext;
        }
    }
    
    /**
     * 取得存储在静态变量中的ApplicationContext.
     *
     * @return the application context
     */
    public static ApplicationContext getApplicationContext(){
        checkApplicationContext();
        return applicationContext;
    }
    
    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param <T> the generic type
     * @param name the name
     * @return the bean
     */
    public static <T> T getBean(String name){
        checkApplicationContext();
        return (T)applicationContext.getBean(name);
    }
    
    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param <T> the generic type
     * @param clazz the clazz
     * @return the bean
     */
    public static <T> T getBean(Class<T> clazz){
        checkApplicationContext();
        return applicationContext.getBean(clazz);
    }
    
    /**
     * 清除applicationContext静态变量.
     */
    public static void cleanApplicationContext(){
        applicationContext = null;
    }
    
    /**
     * Check application context.
     */
    private static void checkApplicationContext(){
        if (applicationContext == null){
            throw new IllegalStateException("applicaitonContext未注入");
        }
    }
    
}