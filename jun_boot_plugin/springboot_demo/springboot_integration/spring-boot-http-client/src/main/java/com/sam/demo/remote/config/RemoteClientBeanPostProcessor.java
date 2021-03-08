package com.sam.demo.remote.config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;


@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class RemoteClientBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {
    @Autowired
    private RemoteClientBuilder clientBuilder;

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        for (Field field : bean.getClass().getDeclaredFields()) {
            RemoteClient client = field.getAnnotation(RemoteClient.class);
            if (client != null) {
                if (Modifier.isStatic(field.getModifiers())) {
                    throw new IllegalStateException("@RemoteClient annotation is not supported on static fields ");
                }

                String url = client.value() == null ? client.url() : client.value();

                // 生成 @RemoteClient 的实例
                Object value = clientBuilder.build(field.getType(), url, client.type(), client.name());

                //使变量域可用
                ReflectionUtils.makeAccessible(field);
                try {
                    field.set(bean, value);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + e);
                }
            }
        }

        return true;
    }
}
