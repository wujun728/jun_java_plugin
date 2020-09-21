/**
 * RabbitMqConfig.java
 * Created at 2016-10-16
 * Created by wangkang
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.amqp.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述 : RabbitMq配置
 *
 * @author wangkang
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 描述 : rabbitListenerContainerFactoryPlus
     *
     * @param rabbitListenerContainerFactory rabbitListenerContainerFactory
     * @param jackson2JsonMessageConverter   jackson2JsonMessageConverter
     * @return rabbitListenerContainerFactoryPlus
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactoryPlus(
            SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory,
            Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        rabbitListenerContainerFactory.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitListenerContainerFactory;
    }

    /**
     * 描述 : jackson2JsonMessageConverter
     *
     * @param objectMapper objectMapper
     * @return jackson2JsonMessageConverter
     */
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    /**
     * exchangeMessageLog
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange exchangeMessageLog() {
        return new DirectExchange(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName(), true, false);
    }

    /**
     * exchangeMessageLogDlx
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange exchangeMessageLogDlx() {
        return new DirectExchange(RabbitmqConstant.EXCHANGE_MESSAGE_LOG_DLX.class.getSimpleName(), true, false);
    }

}
