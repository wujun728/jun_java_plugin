# Spring Cloud Alibaba基础教程：支持的几种服务消费方式（RestTemplate、WebClient、Feign）

**原创**

 [2019-01-26](https://blog.didispace.com/spring-cloud-alibaba-2/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

通过[《Spring Cloud Alibaba基础教程：使用Nacos实现服务注册与发现》](http://blog.didispace.com/spring-cloud-alibaba-1/)一文的学习，我们已经学会如何使用Nacos来实现服务的注册与发现，同时也介绍如何通过LoadBalancerClient接口来获取某个服务的具体实例，并根据实例信息来发起服务接口消费请求。但是这样的做法需要我们手工的去编写服务选取、链接拼接等繁琐的工作，对于开发人员来说非常的不友好。所以接下来，我们再来看看除此之外，还支持哪些其他的服务消费方式。

## 使用RestTemplate

在之前的例子中，已经使用过`RestTemplate`来向服务的某个具体实例发起HTTP请求，但是具体的请求路径是通过拼接完成的，对于开发体验并不好。但是，实际上，在Spring Cloud中对RestTemplate做了增强，只需要稍加配置，就能简化之前的调用方式。

比如：

```
@EnableDiscoveryClient
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Slf4j
    @RestController
    static class TestController {

        @Autowired
        RestTemplate restTemplate;

        @GetMapping("/test")
        public String test() {
            String result = restTemplate.getForObject("http://alibaba-nacos-discovery-server/hello?name=didi", String.class);
            return "Return : " + result;
        }
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
```

可以看到，在定义RestTemplate的时候，增加了`@LoadBalanced`注解，而在真正调用服务接口的时候，原来host部分是通过手工拼接ip和端口的，直接采用服务名的时候来写请求路径即可。在真正调用的时候，Spring Cloud会将请求拦截下来，然后通过负载均衡器选出节点，并替换服务名部分为具体的ip和端口，从而实现基于服务名的负载均衡调用。

关于这种方式，可在文末仓库查看完整代码示例。而对于这种方式的实现原理，可以参考我之前写的这篇文章的前半部分：[Spring Cloud源码分析（二）Ribbon](http://blog.didispace.com/springcloud-sourcecode-ribbon/)

## 使用WebClient

WebClient是Spring 5中最新引入的，可以将其理解为reactive版的RestTemplate。下面举个具体的例子，它将实现与上面RestTemplate一样的请求调用：

```
@EnableDiscoveryClient
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Slf4j
    @RestController
    static class TestController {

        @Autowired
        private WebClient.Builder webClientBuilder;

        @GetMapping("/test")
        public Mono<String> test() {
            Mono<String> result = webClientBuilder.build()
                    .get()
                    .uri("http://alibaba-nacos-discovery-server/hello?name=didi")
                    .retrieve()
                    .bodyToMono(String.class);
            return result;
        }
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

}
```

可以看到，在定义WebClient.Builder的时候，也增加了`@LoadBalanced`注解，其原理与之前的RestTemplate时一样的。关于WebClient的完整例子也可以通过在文末的仓库中查看。

## 使用Feign

上面介绍的RestTemplate和WebClient都是Spring自己封装的工具，下面介绍一个Netflix OSS中的成员，通过它可以更方便的定义和使用服务消费客户端。下面也举一个具体的例子，其实现内容与上面两种方式结果一致：

第一步：在`pom.xml`中增加openfeign的依赖：

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

第二步：定义Feign客户端和使用Feign客户端：

```
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Slf4j
    @RestController
    static class TestController {

        @Autowired
        Client client;

        @GetMapping("/test")
        public String test() {
            String result = client.hello("didi");
            return "Return : " + result;
        }
    }


    @FeignClient("alibaba-nacos-discovery-server")
    interface Client {

        @GetMapping("/hello")
        String hello(@RequestParam(name = "name") String name);

    }

}
```

这里主要先通过`@EnableFeignClients`注解开启扫描Spring Cloud Feign客户端的功能；然后又创建一个Feign的客户端接口定义。使用`@FeignClient`注解来指定这个接口所要调用的服务名称，接口中定义的各个函数使用Spring MVC的注解就可以来绑定服务提供方的REST接口，比如下面就是绑定`alibaba-nacos-discovery-server`服务的`/hello`接口的例子。最后，在Controller中，注入了Client接口的实现，并调用hello方法来触发对服务提供方的调用。关于使用Feign的完整例子也可以通过在文末的仓库中查看。

## 深入思考

如果之前已经用过Spring Cloud的读者，肯定会这样的感受：不论我用的是`RestTempalte`也好、还是用的`WebClient`也好，还是用的`Feign`也好，似乎跟我用不用Nacos没啥关系？我们在之前介绍Eureka和Consul的时候，也都是用同样的方法来实现服务调用的，不是吗？

确实是这样，对于Spring Cloud老手来说，就算我们更换了Nacos作为新的服务注册中心，其实对于我们应用层面的代码是没有影响的。那么为什么Spring Cloud可以带给我们这样的完美编码体验呢？实际上，这完全归功于Spring Cloud Common的封装，由于在服务注册与发现、客户端负载均衡等方面都做了很好的抽象，而上层应用方面依赖的都是这些抽象接口，而非针对某个具体中间件的实现。所以，在Spring Cloud中，我们可以很方便的去切换服务治理方面的中间件。

## 代码示例

本文示例读者可以通过查看下面仓库：

- *Github：*[https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- *Gitee：*[https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)

其中，本文的几种示例可查看下面的几个项目：

- `alibaba-nacos-discovery-server`：服务提供者，必须启动
- `alibaba-nacos-discovery-client-resttemplate`：使用RestTemplate消费
- `alibaba-nacos-discovery-client-webclient`：使用WebClient消费
- `alibaba-nacos-discovery-client-feign`：使用Feign消费