package com.jun.plugin.groovy.util;


import com.jun.plugin.common.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * 动态注册对象到容器
 */
@Slf4j
public class BeanRegisterUtil {
    /**
     * 动态添加controller到spring容器
     * @param controllerBeanName
     * @throws Exception
     */
    public static void registerController(String controllerBeanName) throws Exception {
        final RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                SpringContextUtil.getContext().getBean("requestMappingHandlerMapping");

        if (requestMappingHandlerMapping != null) {
            String handler = controllerBeanName;
            Object controller = SpringContextUtil.getContext().getBean(handler);
            if (controller == null) {
                return;
            }
            unregisterController(controllerBeanName);
            //注册Controller
            Method method = requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().
                    getDeclaredMethod("detectHandlerMethods", Object.class);
            method.setAccessible(true);
            method.invoke(requestMappingHandlerMapping, handler);
            log.info("==>动态注入controller:{}",controllerBeanName);
        }
    }

    /**
     * 动态删除controller
     * @param controllerBeanName
     */
    public static void unregisterController(String controllerBeanName) {
        final RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                SpringContextUtil.getContext().getBean("requestMappingHandlerMapping");
        if (requestMappingHandlerMapping != null) {
            String handler = controllerBeanName;
            Object controller = SpringContextUtil.getContext().getBean(handler);
            if (controller == null) {
                return;
            }
            final Class<?> targetClass = controller.getClass();
            ReflectionUtils.doWithMethods(targetClass, new ReflectionUtils.MethodCallback() {
                @Override
                public void doWith(Method method) {
                    Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
                    try {
                        Method createMappingMethod = RequestMappingHandlerMapping.class.
                                getDeclaredMethod("getMappingForMethod", Method.class, Class.class);
                        createMappingMethod.setAccessible(true);
                        RequestMappingInfo requestMappingInfo = (RequestMappingInfo)
                                createMappingMethod.invoke(requestMappingHandlerMapping, specificMethod, targetClass);
                        if (requestMappingInfo != null) {
                            requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, ReflectionUtils.USER_DECLARED_METHODS);
        }
    }

    /**
     * 向spring容器中添加bean
     * @param className
     * @param serviceName
     * @param app
     */
    public static void addBean(String className, String serviceName, ApplicationContext app) {
        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            registerBean(serviceName, beanDefinitionBuilder.getRawBeanDefinition(), app);
        } catch (ClassNotFoundException e) {
            System.out.println(className + ",主动注册失败.");
        }
    }

    /**
     * 向spring容器中添加bean
     * @param clazz
     * @param serviceName
     * @param app
     */
    public static void addBean(Class clazz, String serviceName, ApplicationContext app) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        registerBean(serviceName, beanDefinitionBuilder.getRawBeanDefinition(), app);
    }

    /**
     * 向spring容器注册bean核心代码
     * @param beanName
     * @param beanDefinition
     * @param context
     */
    private static void registerBean(String beanName, BeanDefinition beanDefinition, ApplicationContext context) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
                .getBeanFactory();
        beanDefinitonRegistry.registerBeanDefinition(beanName, beanDefinition);
        log.info("==> 动态注册bean:{}",beanName);
    }

}
