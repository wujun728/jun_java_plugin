package com.alibaba.boot.dubbo;

import com.alibaba.boot.dubbo.discovery.DubboApplicationEventPublisher;
import com.alibaba.boot.dubbo.discovery.DubboDiscoveryClient;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * Created by wuyu on 2017/4/22.
 */
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class DubboGatewayConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DubboApplicationEventPublisher dubboApplicationEventPublisher() {
        return new DubboApplicationEventPublisher();
    }

    @Bean
    @ConditionalOnMissingBean(DiscoveryClient.class)
    public DubboDiscoveryClient discoveryClient(ServerProperties serverProperties,
                                                List<RegistryConfig> registryConfigs,
                                                @Value("${spring.application.name}") String applicationName,
                                                DubboApplicationEventPublisher dubboApplicationEventPublisher) {
        return new DubboDiscoveryClient(serverProperties, applicationName, registryConfigs, dubboApplicationEventPublisher);
    }

    @Bean
    @ConditionalOnClass(RouteLocator.class)
    @ConditionalOnMissingBean(value = {DubboDiscoveryClient.class, DubboRouteLocator.class})
    @Primary
    public DubboRouteLocator dubboRouteLocator(ServerProperties serverProperties,
                                               ZuulProperties zuulProperties,
                                               List<RegistryConfig> registryConfigs,
                                               DiscoveryClient discoveryClient,
                                               @Value("${spring.application.name}") String applicationName,
                                               DubboApplicationEventPublisher dubboApplicationEventPublisher) {
        return new DubboRouteLocator(serverProperties.getServletPath(), discoveryClient, new DubboDiscoveryClient(serverProperties,
                applicationName, registryConfigs, dubboApplicationEventPublisher), zuulProperties);
    }


}
