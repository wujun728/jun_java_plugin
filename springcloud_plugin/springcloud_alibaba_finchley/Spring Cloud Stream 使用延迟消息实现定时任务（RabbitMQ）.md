# Spring Cloud Stream 使用延迟消息实现定时任务（RabbitMQ）

**原创**

 [2019-01-03](https://blog.didispace.com/spring-cloud-starter-finchley-7-7/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

## 应用场景

我们在使用一些开源调度系统（比如：elastic-job等）的时候，对于任务的执行时间通常都是有规律性的，可能是每隔半小时执行一次，或者每天凌晨一点执行一次。然而实际业务中还存在另外一种定时任务，它可能需要一些触发条件才开始定时，比如：编写博文时候，设置2小时之后发送。对于这些开始时间不确定的定时任务，我们也可以通过Spring Cloud Stream来很好的处理。

为了实现开始时间不确定的定时任务触发，我们将引入延迟消息的使用。RabbitMQ中提供了关于延迟消息的插件，所以本文就来具体介绍以下如何利用Spring Cloud Stream以及RabbitMQ轻松的处理上述问题。

## 动手试试

#### 插件安装

关于RabbitMQ延迟消息的插件介绍可以查看官方网站：https://www.rabbitmq.com/blog/2015/04/16/scheduling-messages-with-rabbitmq/

安装方式很简单，只需要在这个页面：http://www.rabbitmq.com/community-plugins.html 中找到`rabbitmq_delayed_message_exchange`插件，根据您使用的RabbitMQ版本选择对应的插件版本下载即可。

> 注意：只有RabbitMQ 3.6.x以上才支持

在下载好之后，解压得到`.ez`结尾的插件包，将其复制到RabbitMQ安装目录下的`plugins`文件夹。

然后通过命令行启用该插件：

```
rabbitmq-plugins enable rabbitmq_delayed_message_exchange
```

该插件在通过上述命令启用后就可以直接使用，不需要重启。

另外，如果您没有启用该插件，您可能为遇到类似这样的错误：

```
ERROR 156 --- [ 127.0.0.1:5672] o.s.a.r.c.CachingConnectionFactory : Channel shutdown: connection error; protocol method: #method(reply-code=503, reply-text=COMMAND_INVALID - unknown exchange type 'x-delayed-message', class-id=40, method-id=10)
```

#### 应用编码

下面通过编写一个简单的例子来具体体会一下这个属性的用法：

```
@EnableBinding(TestApplication.TestTopic.class)
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Slf4j
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
            log.info("Send: " + message);
            testTopic.output().send(MessageBuilder.withPayload(message).setHeader("x-delay", 5000).build());
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

内容很简单，既包含了消息的生产，也包含了消息消费。在`/sendMessage`接口的定义中，发送了一条消息，一条消息的头信息中包含了`x-delay`字段，该字段用来指定消息延迟的时间，单位为毫秒。所以上述代码发送的消息会在5秒之后被消费。在消息监听类`TestListener`中，对`TestTopic.INPUT`通道定义了`@StreamListener`，这里会对延迟消息做具体的逻辑。由于消息的消费是延迟的，从而变相实现了从消息发送那一刻起开始的定时任务。

在启动应用之前，还要需要做一些必要的配置，下面分消息生产端和消费端做说明：

**消息生产端**

```
spring.cloud.stream.bindings.example-topic-output.destination=delay-topic
spring.cloud.stream.rabbit.bindings.example-topic-output.producer.delayed-exchange=true
```

注意这里的一个新参数`spring.cloud.stream.rabbit.bindings.example-topic-output.producer.delayed-exchange`，用来开启延迟消息的功能，这样在创建exchange的时候，会将其设置为具有延迟特性的exchange，也就是用到上面我们安装的延迟消息插件的功能。

**消息消费端**

```
spring.cloud.stream.bindings.example-topic-input.destination=delay-topic
spring.cloud.stream.bindings.example-topic-input.group=test
spring.cloud.stream.rabbit.bindings.example-topic-input.consumer.delayed-exchange=true
```

在消费端也一样，需要设置`spring.cloud.stream.rabbit.bindings.example-topic-output.producer.delayed-exchange=true`。如果该参数不设置，将会出现类似下面的错误：

```
ERROR 9340 --- [ 127.0.0.1:5672] o.s.a.r.c.CachingConnectionFactory       : Channel shutdown: channel error; protocol method: #method<channel.close>(reply-code=406, reply-text=PRECONDITION_FAILED - inequivalent arg 'type' for exchange 'delay-topic' in vhost '/': received 'topic' but current is ''x-delayed-message'', class-id=40, method-id=10)
```

完成了上面配置之后，就可以启动应用，并尝试访问`localhost:8080/sendMessage?message=hello`接口来发送一个消息到MQ中了。此时可以看到类似下面的日志：

```
2019-01-02 23:28:45.318  INFO 96164 --- [ctor-http-nio-3] c.d.s.TestApplication$TestController     : Send: hello
2019-01-02 23:28:45.328  INFO 96164 --- [ctor-http-nio-3] o.s.a.r.c.CachingConnectionFactory       : Attempting to connect to: [localhost:5672]
2019-01-02 23:28:45.333  INFO 96164 --- [ctor-http-nio-3] o.s.a.r.c.CachingConnectionFactory       : Created new connection: rabbitConnectionFactory.publisher#5c5f9a03:0/SimpleConnection@3278a728 [delegate=amqp://guest@127.0.0.1:5672/, localPort= 53536]
2019-01-02 23:28:50.349  INFO 96164 --- [ay-topic.test-1] c.d.stream.TestApplication$TestListener  : Received: hello
```

从日志中可以看到，`Send: hello`和`Received: hello`两条输出之间间隔了5秒，符合我们上面编码设置的延迟时间。

## 深入思考

在代码层面已经完成了定时任务，那么我们如何查看延迟的消息数等信息呢？

此时，我们可以打开RabbitMQ的Web控制台，首先可以进入Exchanges页面，看看这个特殊exchange，具体如下：

[![img](https://blog.didispace.com/images/pasted-137.png)](https://blog.didispace.com/images/pasted-137.png)

可以看到，这个exchange的Type类型是`x-delayed-message`。点击该exchange的名称，进入详细页面，就可以看到更多具体信息了：

[![img](https://blog.didispace.com/images/pasted-136.png)](https://blog.didispace.com/images/pasted-136.png)

## 代码示例

本文示例读者可以通过查看下面仓库的中的`stream-delayed-message`项目：

- [Github](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- [Gitee](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)