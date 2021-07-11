package com.battcn.boot.extend.configuration.xss;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.*;

/**
 * @author Levin
 * @since 2018/12/15 0005
 */
@Configuration
@EnableConfigurationProperties(value = {XssProperties.class})
@ConditionalOnProperty(prefix = XSS_FILTER, name = ENABLED, havingValue = TRUE, matchIfMissing = true)
public class XssFilterAutoConfiguration {

    @Bean("registrationXssFilter")
    public FilterRegistrationBean registrationXssFilter(XssProperties properties) {
        FilterRegistrationBean<XssFilter> registrationBean = new FilterRegistrationBean<>();
        // 设置过滤路径
        registrationBean.setEnabled(true);
        // 设置顺序
        registrationBean.setOrder(properties.getOrder());
        // 设置 BodyCacheFilter
        registrationBean.setFilter(new XssFilter());
        final String name = properties.getName();
        if (!StringUtils.isEmpty(name)) {
            registrationBean.setName(properties.getName());
        }
        final Map<String, String> initParameters = properties.getInitParameters();
        if (initParameters != null && initParameters.size() > 0) {
            registrationBean.setInitParameters(properties.getInitParameters());
        }
        final Set<ServletRegistrationBean<?>> registrationBeans = properties.getServletRegistrationBeans();
        if (registrationBeans != null && registrationBeans.size() > 0) {
            registrationBean.setServletRegistrationBeans(properties.getServletRegistrationBeans());
        }
        final Set<String> servletNames = properties.getServletNames();
        if (!CollectionUtils.isEmpty(servletNames)) {
            registrationBean.setServletNames(properties.getServletNames());
        }
        return registrationBean;
    }
}
