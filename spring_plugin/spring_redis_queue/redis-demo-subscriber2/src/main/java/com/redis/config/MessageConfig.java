package com.redis.config;

import com.redis.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jujun chen
 * @description:
 * @date: 2019/2/14
 */
@Configuration
public class MessageConfig {
    @Autowired
    private Subscriber subscriber;

    @Autowired
    @Qualifier(value = "customRedisTemplate")
    private RedisTemplate redisTemplate;


    @Bean
    RedisMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisTemplate.getConnectionFactory());
        List<Topic> topicList = new ArrayList<>();
        topicList.add(new PatternTopic("testChannel2"));
        container.addMessageListener(listenerAdapter, topicList);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(subscriber);
    }
}
