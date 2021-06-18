package rabbitMQ.listener;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import po.Mail;


@Component
public class SubscribeListener2 {
	@RabbitListener(queues = "queue2")
	public void subscribe(Mail mail) throws IOException {
		System.out.println("订阅者2收到消息"+mail.toString());
	}
}
