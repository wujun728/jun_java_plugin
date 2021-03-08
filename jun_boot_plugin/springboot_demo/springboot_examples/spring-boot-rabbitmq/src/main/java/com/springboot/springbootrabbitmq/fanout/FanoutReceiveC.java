package com.springboot.springbootrabbitmq.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: shiyao.wei
 * @Date: 2019/10/8 16:38
 * @Version: 1.0
 * @Desc:
 */
@Component
@RabbitListener(queues = "fanout.C")
public class FanoutReceiveC {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String message) {
        logger.info("ReceiveC  :{}", message);
    }
}
