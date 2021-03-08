package com.springboot.springbootrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FanoutConfig
 */
@Configuration
public class FanoutConfig {
    @Bean
    public Queue MessageA() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue MessageB() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue MessageC() {
        return new Queue("fanout.C");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeA(Queue MessageA, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(MessageA).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue MessageB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(MessageB).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue MessageC, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(MessageC).to(fanoutExchange);
    }
}
