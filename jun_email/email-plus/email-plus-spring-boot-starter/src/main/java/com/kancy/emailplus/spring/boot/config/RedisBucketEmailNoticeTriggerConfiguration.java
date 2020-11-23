package com.kancy.emailplus.spring.boot.config;

import com.kancy.emailplus.spring.boot.aop.EmailNoticeTrigger;
import com.kancy.emailplus.spring.boot.aop.RedisBucketEmailNoticeTrigger;
import com.kancy.emailplus.spring.boot.aop.RedisPollingCountEmailNoticeTrigger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisBucketEmailNoticeTriggerConfiguration
 *
 * @author Wujun
 * @date 2020/2/23 1:54
 */
@ConditionalOnClass({RedisOperations.class, RedisConnectionFactory.class})
public class RedisBucketEmailNoticeTriggerConfiguration {

    @Bean
    public EmailNoticeTrigger redisBucketEmailNoticeTrigger(){
        return new RedisBucketEmailNoticeTrigger();
    }

    @Bean
    public EmailNoticeTrigger redisPollingCountEmailNoticeTrigger(){
        return new RedisPollingCountEmailNoticeTrigger();
    }

    @Bean
    public RedisTemplate redisBucketTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
