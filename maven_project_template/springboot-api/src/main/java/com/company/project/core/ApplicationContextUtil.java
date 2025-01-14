package com.company.project.core;
 
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
 
/**
 * @className: ApplicationContextUtil
 * @Description: 解决定时任务获取不到service的问题
 * @Author moneylee
 * @Date 2019-05-11 14:28
 * @Version 1.0
 **/
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
 
    private static ApplicationContext applicationContext;
 
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
 
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
 
    }
 
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
 
}