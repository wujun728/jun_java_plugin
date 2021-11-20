package com.jun.plugin.redission.lock.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jun.plugin.redission.lock.mq.RedissonMQListener;

/**
 * MQ配置
 */
@Configuration
public class MQConfiguration {

    @Bean
    @ConditionalOnMissingBean(RedissonMQListener.class)
    public RedissonMQListener RedissonMQListener() {
        return new RedissonMQListener();
    }
}
