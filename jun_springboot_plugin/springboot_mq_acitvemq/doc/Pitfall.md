# 使用Spring/Spring Boot集成JMS的陷阱

本文旨在指出Spring/Spring Boot中集成JMS的一些性能陷阱，在另一篇文章[Spring JMS各组件详解][ref-Components.md]里有Spring JMS组件介绍及如何正确使用的内容。

## JmsTemplate性能问题

Spring提供了``JmsTemplate``用来简化JMS的操作，但是``JmsTemplate``的性能是有很大问题的，主要体现在以下几个方面：

### 频繁创建connection,session,producer

根据Artemis[官方文档][ref-artemis-anti-pattern]的说法，``JmsTemplate``是一种anti-pattern，如果在使用``JmsTemplate``的情况下觉得很慢，那么就不要怪Artemis。

并且``Connection``，``Session``，``Producer``，``Consumer``这些对象本身设计上是可以复用的。因此``JmsTemplate``的做法会大大降低性能，并且将大部分的时间都花在反复重建这些对象上。

**Spring提供的Workaround**

可以让``JmsTemplate``使用``SingleConnectionFactory``避免频繁创建``Connection``的问题。或者其子类``CachingConnectionFactory``避免频繁创建``Connection``，``Session``，``Producer``，``Consumer``的问题。

### 频繁创建临时Queue

