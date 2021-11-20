package com.alibaba.boot.dubbo.endpoint;

import com.alibaba.boot.dubbo.DubboProperties;
import com.alibaba.boot.dubbo.discovery.DubboDiscoveryClient;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

public class DubboEndpoint extends AbstractEndpoint<Map<String, Object>> implements ApplicationContextAware {

    private ApplicationContext context;

    public DubboEndpoint() {
        super("dubbo");
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    @Override
    public Map<String, Object> invoke() {
        List<ProviderBean> publishedInterfaceList = new ArrayList<>();
        List<ConsumerBean> subscribedInterfaceList = new ArrayList<>();
        String[] names = context.getBeanNamesForType(AnnotationBean.class);
        if (names.length > 0) {
            AnnotationBean annotationBean = context.getBean(names[0], AnnotationBean.class);
            Field serviceConfigsField = ReflectionUtils.findField(AnnotationBean.class, "serviceConfigs");
            ReflectionUtils.makeAccessible(serviceConfigsField);
            Object services = ReflectionUtils.getField(serviceConfigsField, annotationBean);
            if (services instanceof Set) {
                final Set<ServiceConfig<?>> serviceConfigs = (Set<ServiceConfig<?>>) services;
                for (ServiceConfig config : serviceConfigs) {
                    ProviderBean providerBean = new ProviderBean();
                    Class<?> interfaceName = config.getInterfaceClass();
                    Map<String, List<String>> methodNames = new LinkedHashMap<>();
                    Method[] methods = interfaceName.getMethods();
                    for (Method method : methods) {
                        List<String> paramsType = new ArrayList<>();
                        Type[] genericParameterTypes = method.getGenericParameterTypes();
                        for (Type genericParameterType : genericParameterTypes) {
                            JavaType javaType = TypeFactory.defaultInstance().constructType(genericParameterType);
                            Class<?> rawClass = javaType.getRawClass();
                            paramsType.add(rawClass.getName());
                            methodNames.put(method.getName(), paramsType);
                        }
                    }

                    providerBean.setTarget(AopUtils.getTargetClass(config.getRef()).getName());
                    providerBean.setInterfaceName(config.getInterface());
                    providerBean.setVersion(config.getVersion());
                    providerBean.setTimeout(config.getTimeout());
                    providerBean.setMethodNames(methodNames);
                    providerBean.setRetries(config.getRetries());
                    providerBean.setActives(config.getActives());
                    providerBean.setLoadbalance(config.getLoadbalance());
                    providerBean.setConnections(config.getConnections());
                    publishedInterfaceList.add(providerBean);
                }
            }

            Map<String, ReferenceBean> referenceBeanMap = context.getBeansOfType(ReferenceBean.class);
            for (ReferenceBean referenceBean : referenceBeanMap.values()) {
                ConsumerBean consumerBean = new ConsumerBean();
                Class interfaceClass = referenceBean.getInterfaceClass();
                Map<String, List<String>> methodNames = new LinkedHashMap<>();
                Method[] methods = interfaceClass.getMethods();
                for (Method method : methods) {
                    List<String> paramsType = new ArrayList<>();
                    Type[] genericParameterTypes = method.getGenericParameterTypes();
                    for (Type genericParameterType : genericParameterTypes) {
                        JavaType javaType = TypeFactory.defaultInstance().constructType(genericParameterType);
                        Class<?> rawClass = javaType.getRawClass();
                        paramsType.add(rawClass.getName());
                        methodNames.put(method.getName(), paramsType);
                    }
                }
                consumerBean.setGroup(referenceBean.getGroup());
                consumerBean.setInterfaceName(referenceBean.getInterface());
                consumerBean.setMethodNames(methodNames);
                consumerBean.setConnections(referenceBean.getConnections());
                consumerBean.setActives(referenceBean.getActives());
                consumerBean.setRetries(referenceBean.getRetries());
                consumerBean.setVersion(referenceBean.getVersion());
                consumerBean.setTimeout(referenceBean.getTimeout());
                consumerBean.setLoadbalance(referenceBean.getLoadbalance());
                subscribedInterfaceList.add(consumerBean);
            }
        }

        Map<String, DubboDiscoveryClient> dubboDiscoveryClientMap = context.getBeansOfType(DubboDiscoveryClient.class);
        Set<String> urls = new LinkedHashSet<>();
        for (DubboDiscoveryClient dubboDiscoveryClient : dubboDiscoveryClientMap.values()) {
            for (URL url : dubboDiscoveryClient.getUrls()) {
                urls.add(url.toFullString());
            }
        }

        DubboProperties dubboProperties = context.getBean(DubboProperties.class);
        Map<String, Object> map = new HashMap<>();
        map.put("consumer", subscribedInterfaceList);
        map.put("provider", publishedInterfaceList);
        map.put("hosts", urls);
        map.put("properties", dubboProperties);
        return map;
    }

}
