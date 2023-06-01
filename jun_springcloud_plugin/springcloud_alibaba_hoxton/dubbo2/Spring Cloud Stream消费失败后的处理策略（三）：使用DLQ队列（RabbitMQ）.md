# Spring Cloud Stream消费失败后的处理策略（三）：使用DLQ队列（RabbitMQ）

**原创**

 [2018-12-14](https://blog.didispace.com/spring-cloud-starter-finchley-7-4/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

## 应用场景

前两天我们已经介绍了两种Spring Cloud Stream对消息失败的处理策略：

- [自动重试](http://blog.didispace.com/spring-cloud-starter-finchley-7-2/)：对于一些因环境原因（如：网络抖动等不稳定因素）引发的问题可以起到比较好的作用，提高消息处理的成功率。
- [自定义错误处理逻辑](http://blog.didispace.com/spring-cloud-starter-finchley-7-3/)：如果业务上，消息处理失败之后有明确的降级逻辑可以弥补的，可以采用这种方式，但是2.0.x版本有Bug，2.1.x版本修复。

那么如果代码本身存在逻辑错误，无论重试多少次都不可能成功，也没有具体的降级业务逻辑，之前在深入思考中讨论过，可以通过日志，或者降级逻辑记录的方式把错误消息保存下来，然后事后分析、修复Bug再重新处理。但是很显然，这样做非常原始，并且太过笨拙，处理复杂度过高。所以，本文将介绍利用中间件特性来便捷地处理该问题的方案：使用RabbitMQ的DLQ队列。

## 动手试试

准备一个会消费失败的例子，可以直接沿用前文的工程。也可以新建一个，然后创建如下代码的逻辑：

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
         *
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
            log.info("Received payload : " + payload);
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

内容很简单，既包含了消息的生产，也包含了消息消费。消息消费的时候主动抛出了一个异常来模拟消息的消费失败。

在启动应用之前，还要记得配置一下输入输出通道对应的物理目标（exchange或topic名）、并设置一下分组，比如：

```
spring.cloud.stream.bindings.example-topic-input.destination=test-topic
spring.cloud.stream.bindings.example-topic-input.group=stream-exception-handler
spring.cloud.stream.bindings.example-topic-input.consumer.max-attempts=1
spring.cloud.stream.rabbit.bindings.example-topic-input.consumer.auto-bind-dlq=true

spring.cloud.stream.bindings.example-topic-output.destination=test-topic
```

这里加入了一个重要配置`spring.cloud.stream.rabbit.bindings.example-topic-input.consumer.auto-bind-dlq=true`，用来开启DLQ（死信队列）。完成了上面配置之后，启动应用并访问`localhost:8080/sendMessage?message=hello`接口来发送一个消息到MQ中了，此时可以看到消费失败后抛出了异常，消息消费失败，记录了日志。此时，可以查看RabbitMQ的控制台如下：

[![img](https://blog.didispace.com/images/pasted-129.png)](https://blog.didispace.com/images/pasted-129.png)

其中，`test-topic.stream-exception-handler.dlq`队列就是`test-topic.stream-exception-handler`的dlq（死信）队列，当`test-topic.stream-exception-handler`队列中的消息消费时候之后，就会将这条消息原封不动的转存到dlq队列中。这样这些没有得到妥善处理的消息就通过简单的配置实现了存储，之后，我们还可以通过简单的操作对这些消息进行重新消费。我们只需要在控制台中点击`test-topic.stream-exception-handler.dlq`队列的名字进入到详情页面之后，使用`Move messages`功能，直接将这些消息移动回`test-topic.stream-exception-handler`队列，这样这些消息就能重新被消费一次。

[![img](https://blog.didispace.com/images/pasted-130.png)](https://blog.didispace.com/images/pasted-130.png)

如果Move messages功能中是如下内容：

```
To move messages, the shovel plugin must be enabled, try:

$ rabbitmq-plugins enable rabbitmq_shovel rabbitmq_shovel_management
```

那是由于没有安装对应的插件，只需要根据提示的命令安装就能使用该命令了。

## 深入思考

先来总结一下在引入了RabbitMQ的DLQ之后，对于消息异常处理更为完整一些的基本思路：

1. 瞬时的环境抖动引起的异常，利用重试功能提高处理成功率
2. 如果重试依然失败的，日志报错，并进入DLQ队列
3. 日志告警通知相关开发人员，分析问题原因
4. 解决问题（修复程序Bug、扩容等措施）之后，DLQ队列中的消息移回重新处理

在这样的整体思路中，可能还涉及一些微调，这里举几个常见例子，帮助读者进一步了解一些特殊的场景和配置使用！

**场景一：有些消息在业务上存在时效性，进入死信队列之后，过一段时间再处理已经没有意义，这个时候如何过滤这些消息呢？**

只需要配置一个参数即可：

```
spring.cloud.stream.rabbit.bindings.example-topic-input.consumer.dlq-ttl=10000
```

该参数可以控制DLQ队列中消息的存活时间，当超过配置时间之后，该消息会自动的从DLQ队列中移除。

**场景二：可能进入DLQ队列的消息存在各种不同的原因（不同异常造成的），此时如果在做补救措施的时候，还希望根据这些异常做不同的处理时候，我们如何区分这些消息进入DLQ的原因呢？**

再来看看这个参数：

```
spring.cloud.stream.rabbit.bindings.example-topic-input.consumer.republish-to-dlq=true
```

该参数默认是false，如果设置了死信队列的时候，会将消息原封不动的发送到死信队列（也就是上面例子中的实现），此时大家可以在RabbitMQ控制台中通过`Get message(s)`功能来看看队列中的消息，应该如下图所示：

[![img](https://blog.didispace.com/images/pasted-131.png)](https://blog.didispace.com/images/pasted-131.png)

这是一条原始消息。

如果我们该配置设置为true的时候，那么该消息在进入到死信队列的时候，会在headers中加入错误信息，如下图所示：

[![img](https://blog.didispace.com/images/pasted-132.png)](https://blog.didispace.com/images/pasted-132.png)

这样，不论我们是通过移回原通道处理还是新增订阅处理这些消息的时候就可以以此作为依据进行分类型处理了。

关于RabbitMQ的binder中还有很多关于DLQ的配置，这里不一一介绍了，上面几个是目前笔者使用过的几个，其他一些暂时认为采用默认配置已经够用，除非还有其他定制要求，或者是存量内容，需要去适配才会去配置。读者可以查看官方文档了解更多详情！

## 代码示例

本文示例读者可以通过查看下面仓库的中的`stream-exception-handler-3`项目：

- [Github](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- [Gitee](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)