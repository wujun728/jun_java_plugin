package com.monkeyk.sos.web.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

/**
 * 2019/7/6
 * <p>
 * <p>
 * Spring ApplicationContext Holder,
 * get Spring bean from ApplicationContext
 *
 * @author Wujun
 * @since 2.0.1
 */
public class SOSContextHolder implements ApplicationContextAware, InitializingBean {


    private static ApplicationContext applicationContext;


    public SOSContextHolder() {
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SOSContextHolder.applicationContext = context;
    }


    /**
     * Retrieves the {@code ApplicationContext} set when Spring created and initialized the holder bean. If the
     * holder has not been created (see the class documentation for details on how to wire up the holder), or if
     * the holder has not been initialized, this accessor may return {@code null}.
     * <p/>
     *
     * @return the set context, or {@code null} if the holder bean has not been initialized
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * Get Spring Bean by clazz.
     *
     * @param clazz Class
     * @param <T>   class type
     * @return Bean instance
     */
    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            throw new IllegalStateException("applicationContext is null");
        }
        return applicationContext.getBean(clazz);
    }


    /**
     * Get Spring Bean by beanId.
     *
     * @param beanId beanId
     * @param <T>    class type
     * @return Bean instance
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        if (applicationContext == null) {
            throw new IllegalStateException("applicationContext is null");
        }
        return (T) applicationContext.getBean(beanId);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(applicationContext, "applicationContext is null");
    }
}
