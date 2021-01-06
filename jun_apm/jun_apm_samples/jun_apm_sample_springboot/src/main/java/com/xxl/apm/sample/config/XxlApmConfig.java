package com.xxl.apm.sample.config;

import com.xxl.apm.client.factory.XxlApmFactory;
import com.xxl.apm.client.support.XxlApmWebFilter;
import com.xxl.rpc.util.IpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author xuxueli 2019-01-17
 */
@Configuration
public class XxlApmConfig {


    @Value("${xxl-apm.appname}")
    private String appname;

    @Value("${xxl-apm.adminAddress}")
    private String adminAddress;

    @Value("${xxl-apm.rpc.accessToken}")
    private String accessToken;

    @Value("${xxl-apm.msglog.path}")
    private String msglogpath;


    @Value("${server.port}")
    private int serverPort;


    @Bean(initMethod="start", destroyMethod = "stop")
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public XxlApmFactory xxlApmFactory() {

        XxlApmFactory xxlApmFactory = new XxlApmFactory();
        xxlApmFactory.setAppname(appname);
        xxlApmFactory.setAddress(IpUtil.getIpPort(serverPort));
        xxlApmFactory.setAdminAddress(adminAddress);
        xxlApmFactory.setAccessToken(accessToken);
        xxlApmFactory.setMsglogpath(msglogpath);

        return xxlApmFactory;
    }


    /**
     * 可选：针对 Web 请求埋点；
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean xxlApmFilterRegistration() {

        // xxl-apm, web filter init
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("XxlApmWebFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new XxlApmWebFilter());

        return registration;
    }


}