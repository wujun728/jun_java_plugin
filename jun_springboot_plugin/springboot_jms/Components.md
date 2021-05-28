# Spring JMS各组件详解

在上一篇文章[使用Spring/Spring Boot集成JMS的陷阱][ref-Pitfall.md]中讲到了在使用Spring JMS组件时存在这一些性能陷阱，本文会着该文讲解一下Spring JMS的各个组件、它们的作用以及正确使用的方法。

## JmsTemplate

用来send、receive消息（receive是同步式的，会block）。每个``JmsTemplate``实例拥有自己的配置，比如：``connectionFactory``、``sessionTransacted``、``sessionAcknowledgeMode``、``deliveryMode``、``timeToLive``等等，所以需根据不同场景配置提供不同的`JmsTemplate` Bean而不是一个Singleton Bean通吃所有JMS操作。

下面是类图（只包含了部分关键属性）：

![JmsTemplate类图][uml-jms-template]

## ConnectionFactory

Spring提供了两个``javax.jms.ConnectionFactory``的实现：[SingleConnectionFactory][javadoc-scf]和[CachingConnectionFactory][javadoc-ccf]。它们实际上是一种Wrapper，用来缓存如：``Connection``、``Session``、``MessageProducer``、``MessageConsumer``。

事实上[JmsTemplate的Javadoc][javadoc-jms-template]提到过：

> NOTE: The ConnectionFactory used with this template should return pooled Connections (or a single shared Connection) as well as pooled Sessions and MessageProducers. Otherwise, performance of ad-hoc JMS operations is going to suffer.

在Spring JMS文档的[Caching Messaging Resources][doc-jms-caching-resources]中也提到了需要优化资源使用以提升性能：

> The standard API involves creating many intermediate objects. To send a message the following 'API' walk is performed 
>  ConnectionFactory->Connection->Session->MessageProducer->send
> Between the ConnectionFactory and the Send operation there are three intermediate objects that are created and destroyed. To optimise the resource usage and increase performance two implementations of `ConnectionFactory` are provided.

下面是类图（只包含了部分关键属性）：

![ConnectionFactory][uml-connection-factory]

### SingleConnectionFactory

[SingleConnectionFactory][javadoc-scf]顾名思义，无论调用多少次``createConnection(..)``都返回同一个``Connection``实例。但是它并不缓存``Session``，也就是说调用一次``createSession(...)``就会创建一个新的实例。

可以通过[SingleConnectionFactoryTest][src-SingleConnectionFactoryTest]了解详情。

所以在大多数情况下，不推荐使用``SingleConnectionFactory``。

### CachingConnectionFactory

[CachingConnectionFactory][javadoc-ccf]继承自``SingleConnectionFactory``，除了依然保留缓存同一个``Connection``实例的特性外，还增加了对于``Session``、``MessageProducer``、``MessageConsumer``的缓存。

`CachingConnectionFactory`其内部维护了一个`Acknowledge Mode -> List<Session>`的Map，sessionCacheSize实际上指的是`List<Session>`的大小，所以最多会有4 * sessionCacheSize数量的`Session`被缓存（因为JMS规定了四种Acknowledge Mode）。
并且`CachingConnectionFactory`其本质不是一个Object Pool，所以不会因为实际请求Session数量超出sessionCacheSize导致block或者返回null，可以放心使用。

`CachingConnectionFactory`返回的每个`Session`内部都有`ConsumerCacheKey -> MessageConsumer`以及`DestinationCacheKey -> MessageProducer`的Map，用来缓存``MessageProducer``和``MessageConsumer``。

可以通过[CachingConnectionFactory][src-CachingConnectionFactory]了解详情。

## MessageListenerContainer

Spring JMS中有一个特性[MessageListenerContainer][javadoc-MessageListenerContainer]，按照官方文档的说法：

> A message listener container is used to receive messages from a JMS message queue and drive the MessageListener that is injected into it.

上面提到的``MessageListener``就是``javax.jms.MessageListener``，第一次看到这个东西感觉有点奇怪，因为``MessageListener``的正规用法应该``MessageConsumer.setMessageListener()``就行了。

因为``MessageListenerContainer``继承自``SmartLifeCycle``，所以它提供了程序启动时开启connection、session，程序关闭是关闭session、connection的功能，能够让你不用操心资源回收问题。

