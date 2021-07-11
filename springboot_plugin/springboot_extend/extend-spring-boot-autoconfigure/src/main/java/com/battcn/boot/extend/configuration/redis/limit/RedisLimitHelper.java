package com.battcn.boot.extend.configuration.redis.limit;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.REDIS_LIMIT_TEMPLATE;

/**
 * 基于 Redis 实现的分布式限流
 *
 * @author Levin
 * @since 2018/12/25
 */
@Slf4j
@Component("redisLimitHelper")
public class RedisLimitHelper {


    private RedisTemplate<String, Serializable> redisLimitTemplate;

    @Bean(REDIS_LIMIT_TEMPLATE)
    public RedisTemplate<String, Serializable> redisLimitTemplate(LettuceConnectionFactory redisConnectionFactory) {
        this.redisLimitTemplate = new RedisTemplate<>();
        this.redisLimitTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisLimitTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        this.redisLimitTemplate.setConnectionFactory(redisConnectionFactory);
        return redisLimitTemplate;
    }


    /**
     * 尝试获取
     *
     * @param key         key
     * @param limitCount  限流数
     * @param limitExpire 限流时间
     * @param description 描述信息
     * @return true | false
     */
    public boolean tryAcquire(String key, long limitCount, long limitExpire, String description) {
        final List<String> keys = Collections.singletonList(key);
        String luaScript = buildLuaScript();
        RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
        Number count = redisLimitTemplate.execute(redisScript, keys, limitCount, limitExpire);
        if (log.isDebugEnabled()) {
            log.info("Access try count is {} for description={} and key = {}", count, description, key);
        }
        return count != null && count.longValue() <= limitCount;
    }


    /**
     * 限流 脚本
     *
     * @return lua脚本
     */
    private String buildLuaScript() {
        // 调用不超过最大值，则直接返回
        // 执行计算器自加
        // 从第一次调用开始限流，设置对应键值的过期
        return "local c" +
                "\nc = redis.call('get',KEYS[1])" +
                "\nif c and tonumber(c) > tonumber(ARGV[1]) then" +
                "\nreturn c;" +
                "\nend" +
                "\nc = redis.call('incr',KEYS[1])" +
                "\nif tonumber(c) == 1 then" +
                "\nredis.call('expire',KEYS[1],ARGV[2])" +
                "\nend" +
                "\nreturn c;";
    }


}