package com.zh.springbootredisson.service.impl;

import com.zh.springbootredisson.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @date 2019/6/6
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String book() {
        return this.decrementOrder();
    }

    @Override
    public String bookWithLock() {
        RLock lock = this.redissonClient.getLock("lock");
        try {
            lock.lock(10, TimeUnit.SECONDS);
            return this.decrementOrder();
        } catch (Exception e) {
            log.error(e.getMessage());
            return "预定失败";
        } finally {
            lock.unlock();
        }
    }

    private String decrementOrder(){
        Long count = Long.valueOf(this.stringRedisTemplate.opsForValue().get("count"));
        if (count <= 0){
            return "库存不足";
        }else{
            count = this.stringRedisTemplate.opsForValue().decrement("count");
        }
        log.info("==================库存还剩{}个================",count);
        return String.valueOf(count);
    }
}
