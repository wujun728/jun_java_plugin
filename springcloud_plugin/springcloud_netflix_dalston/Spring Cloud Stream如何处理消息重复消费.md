# Spring Cloud Stream如何处理消息重复消费

**原创**

 [2018-10-17](https://blog.didispace.com/spring-cloud-starter-dalston-7-5/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

最近收到好几个类似的问题：使用Spring Cloud Stream操作RabbitMQ或Kafka的时候，出现消息重复消费的问题。通过沟通与排查下来主要还是用户对消费组的认识不够。其实，在之前的博文以及《Spring Cloud微服务实战》一书中都有提到关于消费组的概念以及作用。

那么什么是消费组呢？为什么要用消费组？它解决什么问题呢？摘录一段之前博文的内容，来解答这些疑问：

> 通常在生产环境，我们的每个服务都不会以单节点的方式运行在生产环境，当同一个服务启动多个实例的时候，这些实例都会绑定到同一个消息通道的目标主题（Topic）上。默认情况下，当生产者发出一条消息到绑定通道上，这条消息会产生多个副本被每个消费者实例接收和处理（出现上述重复消费问题）。但是有些业务场景之下，我们希望生产者产生的消息只被其中一个实例消费，这个时候我们需要为这些消费者设置消费组来实现这样的功能。

详细也可查看原文：[消息驱动的微服务（消费组）](http://blog.didispace.com/spring-cloud-starter-dalston-7-1/)。

下面，通过一个例子来看看如何使用消费组：

## 问题重现

### 构建消息消费端

第一步：创建绑定接口，绑定`example-topic`输入通道（默认情况下，会绑定到RabbitMQ的同名Exchange或Kafaka的同名Topic）。

```
interface ExampleBinder {

    String NAME = "example-topic";

    @Input(NAME)
    SubscribableChannel input();

}
```

第二步：对上述输入通道创建监听与处理逻辑。

```
@EnableBinding(ExampleBinder.class)
public class ExampleReceiver {

    private static Logger logger = LoggerFactory.getLogger(ExampleReceiver.class);

    @StreamListener(ExampleBinder.NAME)
    public void receive(String payload) {
        logger.info("Received: " + payload);
    }

}
```

第三步；创建应用主类和配置文件

```
@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

}
spring.application.name=stream-consumer-group
server.port=0
```

这里设置`server.port=0`，以方便在本地启动多实例来重现问题。

完成上述操作之后，启动两个该应用的实例，以备后续调用。

### 构建消息生产端

比较简单，需要注意的是，使用`@Output`创建一个同名的输出绑定，这样发出的消息才能被上述启动的实例接收到。具体实现如下：

```
@RunWith(SpringRunner.class)
@EnableBinding(value = {ExampleApplicationTests.ExampleBinder.class})
public class ExampleApplicationTests {

	@Autowired
	private ExampleBinder exampleBinder;

	@Test
	public void exampleBinderTester() {
        exampleBinder.output().send(MessageBuilder.withPayload("Produce a message from : http://blog.didispace.com").build());
	}

	public interface ExampleBinder {

		String NAME = "example-topic";

		@Output(NAME)
		MessageChannel output();

	}

}
```

启动上述测试用例之后，可以发现之前启动的两个实例都收到的消息，并在日志中打印了：`Received: Produce a message from : http://blog.didispace.com`。消息重复消费的问题成功重现！

## 使用消费组解决问题

如何解决上述消息重复消费的问题呢？我们只需要在配置文件中增加如下配置即可：

```
spring.cloud.stream.bindings.example-topic.group=aaa
```

当我们指定了某个绑定所指向的消费组之后，往当前主题发送的消息在每个订阅消费组中，只会有一个订阅者接收和消费，从而实现了对消息的负载均衡。只所以之前会出现重复消费的问题，是由于默认情况下，任何订阅都会产生一个匿名消费组，所以每个订阅实例都会有自己的消费组，从而当有消息发送的时候，就形成了广播的模式。

另外，需要注意上述配置中`example-topic`是在代码中`@Output`和`@Input`中传入的名字。

## 代码示例

本文示例读者可以通过查看下面仓库的中的`stream-consumer-group`项目：

- [Github](https://github.com/dyc87112/SpringCloud-Learning/tree/master/2-Dalston版教程示例/)
- [Gitee](https://gitee.com/didispace/SpringCloud-Learning/tree/master/2-Dalston版教程示例)

**如果您对这些感兴趣，欢迎star、follow、收藏、转发给予支持！**

#### 以下专题教程也许您会有兴趣

- [Spring Boot基础教程](http://blog.didispace.com/Spring-Boot基础教程/)
- [Spring Cloud基础教程](http://blog.didispace.com/Spring-Cloud基础教程/)