package com.buxiaoxia.system.utils;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @RabbitListener 可以用于注册Listener时使用的信息：
 * 如queue，exchange，key、ListenerContainerFactory和RabbitAdmin的bean name。
 * @RabbitHandler @RabbitHandler结合使用，不同类型的消息使用不同的方法来处理。 这里只有String
 * <p>
 * Created by xw on 2017/3/27.
 * 2017-03-27 22:50
 */
@Component
@RabbitListener(queues = "queue_one1")
public class MyRabitListener {

	@RabbitHandler
	public void handler1(String message) {
		System.out.println("queue1 收到消息 : " + message);
	}
}
