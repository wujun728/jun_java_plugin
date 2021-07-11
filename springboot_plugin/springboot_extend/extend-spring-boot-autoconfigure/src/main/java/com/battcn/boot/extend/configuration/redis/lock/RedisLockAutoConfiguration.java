package com.battcn.boot.extend.configuration.redis.lock;

import com.battcn.boot.extend.configuration.redis.DefaultRedisKeyGenerator;
import com.battcn.boot.extend.configuration.redis.RedisKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.*;

/**
 * @author Levin
 * @since 2018/12/24 0024
 */
@Slf4j
@ConditionalOnClass(RedisAutoConfiguration.class)
@Import({RedisLockInterceptor.class, RedisLockHelper.class})
@ConditionalOnProperty(prefix = REDIS_LOCK, name = ENABLED, havingValue = TRUE, matchIfMissing = true)
public class RedisLockAutoConfiguration {

    @Bean(REDIS_KEY_GENERATOR)
    @ConditionalOnMissingBean
    public RedisKeyGenerator redisKeyGenerator() {
        return new DefaultRedisKeyGenerator();
    }
}
