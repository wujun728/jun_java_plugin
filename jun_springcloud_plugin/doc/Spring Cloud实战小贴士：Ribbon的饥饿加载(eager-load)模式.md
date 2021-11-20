# Spring Cloud实战小贴士：Ribbon的饥饿加载(eager-load)模式

**原创**

 [2017-09-25](https://blog.didispace.com/spring-cloud-tips-ribbon-eager/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 我们在使用Spring Cloud的Ribbon或Feign来实现服务调用的时候，如果我们的机器或网络环境等原因不是很好的话，有时候会发现这样一个问题：我们服务消费方调用服务提供方接口的时候，第一次请求经常会超时，而之后的调用就没有问题了。下面我们就来说说造成这个问题的原因，以及如何解决的方法。

## 问题原因

造成第一次服务调用出现失败的原因主要是Ribbon进行客户端负载均衡的Client并不是在服务启动的时候就初始化好的，而是在调用的时候才会去创建相应的Client，所以第一次调用的耗时不仅仅包含发送HTTP请求的时间，还包含了创建RibbonClient的时间，这样一来如果创建时间速度较慢，同时设置的超时时间又比较短的话，很容易就会出现上面所描述的显现。

从日志中我们也能知道这一点细节，在第一次发起调用的时候我们可以从日志中看到如下信息：

```
2017-09-25 08:29:54,201 INFO  [main] com.netflix.loadbalancer.DynamicServerListLoadBalancer - DynamicServerListLoadBalancer for client hello-service initialized: DynamicServerListLoadBalancer:{NFLoadBalancer:name=hello-service,current list of Servers=[192.168.99.176:9901],Load balancer stats=Zone stats: {unknown=[Zone:unknown;	Instance count:1;	Active connections count: 0;	Circuit breaker tripped count: 0;	Active connections per server: 0.0;]
},Server stats: [[Server:192.168.99.176:9901;	Zone:UNKNOWN;	Total Requests:0;	Successive connection failure:0;	Total blackout seconds:0;	Last connection made:Thu Jan 01 08:00:00 CST 1970;	First connection made: Thu Jan 01 08:00:00 CST 1970;	Active Connections:0;	total failure count in last (1000) msecs:0;	average resp time:0.0;	90 percentile resp time:0.0;	95 percentile resp time:0.0;	min resp time:0.0;	max resp time:0.0;	stddev resp time:0.0]
]}ServerList:ConsulServerList{serviceId='hello-service', tag=null}
```

而Feign的实现基于Ribbon，所以它也有一样的问题，下面就来看看如何解决这个问题。

## 解决方法

解决的方法很简单，既然第一次调用时候产生RibbonClient耗时，那么就让它提前创建，而不是在第一次调用的时候创建。

在Spring Cloud的Dlaston版本中提供了几个新的参数，它们可以很方便的帮我们实现这样的功能。

```
ribbon.eager-load.enabled=true
ribbon.eager-load.clients=hello-service, user-service
```

参数说明：

- ribbon.eager-load.enabled：开启Ribbon的饥饿加载模式
- ribbon.eager-load.clients：指定需要饥饿加载的客户端名称、服务名

通过上面的配置完成之后，我们尝试重启一下服务消费者，这个时候我们会发现，我们没有开始调用服务接口，但是上面初始化负载均衡的日志就已经打印出来了。这就说明我们对ribbon的饥饿加载模块设置已经生效了。