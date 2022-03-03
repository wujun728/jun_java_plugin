package org.springrain.frame.queue;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

//@Component
public class RedisMessageDelegateListener implements MessageListener {
	@Resource
	private RedisMessageListenerContainer redisListenContainner;
	@Resource
	JdkSerializationRedisSerializer jdkSerializationRedisSerializer;
	
	private  final String channel="springrainqueue_1";

	@PostConstruct
	public void addToListenner() {
		redisListenContainner.addMessageListener(this, new PatternTopic(
				channel));
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		System.out.println(extractMessage(message));

	}

	protected Object extractMessage(Message message) {
		if (jdkSerializationRedisSerializer != null) {
			return jdkSerializationRedisSerializer.deserialize(message
					.getBody());
		}
		return message.getBody();
	}

}
