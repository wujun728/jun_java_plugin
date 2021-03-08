package com.springboot.springbootrabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueueConfig
 */
@Configuration
public class QueueConfig {
    @Bean
    public Queue simpleQueue() {
        return new Queue("simple");
    }

    @Bean
    public Queue simpleOneToMany() {
        return new Queue("simpleOneToMany");
    }
}