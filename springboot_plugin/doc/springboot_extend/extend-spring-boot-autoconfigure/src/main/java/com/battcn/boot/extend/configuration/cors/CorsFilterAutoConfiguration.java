package com.battcn.boot.extend.configuration.cors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.*;
import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * Cors 跨域支持
 *
 * @author Levin
 * @since 2017/12/5 0005
 */
@Configuration
@EnableConfigurationProperties(value = {CorsFilterProperties.class})
@ConditionalOnProperty(prefix = CORS, name = ENABLED, havingValue = TRUE, matchIfMissing = true)
public class CorsFilterAutoConfiguration {

    private static final String PATH = "/**";
    private final CorsFilterProperties properties;

    @Autowired
    public CorsFilterAutoConfiguration(CorsFilterProperties properties) {
        this.properties = properties;
    }


    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(defaultString(properties.getOrigin(), CorsConfiguration.ALL));
        corsConfiguration.addAllowedHeader(defaultString(properties.getAllowedHeader(), CorsConfiguration.ALL));
        corsConfiguration.addAllowedMethod(defaultString(properties.getMethod(), CorsConfiguration.ALL));
        // 是否发送 Cookie 信息
        corsConfiguration.setAllowCredentials(properties.getAllowCredentials());
        if (properties.getMaxAge() != null) {
            corsConfiguration.setMaxAge(properties.getMaxAge());
        }
        if (properties.getExposedHeader() != null) {
            corsConfiguration.addExposedHeader(properties.getExposedHeader());
        }
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     *
     * @return Cors过滤器
     */
    @Bean
    @ConditionalOnMissingBean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(defaultString(properties.getPath(), PATH), buildConfig());
        return new CorsFilter(source);
    }

}
