package com.battcn.boot.extend.configuration.wrapper;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.*;

/**
 * QQ交流群：391619659
 *
 * @author Levin
 * @since 2018-12-17
 */
@Configuration
@EnableConfigurationProperties({RequestProperties.class})
@ConditionalOnProperty(prefix = WRAPPER_FILTER, name = ENABLED, havingValue = TRUE, matchIfMissing = true)
public class RequestWrapperFilterAutoConfiguration {

    @Bean("registrationBodyCacheFilter")
    public FilterRegistrationBean registrationBodyCacheFilter(RequestProperties properties) {
        FilterRegistrationBean<BodyCacheFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setEnabled(properties.getEnabled());
        // 设置顺序
        registrationBean.setOrder(properties.getOrder());
        // 设置 BodyCacheFilter
        registrationBean.setFilter(new BodyCacheFilter());
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


    private static class BodyCacheFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            final String method = request.getMethod();
            // 如果不是 POST PATCH PUT 等有流的接口则无需进行类型转换，提高性能
            if (HttpMethod.POST.matches(method) || HttpMethod.PATCH.matches(method) || HttpMethod.PUT.matches(method)) {
                if (!(request instanceof BodyCacheHttpServletRequestWrapper)) {
                    request = new BodyCacheHttpServletRequestWrapper(request);
                }
            }
            if (!(response instanceof BodyCacheHttpServletResponseWrapper)) {
                response = new BodyCacheHttpServletResponseWrapper(response);
            }
            filterChain.doFilter(request, response);
        }
    }
}
