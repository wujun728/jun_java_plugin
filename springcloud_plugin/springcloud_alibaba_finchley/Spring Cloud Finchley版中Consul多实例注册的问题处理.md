# Spring Cloud Finchley版中Consul多实例注册的问题处理

**原创**

 [2018-08-26](https://blog.didispace.com/Spring-Cloud-Finchley-Consul-InstanceId/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 由于Spring Cloud对Etcd的支持一直没能从孵化器中出来，所以目前来说大多用户还在使用Eureka和Consul，之前又因为Eureka 2.0不在开源的消息，外加一些博眼球的标题党媒体使得Eureka的用户有所减少，所以，相信在选择Spring Cloud的用户群体中，应该有不少用户会选择Consul来做服务注册与发现。
>
> 本文就来说一下，当我们使用Spring Cloud最新的Finchley版 + Consul 1.2.x时候最严重的一个坑：多实例注册的问题。

### 问题解读

**问题**：该问题可能在开发阶段不一定会发现，但是在线上部署多实例的时候，将会发现Consul中只有一个实例。

**原因**：造成该问题的主要原因是Spring Cloud Consul在注册的时候实例名（InstanceId）采用了：“服务名-端口号”（即：`{spring.application.name}-{server.port}`）的值，可以看到这个实例名如果不改变端口号的情况下，实例名都是相同的。如果熟悉Spring Cloud Consul的读者，可能会问老版本也是这个规则，怎么没有这个问题呢？。主要是由于Consul对实例唯一性的判断标准也有改变，在老版本的Consul中，对于实例名相同，但是服务地址不同，依然会认为是不同的实例。在Consul 1.2.x中，服务实例名成为了集群中的唯一标识，所以，也就导致了上述问题。

### 解决方法

既然知道了原因，那么我们要解决它就可以有的放矢了。下面就来介绍两个具体的解决方式：

**方法一：通过配置属性指定新的规则**

下面举个例子，通过`spring.cloud.consul.discovery.instance-id`参数直接来配置实例命名规则。这里比较粗暴的通过随机数来一起组织实例名。当然这样的组织方式并不好，因为随机数依然有冲突的可能，所以您还可以用更负责的规则来进行组织实例名。

```
spring.cloud.consul.discovery.instance-id=${spring.application.name}-${random.int[10000,99999]}
```

**方法二：通过扩展`ConsulServiceRegistry`来重设实例名**

由于通过配置属性的方式对于定义实例名的能力有限，所以我们希望可以用更灵活的方式来定义。这时候我们就可以通过重写`ConsulServiceRegistry`的`register`方法来修改。比如下面的实现：

```
public class MyConsulServiceRegistry extends ConsulServiceRegistry {

    public MyConsulServiceRegistry(ConsulClient client, ConsulDiscoveryProperties properties, TtlScheduler ttlScheduler, HeartbeatProperties heartbeatProperties) {
        super(client, properties, ttlScheduler, heartbeatProperties);
    }

    @Override
    public void register(ConsulRegistration reg) {
        reg.getService().setId(reg.getService().getName() + “-” + reg.getService().getAddress() + “-” + reg.getService().getPort());
        super.register(reg);
    }

}
```

上面通过拼接“服务名”-“ip地址”-“端口号”的方式，构造了一个绝对唯一的实例名，这样就可以让每个服务实例都能正确的注册到Consul上了。