> 相关源码： [spring cloud demo](http://git.oschina.net/buxiaoxia/spring-demo "样例")

微服务的目的： **松耦合**

事件驱动的优势：**高度解耦**

# Spring Cloud Stream 的几个概念

> Spring Cloud Stream is a framework for building message-driven microservice applications. 

官方定义 Spring Cloud Stream 是一个构建消息驱动微服务的框架。

![Spring Cloud Stream Application](http://upload-images.jianshu.io/upload_images/4742055-c696c65aa362e51f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

应用程序通过 inputs 或者 outputs 来与 Spring Cloud Stream 中binder 交互，通过我们配置来 binding ，而  Spring Cloud Stream 的 binder 负责与中间件交互。所以，我们只需要搞清楚如何与  Spring Cloud Stream 交互就可以方便使用消息驱动的方式

## Binder 

Binder 是 Spring Cloud Stream 的一个抽象概念，是应用与消息中间件之间的粘合剂。目前 Spring Cloud Stream 实现了 Kafka 和 Rabbit MQ 的binder。

通过 binder ，可以很方便的连接中间件，可以动态的改变消息的
 destinations（对应于 Kafka 的topic，Rabbit MQ 的 exchanges），这些都可以通过外部配置项来做到。

甚至可以任意的改变中间件的类型而不需要修改一行代码。

## Publish-Subscribe 

消息的发布（Publish）和订阅（Subscribe）是事件驱动的经典模式。Spring Cloud Stream 的数据交互也是基于这个思想。生产者把消息通过某个 topic 广播出去（Spring Cloud Stream 中的 destinations）。其他的微服务，通过订阅特定 topic 来获取广播出来的消息来触发业务的进行。

这种模式，极大的降低了生产者与消费者之间的耦合。即使有新的应用的引入，也不需要破坏当前系统的整体结构。

## Consumer Groups

“Group”，如果使用过 Kafka 的童鞋并不会陌生。Spring Cloud Stream 的这个分组概念的意思基本和 Kafka 一致。

微服务中动态的缩放同一个应用的数量以此来达到更高的处理能力是非常必须的。对于这种情况，同一个事件防止被重复消费，只要把这些应用放置于同一个 “group” 中，就能够保证消息只会被其中一个应用消费一次。

## Durability

消息事件的持久化是必不可少的。Spring Cloud Stream 可以动态的选择一个消息队列是持久化，还是 present。

## Bindings

bindings 是我们通过配置把应用和spring cloud stream 的 binder 绑定在一起，之后我们只需要修改 binding 的配置来达到动态修改topic、exchange、type等一系列信息而不需要修改一行代码。


# 基于 RabbitMQ 使用

> 以下内容源码： [spring cloud demo](http://git.oschina.net/buxiaoxia/spring-demo/tree/master/spring-cloud-stream/spring-cloud-stream-rabbitmq?dir=1&filepath=spring-cloud-stream%2Fspring-cloud-stream-rabbitmq&oid=4a87a9d14fe1c8e76f491bf69de2f6eadbe7b5e8&sha=fff7bed1190ae507ffccce58628336f83035ddf7 "源码")

## 消息接收

Spring Cloud Stream 基本用法，需要定义一个接口，如下是内置的一个接口。

```
public interface Sink {
    String INPUT = "input";

    @Input("input")
    SubscribableChannel input();
}
```
 注释__ @Input__ 对应的方法，需要返回 __ SubscribableChannel __ ，并且参入一个参数值。

这就接口声明了一个__ binding __命名为 “__input__” 。

其他内容通过配置指定：

```
spring:
  cloud:
    stream:
      bindings:
        input:
          destination: mqTestDefault   
```

> destination：指定了消息获取的目的地，对应于MQ就是 exchange，这里的exchange就是 mqTestDefault   

```
@SpringBootApplication
@EnableBinding(Sink.class)
public class Application {

    // 监听 binding 为 Sink.INPUT 的消息
    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        System.out.println("一般监听收到：" + message.getPayload());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
```

定义一个 class （这里直接在启动类），并且添加注解__@EnableBinding(Sink.class)__ ，其中 Sink 就是上述的接口。同时定义一个方法（此处是 input）标明注解为 __ @StreamListener(Processor.INPUT) __，方法参数为 Message 。

启动后，**默认是会创建一个临时队列，临时队列绑定的exchange为 “mqTestDefault”，routing key为 “#”。**

所有发送 exchange 为“mqTestDefault” 的MQ消息都会被投递到这个临时队列，并且触发上述的方法。

以上代码就完成了最基本的消费者部分。

## 消息发送

消息的发送同消息的接受，都需要定义一个接口，不同的是接口方法的返回对象是 __MessageChannel__，下面是 Spring Cloud Stream 内置的接口：

```
public interface Source {
    String OUTPUT = "output";

    @Output("output")
    MessageChannel output();
}
```
这就接口声明了一个 binding 命名为 “output” ，不同于上述的 “input”，这个binding 声明了一个消息输出流，也就是消息的生产者。

```
spring:
  cloud:
    stream:
      bindings:
        output:
          destination: mqTestDefault
          contentType: text/plain
```
> contentType：用于指定消息的类型。具体可以参考 [spring cloud stream docs](http://docs.spring.io/spring-cloud-stream/docs/Chelsea.SR2/reference/htmlsingle/index.html#mime-types "文档")

> destination：指定了消息发送的目的地，对应 RabbitMQ，会发送到 exchange 是 mqTestDefault 的所有消息队列中。

代码中调用：

```
@SpringBootApplication
@EnableBinding(Source.class)
public class Application implements CommandLineRunner {

    @Autowired
    @Qualifier("output")
    MessageChannel output;

    @Override
    public void run(String... strings) throws Exception {
        // 字符串类型发送MQ
        System.out.println("字符串信息发送");
        output.send(MessageBuilder.withPayload("大家好").build());
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
    
}
```

> 通过注入__MessageChannel__的方式，发送消息。

> 通过注入__Source__ 接口的方式，发送消息。 具体可以查看样例

以上代码就完成了最基本的生产者部分。

##  自定义消息发送接收

### 自定义接口

Spring Cloud Stream 内置了两种接口，分别定义了 binding 为 “input” 的输入流，和 “output” 的输出流，而在我们实际使用中，往往是需要定义各种输入输出流。使用方法也很简单。

```
interface OrderProcessor {

    String INPUT_ORDER = "inputOrder";
    String OUTPUT_ORDER = "outputOrder";

    @Input(INPUT_ORDER)
    SubscribableChannel inputOrder();

    @Output(OUTPUT_ORDER)
    MessageChannel outputOrder();
}
```
一个接口中，可以定义无数个输入输出流，可以根据实际业务情况划分。上述的接口，定义了一个订单输入，和订单输出两个 binding。

> 使用时，需要在 __@EnableBinding__ 注解中，添加自定义的接口。
> 使用 __@StreamListener__ 做监听的时候，需要指定 __OrderProcessor.INPUT_ORDER__
 
```
spring:
  cloud:
    stream:
      defaultBinder: defaultRabbit
      bindings:
        inputOrder:
          destination: mqTestOrder
        outputOrder:
          destination: mqTestOrder
```
如上配置，指定了 destination 为 mqTestOrder 的输入输出流。

### 分组与持久化

上述自定义的接口配置中，Spring Cloud Stream 会在 RabbitMQ 中创建一个临时的队列，程序关闭，对应的连接关闭的时候，该队列也会消失。而在实际使用中，我们需要一个持久化的队列，并且指定一个分组，用于保证应用服务的缩放。

只需要在消费者端的 binding 添加配置项 **spring.cloud.stream.bindings.[channelName].group = XXX **。对应的队列就是持久化，并且名称为：**mqTestOrder.XXX**。

## rabbitMQ routing key 绑定

用惯了 rabbitMQ 的童鞋，在使用的时候，发现 Spring Cloud Stream 的消息投递，默认是根据 destination + group 进行区分，所有的消息都投递到 routing key 为 “#‘’ 的消息队列里。

如果我们需要进一步根据 routing key 来进行区分消息投递的目的地，或者消息接受，需要进一步配，Spring Cloud Stream 也提供了相关配置：

```
spring:
  cloud:
    stream:
      bindings:
        inputProductAdd:
          destination: mqTestProduct
          group: addProductHandler      # 拥有 group 默认会持久化队列
        outputProductAdd:
          destination: mqTestProduct
      rabbit:
        bindings:
          inputProductAdd:
            consumer:
              bindingRoutingKey: addProduct.*       # 用来绑定消费者的 routing key
          outputProductAdd:
            producer:
              routing-key-expression: '''addProduct.*'''  # 需要用这个来指定 RoutingKey
```

> spring.cloud.stream.rabbit.bindings.[channelName].consumer.bindingRoutingKey 
 指定了生成的消息队列的routing key

> spring.cloud.stream.rabbit.bindings.[channelName].producer.routing-key-expression 指定了生产者消息投递的routing key


## DLX 队列

### DLX 作用

DLX:Dead-Letter-Exchange（死信队列）。利用DLX, 当消息在一个队列中变成死信（dead message）之后，它能被重新publish到另一个Exchange，这个Exchange就是DLX。消息变成死信一向有一下几种情况：

消息被拒绝（basic.reject/ basic.nack）并且requeue=false
消息TTL过期（参考：[RabbitMQ之TTL（Time-To-Live 过期时间）](http://blog.csdn.net/u013256816/article/details/54916011)）
队列达到最大长度

DLX也是一个正常的Exchange，和一般的Exchange没有区别，它能在任何的队列上被指定，实际上就是设置某个队列的属性，当这个队列中有死信时，RabbitMQ就会自动的将这个消息重新发布到设置的Exchange上去，进而被路由到另一个队列，可以监听这个队列中消息做相应的处理。

### Spring Cloud Stream 中使用

spring.cloud.stream.rabbit.bindings.[channelName].consumer.autoBindDlq=true

spring.cloud.stream.rabbit.bindings.[channelName].consumer.republishToDlq=true

配置说明，可以参考 [spring cloud stream rabbitmq consumer properties](http://docs.spring.io/spring-cloud-stream/docs/Chelsea.SR2/reference/htmlsingle/index.html#_rabbitmq_consumer_properties "文档")

# 结论

Spring Cloud Stream 最大的方便之处，莫过于抽象了事件驱动的一些概念，对于消息中间件的进一步封装，可以做到代码层面对中间件的无感知，甚至于动态的切换中间件，切换topic。使得微服务开发的高度解耦，服务可以关注更多自己的业务流程。

# 相关文档

[spring cloud stream 文档](http://docs.spring.io/spring-cloud-stream/docs/Chelsea.SR2/reference/htmlsingle/index.html "文档")

[spring cloud stream 项目](http://cloud.spring.io/spring-cloud-stream/)

[spring cloud stream 样例](http://git.oschina.net/buxiaoxia/spring-demo/tree/master/spring-cloud-stream/spring-cloud-stream-rabbitmq?dir=1&filepath=spring-cloud-stream%2Fspring-cloud-stream-rabbitmq&oid=4a87a9d14fe1c8e76f491bf69de2f6eadbe7b5e8&sha=fff7bed1190ae507ffccce58628336f83035ddf7)