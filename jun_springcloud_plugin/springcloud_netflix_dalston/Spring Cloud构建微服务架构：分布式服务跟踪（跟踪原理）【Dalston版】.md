# Spring Cloud构建微服务架构：分布式服务跟踪（跟踪原理）【Dalston版】

**原创**

 [2018-02-20](https://blog.didispace.com/spring-cloud-starter-dalston-8-2/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 通过上一篇[《分布式服务跟踪（入门）》](http://blog.didispace.com/spring-cloud-starter-dalston-8-2/)的例子，我们已经通过Spring Cloud Sleuth往微服务应用中添加了实现分布式跟踪具备的基本要素。下面通过本文来详细说说实现分布式服务跟踪的一些要点。

分布式系统中的服务跟踪在理论上并不复杂，它主要包括下面两个关键点：

- 为了实现请求跟踪，当请求发送到分布式系统的入口端点时，只需要服务跟踪框架为该请求创建一个唯一的跟踪标识，同时在分布式系统内部流转的时候，框架始终保持传递该唯一标识，直到返回给请求方为止，这个唯一标识就是前文中提到的`Trace ID`。通过`Trace ID`的记录，我们就能将所有请求过程日志关联起来。
- 为了统计各处理单元的时间延迟，当请求达到各个服务组件时，或是处理逻辑到达某个状态时，也通过一个唯一标识来标记它的开始、具体过程以及结束，该标识就是我们前文中提到的Span ID，对于每个Span来说，它必须有开始和结束两个节点，通过记录开始Span和结束Span的时间戳，就能统计出该Span的时间延迟，除了时间戳记录之外，它还可以包含一些其他元数据，比如：事件名称、请求信息等。

在快速入门示例中，我们轻松实现了日志级别的跟踪信息接入，这完全归功于`spring-cloud-starter-sleuth`组件的实现。在Spring Boot应用中，通过在工程中引入`spring-cloud-starter-sleuth`依赖之后， 它会自动的为当前应用构建起各通信通道的跟踪机制，比如：

- 通过诸如RabbitMQ、Kafka（或者其他任何Spring Cloud Stream绑定器实现的消息中间件）传递的请求
- 通过Zuul代理传递的请求
- 通过`RestTemplate`发起的请求

在快速入门示例中，由于`trace-1`对`trace-2`发起的请求是通过`RestTemplate`实现的，所以`spring-cloud-starter-sleuth`组件会对该请求进行处理，在发送到`trace-2`之前sleuth会为在该请求的Header中增加实现跟踪需要的重要信息，主要有下面这几个（更多关于头信息的定义我们可以通过查看`org.springframework.cloud.sleuth.Span`的源码获取）：

- X-B3-TraceId：一条请求链路（Trace）的唯一标识，必须值
- X-B3-SpanId：一个工作单元（Span）的唯一标识，必须值
- X-B3-ParentSpanId:：标识当前工作单元所属的上一个工作单元，Root Span（请求链路的第一个工作单元）的该值为空
- X-B3-Sampled：是否被抽样输出的标志，1表示需要被输出，0表示不需要被输出
- X-Span-Name：工作单元的名称

我们可以通过对`trace-2`的实现做一些修改来输出这些头部信息，具体如下：

```
@RequestMapping(value = "/trace-2", method = RequestMethod.GET)
public String trace(HttpServletRequest request) {
	logger.info("===<call trace-2, TraceId={}, SpanId={}>===",
			request.getHeader("X-B3-TraceId"), request.getHeader("X-B3-SpanId"));
	return "Trace";
}
```

通过上面的改造，我们再运行快速入门的示例内容，并发起对`trace-1`的接口访问，我们可以得到如下输出内容。其中在`trace-2`的控制台中，输出了当前正在处理的`TraceID`和`SpanId`信息。

```
-- trace-1
INFO [trace-1,a6e9175ffd5d2c88,8524f519b8a9e399,true] 10532 --- [nio-9101-exec-2] icationEnhancerBySpringCGLIB27aa9624 : ===<call trace-1>===

-- trace-2
INFO [trace-2,a6e9175ffd5d2c88,ce60dcf1e2ed918f,true] 1208 --- [nio-9102-exec-3] icationEnhancerBySpringCGLIBa7d84797 : ===<call trace-2, TraceId=a6e9175ffd5d2c88, SpanId=be4949ec115e554e>===
```

为了更直观的观察跟踪信息，我们还可以在`application.properties`中增加下面的配置：

```
logging.level.org.springframework.web.servlet.DispatcherServlet=DEBUG
```

通过将Spring MVC的请求分发日志级别调整为`DEBUG`级别，我们可以看到更多跟踪信息：

```
-- trace-1
2016-11-27 09:26:52.663 DEBUG [trace-1,a6e9175ffd5d2c88,a6e9175ffd5d2c88,true] 10532 --- [nio-9101-exec-2] o.s.web.servlet.DispatcherServlet        : DispatcherServlet with name 'dispatcherServlet' processing GET request for [/trace-1]
2016-11-27 09:26:52.666 DEBUG [trace-1,a6e9175ffd5d2c88,a6e9175ffd5d2c88,true] 10532 --- [nio-9101-exec-2] o.s.web.servlet.DispatcherServlet        : Last-Modified value for [/trace-1] is: -1
2016-11-27 09:26:52.685 DEBUG [trace-1,a6e9175ffd5d2c88,8524f519b8a9e399,true] 10532 --- [nio-9101-exec-2] o.s.web.servlet.DispatcherServlet        : Null ModelAndView returned to DispatcherServlet with name 'dispatcherServlet': assuming HandlerAdapter completed request handling
2016-11-27 09:26:52.685 DEBUG [trace-1,a6e9175ffd5d2c88,a6e9175ffd5d2c88,true] 10532 --- [nio-9101-exec-2] o.s.web.servlet.DispatcherServlet        : Successfully completed request

-- trace-2
2016-11-27 09:26:52.673 DEBUG [trace-2,a6e9175ffd5d2c88,be4949ec115e554e,true] 1208 --- [nio-9102-exec-3] o.s.web.servlet.DispatcherServlet        : DispatcherServlet with name 'dispatcherServlet' processing GET request for [/trace-2]
2016-11-27 09:26:52.679 DEBUG [trace-2,a6e9175ffd5d2c88,be4949ec115e554e,true] 1208 --- [nio-9102-exec-3] o.s.web.servlet.DispatcherServlet        : Last-Modified value for [/trace-2] is: -1
2016-11-27 09:26:52.682 DEBUG [trace-2,a6e9175ffd5d2c88,ce60dcf1e2ed918f,true] 1208 --- [nio-9102-exec-3] o.s.web.servlet.DispatcherServlet        : Null ModelAndView returned to DispatcherServlet with name 'dispatcherServlet': assuming HandlerAdapter completed request handling
2016-11-27 09:26:52.683 DEBUG [trace-2,a6e9175ffd5d2c88,be4949ec115e554e,true] 1208 --- [nio-9102-exec-3] o.s.web.servlet.DispatcherServlet        : Successfully completed request
```

### 本文完整示例：

读者可以根据喜好选择下面的两个仓库中查看`trace-1`和`trace-2`两个项目：

- [Github：https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/2-Dalston版教程示例/)
- [Gitee：https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/2-Dalston版教程示例)