下面介绍一下两个实现[SimpleMessageListenerContainer][javadoc-SimpleMessageListenerContainer]和[DefaultMessageListenerContainer][javadoc-DefaultMessageListenerContainer]。

下面是类图（只包含了部分关键属性）：

![MessageListenerContainer][uml-message-listener-container]

### SimpleMessageListenerContainer

[SimpleMessageListenerContainer][javadoc-SimpleMessageListenerContainer]使用``MessageConsumer.setMessageListener()``来监听消息，它不支持参与外部事务（比如PlatformTransactionManager）。

它是可以持有多个`MessageConsumer`实例的。代码如下：

```java
// ...

private int concurrentConsumers = 1;
private Set<Session> sessions;
private Set<MessageConsumer> consumers;

// ...
protected void initializeConsumers() throws JMSException {
  // Register Sessions and MessageConsumers.
  synchronized (this.consumersMonitor) {
    if (this.consumers == null) {
      this.sessions = new HashSet<Session>(this.concurrentConsumers);
      this.consumers = new HashSet<MessageConsumer>(this.concurrentConsumers);
      Connection con = getSharedConnection();
      for (int i = 0; i < this.concurrentConsumers; i++) {
        Session session = createSession(con);
        MessageConsumer consumer = createListenerConsumer(session);
        this.sessions.add(session);
        this.consumers.add(consumer);
      }
    }
  }
}
```

其处理消息的方式有两种：1）传统的``MessageConsumer.setMessageListener()``；2）使用`Executor`。

```java
if (this.taskExecutor != null) {
  consumer.setMessageListener(new MessageListener() {
    @Override
    public void onMessage(final Message message) {
      taskExecutor.execute(new Runnable() {
        @Override
        public void run() {
          processMessage(message, session);
        }
      });
    }
  });
}
else {
  consumer.setMessageListener(new MessageListener() {
    @Override
    public void onMessage(Message message) {
      processMessage(message, session);
    }
  });
}
```


### DefaultMessageListenerContainer

[DefaultMessageListenerContainer][javadoc-DefaultMessageListenerContainer]和[SimpleMessageListenerContainer][javadoc-SimpleMessageListenerContainer]不同，它使用``MessageConsumer.receive()``来处理消息，并且支持XA transaction。

因为``receive()``是同步的、blocking方法，其性能没有``setMessageListener()``好，所以它非常依赖多线程(``TaskExecutor``)，这也也带来来dynamic scaling的好处。

请注意不要对Topic采用多线程，否则会收到重复的消息，详情见[官方文档][javadoc-DefaultMessageListenerContainer-setConcurrentConsumers]。

## 异步接收消息

同步接收消息的方式有``JmsTemplate.receive*()``和``MessageConsumer.receive*()``，这里不多讲，重点讲异步接收消息的几种方式。

### MessageListener & MessageListenerContainer

把``MessageListener``包装到``MessageListenerContainer``里接收消息，例子参见官方文档[Asynchronous Reception - Message-Driven POJOs][ref-jms-asynchronousMessageReception]

### SessionAwareMessageListener

[SessionAwareMessageListener][javadoc-SessionAwareMessageListener]是Spring提供和``MessageListener``类似的接口，``MessageListenerContainer``支持这个接口，用法和``MessageListener``一样。

### MessageListenerAdapter

[MessageListenerAdapter][javadoc-MessageListenerAdapter]是Spring提供的另一个异步接收消息的方式，它``MessageListener``与``SessionAwareMessageListener``更灵活，因为它采用反射机制来把消息传递到你的接收消息的方法上。

使用方法见官方文档[the SessionAwareMessageListener interface][ref-jms-receiving-async-session-aware-message-listener]。

### @JmsListener

[@JmsListener][javadoc-JmsListener]是另一种接收消息的方法，怎么使用它可以看官方文档[Annotation-driven listener endpoints][ref-jms-annotated]。

``@JmsListener``和``MessageListener``、``SessionAwareMessageListener``、``MessageListenerAdapter``一样也需要一个Container，用户可以通过``@JmsListener.containerFactory``属性来指定[JmsListenerContainerFactory][javadoc-JmsListenerContainerFactory]。

Spring提供了两种JmsListenerContainerFactory实现：

