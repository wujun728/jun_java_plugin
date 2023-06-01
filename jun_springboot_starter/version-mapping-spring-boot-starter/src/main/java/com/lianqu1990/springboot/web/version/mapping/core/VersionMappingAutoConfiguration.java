package com.lianqu1990.springboot.web.version.mapping.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hanchao
 * @date 2018/3/9 16:52
 */
@Configuration
@ConditionalOnWebApplication
public class VersionMappingAutoConfiguration {
    @Bean
    public WebMvcRegistrations customWebMvcRegistrations(){
        return new CustomWebMvcRegistrations();
    }
}
