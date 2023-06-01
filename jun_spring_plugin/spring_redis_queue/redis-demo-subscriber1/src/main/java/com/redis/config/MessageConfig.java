package com.redis.config;

import com.redis.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jujun chen
 * @description:
 * @date: 2019/2/14
 */
@Configuration
public class MessageConfig {
    @Autowired
    private Subscriber subscriber;

    @Autowired
    @Qualifier(value = "customRedisTemplate")
    private RedisTemplate redisTemplate;


    /**
     * RedisMessageListenerContainer充当消息侦听器容器。
     * 它用于从Redis通道接收消息并驱动注入其中的MessageListener实例。
     * 侦听器容器负责消息接收的所有线程并将其分派到侦听器进行处理。
     * 消息监听器容器是MDP和消息传递提供者之间的中介，并负责注册以接收消息，资源获取和释放，异常转换等。
     *
     * 此外，为了最小化应用程序占用空间，RedisMessageListenerContainer允许多个侦听器共享一个连接和一个线程，即使它们不共享订阅。
     * 因此，无论应用程序跟踪多少个侦听器或通道，运行时成本在其整个生命周期内保持不变。
     * 此外，容器允许更改运行时配置，以便您可以在应用程序运行时添加或删除侦听器，而无需重新启动。
     * 此外，容器使用延迟订阅方法，仅在需要时使用RedisConnection。
     * 如果所有侦听器都已取消订阅，则会自动执行清理，并释放该线程。

     * 为了帮助消息的异步性，容器需要一个java.util.concurrent.Executor（或Spring的TaskExecutor）来分派消息。
     * 根据负载，侦听器数量或运行时环境，您应该更改或调整执行程序以更好地满足您的需求。 强烈建议选择适当的TaskExecutor来利用其运行时。
     * @param listenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisTemplate.getConnectionFactory());
        List<Topic> topicList = new ArrayList<>();
        topicList.add(new PatternTopic("testChannel"));
        container.addMessageListener(listenerAdapter, topicList);
        return container;
    }

    /**
     * 消息侦听器适配器,能将消息委托给目标侦听器方法
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(subscriber);
    }

}
