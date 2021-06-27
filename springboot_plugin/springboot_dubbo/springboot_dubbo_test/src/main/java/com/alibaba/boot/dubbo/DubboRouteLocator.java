package com.alibaba.boot.dubbo;

import com.alibaba.boot.dubbo.discovery.DubboDiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by wuyu on 2017/4/21.
 */
public class DubboRouteLocator extends DiscoveryClientRouteLocator {

    private DubboDiscoveryClient dubboDiscoveryClient;

    private ZuulProperties zuulProperties;

    public DubboRouteLocator(String servletPath, DiscoveryClient discovery, DubboDiscoveryClient dubboDiscoveryClient, ZuulProperties zuulProperties) {
        super(servletPath, discovery, zuulProperties);
        this.dubboDiscoveryClient = dubboDiscoveryClient;
        this.zuulProperties = zuulProperties;
    }

    @Override
    protected LinkedHashMap<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> zuulRouteLinkedHashMap = super.locateRoutes();
        for (String service : dubboDiscoveryClient.getServices()) {
            List<ServiceInstance> instances = dubboDiscoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                ZuulProperties.ZuulRoute route = new ZuulProperties.ZuulRoute(instance.getServiceId(),
                        "/" + instance.getServiceId() + "/**",
                        instance.getServiceId(),
                        instance.getUri().toString(),
                        zuulProperties.isStripPrefix(),
                        zuulProperties.getRetryable(),
                        zuulProperties.getIgnoredHeaders());
                zuulRouteLinkedHashMap.put(instance.getServiceId(), route);
            }
        }
        return zuulRouteLinkedHashMap;
    }

}
