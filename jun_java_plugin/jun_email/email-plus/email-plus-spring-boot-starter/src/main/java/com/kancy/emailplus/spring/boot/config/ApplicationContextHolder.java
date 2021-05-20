package com.kancy.emailplus.spring.boot.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ApplicationContextHolder
 *
 * @author Wujun
 * @date 2020/2/21 1:21
 */
public class ApplicationContextHolder implements ApplicationContextInitializer {
    public static ApplicationContext applicationContext;

    /**
     * 获取ApplicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        setApplicationContext(configurableApplicationContext);
    }

    private static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextHolder.applicationContext = applicationContext;
    }
}
