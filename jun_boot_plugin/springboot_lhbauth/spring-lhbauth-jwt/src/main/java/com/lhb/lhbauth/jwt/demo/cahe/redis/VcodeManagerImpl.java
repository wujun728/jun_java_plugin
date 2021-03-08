package com.lhb.lhbauth.jwt.demo.cahe.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @description
 * @date 2018/12/17 0017 17:16
 */
@Service
public class VcodeManagerImpl implements VcodeManager {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public String generateVcode() {
        return null;
    }

    @Override
    public void saveVcode(String mobile, String code, long expireTime, TimeUnit unit) {
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(mobile, code);
            redisTemplate.expire(mobile, expireTime, unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getVcode(String mobile) {
        Object result;
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        result = operations.get(mobile);
        return result;
    }

    @Override
    public void removeVcode(String mobile) {
        if(redisTemplate.hasKey(mobile)){
            redisTemplate.delete(mobile);
        }
    }
}
