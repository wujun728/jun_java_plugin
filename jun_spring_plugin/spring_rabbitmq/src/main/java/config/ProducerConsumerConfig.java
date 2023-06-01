package config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rabbitMQ.listener.QueueListener1;
import rabbitMQ.listener.QueueListener2;
//生产者消费者模式的配置,包括一个队列和两个对应的消费者
@Configuration
public class ProducerConsumerConfig {
		@Autowired
		RabbitConfig rabbitconfig;
	
	 	@Bean
	    public Queue myQueue() {
	       Queue queue=new Queue("myqueue");
	       return queue;
	    }
	    
}
