# Spring Cloud Zuul中使用Swagger汇总API接口文档

**原创**

 [2018-05-24](https://blog.didispace.com/Spring-Cloud-Zuul-use-Swagger-API-doc/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 有很多读者问过这样的一个问题：虽然使用Swagger可以为Spring MVC编写的接口生成了API文档，但是在微服务化之后，这些API文档都离散在各个微服务中，是否有办法将这些接口都整合到一个文档中？之前给大家的回复都只是简单的说了个思路，昨天正好又有人问起，索性就举个例子写成博文供大家参考吧。

如果您还不了解`Spring Cloud Zuul`和`Swagger`，建议优先阅读下面两篇，有一个初步的了解：

- [Spring Cloud构建微服务架构：服务网关（基础）](http://blog.didispace.com/spring-cloud-starter-dalston-6-1/)
- [Spring Boot中使用Swagger2构建强大的RESTful API文档](http://blog.didispace.com/springbootswagger2/)

### 准备工作

上面说了问题的场景是在微服务化之后，所以我们需要先构建两个简单的基于Spring Cloud的微服务，命名为`swagger-service-a`和`swagger-service-b`。

下面只详细描述一个服务的构建内容，另外一个只是名称不同，如有疑问可以在文末查看详细的代码样例。

- 第一步：构建一个基础的Spring Boot应用，在`pom.xml`中引入eureka的依赖、web模块的依赖以及swagger的依赖（这里使用了我们自己构建的starter，[详细可点击查看](https://github.com/SpringForAll/spring-boot-starter-swagger)）。主要内容如下：

```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.10.RELEASE</version>
    <relativePath/>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>com.spring4all</groupId>
        <artifactId>swagger-spring-boot-starter</artifactId>
        <version>1.7.0.RELEASE</version>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Dalston.SR1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

- 第二步：编写应用主类：

```
@EnableSwagger2Doc
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

    @RestController
    class AaaController {
        @Autowired
        DiscoveryClient discoveryClient;

        @GetMapping("/service-a")
        public String dc() {
            String services = "Services: " + discoveryClient.getServices();
            System.out.println(services);
            return services;
        }
    }
}
```

其中，`@EnableSwagger2Doc`注解是我们自制Swagger Starter中提供的自定义注解，通过该注解会初始化默认的Swagger文档设置。下面还创建了一个通过Spring MVC编写的HTTP接口，用来后续在文档中查看使用。

- 第三步：设置配置文件内容：

```
spring.application.name=swagger-service-a
server.port=10010

eureka.client.serviceUrl.defaultZone=http://eureka.didispace.com/eureka/

swagger.base-package=com.didispace
```

其中，eureka服务端的配置采用了本站的公益eureka，大家可以通过http://eureka.didispace.com/查看详细以及使用方法。另外，`swagger.base-package`参数制定了要生成文档的package，只有`com.didispace`包下的Controller才会被生成文档。

**注意：上面构建了swagger-service-a服务，swagger-service-b服务可以如法炮制，不再赘述。**

### 构建API网关并整合Swagger

在[Spring Cloud构建微服务架构：服务网关（基础）](http://blog.didispace.com/spring-cloud-starter-dalston-6-1/)一文中，已经非常详细的介绍过使用Spring Cloud Zuul构建网关的详细步骤，这里主要介绍在基础网关之后，如何整合Swagger来汇总这些API文档。

- 第一步：在`pom.xml`中引入swagger的依赖，这里同样使用了我们自制的starter，所以主要的依赖包含下面这些：

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zuul</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
<dependency>
    <groupId>com.spring4all</groupId>
    <artifactId>swagger-spring-boot-starter</artifactId>
    <version>1.7.0.RELEASE</version>
</dependency>
```

- 第二步：在应用主类中配置swagger，具体如下：

```
@EnableSwagger2Doc
@EnableZuulProxy
@SpringCloudApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

    @Component
    @Primary
    class DocumentationConfig implements SwaggerResourcesProvider {
        @Override
        public List<SwaggerResource> get() {
            List resources = new ArrayList<>();
            resources.add(swaggerResource("service-a", "/swagger-service-a/v2/api-docs", "2.0"));
            resources.add(swaggerResource("service-b", "/swagger-service-b/v2/api-docs", "2.0"));
            return resources;
        }

        private SwaggerResource swaggerResource(String name, String location, String version) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(version);
            return swaggerResource;
        }
    }
}
```

说明：`@EnableSwagger2Doc`上面说过是开启Swagger功能的注解。这里的核心是下面对`SwaggerResourcesProvider`的接口实现部分，通过`SwaggerResource`添加了多个文档来源，按上面的配置，网关上Swagger会通过访问`/swagger-service-a/v2/api-docs`和`swagger-service-b/v2/api-docs`来加载两个文档内容，同时由于当前应用是Zuul构建的API网关，这两个请求会被转发到`swagger-service-a`和`swagger-service-b`服务上的`/v2/api-docs`接口获得到Swagger的JSON文档，从而实现汇总加载内容。

### 测试验证

将上面构建的两个微服务以及API网关都启动起来之后，访问网关的swagger页面，比如：`http://localhost:11000/swagger-ui.html`，此时可以看到如下图所示的内容：

[![img](https://blog.didispace.com/content/images/posts/Spring-Cloud-Zuul-use-Swagger-API-doc-1.png)](https://blog.didispace.com/content/images/posts/Spring-Cloud-Zuul-use-Swagger-API-doc-1.png)

可以看到在分组选择中就是当前配置的两个服务的选项，选择对应的服务名之后就会展示该服务的API文档内容。

### 代码示例

本文示例读者可以通过查看下面仓库的中的`swagger-service-a`、`swagger-service-b`、`swagger-api-gateway`三个项目：

- [Github](https://github.com/dyc87112/SpringCloud-Learning/tree/master/2-Dalston版教程示例/)
- [Gitee](https://gitee.com/didispace/SpringCloud-Learning/tree/master/2-Dalston版教程示例)

**如果您对这些感兴趣，欢迎star、follow、收藏、转发给予支持！**

##### 以下专题教程也许您会有兴趣

- [Spring Boot基础教程](http://blog.didispace.com/Spring-Boot基础教程/)
- [Spring Cloud基础教程](http://blog.didispace.com/Spring-Cloud基础教程/)