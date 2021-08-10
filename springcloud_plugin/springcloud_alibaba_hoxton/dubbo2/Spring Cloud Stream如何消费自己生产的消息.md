# Spring Cloud Stream如何消费自己生产的消息

**原创**

 [2018-11-18](https://blog.didispace.com/spring-cloud-starter-finchley-7-1/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

在上一篇[《Spring Cloud Stream如何处理消息重复消费》](http://blog.didispace.com/spring-cloud-starter-dalston-7-5/)中，我们通过消费组的配置解决了多实例部署情况下消息重复消费这一入门时的常见问题。本文将继续说说在另外一个被经常问到的问题：如果微服务生产的消息自己也想要消费一份，应该如何实现呢？

## 常见错误

在放出标准答案前，先放出一个常见的错误姿势和告警信息（以便您可以通过搜索引擎找到这里^_^）。以下错误基于Spring Boot 2.0.5、Spring Cloud Finchley SR1。

首先，根据入门示例，为了生产和消费消息，需要定义两个通道：一个输入、一个输出。比如下面这样：

```
public interface TestTopic {

    String OUTPUT = "example-topic";
    String INPUT = "example-topic";

    @Output(OUTPUT)
    MessageChannel output();

    @Input(INPUT)
    SubscribableChannel input();

}
```

通过`INPUT`和`OUTPUT`使用相同的名称，让生产消息和消费消息指向相同的Topic，从而实现消费自己发出的消息。

接下来，创建一个HTTP接口，并通过上面定义的输出通道触来生产消息，比如：

```
@Slf4j
@RestController
public class TestController {

    @Autowired
    private TestTopic testTopic;

    @GetMapping("/sendMessage")
    public String messageWithMQ(@RequestParam String message) {
        testTopic.output().send(MessageBuilder.withPayload(message).build());
        return "ok";
    }

}
```

已经有生产消息的实现，下面来创建对输入通道的监听，以实现消息的消费逻辑。

```
@Slf4j
@Component
public class TestListener {

    @StreamListener(TestTopic.INPUT)
    public void receive(String payload) {
        log.info("Received: " + payload);
        throw new RuntimeException("BOOM!");
    }

}
```

最后，在应用主类中，使用@EnableBinding注解来开启它，比如：

```
@EnableBinding(TestTopic.class)
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
```

看似天衣无缝的操作，然而在启动的瞬间，你可能收到了下面这样的错误：

```
org.springframework.beans.factory.BeanDefinitionStoreException: Invalid bean definition with name 'example-topic' defined in com.didispace.stream.TestTopic: bean definition with this name already exists - Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=com.didispace.stream.TestTopic; factoryMethodName=input; initMethodName=null; destroyMethodName=null
	at org.springframework.cloud.stream.binding.BindingBeanDefinitionRegistryUtils.registerBindingTargetBeanDefinition(BindingBeanDefinitionRegistryUtils.java:64) ~[spring-cloud-stream-2.0.1.RELEASE.jar:2.0.1.RELEASE]
	at org.springframework.cloud.stream.binding.BindingBeanDefinitionRegistryUtils.registerOutputBindingTargetBeanDefinition(BindingBeanDefinitionRegistryUtils.java:54) ~[spring-cloud-stream-2.0.1.RELEASE.jar:2.0.1.RELEASE]
	at org.springframework.cloud.stream.binding.BindingBeanDefinitionRegistryUtils.lambda$registerBindingTargetBeanDefinitions$0(BindingBeanDefinitionRegistryUtils.java:86) ~[spring-cloud-stream-2.0.1.RELEASE.jar:2.0.1.RELEASE]
	at org.springframework.util.ReflectionUtils.doWithMethods(ReflectionUtils.java:562) ~[spring-core-5.0.9.RELEASE.jar:5.0.9.RELEASE]
	at org.springframework.util.ReflectionUtils.doWithMethods(ReflectionUtils.java:541) ~[spring-core-5.0.9.RELEASE.jar:5.0.9.RELEASE]
	at org.springframework.cloud.stream.binding.BindingBeanDefinitionRegistryUtils.registerBindingTargetBeanDefinitions(BindingBeanDefinitionRegistryUtils.java:76) ~[spring-cloud-stream-2.0.1.RELEASE.jar:2.0.1.RELEASE]
	at org.springframework.cloud.stream.config.BindingBeansRegistrar.registerBeanDefinitions(BindingBeansRegistrar.java:45) ~[spring-cloud-stream-2.0.1.RELEASE.jar:2.0.1.RELEASE]
	at org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader.lambda$loadBeanDefinitionsFromRegistrars$1(ConfigurationClassBeanDefinitionReader.java:358) ~[spring-context-5.0.9.RELEASE.jar:5.0.9.RELEASE]
	at java.util.LinkedHashMap.forEach(LinkedHashMap.java:684) ~[na:1.8.0_151]
	at org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader.loadBeanDefinitionsFromRegistrars(ConfigurationClassBeanDefinitionReader.java:357) ~[spring-context-5.0.9.RELEASE.jar:5.0.9.RELEASE]
	at org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader.loadBeanDefinitionsForConfigurationClass(ConfigurationClassBeanDefinitionReader.java:145) ~[spring-context-5.0.9.RELEASE.jar:5.0.9.RELEASE]
	at org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader.loadBeanDefinitions(ConfigurationClassBeanDefinitionReader.java:117) ~[spring-context-5.0.9.RELEASE.jar:5.0.9.RELEASE]
	at org.springframework.context.annotation.ConfigurationClassPostProcessor.processConfigBeanDefinitions(ConfigurationClassPostProcessor.java:328) ~[spring-context-5.0.9.RELEASE.jar:5.0.9.RELEASE]
	at org.springframework.context.annotation.ConfigurationClassPostProcessor.postProcessBeanDefinitionRegistry(ConfigurationClassPostProcessor.java:233) ~[spring-context-5.0.9.RELEASE.jar:5.0.9.RELEASE]
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanDefinitionRegistryPostProcessors(PostProcessorRegistrationDelegate.java:271) ~[spring-context-5.0.9.RELEASE.jar:5.0.9.RELEASE]
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:91) ~[spring-context-5.0.9.RELEASE.jar:5.0.9.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:694) ~[spring-context-5.0.9.RELEASE.jar:5.0.9.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:532) ~[spring-context-5.0.9.RELEASE.jar:5.0.9.RELEASE]
	at org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext.refresh(ReactiveWebServerApplicationContext.java:61) ~[spring-boot-2.0.5.RELEASE.jar:2.0.5.RELEASE]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:780) [spring-boot-2.0.5.RELEASE.jar:2.0.5.RELEASE]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:412) [spring-boot-2.0.5.RELEASE.jar:2.0.5.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:333) [spring-boot-2.0.5.RELEASE.jar:2.0.5.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1277) [spring-boot-2.0.5.RELEASE.jar:2.0.5.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1265) [spring-boot-2.0.5.RELEASE.jar:2.0.5.RELEASE]
	at com.didispace.stream.TestApplication.main(TestApplication.java:13) [classes/:na]
```

## 正确姿势

根据错误提示：`Invalid bean definition with name 'example-topic' defined in com.didispace.stream.TestTopic: bean definition with this name already exists`，没有启动成功的原因是已经存在了一个名为`example-topic`的Bean，那么为什么会重复创建这个Bean呢？

实际上，在F版的Spring Cloud Stream中，当我们使用`@Output`和`@Input`注解来定义消息通道时，都会根据传入的通道名称来创建一个Bean。而在上面的例子中，我们定义的`@Output`和`@Input`名称是相同的，因为我们系统输入和输出是同一个Topic，这样才能实现对自己生产消息的消费。

既然这样，我们定义相同的通道名是行不通了，那么我们只能通过定义不同的通道名，并为这两个通道配置相同的目标Topic来将这一对输入输出指向同一个实际的Topic。对于上面的错误程序，只需要做如下两处改动：

第一步：修改通道名，使用不同的名字

```
public interface TestTopic {

    String OUTPUT = "example-topic-output";
    String INPUT = "example-topic-input";

    @Output(OUTPUT)
    MessageChannel output();

    @Input(INPUT)
    SubscribableChannel input();

}
```

第二步：在配置文件中，为这两个通道设置相同的Topic名称，比如：

```
spring.cloud.stream.bindings.example-topic-input.destination=aaa-topic
spring.cloud.stream.bindings.example-topic-output.destination=aaa-topic
```

这样，这两个输入输出通道就会都指向名为`aaa-topic`的Topic了。

最后，再启动该程序，没有报错。然后访问接口：`localhost:8080/sendMessage?message=hello-didi`，可以在控制台中看到如下信息：

```
2018-11-17 23:24:10.425  INFO 32039 --- [ctor-http-nio-2] o.s.a.r.c.CachingConnectionFactory       : Attempting to connect to: [localhost:5672]
2018-11-17 23:24:10.453  INFO 32039 --- [ctor-http-nio-2] o.s.a.r.c.CachingConnectionFactory       : Created new connection: rabbitConnectionFactory.publisher#266753da:0/SimpleConnection@627fba83 [delegate=amqp://guest@127.0.0.1:5672/, localPort= 60752]
2018-11-17 23:24:10.458  INFO 32039 --- [ctor-http-nio-2] o.s.amqp.rabbit.core.RabbitAdmin         : Auto-declaring a non-durable, auto-delete, or exclusive Queue (aaa-topic.anonymous.fNUxZ8C0QIafxrhkFBFI1A) durable:false, auto-delete:true, exclusive:true. It will be redeclared if the broker stops and is restarted while the connection factory is alive, but all messages will be lost.
2018-11-17 23:24:10.483  INFO 32039 --- [IafxrhkFBFI1A-1] com.didispace.stream.TestListener        : Received: hello-didi
```

消费自己生产的消息成功了！读者也还可以访问一下应用的`/actuator/beans`端点，看看当前Spring上下文中有哪些Bean，应该可以看到有下面Bean，也就是上面分析的两个通道的Bean对象

```
"example-topic-output": {
    "aliases": [],
    "scope": "singleton",
    "type": "org.springframework.integration.channel.DirectChannel",
    "resource": null,
    "dependencies": []
},
"example-topic-input": {
    "aliases": [],
    "scope": "singleton",
    "type": "org.springframework.integration.channel.DirectChannel",
    "resource": null,
    "dependencies": []
},
```

## 后记

其实大部分开发者在使用Spring Cloud Stream时候碰到的问题都源于对Spring Cloud Stream的核心概念还是不够理解。所以，还是推荐读一下下面的文章和示例：

- [入门示例](http://blog.didispace.com/spring-cloud-starter-dalston-7-1/)
- [核心概念](http://blog.didispace.com/spring-cloud-starter-dalston-7-2/)
- [消费组](http://blog.didispace.com/spring-cloud-starter-dalston-7-3/)
- [消费分区](http://blog.didispace.com/spring-cloud-starter-dalston-7-4/)

### 代码示例

本文示例读者可以通过查看下面仓库的中的`stream-consumer-self`项目：

- [Github](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- [Gitee](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)