1. [DefaultJmsListenerContainerFactory][javadoc-DefaultJmsListenerContainerFactory]，用来生产DefaultMessageListenerContainer，Spring Boot提供``DefaultJmsListenerContainerFactoryConfigurer``作为配置工具
1. [SimpleJmsListenerContainerFactory][javadoc-SimpleJmsListenerContainerFactory]，用来生产SimpleMessageListenerContainer

所以在使用``@JmsListener``需要仔细的选择正确的``JmsListenerContainerFactory``，而不是全局采用一种配置。

## 总结

使用Spring JMS时有需要注意以下三点：

1. 根据实际情况，配置合适的ConnectionFactory Bean，如有需要可以有多个ConnectionFactory Bean。
1. JmsTemplate, MessageListenerContainer, JmsListenerContainerFactory需根据实际情况配置不同Bean，避免全局使用一套。
1. JmsTemplate, MessageListenerContainer, JmsListenerContainerFactory选择合适的ConnectionFactory。
1. 设定好合适的Executor/线程池大小，避免大量Thread block。

下面是一张各个组件的关系图。

![关系图][uml-relation]

## 参考资料

* [Spring JMS][ref-spring-jms]
* [Spring JMS Listener Adapters][dzone-spring-jms-listener-adapters]
* [JMS Javadoc][javadoc-jms]

[ref-spring-jms]: https://docs.spring.io/spring/docs/4.3.9.RELEASE/spring-framework-reference/html/jms.html
[javadoc-jms-template]: https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/core/JmsTemplate.html
[javadoc-scf]: https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/connection/SingleConnectionFactory.html
[javadoc-ccf]: https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/connection/CachingConnectionFactory.html
[doc-jms-caching-resources]: https://docs.spring.io/spring/docs/4.3.9.RELEASE/spring-framework-reference/html/jms.html#jms-caching-resources
[dzone-spring-jms-listener-adapters]: https://dzone.com/articles/spring-jms-listener-adapters
[src-SingleConnectionFactoryTest]: src/test/java/me/chanjar/springjmslearn/SingleConnectionFactoryTest.java
[src-CachingConnectionFactory]: src/test/java/me/chanjar/springjmslearn/CachingConnectionFactoryTest.java
[javadoc-MessageListenerContainer]: https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/listener/MessageListenerContainer.html
[javadoc-DefaultMessageListenerContainer]: https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/listener/DefaultMessageListenerContainer.html
[javadoc-SimpleMessageListenerContainer]: https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/listener/SimpleMessageListenerContainer.html
[javadoc-jms]: https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html
[uml-jms-template]: uml/类图-JmsTemplate.png
[uml-connection-factory]: uml/类图-ConnectionFactory.png
[uml-message-listener-container]: uml/类图-MessageListenerContainer.png
[javadoc-DefaultMessageListenerContainer-setConcurrentConsumers]: https://docs.spring.io/spring/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/listener/DefaultMessageListenerContainer.html#setConcurrentConsumers-int-
[ref-jms-asynchronousMessageReception]: https://docs.spring.io/spring/docs/4.3.9.RELEASE/spring-framework-reference/html/jms.html#jms-asynchronousMessageReception
[javadoc-SessionAwareMessageListener]: https://docs.spring.io/spring/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/listener/SessionAwareMessageListener.html
[javadoc-MessageListenerAdapter]: https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/listener/adapter/MessageListenerAdapter.html
[ref-jms-receiving-async-session-aware-message-listener]: https://docs.spring.io/spring/docs/4.3.9.RELEASE/spring-framework-reference/html/jms.html#jms-receiving-async-session-aware-message-listener
[javadoc-JmsListener]: https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/annotation/JmsListener.html
[ref-jms-annotated]: https://docs.spring.io/spring/docs/4.3.9.RELEASE/spring-framework-reference/html/jms.html#jms-annotated
[javadoc-JmsListenerContainerFactory]: https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/config/JmsListenerContainerFactory.html
[javadoc-DefaultJmsListenerContainerFactory]: https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/config/DefaultJmsListenerContainerFactory.html
[javadoc-SimpleJmsListenerContainerFactory]: https://docs.spring.io/spring-framework/docs/4.3.9.RELEASE/javadoc-api/org/springframework/jms/config/SimpleJmsListenerContainerFactory.html
[uml-relation]: uml/类图-关系.png
[ref-Pitfall.md]: Pitfall.md
