# Spring Cloud构建微服务架构：消息驱动的微服务（消费组）【Dalston版】

**原创**

 [2018-01-29](https://blog.didispace.com/spring-cloud-starter-dalston-7-3/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 通过之前的[《消息驱动的微服务（入门）》](http://blog.didispace.com/spring-cloud-starter-dalston-7-1/)一文，相信很多朋友已经对Spring Cloud Stream有了一个初步的认识。但是，对于[《消息驱动的微服务（核心概念）》](http://blog.didispace.com/spring-cloud-starter-dalston-7-2/)一文中提到的一些核心概念可能还有些迷糊，下面我们将详细的来学习一下这些概念。本文我们就来学习和使用一下“消费组”这一概念。

## 使用消费组实现消息消费的负载均衡

通常在生产环境，我们的每个服务都不会以单节点的方式运行在生产环境，当同一个服务启动多个实例的时候，这些实例都会绑定到同一个消息通道的目标主题（Topic）上。

默认情况下，当生产者发出一条消息到绑定通道上，这条消息会产生多个副本被每个消费者实例接收和处理，但是有些业务场景之下，我们希望生产者产生的消息只被其中一个实例消费，这个时候我们需要为这些消费者设置消费组来实现这样的功能，实现的方式非常简单，我们只需要在服务消费者端设置`spring.cloud.stream.bindings.input.group`属性即可，比如我们可以这样实现：

- 先创建一个消费者应用`SinkReceiver`，实现了`greetings`主题上的输入通道绑定，它的实现如下：

```
@EnableBinding(value = {Sink.class})
public class SinkReceiver {

    private static Logger logger = LoggerFactory.getLogger(SinkReceiver.class);

    @StreamListener(Sink.INPUT)
    public void receive(User user) {
        logger.info("Received: " + user);
    }
}
```

- 为了将`SinkReceiver`的输入通道目标设置为`greetings`主题，以及将该服务的实例设置为同一个消费组，做如下设置：

```
spring.cloud.stream.bindings.input.group=Service-A
spring.cloud.stream.bindings.input.destination=greetings
```

通过`spring.cloud.stream.bindings.input.group`属性指定了该应用实例都属于`Service-A`消费组，而`spring.cloud.stream.bindings.input.destination`属性则指定了输入通道对应的主题名。

- 完成了消息消费者之后，我们再来实现一个消息生产者应用`SinkSender`，具体如下：

```
@EnableBinding(value = {Source.class})
public class SinkSender {

    private static Logger logger = LoggerFactory.getLogger(SinkSender.class);

    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "2000"))
    public MessageSource<String> timerMessageSource() {
        return () -> new GenericMessage<>("{\"name\":\"didi\", \"age\":30}");
    }

}
```

- 为消息生产者`SinkSender`做一些设置，让它的输出通道绑定目标也指向`greetings`主题，具体如下：

```
spring.cloud.stream.bindings.output.destination=greetings
```

到这里，对于消费分组的示例就已经完成了。分别运行上面实现的生产者与消费者，其中消费者我们启动多个实例。通过控制台，我们可以发现每个生产者发出的消息，会被启动的消费者以轮询的方式进行接收和输出。

**以下专题教程也许您会有兴趣**

- [Spring Boot基础教程](http://blog.didispace.com/Spring-Boot基础教程/)
- [Spring Cloud基础教程](http://blog.didispace.com/Spring-Cloud基础教程/)