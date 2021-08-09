# Spring Cloud构建微服务架构：服务消费（Feign）【Dalston版】

**原创**

 [2017-06-27](https://blog.didispace.com/spring-cloud-starter-dalston-2-3/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 通过前两篇[《Spring Cloud构建微服务架构：服务消费（基础）》](http://blog.didispace.com/spring-cloud-starter-dalston-2-1/)和[《Spring Cloud构建微服务架构：服务消费（Ribbon）》](http://blog.didispace.com/spring-cloud-starter-dalston-2-2/)，我们已经学会了在Spring Cloud中基本的服务调用方式。本文我们将继续介绍Spring Cloud中的另外一个服务消费的工具：Spring Cloud Feign。

## Spring Cloud Feign

Spring Cloud Feign是一套基于Netflix Feign实现的声明式服务调用客户端。它使得编写Web服务客户端变得更加简单。我们只需要通过创建接口并用注解来配置它既可完成对Web服务接口的绑定。它具备可插拔的注解支持，包括Feign注解、JAX-RS注解。它也支持可插拔的编码器和解码器。Spring Cloud Feign还扩展了对Spring MVC注解的支持，同时还整合了Ribbon和Eureka来提供均衡负载的HTTP客户端实现。

下面，我们通过一个例子来展现Feign如何方便的声明对eureka-client服务的定义和调用。

## 动手试一试

下面的例子，我们将利用之前构建的`eureka-server`作为服务注册中心、`eureka-client`作为服务提供者作为基础。而基于Spring Cloud Ribbon实现的消费者，我们可以根据`eureka-consumer`实现的内容进行简单改在就能完成，具体步骤如下：

- 根据`eureka-consumer`复制一个服务消费者工程，命名为：`eureka-consumer-feign`。在`pom.xml`中增加下面的依赖：

```
<dependencies>
    ...
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-feign</artifactId>
    </dependency>
</dependencies>
```

- 修改应用主类。通过`@EnableFeignClients`注解开启扫描Spring Cloud Feign客户端的功能：

```
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}
}
```

- 创建一个Feign的客户端接口定义。使用`@FeignClient`注解来指定这个接口所要调用的服务名称，接口中定义的各个函数使用Spring MVC的注解就可以来绑定服务提供方的REST接口，比如下面就是绑定`eureka-client`服务的`/dc`接口的例子：

```
@FeignClient("eureka-client")
public interface DcClient {

    @GetMapping("/dc")
    String consumer();

}
```

- 修改Controller。通过定义的feign客户端来调用服务提供方的接口：

```
@RestController
public class DcController {

    @Autowired
    DcClient dcClient;

    @GetMapping("/consumer")
    public String dc() {
        return dcClient.consumer();
    }

}
```

通过Spring Cloud Feign来实现服务调用的方式更加简单了，通过`@FeignClient`定义的接口来统一的生命我们需要依赖的微服务接口。而在具体使用的时候就跟调用本地方法一点的进行调用即可。由于Feign是基于Ribbon实现的，所以它自带了客户端负载均衡功能，也可以通过Ribbon的IRule进行策略扩展。另外，Feign还整合的Hystrix来实现服务的容错保护，在Dalston版本中，Feign的Hystrix默认是关闭的。待后文介绍Hystrix带领大家入门之后，我们再结合介绍Feign中的Hystrix以及配置方式。

在完成了上面你的代码编写之后，读者可以将eureka-server、eureka-client、eureka-consumer-feign都启动起来，然后访问http://localhost:2101/consumer ，来跟踪观察eureka-consumer-feign服务是如何消费eureka-client服务的`/dc`接口的，并且也可以通过启动多个eureka-client服务来观察其负载均衡的效果。

**更多Spring Cloud内容请持续关注我的博客更新或在《Spring Cloud微服务实战》中获取。**

## 代码示例

样例工程将沿用之前在码云和GitHub上创建的SpringCloud-Learning项目，重新做了一下整理。通过不同目录来区分Brixton和Dalston的示例。

- 码云：[点击查看](http://git.oschina.net/didispace/SpringCloud-Learning/tree/master/2-Dalston版教程示例)
- GitHub：[点击查看](https://github.com/dyc87112/SpringCloud-Learning/tree/master/2-Dalston版教程示例)

具体工程说明如下：

- eureka的服务注册中心：eureka-server
- eureka的服务提供方：eureka-client
- eureka的服务消费者：eureka-consumer-feign