package com.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: jujun chen
 * @description:
 * @date: 2019/2/13
 */
@Component
public class Publisher {

    @Autowired
    @Qualifier(value = "customRedisTemplate")
    private RedisTemplate redisTemplate;


    /**
     * 向指定频道发布消息
     * @param channel 频道名称
     * @param object 消息
     */
    public void publish(String channel,Object object){
        redisTemplate.convertAndSend(channel,object);
    }

}
