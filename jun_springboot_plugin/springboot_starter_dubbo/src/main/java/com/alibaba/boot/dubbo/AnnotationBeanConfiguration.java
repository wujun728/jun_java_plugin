package com.alibaba.boot.dubbo;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Protocol;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by wuyu on 2017/4/19.
 */
public class AnnotationBeanConfiguration extends AnnotationBean implements ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext applicationContext;

    private String annotationPackage;


    /**
     * 去除Dubbo扫描com.alibaba.dubbo.config.annotation.Service，仅把Service做为描述接口注解使用
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        super.setApplicationContext(applicationContext);
        this.applicationContext = applicationContext;
    }

    @Override
    public void setPackage(String annotationPackage) {
        super.setPackage(annotationPackage);
        this.annotationPackage = annotationPackage;
    }

    /**
     * 修复 @Service 注解bug
     *
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (!isMatchPackage(bean)) {
            return bean;
        }

        Class<?> targetClass = AopUtils.getTargetClass(bean);
        Service service = targetClass.getAnnotation(Service.class);

        //全局超时时间
        String timeout = applicationContext.getEnvironment().resolvePlaceholders("${spring.dubbo.timeout}");

        if (service != null) {
            ServiceBean<Object> serviceConfig = new ServiceBean<Object>(service);
            if (void.class.equals(service.interfaceClass()) && "".equals(service.interfaceName())) {
                if (targetClass.getInterfaces().length > 0) {
                    serviceConfig.setInterface(targetClass.getInterfaces()[0]);
                } else {
                    throw new IllegalStateException("Failed to export remote service class " + targetClass.getName() + ", cause: The @Service undefined interfaceClass or interfaceName, and the service class unimplemented any interfaces.");
                }
            }

            if (applicationContext != null) {
                serviceConfig.setApplicationContext(applicationContext);
                if (service.registry().length > 0) {
                    List<RegistryConfig> registryConfigs = new ArrayList<RegistryConfig>();
                    for (String registryId : service.registry()) {
                        if (registryId != null && registryId.length() > 0) {
                            registryConfigs.add((RegistryConfig) applicationContext.getBean(registryId, RegistryConfig.class));
                        }
                    }
                    serviceConfig.setRegistries(registryConfigs);
                }
                if (service.provider().length() > 0) {
                    serviceConfig.setProvider(applicationContext.getBean(service.provider(), ProviderConfig.class));
                }
                if (service.monitor().length() > 0) {
                    serviceConfig.setMonitor(applicationContext.getBean(service.monitor(), MonitorConfig.class));
                }
                if (service.application().length() > 0) {
                    serviceConfig.setApplication(applicationContext.getBean(service.application(), ApplicationConfig.class));
                }
                if (service.module().length() > 0) {
                    serviceConfig.setModule(applicationContext.getBean(service.module(), ModuleConfig.class));
                }
                if (service.provider().length() > 0) {
                    serviceConfig.setProvider(applicationContext.getBean(service.provider(), ProviderConfig.class));
                }
                if (service.timeout() == 0) {
                    //设置全局超时时间
                    serviceConfig.setTimeout(Integer.parseInt(timeout));
                }

                if (service.protocol().length > 0) {
                    List<ProtocolConfig> protocolConfigs = new ArrayList<>();
                    Map<String, ProtocolConfig> beansOfType = applicationContext.getBeansOfType(ProtocolConfig.class);

                    for (String protocol : service.protocol()) {
                        boolean flag = false;
                        for (ProtocolConfig protocolConfig : beansOfType.values()) {
                            if (protocolConfig.getName().equalsIgnoreCase(protocol)) {
                                protocolConfigs.add(protocolConfig);
                                flag=true;
                            }
                        }

                        if(!flag){
                            protocolConfigs.add(new ProtocolConfig(protocol));
                        }
                    }
                    serviceConfig.setProtocols(protocolConfigs);
                }
                try {
                    serviceConfig.afterPropertiesSet();
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new IllegalStateException(e.getMessage(), e);
                }
            }
            serviceConfig.setRef(bean);
            addServiceConfig(serviceConfig);
            serviceConfig.export();
        } /*else if (restController != null && "true".equalsIgnoreCase(applicationContext.getEnvironment().resolvePlaceholders("${spring.dubbo.restController}"))) {
            if (targetClass.getAnnotation(DubboClient.class) != null) return bean;
            ServiceBean<Object> serviceConfig = new ServiceBean<Object>();
            if (targetClass.getInterfaces().length > 0) {
                serviceConfig.setInterface(targetClass.getInterfaces()[0]);
            } else {
                throw new IllegalStateException("Failed to export remote service class " + targetClass.getName() + ", cause: The @Service undefined interfaceClass or interfaceName, and the service class unimplemented any interfaces.");
            }
            //全局超时时间
            serviceConfig.setTimeout(Integer.parseInt(timeout));
            String port = applicationContext.getEnvironment().resolvePlaceholders("${server.port}");
            serviceConfig.setProtocol(new ProtocolConfig("feign", Integer.parseInt(port)));

            try {
                serviceConfig.afterPropertiesSet();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage(), e);
            }

            serviceConfig.setRef(bean);
            addServiceConfig(serviceConfig);
            serviceConfig.export();
        }*/
        return bean;
    }


    protected void addServiceConfig(ServiceBean serviceBean) {
        getServiceConfigs().add(serviceBean);
    }

    protected Set<ServiceConfig<?>> getServiceConfigs() {
        Field serviceConfigsField = ReflectionUtils.findField(AnnotationBean.class, "serviceConfigs");
        serviceConfigsField.setAccessible(true);
        try {
            return (Set<ServiceConfig<?>>) serviceConfigsField.get(this);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    protected ConcurrentMap<String, ReferenceBean<?>> getReferenceConfigs() {
        Field referenceConfigsField = ReflectionUtils.findField(AnnotationBean.class, "referenceConfigs");
        referenceConfigsField.setAccessible(true);
        try {
            return (ConcurrentMap<String, ReferenceBean<?>>) referenceConfigsField.get(this);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private boolean isMatchPackage(Object bean) {
        if (annotationPackage == null || annotationPackage.length() == 0) {
            return false;
        }

        String beanClassName = bean.getClass().getName();
        for (String pkg : annotationPackage.split(",")) {
            if (beanClassName.startsWith(pkg)) {
                return true;
            }
        }
        return false;
    }

    private Set<String> filterKeys = new HashSet<>();

    private Set<String> protocolKeys = new HashSet<>();


    protected void autowired(Object object) {
        if (object == null) return;
        Class<?> targetClass = AopUtils.getTargetClass(object);
        for (Field field : targetClass.getDeclaredFields()) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            Resource resource = field.getAnnotation(Resource.class);
            if (autowired != null || resource != null) {
                applicationContext.getAutowireCapableBeanFactory().autowireBean(object);
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        for (ServiceConfig<?> serviceConfig : getServiceConfigs()) {
            filterKeys.addAll(Arrays.asList(serviceConfig.getFilter().split(",")));
            ProtocolConfig protocol = serviceConfig.getProtocol();
            if (protocol != null && protocol.getName() != null) {
                protocolKeys.add(protocol.getName());
            }

            List<ProtocolConfig> protocols = serviceConfig.getProtocols();
            if (protocols != null) {
                for (ProtocolConfig protocolConfig : protocols) {
                    if (protocolConfig.getName() != null) {
                        protocolKeys.add(protocolConfig.getName());
                    }
                }
            }
        }


        for (ReferenceBean<?> referenceBean : applicationContext.getBeansOfType(ReferenceBean.class).values()) {
            filterKeys.addAll(Arrays.asList(referenceBean.getFilter().split(",")));

            String protocol = referenceBean.getProtocol();
            if (protocol != null) {
                protocolKeys.add(protocol);
            }
        }

        for (String filterKey : filterKeys) {
            if(StringUtils.isEmpty(filterKey)){
                continue;
            }
            Filter filter = ExtensionLoader.getExtensionLoader(Filter.class).getExtension(filterKey);
            autowired(filter);
        }

        for (String protocolKey : protocolKeys) {
            if(StringUtils.isEmpty(protocolKey)){
                continue;
            }
            Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(protocolKey);
            autowired(protocol);
        }
    }
}
