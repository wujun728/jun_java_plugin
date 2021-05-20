package com.alibaba.boot.dubbo;

import com.alibaba.boot.dubbo.discovery.DubboApplicationEventPublisher;
import com.alibaba.boot.dubbo.discovery.DubboDiscoveryClient;
import com.alibaba.boot.dubbo.generic.DubboGenericController;
import com.alibaba.boot.dubbo.generic.DubboGenericService;
import com.alibaba.boot.dubbo.websocket.GenericProxyHandler;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.extension.SpringExtensionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketConfiguration;

import java.util.List;

/**
 * Created by wuyu on 2017/4/22.
 */
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class DubboProxyConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DubboApplicationEventPublisher dubboApplicationEventPublisher() {
        return new DubboApplicationEventPublisher();
    }


    @Bean
    @ConditionalOnProperty(value = "spring.dubbo.generic-prefix")
    public DubboGenericService dubboGenericProxyService(DubboProperties dubboProperties, ZuulProperties zuulProperties, ApplicationContext applicationContext) {
        zuulProperties.getIgnoredPatterns().add(dubboProperties.getGenericPrefix() + "/**");
        SpringExtensionFactory.addApplicationContext(applicationContext);
        return new DubboGenericService();
    }


    @Bean
    @ConditionalOnBean(DubboGenericService.class)
    public DubboGenericController dubboGenericProxy() {
        return new DubboGenericController();
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
