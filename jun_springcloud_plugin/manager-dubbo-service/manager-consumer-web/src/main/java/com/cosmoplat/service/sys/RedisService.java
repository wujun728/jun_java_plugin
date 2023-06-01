package com.cosmoplat.service.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author : wenbin
 */

@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public String get(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }


    public void del(String key) {
        if (this.exists(key)) {
            this.redisTemplate.delete(key);
        }

    }

    public void setAndExpire(String key, String value, long seconds) {
        this.redisTemplate.opsForValue().set(key, value);
        this.redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }


    public void setExpire(String key, Date endTime) {
        long seconds = endTime.getTime() - (new Date()).getTime();
        this.redisTemplate.expire(key, (long) ((int) (seconds / 1000L)), TimeUnit.SECONDS);
    }


    public Set<String> keys(String pattern) {
        return redisTemplate.keys("*" + pattern);
    }

    public void delKeys(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (!CollectionUtils.isEmpty(keys)) {
            this.redisTemplate.delete(keys);
        }
    }
}
