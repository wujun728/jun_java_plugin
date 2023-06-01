# Spring Cloud实战小贴士：Zuul的饥饿加载（eager-load）使用

**原创**

 [2017-09-28](https://blog.didispace.com/spring-cloud-tips-zuul-eager/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> [上一篇](http://blog.didispace.com/spring-cloud-tips-ribbon-eager/)我们介绍了如何使用Ribbon的`earger-load`配置加速Spring Cloud中对服务接口的第一次调用。可是这样只是解决了内部服务间的调用，另外一个问题依然经常困扰我们，那就是网关到内部服务的访问。由于Spring Cloud Zuul的路由转发也是通过Ribbon实现负载均衡的，所以它也会存在第一次调时比较慢的情况。那么这个时候我们要如何设置呢？

## Zuul中的Eager Load配置

在Spring Cloud Zuul中也提供了一个配置参数来实现earger-load，具体如下：

```
zuul.ribbon.eager-load.enabled=true
```

但是，可能你尝试一下之后会发现，并没有起效？为什么呢？这是由于Spring Cloud Zuul中实现eager-load的时候同Ribbon中一样，都需要指定具体哪些服务需要饥饿加载。那么在Spring Cloud Zuul中如何具体指定呢？

在Spring Cloud Zuul的饥饿加载中没有设计专门的参数来配置，而是直接采用了读取路由配置来进行饥饿加载的做法。所以，如果我们使用默认路由，而没有通过配置的方式指定具体路由规则，那么`zuul.ribbon.eager-load.enabled=true`的配置就没有什么作用了。

因此，在真正使用的时候，我们可以通过`zuul.ignored-services=*`来忽略所有的默认路由，让所有路由配置均维护在配置文件中，以达到网关启动的时候就默认初始化好各个路由转发的负载均衡对象。