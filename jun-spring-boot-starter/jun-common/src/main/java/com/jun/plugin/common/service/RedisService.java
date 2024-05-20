package com.jun.plugin.common.service;

import com.jun.plugin.common.exception.BusinessException;
import com.jun.plugin.common.exception.code.BaseResponseCode;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * redis
 *
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public StringRedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public Long getExpire(String key) {
        if (null == key) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR.getCode(), "key or TomeUnit 不能为空");
        }
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    public void set(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value);
        this.redisTemplate.expire(key, 60*60*24*7, TimeUnit.SECONDS);
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
//    public void setAndExpire(String key, String value, long seconds,TimeUnit timeUnit) {
//        this.redisTemplate.opsForValue().set(key, value);
//        this.redisTemplate.expire(key, seconds, timeUnit);
//    }


    public Set<String> keys(String pattern) {
        return redisTemplate.keys("*" + pattern);
    }

    public void delKeys(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (!CollectionUtils.isEmpty(keys)) {
            this.redisTemplate.delete(keys);
        }
    }

    public void expire(String key,  long seconds) {
        this.redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }



}
