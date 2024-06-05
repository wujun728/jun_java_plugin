package com.sms.starter.config;

import com.sms.starter.SmsService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信自动配置类
 *
 * @Author Sans
 * @CreateTime 2019/4/20
 * @attention
 */
@Configuration  //注释使类成为bean的工厂
@EnableConfigurationProperties(SmsProperties.class) //使@ConfigurationProperties注解生效
public class SmsAutoConfiguration {
    @Bean
    public SmsService getBean(SmsProperties smsProperties){
        SmsService smsService = new SmsService(smsProperties);
        return smsService;
    }
}
