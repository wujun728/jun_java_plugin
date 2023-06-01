# Spring Cloud Stream消费失败后的处理策略（四）：重新入队（RabbitMQ）

**原创**

 [2018-12-16](https://blog.didispace.com/spring-cloud-starter-finchley-7-5/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

## 应用场景

之前我们已经通过[《Spring Cloud Stream消费失败后的处理策略（一）：自动重试》](http://blog.didispace.com/spring-cloud-starter-finchley-7-2/)一文介绍了Spring Cloud Stream默认的消息重试功能。本文将介绍RabbitMQ的binder提供的另外一种重试功能：重新入队。

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

        private int count = 1;

        @StreamListener(TestTopic.INPUT)
        public void receive(String payload) {
            log.info("Received payload : " + payload + ", " + count);
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
spring.cloud.stream.rabbit.bindings.example-topic-input.consumer.requeue-rejected=true

spring.cloud.stream.bindings.example-topic-output.destination=test-topic
```

完成了上面配置之后，启动应用并访问`localhost:8080/sendMessage?message=hello`接口来发送一个消息到MQ中了，此时可以看到程序不断的抛出了消息消费异常。这是由于这里我们多加了一个配置：`spring.cloud.stream.rabbit.bindings.example-topic-input.consumer.requeue-rejected=true`。在该配置作用之下，消息消费失败之后，并不会将该消息抛弃，而是将消息重新放入队列，所以消息的消费逻辑会被重复执行，直到这条消息消费成功为止。

## 深入思考

在完成了上面的这个例子之后，可能读者会有下面两个常见问题：

**问题一：之前介绍的Spring Cloud Stream默认提供的默认功能（spring.cloud.stream.bindings.example-topic-input.consumer.max-attempts）与本文所说的重入队列实现的重试有什么区别？**

Spring Cloud Stream默认提供的默认功能只是对处理逻辑的重试，它们的处理逻辑是由同一条消息触发的。而本文所介绍的重新入队史通过重新将消息放入队列而触发的，所以实际上是收到了多次消息而实现的重试。

**问题二：如上面的例子那样，消费一直不成功，这些不成功的消息会被不断堆积起来，如何解决这个问题？**

对于这个问题，我们可以联合前文介绍的[DLQ队列](http://blog.didispace.com/spring-cloud-starter-finchley-7-4/)来完善消息的异常处理。

我们只需要增加如下配置，自动绑定dlq队列：

```
spring.cloud.stream.rabbit.bindings.example-topic-input.consumer.auto-bind-dlq=true
```

然后改造一下消息处理程序，可以根据业务情况，为进入dlq队列增加一个条件，比如下面的例子：

```
@StreamListener(TestTopic.INPUT)
public void receive(String payload) {
    log.info("Received payload : " + payload + ", " + count);
    if (count == 3) {
        count = 1;
        throw new AmqpRejectAndDontRequeueException("tried 3 times failed, send to dlq!");
    } else {
        count ++;
        throw new RuntimeException("Message consumer failed!");
    }
}
```

设定了计数器count，当count为3的时候抛出`AmqpRejectAndDontRequeueException`这个特定的异常。此时，当只有当抛出这个异常的时候，才会将消息放入DLQ队列，从而不会造成严重的堆积问题。

## 代码示例

本文示例读者可以通过查看下面仓库的中的`stream-exception-handler-4`项目：

- [Github](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- [Gitee](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)