[``JmsTemplate#sendAndReceive``][javadoc-JmsTemplate-sendAndReceive]方法可以用来模拟RPC调用过程，内部原理是：

1. A程序创建一个临时Queue作为接受相应的Queue
2. send一个Message到Queue，并在这个临时Queue上等待结果
3. B程序consume了这个Message把结果send到那个临时Queue
4. A接受到结果，把这个临时Queue干掉

当然整个过程中还包括前面提到的创建``Connection``，``Session``，``Producer``，``Consumer``的动作。

不出所料，Artemis[官方文档][ref-artemis-anti-pattern]也提到了，频繁创建临时Queue是一种anti-pattern，会大大影响性能。

## @JmsListener性能问题

### 使用sync方法consume消息

使用``@JmsListener``注解可以很方便的用来consume消息，但它是同步而非异步，这个和[官方文档][ref-spring-async-message]所说的恰恰相反（关于sync/async consume消息的[资料][ref-jee-message-consumer]）。

Spring Boot的[``JmsAnnotationDrivenConfiguration``][src-JmsAnnotationDrivenConfiguration]默认使用``DefaultJmsListenerContainerFactory``生成``DefaultMessageListenerContainer ``，而它的内部原理是使用``TaskExecutor``发起多个线程同时从Queue中拉取消息，这也就是为什么[Spring官方文档][javadoc-setConcurrentConsumers]里说如果监听的是``Topic``且``concurrency`` > 1，那么可能会收到重复消息的原因。

[DefaultMessageListenerContainer][javadoc-DefaultMessageListenerContainer]的javadoc中说道：

> Actual MessageListener execution happens in asynchronous work units which are created through Spring's TaskExecutor abstraction

这里就有个矛盾，如果使用async的方式consume消息，那么只需给consumer设置``MessageListener``就行了，何必使用``TaskExecutor``呢？

一看代码果然不出所料：

1. ``DefaultMessageListenerContainer#run``[call][src-DefaultMessageListenerContainer.java#L1060]``invokeListener``
1. 然后[call][src-DefaultMessageListenerContainer.java#L1164]``AbstractPollingMessageListenerContainer#receiveAndExecute``
1. 然后[call][src-AbstractPollingMessageListenerContainer.java#L235]``doReceiveAndExecute``
1. 然后[call][src-AbstractPollingMessageListenerContainer.java#L302]``receiveMessage``
1. 然后[call][src-AbstractPollingMessageListenerContainer.java#L416]``receiveFromConsumer``
1. 然后[call][src-JmsDestinationAccessor.java#L128]``JmsDestinationAccessor#receiveFromConsumer``这个方法调用了``MessageConsumer#consume``

也就是说Spring只是使用一个或多个线程在不停的同步的consume消息而已。

虽然可以使用``concurrency``参数提高并发，但是多线程从Queue/Topic中consume消息的性能比``javax.jms.MessageConsumer#setMessageListener``的方法要低上很多。

有兴趣的同学可以使用Artemis Example（[下载地址][artemis-download]）里的JMS Perf代码做一下测试，它的测试代码用的是``javax.jms.MessageConsumer#setMessageListener``的方式来consume消息的，在配置正确的情况下可以达到接近10w/s。至于使用``MessageConsumer.receive``的性能测试则留给同学自己做了。

### 为@JmsListener创建的session默认transacted=true

还是之前提到的[JmsAnnotationDrivenConfiguration][src-JmsAnnotationDrivenConfiguration#L77]，使用的[DefaultJmsListenerContainerFactoryConfigurer][src-DefaultJmsListenerContainerFactoryConfigurer.java#L94]默认是把session设置为transacted的。

根据测算，当一个session是transacted的时候其性能会相差20%，有兴趣的同学可以使用Artemis Example（[下载地址][artemis-download]）里的JMS Perf代码做一下测试。

下载之后找到``examples/jms/perf``目录，看这个目录下的readme.html获得执行方法，在执行之前修改``src/main/resources/perf.properties``文件，下面是部分参数的解释：

1. num-messages，测试多少个消息
2. num-warmup-messages，热身用的消息数，热身过之后性能会提升
3. durable，对应DeliveryMode
4. transacted，是否transacted
5. batch-size，批量commit的大小
6. drain-queue，是否测试前先把队列里已有的消息都清空

可以使用以下两套配置对比transacted的性能差别：

配置一，transacted=false：

```java
num-messages=100000
num-warmup-messages=1000
message-size=1024
durable=false
transacted=false
batch-size=1
drain-queue=true
destination-lookup=perfQueue
connection-factory-lookup=/ConnectionFactory
throttle-rate=-1
dups-ok-acknowledge=false
disable-message-id=true
disable-message-timestamp=true
```

配置二，transacted=true：

```java
num-messages=100000
num-warmup-messages=1000
message-size=1024
durable=false
transacted=true
batch-size=1
drain-queue=true
destination-lookup=perfQueue
connection-factory-lookup=/ConnectionFactory
throttle-rate=-1
dups-ok-acknowledge=false
disable-message-id=true
disable-message-timestamp=true
```

### @JmsListener创建的session默认加入了事务控制

关于加入事务控制是否会有性能问题没有实际测试过，不过值得注意的这是Spring Boot的默认行为。

相关连接：[代码1][src-JmsAnnotationDrivenConfiguration.java#L69], [代码2][src-DefaultJmsListenerContainerFactoryConfigurer.java#L90]，[代码3][src-DefaultJmsListenerContainerFactory.java#L122]，[Javadoc][javadoc-AbstractPollingMessageListenerContainer-setTransactionManager]。


## Spring Boot配置

### ConnectionFactory全局只有一个实例

Spring将JMS的集成变得非常简单，只需提供几个配置参数就可以了，但是只能提供一种默认配置，不管业务场景如何都使用同一种配置是非常有问题的。

spring-boot提供了以下几种方式（[文档][ref-artemis-support]）集成JMS：

1. JNDI
2. Artemis, native模式和embedded模式
3. ActiveMQ

这几种模式的缺点都是只能配置一个ConnectionFactory，这对于简单应用来说没问题，对于复杂应用来说就显得不够用了，这让你丧失了针对不同场景配置不同ConnectionFactory的机会。
而且Artemis的native模式只支持host:port，不支持更丰富参数的url模式。

### @JmsListener的默认配置也就只提供一种

看过[``DefaultJmsListenerContainerFactoryConfigurer``][src-DefaultJmsListenerContainerFactoryConfigurer]和[``JmsAnnotationDrivenConfiguration``][src-JmsAnnotationDrivenConfiguration]的代码就明白了。


[ref-artemis-anti-pattern]: http://activemq.apache.org/artemis/docs/1.5.5/perf-tuning.html#avoiding-anti-patterns
[javadoc-JmsTemplate-sendAndReceive]: https://docs.spring.io/spring/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/core/JmsTemplate.html#sendAndReceive-javax.jms.Destination-org.springframework.jms.core.MessageCreator-
[ref-spring-async-message]: https://docs.spring.io/spring/docs/4.3.9.RELEASE/spring-framework-reference/htmlsingle/#jms-asynchronousMessageReception
[ref-jee-message-consumer]: https://docs.oracle.com/javaee/6/tutorial/doc/bnceh.html#bncep
[src-JmsAnnotationDrivenConfiguration#L77]: https://github.com/spring-projects/spring-boot/blob/v1.5.9.RELEASE/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jms/JmsAnnotationDrivenConfiguration.java#L77
[src-JmsAnnotationDrivenConfiguration]: https://github.com/spring-projects/spring-boot/blob/v1.5.9.RELEASE/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jms/JmsAnnotationDrivenConfiguration.java
[javadoc-setConcurrentConsumers]: https://docs.spring.io/spring/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/listener/DefaultMessageListenerContainer.html#setConcurrentConsumers-int-
[javadoc-DefaultMessageListenerContainer]: https://docs.spring.io/spring/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/listener/DefaultMessageListenerContainer.html
[src-DefaultMessageListenerContainer.java#L1060]: https://github.com/spring-projects/spring-framework/blob/v4.3.9.RELEASE/spring-jms/src/main/java/org/springframework/jms/listener/DefaultMessageListenerContainer.java#L1060
[src-DefaultMessageListenerContainer.java#L1164]: https://github.com/spring-projects/spring-framework/blob/v4.3.9.RELEASE/spring-jms/src/main/java/org/springframework/jms/listener/DefaultMessageListenerContainer.java#L1166
[src-AbstractPollingMessageListenerContainer.java#L235]: https://github.com/spring-projects/spring-framework/blob/v4.3.9.RELEASE/spring-jms/src/main/java/org/springframework/jms/listener/AbstractPollingMessageListenerContainer.java#L235
[src-AbstractPollingMessageListenerContainer.java#L302]: https://github.com/spring-projects/spring-framework/blob/v4.3.9.RELEASE/spring-jms/src/main/java/org/springframework/jms/listener/AbstractPollingMessageListenerContainer.java#L302
[src-AbstractPollingMessageListenerContainer.java#L416]: https://github.com/spring-projects/spring-framework/blob/v4.3.9.RELEASE/spring-jms/src/main/java/org/springframework/jms/listener/AbstractPollingMessageListenerContainer.java#L416
[src-JmsDestinationAccessor.java#L128]: https://github.com/spring-projects/spring-framework/blob/v4.3.9.RELEASE/spring-jms/src/main/java/org/springframework/jms/support/destination/JmsDestinationAccessor.java#L128
[artemis-download]: http://activemq.apache.org/artemis/download.html
[src-DefaultJmsListenerContainerFactoryConfigurer.java#L94]: https://github.com/spring-projects/spring-boot/blob/v1.5.9.RELEASE/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jms/DefaultJmsListenerContainerFactoryConfigurer.java#L94
[src-JmsAnnotationDrivenConfiguration.java#L69]: https://github.com/spring-projects/spring-boot/blob/v1.5.9.RELEASE/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jms/JmsAnnotationDrivenConfiguration.java#L69
[src-DefaultJmsListenerContainerFactoryConfigurer.java#L90]: https://github.com/spring-projects/spring-boot/blob/v1.5.9.RELEASE/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jms/DefaultJmsListenerContainerFactoryConfigurer.java#L90
[src-DefaultJmsListenerContainerFactory.java#L122]: https://github.com/spring-projects/spring-framework/blob/v4.3.9.RELEASE/spring-jms/src/main/java/org/springframework/jms/config/DefaultJmsListenerContainerFactory.java#L122
[javadoc-AbstractPollingMessageListenerContainer-setTransactionManager]: https://docs.spring.io/spring/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/listener/AbstractPollingMessageListenerContainer.html#setTransactionManager-org.springframework.transaction.PlatformTransactionManager-
[ref-artemis-support]: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-messaging.html#boot-features-artemis
[src-DefaultJmsListenerContainerFactoryConfigurer]: https://github.com/spring-projects/spring-boot/blob/v1.5.9.RELEASE/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/jms/DefaultJmsListenerContainerFactoryConfigurer.java
[ref-Components.md]: Components.md
