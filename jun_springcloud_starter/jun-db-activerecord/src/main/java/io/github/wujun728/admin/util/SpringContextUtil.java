package io.github.wujun728.admin.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description: Spring 上下文工具, 可用于获取spring 容器中的Bean
 * @author: zongf
 * @date: 2018-12-26 10:34
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * @Description: 获取spring容器中的bean,通过bean名称获取
     * @param beanName bean名称
     * @return: Object 返回Object,需要做强制类型转换
     * @author: zongf
     * @time: 2018-12-26 10:45:07
     */
    public static <T> T getBean(String beanName){
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * @Description: 获取spring容器中的bean, 通过bean类型获取
     * @param beanClass bean 类型
     * @return: T 返回指定类型的bean实例
     * @author: zongf
     * @time: 2018-12-26 10:46:31
     */
    public static <T> T getBean(Class<T> beanClass) {
        if(applicationContext==null){
            return null;
        }
        return applicationContext.getBean(beanClass);
    }

    /**
     * @Description: 获取spring容器中的bean, 通过bean名称和bean类型精确获取
     * @param beanName bean 名称
     * @param beanClass bean 类型
     * @return: T 返回指定类型的bean实例
     * @author: zongf
     * @time: 2018-12-26 10:47:45
     */
    public static <T> T getBean(String beanName, Class<T> beanClass){
        if(applicationContext==null){
            return null;
        }
        return applicationContext.getBean(beanName,beanClass);
    }

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request;
    }

    public static HttpServletResponse getResponse(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        return response;
    }

    public static HttpSession getSession(){
        return getRequest().getSession();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    public static String getActiveProfile() {
        String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
        if (profiles.length != 0) {
            return profiles[0];
        }
        return "";
    }

    public static boolean isTest(){
        String profile = getActiveProfile();
        return !"prod".equals(profile);
    }
}
