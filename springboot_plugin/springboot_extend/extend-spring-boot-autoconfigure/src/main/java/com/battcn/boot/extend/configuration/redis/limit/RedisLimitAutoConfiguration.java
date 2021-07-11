package com.battcn.boot.extend.configuration.redis.limit;

import com.battcn.boot.extend.configuration.redis.DefaultRedisKeyGenerator;
import com.battcn.boot.extend.configuration.redis.RedisKeyGenerator;
import com.battcn.boot.extend.configuration.redis.properties.ExtendRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.*;

/**
 * @author Levin
 * @since 2018/12/24 0024
 */
@Slf4j
@ConditionalOnClass(RedisAutoConfiguration.class)
@EnableConfigurationProperties(ExtendRedisProperties.class)
@Import({RedisLimitInterceptor.class, RedisLimitHelper.class})
@ConditionalOnProperty(prefix = REDIS_LIMIT, name = ENABLED, havingValue = TRUE, matchIfMissing = true)
public class RedisLimitAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedisKeyGenerator redisKeyGenerator() {
        return new DefaultRedisKeyGenerator();
    }

}