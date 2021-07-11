package com.battcn.boot.extend.configuration.mybatis.plugins;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.*;


/**
 * mybatis 预备拦截器处理插件
 *
 * @author Levin
 */
@Configuration
@EnableConfigurationProperties(PreparePluginProperties.class)
@ConditionalOnProperty(prefix = MYBATIS_PLUGIN, name = ENABLED, havingValue = TRUE, matchIfMissing = true)
public class PreparePluginAutoConfiguration {

    @Bean
    public PrepareInterceptor prepareInterceptor(PreparePluginProperties preparePluginProperties,
                                                 @NotNull PreparePluginContent preparePluginContent) {
        return new PrepareInterceptor(preparePluginProperties, preparePluginContent);
    }
}