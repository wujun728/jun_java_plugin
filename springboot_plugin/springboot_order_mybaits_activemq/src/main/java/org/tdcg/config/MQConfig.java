package org.tdcg.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

/**
 * @Title: MQConfig
 * @Package: org.tdcg.config
 * @Description: 订单队列配置
 * @Author: 二东 <zwd_1222@126.com>
 * @date: 2016/10/29
 * @Version: V1.0
 */
@Configuration
@EnableJms
public class MQConfig {
    @Bean(name = "queue")
    public Queue orderQueue() {
        return new ActiveMQQueue("activemq.order");
    }
}
