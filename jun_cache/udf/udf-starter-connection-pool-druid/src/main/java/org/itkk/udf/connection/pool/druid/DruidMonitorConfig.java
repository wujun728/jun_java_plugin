/**
 * DruidStatViewConfig.java
 * Created at 2016-11-02
 * Created by wangkang
 * Copyright (C) 2016 egridcloud.com, All rights reserved.
 */
package org.itkk.udf.connection.pool.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述 : DruidStatViewConfig
 *
 * @author wangkang
 */
@Configuration
@ConditionalOnProperty(value = "org.itkk.connection.pool.druid.minitor.enabled", matchIfMissing = true)
@ConfigurationProperties(prefix = "org.itkk.connection.pool.druid.minitor")
@Data
public class DruidMonitorConfig {

    /**
     * 描述 : 根路径
     */
    private String path;

    /**
     * 描述 : 白名单
     */
    private String allow;

    /**
     * 描述 : 黑名单
     */
    private String deny;

    /**
     * 描述 : 登入名
     */
    private String loginUsername;

    /**
     * 描述 : 密码
     */
    private String loginPassword;

    /**
     * 描述 : 是否能够重置数据
     */
    private String resetEnable;

    /**
     * 描述 : 过滤规则
     */
    private String urlPatterns;

    /**
     * 描述 : 添加不需要忽略的格式信息.
     */
    private String exclusions;

    /**
     * 注册一个StatViewServlet
     *
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean druidStatViewServle() {
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
                this.getPath());
        //白名单
        if (StringUtils.isNotEmpty(this.getAllow())) {
            servletRegistrationBean.addInitParameter("allow", this.getAllow());
        }
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        if (StringUtils.isNotEmpty(this.getDeny())) {
            servletRegistrationBean.addInitParameter("deny", this.getDeny());
        }
        //登入查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", this.getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", this.getLoginPassword());
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", this.getResetEnable());
        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns(this.getUrlPatterns());
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", this.getExclusions());
        return filterRegistrationBean;
    }

}
