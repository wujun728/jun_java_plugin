# Spring Cloud Alibaba基础教程：Sentinel使用Apollo存储规则

**原创**

 [2019-04-19](https://blog.didispace.com/spring-cloud-alibaba-sentinel-2-2/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

[上一篇](http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-1/)我们介绍了如何通过Nacos的配置功能来存储限流规则。Apollo是国内用户非常多的配置中心，所以，今天我们继续说说Spring Cloud Alibaba Sentinel中如何将流控规则存储在Apollo中。

## 使用Apollo存储限流规则

Sentinel自身就支持了多种不同的数据源来持久化规则配置，目前包括以下几种方式：

- 文件配置
- [Nacos配置](http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-1/)
- ZooKeeper配置
- [Apollo配置](http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-2/)

本文我们就来一起动手尝试一下，如何使用Apollo来存储限流规则。

### 准备工作

下面我们将同时使用到`Apollo`和`Sentinel Dashboard`，所以可以先把`Apollo`和`Sentinel Dashboard`启动起来。

如果还没入门`Sentinel Dashboard`可以通过文末的系列目录先学习之前的内容。Apollo的话相对复杂一些，这里不做详细介绍了，如果还没有接触过Apollo的读者可以查看其[官方文档](https://github.com/ctripcorp/apollo/wiki/Quick-Start)进一步学习。

### 应用配置

**第一步**：在Spring Cloud应用的`pom.xml`中引入Spring Cloud Alibaba的Sentinel模块和Apollo存储扩展：

```
 <dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
    <dependency>
        <groupId>com.alibaba.csp</groupId>
        <artifactId>sentinel-datasource-apollo</artifactId>
        <version>1.5.2</version>
    </dependency>
</dependencies>
```

**第二步**：在Spring Cloud应用中配置的服务信息，在`resource`目录下，创建`apollo-env.properties`文件，内容样例：

```
local.meta=http://192.168.0.201:8080
dev.meta=http://192.168.0.202:8080
```

**这里需要了解Apollo对多环境的配置，这里设置的是每个环境不同的配置服务地址，读者需要根据自己的实际情况修改。**

**第三步**：在Spring Cloud应用中添加配置信息：

```
spring.application.name=sentinel-datasource-apollo
server.port=8002

# apollo config
app.id=${spring.application.name}

# sentinel dashboard
spring.cloud.sentinel.transport.dashboard=localhost:8080

# sentinel datasource apollo
spring.cloud.sentinel.datasource.ds.apollo.namespaceName=application
spring.cloud.sentinel.datasource.ds.apollo.flowRulesKey=sentinel.flowRules
spring.cloud.sentinel.datasource.ds.apollo.rule-type=flow
```

- `app.id`：Apollo中的创建的项目名称，这里采用`spring.application.name`参数的引用，从而达到服务名与配置项目名一致的效果
- `spring.cloud.sentinel.transport.dashboard`：sentinel dashboard的访问地址，根据上面准备工作中启动的实例配置
- `spring.cloud.sentinel.datasource.ds.apollo.namespaceName`：Apollo的空间名
- `spring.cloud.sentinel.datasource.ds.apollo.flowRulesKey`：配置规则的key名称
- `spring.cloud.sentinel.datasource.ds.apollo.rule-type`：该参数是spring cloud alibaba升级到0.2.2之后增加的配置，用来定义存储的规则类型。所有的规则类型可查看枚举类：`org.springframework.cloud.alibaba.sentinel.datasource.RuleType`，每种规则的定义格式可以通过各枚举值中定义的规则对象来查看，比如限流规则可查看：`com.alibaba.csp.sentinel.slots.block.flow.FlowRule`

关于Apollo相关配置的对应关系可见下图所示：

[![upload successful](https://blog.didispace.com/images/pasted-206.png)](https://blog.didispace.com/images/pasted-206.png)upload successful

**第四步**：创建应用主类，并提供一个rest接口，比如：

```
@EnableApolloConfig
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Slf4j
    @RestController
    static class TestController {

        @GetMapping("/hello")
        public String hello() {
            return "didispace.com";
        }

    }

}
```

其中`@EnableApolloConfig`注解是开启Apollo的配置加载功能。

**第五步**：Apollo中配置限流规则，具体可见第三步的截图中的样子。其中，key值的内容是下面的json

```
[
    {
        "resource": "/hello",
        "limitApp": "default",
        "grade": 1,
        "count": 5,
        "strategy": 0,
        "controlBehavior": 0,
        "clusterMode": false
    }
]
```

可以看到上面配置规则是一个数组类型，数组中的每个对象是针对每一个保护资源的配置对象，每个对象中的属性解释如下：

- resource：资源名，即限流规则的作用对象
- limitApp：流控针对的调用来源，若为 default 则不区分调用来源
- grade：限流阈值类型（QPS 或并发线程数）；`0`代表根据并发数量来限流，`1`代表根据QPS来进行流量控制
- count：限流阈值
- strategy：调用关系限流策略
- controlBehavior：流量控制效果（直接拒绝、Warm Up、匀速排队）
- clusterMode：是否为集群模式

这里我们只做简单的配置解释，以便于理解这里的配置作用。实际上这里还有非常多可配置选项和规则，更复杂的配置后面我们单独开一篇来深入学习。

**第六步**：启动应用。如果一些顺利，可以看到类似下面的日志，代表已经成功从Nacos加载了一条限流规则：

```
2019-04-18 23:56:11.278  INFO 29149 --- [           main] o.s.c.a.s.c.SentinelDataSourceHandler    : [Sentinel Starter] DataSource ds-sentinel-apollo-datasource start to loadConfig
2019-04-18 23:56:11.279  INFO 29149 --- [           main] o.s.c.a.s.c.SentinelDataSourceHandler    : [Sentinel Starter] DataSource ds-sentinel-apollo-datasource load 1 FlowRule
```

通过postman或者curl访问几下`localhost:8002/hello`接口：

```
$ curl localhost:8002/hello
didispace.com
```

此时，在Sentinel Dashboard中就可以看到当前我们启动的`sentinel-datasource-apollo`服务。点击左侧菜单中的流控规则，可以看到已经存在一条记录了，这条记录就是上面我们在Apollo中配置的限流规则。

### 深入思考

在使用Apollo存储规则配置的时候与Nacos存储一样，对于Sentinel控制台这些数据是只读的，也就是说：

- Sentinel控制台中修改规则：仅存在于服务的内存中，不会修改Apollo中的配置值，重启后恢复原来的值。
- Nacos控制台中修改规则：服务的内存中规则会更新，Apollo中持久化规则也会更新，重启后依然保持。

## 代码示例

本文介绍内容的客户端代码，示例读者可以通过查看下面仓库中的`alibaba-sentinel-datasource-apollo`项目：

- *Github：*[https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- *Gitee：*[https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)