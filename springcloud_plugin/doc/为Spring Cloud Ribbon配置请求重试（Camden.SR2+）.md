# 为Spring Cloud Ribbon配置请求重试（Camden.SR2+）

**原创**

 [2016-12-22](https://blog.didispace.com/spring-cloud-ribbon-failed-retry/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

当我们使用Spring Cloud Ribbon实现客户端负载均衡的时候，通常都会利用`@LoadBalanced`来让`RestTemplate`具备客户端负载功能，从而实现面向服务名的接口访问（原理可见[《Spring Cloud源码分析（二）Ribbon》](http://blog.didispace.com/springcloud-sourcecode-ribbon/)一文，如果对Spring Cloud中使用Ribbon进行服务消费还没有概念的话，建议先阅读[《Spring Cloud构建微服务架构（二）服务消费者》](http://blog.didispace.com/springcloud2/)一文。）。

下面的例子，实现了对服务名为`hello-service`的`/hello`接口的调用。由于`RestTemplate`被`@LoadBalanced`修饰，所以它具备客户端负载均衡的能力，当请求真正发起的时候，url中的服务名会根据负载均衡策略从服务清单中挑选出一个实例来进行访问。

```
@SpringCloudApplication
public class Application {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@RestController
	class ConsumerController {

		@Autowired
		RestTemplate restTemplate;

		@RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
		public String helloConsumer() {
			return restTemplate.getForObject("http://hello-service/hello", String.class);
		}

	}
	
	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
```

大多数情况下，上面的实现没有任何问题，但是总有一些意外发生，比如：有一个实例发生了故障而该情况还没有被服务治理机制及时的发现和摘除，这时候客户端访问该节点的时候自然会失败。所以，为了构建更为健壮的应用系统，我们希望当请求失败的时候能够有一定策略的重试机制，而不是直接返回失败。这个时候就需要开发人员人工的来为上面的`RestTemplate`调用实现重试机制。

不过，从**Spring Cloud Camden SR2**版本开始，我们就不用那么麻烦了。从该版本开始，Spring Cloud整合了Spring Retry来实现重试逻辑，而对于开发者只需要做一些配置即可。

以上面对`hello-service`服务的调用为例，我们可以在配置文件中增加如下内容：

```
spring.cloud.loadbalancer.retry.enabled=true

hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=10000

hello-service.ribbon.ConnectTimeout=250
hello-service.ribbon.ReadTimeout=1000
hello-service.ribbon.OkToRetryOnAllOperations=true
hello-service.ribbon.MaxAutoRetriesNextServer=2
hello-service.ribbon.MaxAutoRetries=1
```

各参数的配置说明：

- `spring.cloud.loadbalancer.retry.enabled`：该参数用来开启重试机制，它默认是关闭的。这里需要注意，官方文档中的配置参数少了`enabled`。

  [![img](https://blog.didispace.com/assets/CAMDEN-SR2-RETRY.png)](https://blog.didispace.com/assets/CAMDEN-SR2-RETRY.png)

  源码定义如下：

  ```
  @ConfigurationProperties("spring.cloud.loadbalancer.retry")
  public class LoadBalancerRetryProperties {
      private boolean enabled = false;
   	... 
  }
  ```

- `hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds`：断路器的超时时间需要大于ribbon的超时时间，不然不会触发重试。

- `hello-service.ribbon.ConnectTimeout`：请求连接的超时时间

- `hello-service.ribbon.ReadTimeout`：请求处理的超时时间

- `hello-service.ribbon.OkToRetryOnAllOperations`：对所有操作请求都进行重试

- `hello-service.ribbon.MaxAutoRetriesNextServer`：切换实例的重试次数

- `hello-service.ribbon.MaxAutoRetries`：对当前实例的重试次数

根据如上配置，当访问到故障请求的时候，它会再尝试访问一次当前实例（次数由`MaxAutoRetries`配置），如果不行，就换一个实例进行访问，如果还是不行，再换一次实例访问（更换次数由`MaxAutoRetriesNextServer`配置），如果依然不行，返回失败信息。