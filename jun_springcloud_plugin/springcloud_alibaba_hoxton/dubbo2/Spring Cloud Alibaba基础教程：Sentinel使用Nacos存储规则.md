# Spring Cloud Alibaba基础教程：Sentinel使用Nacos存储规则

**原创**

 [2019-04-16](https://blog.didispace.com/spring-cloud-alibaba-sentinel-2-1/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

通过上一篇[《使用Sentinel实现接口限流》](http://blog.didispace.com/spring-cloud-alibaba-sentinel-1/)的介绍，相信大家对Sentinel已经有了初步的认识。在Spring Cloud Alibaba的整合封装之下，接口限流这件事情可以非常轻易的整合到我们的Spring Cloud应用中。但是，通过上篇的整合，依然还不能完美的满足我们日常的生产需求。其中，非常重要的一点就是限流规则的持久化问题。不少细心的读者也在留言中提出了Dashboard中设置的限流规则在应用重启之后就丢失了的问题。那么，接下来我们就来说说Sentinel的规则持久化如何实现。

## 使用Nacos存储限流规则

Sentinel自身就支持了多种不同的数据源来持久化规则配置，目前包括以下几种方式：

- 文件配置
- [Nacos配置](http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-1/)
- ZooKeeper配置
- [Apollo配置](http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-2/)

本文我们就来一起动手尝试一下，使用Spring Cloud Alibaba的中整合的配置中心`Nacos`存储限流规则。

### 准备工作

下面我们将同时使用到`Nacos`和`Sentinel Dashboard`，所以可以先把`Nacos`和`Sentinel Dashboard`启动起来。

默认配置下启动后，它们的访问地址（后续会用到）为：

- Nacos：http://localhost:8848/
- Sentinel Dashboard：http://localhost:8080/

如果还没入门`Nacos`和`Sentinel Dashboard`可以通过文末的系列目录先学习之前的内容。

### 应用配置

**第一步**：在Spring Cloud应用的`pom.xml`中引入Spring Cloud Alibaba的Sentinel模块和Nacos存储扩展：

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
        <artifactId>sentinel-datasource-nacos</artifactId>
        <version>1.5.2</version>
    </dependency>
</dependencies>
```

**第二步**：在Spring Cloud应用中添加配置信息：

```
spring.application.name=alibaba-sentinel-datasource-nacos
server.port=8003

# sentinel dashboard
spring.cloud.sentinel.transport.dashboard=localhost:8080

# sentinel datasource nacos ：http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-1/
spring.cloud.sentinel.datasource.ds.nacos.server-addr=localhost:8848
spring.cloud.sentinel.datasource.ds.nacos.dataId=${spring.application.name}-sentinel
spring.cloud.sentinel.datasource.ds.nacos.groupId=DEFAULT_GROUP
spring.cloud.sentinel.datasource.ds.nacos.rule-type=flow
```

- `spring.cloud.sentinel.transport.dashboard`：sentinel dashboard的访问地址，根据上面准备工作中启动的实例配置
- `spring.cloud.sentinel.datasource.ds.nacos.server-addr`：nacos的访问地址，，根据上面准备工作中启动的实例配置
- `spring.cloud.sentinel.datasource.ds.nacos.groupId`：nacos中存储规则的groupId
- `spring.cloud.sentinel.datasource.ds.nacos.dataId`：nacos中存储规则的dataId
- `spring.cloud.sentinel.datasource.ds.nacos.rule-type`：该参数是spring cloud alibaba升级到0.2.2之后增加的配置，用来定义存储的规则类型。所有的规则类型可查看枚举类：`org.springframework.cloud.alibaba.sentinel.datasource.RuleType`，每种规则的定义格式可以通过各枚举值中定义的规则对象来查看，比如限流规则可查看：`com.alibaba.csp.sentinel.slots.block.flow.FlowRule`

这里对于dataId使用了`${spring.application.name}`变量，这样可以根据应用名来区分不同的规则配置。

**注意**：由于版本迭代关系，Github Wiki中的文档信息不一定适用所有版本。比如：在这里适用的0.2.1版本中，并没有`spring.cloud.sentinel.datasource.ds2.nacos.rule-type`这个参数。所以，读者在使用的时候，可以通过查看`org.springframework.cloud.alibaba.sentinel.datasource.config.DataSourcePropertiesConfiguration`和`org.springframework.cloud.alibaba.sentinel.datasource.config.NacosDataSourceProperties`两个类来分析具体的配置内容，会更为准确。

比如，`Nacos`存储的具体配置类源码如下：

```
public class NacosDataSourceProperties extends AbstractDataSourceProperties {

    private String serverAddr;
    private String groupId;
    private String dataId;

    // commercialized usage

    private String endpoint;
    private String namespace;
    private String accessKey;
    private String secretKey;

}
```

上面我们使用了前三个属性，后四个属性是阿里云的商业化产品使用的，这里就不具体介绍了，有使用阿里云商业产品的童鞋可以了解一下。

**第三步**：创建应用主类，并提供一个rest接口，比如：

```
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

**第四步**：Nacos中创建限流规则的配置，比如：

[![upload successful](https://blog.didispace.com/images/pasted-204.png)](https://blog.didispace.com/images/pasted-204.png)upload successful

其中：`Data ID`、`Group`就是上面**第二步**中配置的内容。配置格式选择JSON，并在配置内容中填入下面的内容：

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

**第五步**：启动应用。如果一些顺利，可以看到类似下面的日志，代表已经成功从Nacos加载了一条限流规则：

```
2019-04-16 14:24:42.919  INFO 89484 --- [           main] o.s.c.a.s.c.SentinelDataSourceHandler    : [Sentinel Starter] DataSource ds-sentinel-nacos-datasource start to loadConfig
2019-04-16 14:24:42.938  INFO 89484 --- [           main] o.s.c.a.s.c.SentinelDataSourceHandler    : [Sentinel Starter] DataSource ds-sentinel-nacos-datasource load 1 FlowRule
```

通过postman或者curl访问几下`localhost:8003/hello`接口：

```
$ curl localhost:8003/hello
didispace.com
```

此时，在Sentinel Dashboard中就可以看到当前我们启动的`alibaba-sentinel-datasource-nacos`服务。点击左侧菜单中的流控规则，可以看到已经存在一条记录了，具体如下：

[![upload successful](https://blog.didispace.com/images/pasted-205.png)](https://blog.didispace.com/images/pasted-205.png)upload successful

这条记录就是上面我们在Nacos中配置的限流规则。

### 深入思考

在完成了上面的整合之后，对于接口流控规则的修改就存在两个地方了：Sentinel控制台、Nacos控制台。

这个时候，需要注意当前版本的Sentinel控制台不具备同步修改Nacos配置的能力，而Nacos由于可以通过在客户端中使用Listener来实现自动更新。所以，在整合了Nacos做规则存储之后，需要知道在下面两个地方修改存在不同的效果：

- Sentinel控制台中修改规则：仅存在于服务的内存中，不会修改Nacos中的配置值，重启后恢复原来的值。
- Nacos控制台中修改规则：服务的内存中规则会更新，Nacos中持久化规则也会更新，重启后依然保持。

## 代码示例

本文介绍内容的客户端代码，示例读者可以通过查看下面仓库中的`alibaba-sentinel-datasource-nacos`项目：

- *Github：*[https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- *Gitee：*[https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)