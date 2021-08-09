# Spring Cloud实战小贴士：Zuul统一异常处理（三）【Dalston版】

**原创**

 [2017-07-28](https://blog.didispace.com/spring-cloud-zuul-exception-3/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 本篇作为《Spring Cloud微服务实战》一书关于Spring Cloud Zuul网关在Dalston版本对异常处理的补充。没有看过本书的读书也不要紧，可以先阅读我之前的两篇博文：[《Spring Cloud实战小贴士：Zuul统一异常处理（一）》](http://blog.didispace.com/spring-cloud-zuul-exception-1/)和[《Spring Cloud实战小贴士：Zuul统一异常处理（二）》](http://blog.didispace.com/spring-cloud-zuul-exception-2/)，这两篇文章都详细介绍和分析了Spring Cloud Zuul在过滤器设计中对异常处理的不足。同时，在这两篇文章中，也针对不足之处做了相应的解决方案。不过，这些方案都是基于Brixton版本所做的，在最新的Dalston版本中，Spring Cloud Zuul做了一些优化，所以我们不再需要做这些扩展就已经能够正确的处理异常信息了。那么，在Dalston版中，Spring Cloud Zuul中做了怎么样的修改以达到之前我们自己扩展的效果呢？

## 过滤器类型的变更

读者是否还记得我们之前分析了Spring Cloud Zuul自带的核心过滤器有哪些呢？我们先根据下图回忆一下：

[![img](https://blog.didispace.com/content/images/posts/spring-cloud-zuul-exception-3-1.png)](https://blog.didispace.com/content/images/posts/spring-cloud-zuul-exception-3-1.png)

这次主要将`SendErrorFilter`过滤器的类型从`POST`改为了`ERROR`，所以核心过滤器变成了如下图的结构：

[![img](https://blog.didispace.com/content/images/posts/spring-cloud-zuul-exception-3-2.png)](https://blog.didispace.com/content/images/posts/spring-cloud-zuul-exception-3-2.png)

## 处理逻辑的变化

既然过滤器类型发生了变化，那么请求的处理生命周期就会有所变化。在变化之前，各阶段过滤器的流转如下图所示：

[![img](https://blog.didispace.com/content/images/posts/spring-cloud-zuul-exception-3-3.png)](https://blog.didispace.com/content/images/posts/spring-cloud-zuul-exception-3-3.png)

针对异常情况，在图中我们标出了不同的颜色。从pre和route阶段抛出的异常将会进入error阶段，再进入到post阶段进行返回。由于SendErrorFilter需要判断请求上下文中是否包含`error.status_code`属性：有的话就用SendErrorFilter处理错误结果；没有的话就用SendResponseFilter返回正常结果，但是`error.status_code`属性默认是在各个阶段过滤器中自己put进去的，这就导致，各个阶段过滤器抛出异常之后，是没有办法返回错误结果的。因此，我们扩展了一个ErrorFilter来捕获异常，然后手工的设置`error.status_code`属性，让SendErrorFilter能正常运作。

通过上面你的改造，从pre和route阶段的异常都能处理了，但是post阶段抛出异常后，是不会再进入post阶段的，这使得ErrorFilter设置了设置`error.status_code`属性之后，也没有过滤器去组织返回结果，所以我们通过继承SendErrorFilter在error阶段增加了一个返回错误信息的过滤器。

而这次在Dalston版本中，做了很巧妙的变动：就是上文所述的对SendErrorFilter过滤器类型的变更，这一变动使得所有阶段的异常都会被SendErrorFilter处理，直接解决的上面的第二个问题。当然只是做个变动还是不够的，为了区分SendErrorFilter和SendResponseFitler分别处理出现异常和未出现异常的情况，修改原来根据`error.status_code`属性判断的逻辑，而是改为根据请求上下文中是否包含Throwable来作为基本依据，而这个对象是在过滤器出现异常之后，Zuul往请求上下文中置入的，所以可以更为准确的判断当前请求处理是否出现了异常，而不再需要我们之前扩展的ErrorFilter了。

```
public class SendErrorFilter extends ZuulFilter {	
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		return ctx.containsKey("error.status_code")
				&& !ctx.getBoolean(SEND_ERROR_FILTER_RAN, false);
	}
	...
}

public class SendResponseFilter extends ZuulFilter {
	@Override
	public boolean shouldFilter() {
		RequestContext context = RequestContext.getCurrentContext();
		return context.getThrowable() == null
			&& (!context.getZuulResponseHeaders().isEmpty()
				|| context.getResponseDataStream() != null
				|| context.getResponseBody() != null);
	}
	...
}
```

所以，最后修改之后，整个处理逻辑变为如下图所示的流程：

[![img](https://blog.didispace.com/content/images/posts/spring-cloud-zuul-exception-3-4.png)](https://blog.didispace.com/content/images/posts/spring-cloud-zuul-exception-3-4.png)

## 推荐阅读

- [Spring Cloud构建微服务架构：服务注册与发现（Eureka、Consul）](http://blog.didispace.com/spring-cloud-starter-dalston-1/)
- [Spring Cloud构建微服务架构：服务消费者（基础）](http://blog.didispace.com/spring-cloud-starter-dalston-2-1/)
- [Spring Cloud构建微服务架构：服务消费者（Ribbon）](http://blog.didispace.com/spring-cloud-starter-dalston-2-2/)
- [Spring Cloud构建微服务架构：服务消费者（Feign）](http://blog.didispace.com/spring-cloud-starter-dalston-2-3/)
- [Spring Cloud构建微服务架构：分布式配置中心](http://blog.didispace.com/spring-cloud-starter-dalston-3)
- [Spring Cloud构建微服务架构：服务容错保护（hystrix服务降级）](http://blog.didispace.com/spring-cloud-starter-dalston-4-1)
- [Spring Cloud构建微服务架构：服务容错保护（hystrix依赖隔离）](http://blog.didispace.com/spring-cloud-starter-dalston-4-2)
- [Spring Cloud构建微服务架构：服务容错保护（hystrix断路器）](http://blog.didispace.com/spring-cloud-starter-dalston-4-3)
- [Spring Cloud构建微服务架构：Hystrix监控面板](http://blog.didispace.com/spring-cloud-starter-dalston-5-1)
- [Spring Cloud构建微服务架构：Hystrix监控数据聚合](http://blog.didispace.com/spring-cloud-starter-dalston-5-2)
- [更多Spring Cloud内容…](http://blog.didispace.com/Spring-Cloud基础教程/)