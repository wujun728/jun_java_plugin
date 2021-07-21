package com.wf.ew.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by wangfan on 2019-01-04 下午 3:40.
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    // 支持跨域访问
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").maxAge(3600).allowedHeaders("Content-Type, x-requested-with, X-Custom-Header, Authorization");
    }

    // 支持PUT请求
    @Bean
    public HttpPutFormContentFilter httpPutFormContentFilter() {
        return new HttpPutFormContentFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OptionsInterceptor()).addPathPatterns("/**");
    }
}