package com.battcn.boot.extend.configuration.sensitive;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.*;

/**
 * 脱敏自动装配类
 *
 * @author Levin
 * @since 2019/1/2 0002
 */
@Configuration
@EnableConfigurationProperties(value = {SensitiveProperties.class})
@ConditionalOnProperty(prefix = SENSITIVE, name = ENABLED, havingValue = TRUE, matchIfMissing = true)
public class SensitiveAutoConfiguration {

    @Bean("registrationSensitiveFilter")
    public FilterRegistrationBean registrationSensitiveFilter(SensitiveProperties properties) throws IOException {
        final URL resource = getClass().getClassLoader().getResource(properties.getDictFilePath());
        if (resource == null) {
            throw new NullPointerException("dict file not found");
        }
        String path = resource.getPath();
        boolean relative = path.startsWith("/");
        if (relative) {
            path = path.replaceFirst("/", "");
        }
        // 加载数据字典
        SensitiveProcessor.getInstance().loadWordDict(path, Charset.forName(properties.getEncoding()));

        FilterRegistrationBean<SensitiveFilter> registrationBean = new FilterRegistrationBean<>();
        // 设置 SensitiveFilter
        registrationBean.setFilter(new SensitiveFilter(properties));
        registrationBean.setEnabled(true);
        final SensitiveProperties.Filter filter = properties.getFilter();
        if (filter != null) {
            // 设置顺序
            registrationBean.setOrder(filter.getOrder());
            final String name = filter.getName();
            if (!StringUtils.isEmpty(name)) {
                registrationBean.setName(filter.getName());
            }
            final Map<String, String> initParameters = filter.getInitParameters();
            if (initParameters != null && initParameters.size() > 0) {
                registrationBean.setInitParameters(filter.getInitParameters());
            }
            final Set<ServletRegistrationBean<?>> registrationBeans = filter.getServletRegistrationBeans();
            if (registrationBeans != null && registrationBeans.size() > 0) {
                registrationBean.setServletRegistrationBeans(filter.getServletRegistrationBeans());
            }
            final Set<String> servletNames = filter.getServletNames();
            if (!CollectionUtils.isEmpty(servletNames)) {
                registrationBean.setServletNames(filter.getServletNames());
            }
        }
        return registrationBean;
    }
}
