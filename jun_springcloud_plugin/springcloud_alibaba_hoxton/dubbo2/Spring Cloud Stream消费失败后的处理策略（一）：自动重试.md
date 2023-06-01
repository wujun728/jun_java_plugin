# Spring Cloud Stream消费失败后的处理策略（一）：自动重试

**原创**

 [2018-12-10](https://blog.didispace.com/spring-cloud-starter-finchley-7-2/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

之前写了几篇关于Spring Cloud Stream使用中的常见问题，比如：

- [如何处理消息重复消费](http://blog.didispace.com/spring-cloud-starter-dalston-7-5/)
- [如何消费自己生产的消息](http://blog.didispace.com/spring-cloud-starter-finchley-7-1/)

下面几天就集中来详细聊聊，当消息消费失败之后该如何处理的几种方式。不过不论哪种方式，都需要与具体业务结合，解决不同业务场景可能出现的问题。

今天第一节，介绍一下Spring Cloud Stream中默认就已经配置了的一个异常解决方案：重试！

## 应用场景

依然要明确一点，任何解决方案都要结合具体的业务实现来确定，不要有了锤子看什么问题都是钉子。那么重试可以解决什么问题呢？由于重试的基础逻辑并不会改变，所以通常重试只能解决因环境不稳定等外在因素导致的失败情况，比如：当我们接收到某个消息之后，需要调用一个外部的Web Service做一些事情，这个时候如果与外部系统的网络出现了抖动，导致调用失败而抛出异常。这个时候，通过重试消息消费的具体逻辑，可能在下一次调用的时候，就能完成整合业务动作，从而解决刚才所述的问题。

## 动手试试

先通过一个小例子来看看Spring Cloud Stream默认的重试机制是如何运作的。之前在[如何消费自己生产的消息](http://blog.didispace.com/spring-cloud-starter-finchley-7-1/)一文中的例子，我们可以继续沿用，或者也可以精简一些，都写到一个主类中，比如下面这样：

```
@EnableBinding(TestApplication.TestTopic.class)
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @RestController
    static class TestController {

        @Autowired
        private TestTopic testTopic;

        /**
         * 消息生产接口
         * @param message
         * @return
         */
        @GetMapping("/sendMessage")
        public String messageWithMQ(@RequestParam String message) {
            testTopic.output().send(MessageBuilder.withPayload(message).build());
            return "ok";
        }

    }

    /**
     * 消息消费逻辑
     */
    @Slf4j
    @Component
    static class TestListener {

        @StreamListener(TestTopic.INPUT)
        public void receive(String payload) {
            log.info("Received: " + payload);
            throw new RuntimeException("Message consumer failed!");
        }

    }

    interface TestTopic {

        String OUTPUT = "example-topic-output";
        String INPUT = "example-topic-input";

        @Output(OUTPUT)
        MessageChannel output();

        @Input(INPUT)
        SubscribableChannel input();

    }

}
```

内容很简单，既包含了消息的生产，也包含了消息消费。与之前例子不同的就是在消息消费逻辑中，主动的抛出了一个异常来模拟消息的消费失败。

在启动应用之前，还要记得配置一下输入输出通道对应的物理目标（exchange或topic名），比如：

```
spring.cloud.stream.bindings.example-topic-input.destination=test-topic
spring.cloud.stream.bindings.example-topic-output.destination=test-topic
```

完成了上面配置之后，就可以启动应用，并尝试访问`localhost:8080/sendMessage?message=hello`接口来发送一个消息到MQ中了。此时可以看到类似下面的日志：

```
2018-12-10 11:20:21.345  INFO 30499 --- [w2p2yKethOsqg-1] c.d.stream.TestApplication$TestListener  : Received: hello
2018-12-10 11:20:22.350  INFO 30499 --- [w2p2yKethOsqg-1] c.d.stream.TestApplication$TestListener  : Received: hello
2018-12-10 11:20:24.354  INFO 30499 --- [w2p2yKethOsqg-1] c.d.stream.TestApplication$TestListener  : Received: hello
2018-12-10 11:20:54.651 ERROR 30499 --- [w2p2yKethOsqg-1] o.s.integration.handler.LoggingHandler   : org.springframework.messaging.MessagingException: Exception thrown while invoking com.didispace.stream.TestApplication$TestListener#receive[1 args]; nested exception is java.lang.RuntimeException: Message consumer failed!, failedMessage=GenericMessage [payload=byte[5], headers={amqp_receivedDeliveryMode=PERSISTENT, amqp_receivedRoutingKey=test-topic, amqp_receivedExchange=test-topic, amqp_deliveryTag=2, deliveryAttempt=3, amqp_consumerQueue=test-topic.anonymous.EuqBJu66Qw2p2yKethOsqg, amqp_redelivered=false, id=a89adf96-7de2-f29d-20b6-2fcb0c64cd8c, amqp_consumerTag=amq.ctag-XFy6vXU2w4RB_NRBzImWTA, contentType=application/json, timestamp=1544412051638}]
	at org.springframework.cloud.stream.binding.StreamListenerMessageHandler.handleRequestMessage(StreamListenerMessageHandler.java:63)
	at org.springframework.integration.handler.AbstractReplyProducingMessageHandler.handleMessageInternal(AbstractReplyProducingMessageHandler.java:109)
	at org.springframework.integration.handler.AbstractMessageHandler.handleMessage(AbstractMessageHandler.java:158)
	at org.springframework.integration.dispatcher.AbstractDispatcher.tryOptimizedDispatch(AbstractDispatcher.java:116)
	at org.springframework.integration.dispatcher.UnicastingDispatcher.doDispatch(UnicastingDispatcher.java:132)
	at org.springframework.integration.dispatcher.UnicastingDispatcher.dispatch(UnicastingDispatcher.java:105)
	at org.springframework.integration.channel.AbstractSubscribableChannel.doSend(AbstractSubscribableChannel.java:73)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:445)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:394)
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:181)
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:160)
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:47)
	at org.springframework.messaging.core.AbstractMessageSendingTemplate.send(AbstractMessageSendingTemplate.java:108)
	at org.springframework.integration.endpoint.MessageProducerSupport.sendMessage(MessageProducerSupport.java:203)
	at org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter.access$1100(AmqpInboundChannelAdapter.java:60)
	at org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter$Listener.lambda$onMessage$0(AmqpInboundChannelAdapter.java:214)
	at org.springframework.retry.support.RetryTemplate.doExecute(RetryTemplate.java:287)
	at org.springframework.retry.support.RetryTemplate.execute(RetryTemplate.java:180)
	at org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter$Listener.onMessage(AmqpInboundChannelAdapter.java:211)
	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.doInvokeListener(AbstractMessageListenerContainer.java:1414)
	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.actualInvokeListener(AbstractMessageListenerContainer.java:1337)
	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.invokeListener(AbstractMessageListenerContainer.java:1324)
	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.executeListener(AbstractMessageListenerContainer.java:1303)
	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.doReceiveAndExecute(SimpleMessageListenerContainer.java:817)
	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.receiveAndExecute(SimpleMessageListenerContainer.java:801)
	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.access$700(SimpleMessageListenerContainer.java:77)
	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer$AsyncMessageProcessingConsumer.run(SimpleMessageListenerContainer.java:1042)
	at java.lang.Thread.run(Thread.java:748)
Caused by: java.lang.RuntimeException: Message consumer failed!
	at com.didispace.stream.TestApplication$TestListener.receive(TestApplication.java:65)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:181)
	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:114)
	at org.springframework.cloud.stream.binding.StreamListenerMessageHandler.handleRequestMessage(StreamListenerMessageHandler.java:55)
	... 27 more
```

从日志中可以看到，一共输出了三次`Received: hello`，也就是说消息消费逻辑执行了3次，然后抛出了最终执行失败的异常。

### 设置重复次数

默认情况下Spring Cloud Stream会重试3次，我们也可以通过配置的方式修改这个默认配置，比如下面的配置可以将重试次数调整为1次：

```
spring.cloud.stream.bindings.example-topic-input.consumer.max-attempts=1
```

对于一些纯内部计算逻辑，不需要依赖外部环境，如果出错通常是代码逻辑错误的情况下，不论我们如何重试都会继续错误的业务逻辑可以将该参数设置为0，避免不必要的重试影响消息处理的速度。

## 深入思考

完成了上面的基础尝试之后，再思考下面两个问题：

**问题一：如果在重试过程中消息处理成功了，还会有异常信息吗？**

答案是不会。因为重试过程是消息处理的一个整体，如果某一次重试成功了，会任务对所收到消息的消费成功了。

这个问题可以在上述例子中做一些小改动来验证，比如：

```
@Slf4j
@Component
static class TestListener {

    int counter = 1;

    @StreamListener(TestTopic.INPUT)
    public void receive(String payload) {
        log.info("Received: " + payload + ", " + counter);
        if (counter == 3) {
            counter = 1;
            return;
        } else {
            counter++;
            throw new RuntimeException("Message consumer failed!");
        }
    }

}
```

通过加入一个计数器，当重试是第3次的时候，不抛出异常来模拟消费逻辑处理成功了。此时重新运行程序，并调用接口`localhost:8080/sendMessage?message=hello`，可以获得如下日志结果，并没有异常打印出来。

```
2018-12-10 16:07:38.390  INFO 66468 --- [L6MGAj-MAj7QA-1] c.d.stream.TestApplication$TestListener  : Received: hello, 1
2018-12-10 16:07:39.398  INFO 66468 --- [L6MGAj-MAj7QA-1] c.d.stream.TestApplication$TestListener  : Received: hello, 2
2018-12-10 16:07:41.402  INFO 66468 --- [L6MGAj-MAj7QA-1] c.d.stream.TestApplication$TestListener  : Received: hello, 3
```

也就是，虽然前两次消费抛出了异常，但是并不影响最终的结果，也不会打印中间过程的异常，避免了对日志告警产生误报等问题。

**问题二：如果重试都失败之后应该怎么办呢？**

如果消息在重试了还是失败之后，目前的配置唯一能做的就是将异常信息记录下来，进行告警。由于日志中有消息的消息信息描述，所以应用维护者可以根据这些信息来做一些补救措施。

当然，这样的做法显然不是最好的，因为太过麻烦。那么怎么做才好呢？且听下回分解！

## 代码示例

本文示例读者可以通过查看下面仓库的中的`stream-exception-handler-1`项目：

- [Github](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- [Gitee](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)