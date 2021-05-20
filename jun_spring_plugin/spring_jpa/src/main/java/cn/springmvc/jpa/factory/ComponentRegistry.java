package cn.springmvc.jpa.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import cn.springmvc.jpa.common.memcached.MemcachedFactory;

/**
 * @author Vincent.wang
 *
 */
@Component
public class ComponentRegistry {
    private static ApplicationContext applicationContext;

    private static MemcachedFactory memcachedFactory;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        ComponentRegistry.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> className) {
        return applicationContext.getBean(className);
    }

    public static MemcachedFactory getMemcachedFactory() {
        return memcachedFactory;
    }

    @Autowired
    public void setMemcachedFactory(MemcachedFactory memcachedFactory) {
        ComponentRegistry.memcachedFactory = memcachedFactory;
    }

}
