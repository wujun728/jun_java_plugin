# Spring Cloud实战小贴士：随机端口

**原创**

 [2017-03-25](https://blog.didispace.com/spring-cloud-tips-2/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 太久没有更新，一时不知道该从哪儿开始，索性就从一个小技巧开始吧。

在之前的《Spring Cloud构建微服务架构》系列博文中，我们经常会需要启动多个实例的情况来测试注册中心、配置中心等基础设施的高可用，也会用来测试客户端负载均衡的调用等。但是，我们一个应用只能有一个端口号，这就使得在本机测试的时候，不得不为同一个服务设置不同的端口来进行启动。

在本地用不同端口启动同一服务实例的方法有很多。最传统的我们可以粗暴地修改配置文件中的`server.port`属性来分别启动多个实例，这种方法虽然可以实现，但是非常的不方便。比较好的一种方法是在启动的时候通过命令的方式为`server.port`属性来设置不同的值，这样我们的配置文件就不用反复的进行修改了。

在本文中，我们将介绍另外一种方法：采用随机端口的方式来设置各个服务实例，这样我们不用去编辑任何命令就可以在本地轻松地启动多个实例了。

### 使用随机端口

为Spring Cloud的应用实用随机端口非常简单，主要有两种方法：

- 设置`server.port=0`，当应用启动的时候会自动的分配一个随机端口，但是该方式在注册到Eureka的时候会一个问题：所有实例都使用了同样的实例名（如：`Lenovo-zhaiyc:hello-service:0`），这导致只出现了一个实例。所以，我们还需要修改实例ID的定义，让每个实例的ID不同，比如使用随机数来配置实例ID：

```
server.port=0
eureka.instance.instance-id=${spring.application.name}:${random.int}
```

- 除了上面的方法，实际上我们还可以直接使用`random`函数来配置`server.port`。这样就可以指定端口的取值范围，比如：

```
server.port=${random.int[10000,19999]}
```

由于默认的实例ID会由`server.port`拼接，而此时`server.port`设置的随机值会重新取一次随机数，所以使用这种方法的时候不需要重新定义实例ID的规则就能产生不同的实例ID了。