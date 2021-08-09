# Spring Cloud Stream消费失败后的处理策略（二）：自定义错误处理逻辑

**原创**

 [2018-12-12](https://blog.didispace.com/spring-cloud-starter-finchley-7-3/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

## 应用场景

上一篇[《Spring Cloud Stream消费失败后的处理策略（一）：自动重试》](http://blog.didispace.com/spring-cloud-starter-finchley-7-2/)介绍了默认就会生效的消息重试功能。对于一些因环境原因、网络抖动等不稳定因素引发的问题可以起到比较好的作用。但是对于诸如代码本身存在的逻辑错误等，无论重试多少次都不可能成功的问题，是无法修复的。对于这样的情况，前文中说了可以利用日志记录消息内容，配合告警来做补救，但是很显然，这样做非常原始，并且太过笨拙，处理复杂度过高。所以，我们需要需求更好的办法，本文将介绍针对该类问题的一种处理方法：自定义错误处理逻辑。

## 动手试试

准备一个会消费失败的例子，可以直接沿用前文的工程，也可以新建一个，然后创建如下代码的逻辑：

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

spring.cloud.stream.bindings.example-topic-output.destination=test-topic
```

完成了上面配置之后，启动应用并访问`localhost:8080/sendMessage?message=hello`接口来发送一个消息到MQ中了，此时可以看到消费失败后抛出了异常，跟上一篇文章的结果一样，消息消费失败，记录了日志，消息信息丢弃。

下面，针对消息消费失败，在`TestListener`中针对消息消费逻辑创建一段错误处理逻辑，比如：

```
@Slf4j
@Component
static class TestListener {

    @StreamListener(TestTopic.INPUT)
    public void receive(String payload) {
        log.info("Received payload : " + payload);
        throw new RuntimeException("Message consumer failed!");
    }

    /**
     * 消息消费失败的降级处理逻辑
     *
     * @param message
     */
    @ServiceActivator(inputChannel = "test-topic.stream-exception-handler.errors")
    public void error(Message<?> message) {
        log.info("Message consumer failed, call fallback!");
    }

}
```

通过使用`@ServiceActivator(inputChannel = "test-topic.stream-exception-handler.errors")`指定了某个通道的错误处理映射。其中，inputChannel的配置中对应关系如下：

- `test-topic`：消息通道对应的目标（destination，即：`spring.cloud.stream.bindings.example-topic-input.destination`的配置）
- `stream-exception-handler`：消息通道对应的消费组（group，即：`spring.cloud.stream.bindings.example-topic-input.group`的配置）

再启动应用并访问`localhost:8080/sendMessage?message=hello`接口来发送一个消息到MQ中，此时可以看到日志如下：

```
2018-12-11 12:00:35.500  INFO 75269 --- [ctor-http-nio-3] o.s.a.r.c.CachingConnectionFactory       : Attempting to connect to: [localhost:5672]
2018-12-11 12:00:35.512  INFO 75269 --- [ctor-http-nio-3] o.s.a.r.c.CachingConnectionFactory       : Created new connection: rabbitConnectionFactory.publisher#311db1cb:0/SimpleConnection@40370d8c [delegate=amqp://guest@127.0.0.1:5672/, localPort= 54391]
2018-12-11 12:00:35.527  INFO 75269 --- [ption-handler-1] c.d.stream.TestApplication$TestListener  : Received: hello,
2018-12-11 12:00:38.541  INFO 75269 --- [ption-handler-1] c.d.stream.TestApplication$TestListener  : Message consumer failed, call fallback!
```

虽然消费逻辑中输出了消息内容之后抛出了异常，但是会进入到error函数中，执行错误处理逻辑（这里只是答应了一句话），用户可以根据需要读取消息内容以及异常详情做更进一步的细化处理。

## 深入思考

由于error逻辑是通过编码方式来实现的，所以这段逻辑相对来说比较死。通常，只有业务上有明确的错误处理逻辑的时候，这种方法才可以比较好的被应用到。不然能做的可能也只是将消息记录下来，然后具体的分析原因后再去做补救措施。所以这种方法也不是万能的，主要适用于有明确错误处理方案的方式来使用（这种场景并不多），另外。。。

> **注意：有坑！** 这个方案在目前版本（2.0.x）其实还有一个坑，这种方式并不能很好的处理异常消息，会有部分消息得不到正确的处理，由于应用场景也不多，所以目前不推荐使用这种方法来做（完全可以用原始的异常捕获机制来处理，只是没有这种方式那么优雅）。目前看官方issue是在Spring Cloud Stream的2.1.0版本中会修复，后续发布之后可以使用该功能，具体点击查看：[Issue #1357](https://github.com/spring-cloud/spring-cloud-stream/issues/1357)。

而对于没有特定的错误处理方案的，也只能通过记录和后续处理来解决，可能这样的方式也只是比从日志中抓去简单那么一些，并没有得到很大的提升。但是，不要紧，因为下一篇我们将继续介绍其他更好的处理方案。

## 代码示例

本文示例读者可以通过查看下面仓库的中的`stream-exception-handler-2`项目：

- [Github](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- [Gitee](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)