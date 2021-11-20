# Spring Cloud实战小贴士：Feign的继承特性(伪RPC模式)

**原创**

 [2017-08-08](https://blog.didispace.com/spring-cloud-tips-feign-rpc/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 通过之前发布的[《Spring Cloud构建微服务架构：服务消费者（Feign）》](http://blog.didispace.com/spring-cloud-starter-dalston-2-3/)，我们已经学会如何使用Spring MVC的注解来绑定服务接口。我们几乎完全可以从服务提供方的Controller中依靠复制操作，来构建出相应的服务接口客户端，或是通过Swagger生成的API文档来编写出客户端，亦或是通过Swagger的代码生成器来生成客户端绑定。即便如此，有很多的方式来产生Feign的客户端程序，依然有很多开发者热衷于利用公共的依赖接口来连接服务提供者和服务消费者的方式。由此，Feign的继承特性就能很好的派上用处。下面，我们来详细看看如何使用Spring Cloud Feign的继承特性。

## 动手试一试

接下来的示例将分为三个模块：

- 服务接口定义模块：通过Spring MVC注解定义抽象的interface服务接口
- 服务接口实现模块：实现服务接口定义模块的interface，该模块作为服务提供者注册到eureka
- 服务接口消费模块：服务接口定义模块的客户端实现，该模块通过注册到eureka来消费服务接口

### 服务接口的定义

- 创建一个Spring Boot项目：eureka-feign-api，`pom.xml`的主要内容如下：

```
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>1.5.6.RELEASE</version>
	<relativePath/>
</parent>

<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
</dependencies>

<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-dependencies</artifactId>
			<version>Dalston.SR2</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

- 使用Spring MVC注解来定义服务接口：

```
public interface HelloService {

    @GetMapping("/hello")
    String hello(@RequestParam(value = "name") String name);

}
```

- 完成了上述构建之后，我们使用`mvn install`将该模块构建到本地的Maven仓库中。

### 服务接口的实现

- 创建一个Spring Boot项目：eureka-feign-client，`pom.xml`的主要内容如下：

```
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>1.5.6.RELEASE</version>
	<relativePath/>
</parent>

<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>
	<dependency>
		<groupId>com.didispace</groupId>
		<artifactId>eureka-feign-api</artifactId>
		<version>1.0.0</version>
	</dependency>
</dependencies>

<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-dependencies</artifactId>
			<version>Dalston.SR2</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

该模块需要依赖上面定义的`eureka-feign-api`，将使用上述定义的`HelloService`接口来实现对应的REST服务。同时依赖Eureka是为了将该服务注册到Eureka上供服务消费者发现。

- 创建应用主类。使用`@EnableDiscoveryClient`注解开启服务注册与发现，并实现`HelloService`接口的REST服务：

```
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

	@RestController
	class HelloController implements HelloService {
		@Override
		public String hello(String name) {
			return "hello " + name;
		}
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}

}
```

- 编辑`application.properties`配置内容：

```
spring.application.name=eureka-feign-client
server.port=2101

eureka.client.serviceUrl.defaultZone=http://eureka.didispace.com/eureka/
```

配置了服务提供者的名称`eureka-feign-client`，服务提供者的端口号`2101`，并将该服务注册到我的公益Eureka注册中心上。启动该项目，我们可以通过访问：http://eureka.didispace.com/ ，在该页面中找到它。

### 服务接口的消费

- 创建一个Spring Boot项目：eureka-feign-consumer，`pom.xml`的主要内容如下：

```
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>1.5.6.RELEASE</version>
	<relativePath/>
</parent>

<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-feign</artifactId>
	</dependency>
	<dependency>
		<groupId>com.didispace</groupId>
		<artifactId>eureka-feign-api</artifactId>
		<version>1.0.0</version>
	</dependency>
</dependencies>

<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-dependencies</artifactId>
			<version>Dalston.SR2</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

该模块较服务提供者的依赖增加了Feign的依赖，因为这里将使用Feign来绑定服务接口的客户端。下面我们将使用Feign的继承特性来轻松的构建Feign客户端。

- 创建应用主类。使用`@EnableDiscoveryClient`注解开启服务注册与发现，并通过`@FeignClient`注解来声明服务绑定客户端：

```
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

	@FeignClient("eureka-feign-client")
	interface HelloServiceClient extends HelloService {
	}

	@RestController
	class TestController {
		@Autowired
		private HelloServiceClient helloServiceClient;
		@GetMapping("/test")
		public String test(String name) {
			return helloServiceClient.hello(name);
		}
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}
}
```

从上述代码中我们可以看到，利用Feign的继承特性，`@FeignClient`注解只需要通过声明一个接口来继承在API模块中定义的公共interface就能产生服务接口的Feign客户端了。而`@FeignClient`中的值需要填写该服务的具体服务名（服务提供者的`spring.application.name`配置值）。

- 编辑服务消费者的`application.properties`配置内容，将服务消费者注册到eureka上来消费服务：

```
spring.application.name=eureka-feign-consumer
server.port=2102

eureka.client.serviceUrl.defaultZone=http://eureka.didispace.com/eureka/
```

- 启动`eureka-feign-consumer`之后，我们可以通过访问：http://localhost:2102/test ，来实验`eureka-feign-consumer`对`eureka-feign-client`接口的调用。

## 本文示例

- [码云](https://gitee.com/didispace/SpringCloud-Learning/tree/master/2-Dalston版教程示例)
- [GitHub](https://github.com/dyc87112/SpringCloud-Learning/tree/master/2-Dalston版教程示例)