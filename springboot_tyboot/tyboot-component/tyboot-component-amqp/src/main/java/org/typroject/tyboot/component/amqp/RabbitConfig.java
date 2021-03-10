package org.typroject.tyboot.component.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 子杨 on 2017/4/24.
 * @TODO 如何根据messageType的定义自动创建多个队列
 */
@Configuration
public class RabbitConfig {



    //短信消息队列
    public static final String QUEUE_SMS = "SMS";

    //推送消息队列
    public static final String QUEUE_PUSH = "PUSH";



    //websoket
    public static final String QUEUE_WEBSOKET = "WEBSOKET";


    //通用队列
    public static final String QUEUE_COMMON = "COMMON";

    @Bean
    public Queue smsQueue() {
        return new Queue(QUEUE_SMS);
    }


    @Bean
    public Queue pushQueue() {
        return new Queue(QUEUE_PUSH);
    }

    @Bean
    public Queue websoketQueue() {
        return new Queue(QUEUE_WEBSOKET);
    }

    @Bean
    public Queue commonQueue() {
        return new Queue(QUEUE_COMMON);
    }

}