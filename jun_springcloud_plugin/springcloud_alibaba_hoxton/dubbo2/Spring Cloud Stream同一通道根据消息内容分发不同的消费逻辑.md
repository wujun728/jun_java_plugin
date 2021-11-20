# Spring Cloud Stream同一通道根据消息内容分发不同的消费逻辑

**原创**

 [2018-12-24](https://blog.didispace.com/spring-cloud-starter-finchley-7-6/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

## 应用场景

有的时候，我们对于同一通道中的消息处理，会通过判断头信息或者消息内容来做一些差异化处理，比如：可能在消息头信息中带入消息版本号，然后通过if判断来执行不同的处理逻辑，其代码结构可能是这样的：

```
@StreamListener(value = TestTopic.INPUT)
public void receiveV1(String payload, @Header("version") String version) {
    if("1.0".equals(version)) {
        // Version 1.0
    }
    if("2.0".equals(version)) {
        // Version 2.0
    }
}
```

那么当消息处理逻辑复杂的时候，这段逻辑就会变得特别复杂。针对这个问题，在`@StreamListener`注解中提供了一个不错的属性`condition`，可以用来优化这样的处理结构。

## 动手试试

下面通过编写一个简单的例子来具体体会一下这个属性的用法：

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
            testTopic.output().send(MessageBuilder.withPayload(message).setHeader("version", "1.0").build());
            testTopic.output().send(MessageBuilder.withPayload(message).setHeader("version", "2.0").build());
            return "ok";
        }

    }

    /**
     * 消息消费逻辑
     */
    @Slf4j
    @Component
    static class TestListener {

        @StreamListener(value = TestTopic.INPUT, condition = "headers['version']=='1.0'")
        public void receiveV1(String payload, @Header("version") String version) {
            log.info("Received v1 : " + payload + ", " + version);
        }

        @StreamListener(value = TestTopic.INPUT, condition = "headers['version']=='2.0'")
        public void receiveV2(String payload, @Header("version") String version) {
            log.info("Received v2 : " + payload + ", " + version);
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

内容很简单，既包含了消息的生产，也包含了消息消费。在`/sendMessage`接口的定义中，发送了两条消息，一条消息的头信息中包含version=1.0，另外一条消息的头信息中包含version=2.0。在消息监听类`TestListener`中，对`TestTopic.INPUT`通道定义了两个`@StreamListener`，这两个监听逻辑有不同的condition，这里的表达式表示会根据消息头信息中的`version`值来做不同的处理逻辑分发。

在启动应用之前，还要记得配置一下输入输出通道对应的物理目标（exchange或topic名），比如：

```
spring.cloud.stream.bindings.example-topic-input.destination=test-topic
spring.cloud.stream.bindings.example-topic-input.group=stream-content-route
spring.cloud.stream.bindings.example-topic-output.destination=test-topic
```

完成了上面配置之后，就可以启动应用，并尝试访问`localhost:8080/sendMessage?message=hello`接口来发送一个消息到MQ中了。此时可以看到类似下面的日志：

```
2018-12-24 15:50:33.361  INFO 17683 --- [content-route-1] c.d.stream.TestApplication$TestListener  : Received v1 : hello, 1.0
2018-12-24 15:50:33.363  INFO 17683 --- [content-route-1] c.d.stream.TestApplication$TestListener  : Received v2 : hello, 2.0
```

从日志中可以看到，两条带有不同头信息的消息，分别通过不同的监听处理逻辑输出了对应的日志打印。

## 代码示例

本文示例读者可以通过查看下面仓库的中的`stream-content-route`项目：

- [Github](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- [Gitee](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)