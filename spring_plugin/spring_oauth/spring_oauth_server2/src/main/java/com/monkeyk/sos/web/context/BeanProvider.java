package com.monkeyk.sos.web.context;

import org.springframework.context.ApplicationContext;

/**
 * 2018/10/14
 * <p>
 * Spring bean容器， 启动时初始化
 *
 * @author Wujun
 * @see SOSContextLoaderListener
 * @since 1.0
 */
public abstract class BeanProvider {

    private static ApplicationContext springApplicationContext;


    //private
    private BeanProvider() {
    }

    static void initialize(ApplicationContext applicationContext) {
        BeanProvider.springApplicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return springApplicationContext == null ? null : springApplicationContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        if (springApplicationContext == null) {
            return null;
        }
        return (T) springApplicationContext.getBean(beanId);
    }

}
