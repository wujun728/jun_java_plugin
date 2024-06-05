package com.sms.starter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;



/**
 * RestTemplateConfig配置
 *
 * @Author Sans
 * @CreateTime 2019/4/20
 * @attention
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate( ) {
        return new RestTemplate();
    }
}
