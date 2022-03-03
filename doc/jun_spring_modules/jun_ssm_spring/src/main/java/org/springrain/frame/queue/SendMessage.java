package org.springrain.frame.queue;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisTemplate;

public class SendMessage {
	private RedisTemplate<String, Object> redisTemplate;


    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }



    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }



    public void sendMessage(String channel, Serializable message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
