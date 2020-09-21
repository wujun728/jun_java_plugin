/**
 * Config.java
 * Created at 2016-11-19
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.rms;

import lombok.Data;
import org.itkk.udf.core.exception.handle.RmsResponseErrorHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.constraints.NotNull;

/**
 * 描述 : Config
 *
 * @author Administrator
 */
@Configuration
@ConfigurationProperties(prefix = "org.itkk.rms.config")
@Validated
@Data
public class RmsConfig {

    /**
     * 描述 : rms扫描路径
     */
    @NotNull
    private String rmsPathPatterns;

    /**
     * 描述 : restTemplate
     *
     * @param requestFactory requestFactory
     * @return restTemplate
     */
    @Bean
    @LoadBalanced
    @Primary
    public RestTemplate restTemplate(ClientHttpRequestFactory requestFactory) {
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new RmsResponseErrorHandler());
        return restTemplate;
    }

    /**
     * 描述 : rmsAuthConfigurer
     *
     * @return WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer rmsAuthConfigurer() { //NOSONAR
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                String[] rmsPathPatternsArray = rmsPathPatterns.split(",");
                registry.addInterceptor(rmsAuthHandlerInterceptor()).addPathPatterns(rmsPathPatternsArray);
                super.addInterceptors(registry);
            }
        };
    }

    /**
     * 描述 : RmsAuthHandlerInterceptor
     *
     * @return rmsAuthHandlerInterceptor
     */
    @Bean
    public RmsAuthHandlerInterceptor rmsAuthHandlerInterceptor() {
        return new RmsAuthHandlerInterceptor();
    }

}
