package com.sms.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SMS配置属性类
 *
 * @Author Sans
 * @CreateTime 2019/4/20
 * @attention 使用ConfigurationProperties注解可将配置文件（yml/properties）中指定前缀的配置转为bean
 */
@Data
@ConfigurationProperties(prefix = "sms-config")
public class SmsProperties {
    private String appid;
    private String accountSid;
    private String authToken;
}
