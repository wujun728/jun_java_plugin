package com.battcn.boot.extend.configuration.xss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.XSS_FILTER;

/**
 * @author Levin
 * @since 2017/12/5 0005
 */
@Data
@ConfigurationProperties(XSS_FILTER)
public class XssProperties {

    private boolean enabled = true;
    private int order;
    private String name = "xss-filter";
    private Map<String, String> initParameters = new LinkedHashMap<>();
    private Set<String> servletNames = new LinkedHashSet<>();
    private Set<ServletRegistrationBean<?>> servletRegistrationBeans = new LinkedHashSet<>();
    private Set<String> urlPatterns = new LinkedHashSet<>();

}
