# Spring Cloud构建微服务架构：消息驱动的微服务（消费分区）【Dalston版】

**原创**

 [2018-01-30](https://blog.didispace.com/spring-cloud-starter-dalston-7-4/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 通过上一篇[《消息驱动的微服务（消费组）》](http://blog.didispace.com/spring-cloud-starter-dalston-7-3/)的学习，我们已经能够在多实例环境下，保证同一消息只被一个消费者实例进行接收和处理。但是，对于一些特殊场景，除了要保证单一实例消费之外，还希望那些具备相同特征的消息都能够被同一个实例进行消费。这时候我们就需要对消息进行分区处理。

## 使用消息分区

在Spring Cloud Stream中实现消息分区非常简单，我们可以根据消费组示例做一些配置修改就能实现，具体如下：

- 在消费者应用`SinkReceiver`中，我们对配置文件做一些修改，具体如下：

```
spring.cloud.stream.bindings.input.group=Service-A

spring.cloud.stream.bindings.input.destination=greetings
spring.cloud.stream.bindings.input.consumer.partitioned=true
spring.cloud.stream.instanceCount=2
spring.cloud.stream.instanceIndex=0
```

从上面的配置中，我们可以看到增加了这三个参数：

1. `spring.cloud.stream.bindings.input.consumer.partitioned`：通过该参数开启消费者分区功能；
2. `spring.cloud.stream.instanceCount`：该参数指定了当前消费者的总实例数量；
3. `spring.cloud.stream.instanceIndex`：该参数设置当前实例的索引号，从0开始，最大值为`spring.cloud.stream.instanceCount`参数 - 1。我们试验的时候需要启动多个实例，可以通过运行参数来为不同实例设置不同的索引值。

- 在生产者应用`SinkSender`中，我们对配置文件也做一些修改，具体如下：

```
spring.cloud.stream.bindings.output.destination=greetings
spring.cloud.stream.bindings.output.producer.partitionKeyExpression=payload
spring.cloud.stream.bindings.output.producer.partitionCount=2
```

从上面的配置中，我们可以看到增加了这两个参数：

1. `spring.cloud.stream.bindings.output.producer.partitionKeyExpression`：通过该参数指定了分区键的表达式规则，我们可以根据实际的输出消息规则来配置SpEL来生成合适的分区键；
2. `spring.cloud.stream.bindings.output.producer.partitionCount`：该参数指定了消息分区的数量。

到这里消息分区配置就完成了，我们可以再次启动这两个应用，同时消费者启动多个，但需要注意的是要为消费者指定不同的实例索引号，这样当同一个消息被发给消费组时，我们可以发现只有一个消费实例在接收和处理这些相同的消息。

**以下专题教程也许您会有兴趣**

- [Spring Boot基础教程](http://blog.didispace.com/Spring-Boot基础教程/)
- [Spring Cloud基础教程](http://blog.didispace.com/Spring-Cloud基础教程/)