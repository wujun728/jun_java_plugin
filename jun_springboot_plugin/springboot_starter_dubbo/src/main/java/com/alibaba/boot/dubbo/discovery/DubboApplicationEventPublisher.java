package com.alibaba.boot.dubbo.discovery;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.registry.NotifyListener;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import java.util.List;

/**
 * Created by wuyu on 2017/4/23.
 */
public class DubboApplicationEventPublisher implements ApplicationEventPublisherAware, NotifyListener {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void notify(List<URL> urls) {
        applicationEventPublisher.publishEvent(new InstanceRegisteredEvent<>(DubboDiscoveryClient.class, urls));
    }
}
