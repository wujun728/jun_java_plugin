package com.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @author: jujun chen
 * @description:
 * @date: 2019/2/13
 */
@Component
public class Subscriber implements MessageListener{

    @Autowired
    @Qualifier(value = "customRedisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisSerializer<?> keySerializer = redisTemplate.getKeySerializer();
        RedisSerializer<?> valueSerializer = redisTemplate.getValueSerializer();
        Object channel = keySerializer.deserialize(message.getChannel());
        Object body = valueSerializer.deserialize(message.getBody());
        System.out.println("主题: " + channel);
        System.out.println("消息内容: " + String.valueOf(body));
    }
}
