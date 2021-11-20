# Spring Cloud Alibaba基础教程：使用Sentinel实现接口限流

**原创**

 [2019-04-09](https://blog.didispace.com/spring-cloud-alibaba-sentinel-1/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

> 最近管点闲事浪费了不少时间，感谢网友`libinwalan`的留言提醒。及时纠正路线，继续跟大家一起学习Spring Cloud Alibaba。

Nacos作为注册中心和配置中心的基础教程，到这里先告一段落，后续与其他结合的内容等讲到的时候再一起拿出来说，不然内容会有点跳跃。接下来我们就来一起学习一下Spring Cloud Alibaba下的另外一个重要组件：Sentinel。

## Sentinel是什么

Sentinel的官方标题是：分布式系统的流量防卫兵。从名字上来看，很容易就能猜到它是用来作服务稳定性保障的。对于服务稳定性保障组件，如果熟悉Spring Cloud的用户，第一反应应该就是Hystrix。但是比较可惜的是Netflix已经宣布对Hystrix停止更新。那么，在未来我们还有什么更好的选择呢？除了Spring Cloud官方推荐的resilience4j之外，目前Spring Cloud Alibaba下整合的Sentinel也是用户可以重点考察和选型的目标。

Sentinel的功能和细节比较多，一篇内容很难介绍完整。所以下面我会分多篇来一一介绍Sentinel的重要功能。本文就先从限流入手，说说如何把Sentinel整合到Spring Cloud应用中，以及如何使用Sentinel Dashboard来配置限流规则。通过这个简单的例子，先将这一套基础配置搭建起来。

## 使用Sentinel实现接口限流

Sentinel的使用分为两部分：

- sentinel-dashboard：与hystrix-dashboard类似，但是它更为强大一些。除了与hystrix-dashboard一样提供实时监控之外，还提供了流控规则、熔断规则的在线维护等功能。
- 客户端整合：每个微服务客户端都需要整合sentinel的客户端封装与配置，才能将监控信息上报给dashboard展示以及实时的更改限流或熔断规则等。

下面我们就分两部分来看看，如何使用Sentienl来实现接口限流。

### 部署Sentinel Dashboard

> **补充（2019-04-28）**：本文案例已升级Spring Cloud Alibaba 0.2.2，由于该版本中升级了Sentinel到1.5.2，所以对sentinel-dashboard做一次升级。但是sentinel-dashboard的1.5.2版本的打包文件没有提供下载，如果一定要该版本的话，需要自己编译。这里笔者尝试了一下直接使用1.6.0的sentinel-dashboard，暂时也没有发现什么问题，所以就以这个版本为例。

- 下载地址：[sentinel-dashboard-1.6.0.jar](https://github.com/alibaba/Sentinel/releases/download/1.6.0/sentinel-dashboard-1.6.0.jar)
- 其他版本：[Sentinel/releases](https://github.com/alibaba/Sentinel/releases)

> 同以往的Spring Cloud教程一样，这里也不推荐大家跨版本使用，不然可能会出现各种各样的问题。

通过命令启动：

```
java -jar sentinel-dashboard-1.6.0.jar
```

sentinel-dashboard不像Nacos的服务端那样提供了外置的配置文件，比较容易修改参数。不过不要紧，由于sentinel-dashboard是一个标准的spring boot应用，所以如果要自定义端口号等内容的话，可以通过在启动命令中增加参数来调整，比如：`-Dserver.port=8888`。

默认情况下，sentinel-dashboard以8080端口启动，所以可以通过访问：`localhost:8080`来验证是否已经启动成功，如果一切顺利的话，可以看到如下页面：

[![img](https://blog.didispace.com/images/pasted-211.png)](https://blog.didispace.com/images/pasted-211.png)

**注意**：只有1.6.0及以上版本，才有这个简单的登录页面。默认用户名和密码都是`sentinel`。对于用户登录的相关配置可以在启动命令中增加下面的参数来进行配置：

- `-Dsentinel.dashboard.auth.username=sentinel`: 用于指定控制台的登录用户名为 sentinel；
- `-Dsentinel.dashboard.auth.password=123456`: 用于指定控制台的登录密码为 123456；如果省略这两个参数，默认用户和密码均为 sentinel
- `-Dserver.servlet.session.timeout=7200`: 用于指定 Spring Boot 服务端 session 的过期时间，如 7200 表示 7200 秒；60m 表示 60 分钟，默认为 30 分钟；

输入账户密码登录后，可以看到如下页面：

[![upload successful](https://blog.didispace.com/images/pasted-203.png)](https://blog.didispace.com/images/pasted-203.png)upload successful

### 整合Sentinel

**第一步**：在Spring Cloud应用的`pom.xml`中引入Spring Cloud Alibaba的Sentinel模块：

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
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.2</version>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

**第二步**：在Spring Cloud应用中通过`spring.cloud.sentinel.transport.dashboard`参数配置sentinel dashboard的访问地址，比如：

```
spring.application.name=alibaba-sentinel-rate-limiting
server.port=8001

# sentinel dashboard
spring.cloud.sentinel.transport.dashboard=localhost:8080
```

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

**第四步**：启动应用，然后通过postman或者curl访问几下`localhost:8001/hello`接口。

```
$ curl localhost:8001/hello
didispace.com
```

此时，在上一节启动的Sentinel Dashboard中就可以当前我们启动的`alibaba-sentinel-rate-limiting`这个服务以及接口调用的实时监控了。具体如下图所示：

[![upload successful](https://blog.didispace.com/images/pasted-202.png)](https://blog.didispace.com/images/pasted-202.png)upload successful

### 配置限流规则

在完成了上面的两节之后，我们在`alibaba-sentinel-rate-limiting`服务下，点击`簇点链路`菜单，可以看到如下界面：

[![upload successful](https://blog.didispace.com/images/pasted-201.png)](https://blog.didispace.com/images/pasted-201.png)upload successful

其中`/hello`接口，就是我们上一节中实现并调用过的接口。通过点击`流控`按钮，来为该接口设置限流规则，比如：

[![upload successful](https://blog.didispace.com/images/pasted-200.png)](https://blog.didispace.com/images/pasted-200.png)upload successful

这里做一个最简单的配置：

- 阈值类型选择：QPS
- 单机阈值：2

综合起来的配置效果就是，该接口的限流策略是每秒最多允许2个请求进入。

点击`新增`按钮之后，可以看到如下界面：

[![upload successful](https://blog.didispace.com/images/pasted-199.png)](https://blog.didispace.com/images/pasted-199.png)upload successful

其实就是左侧菜单中`流控规则`的界面，这里可以看到当前设置的所有限流策略。

### 验证限流规则

在完成了上面所有内容之后，我们可以尝试一下快速的调用这个接口，看看是否会触发限流控制，比如：

```
$ curl localhost:8001/hello
didispace.com
$ curl localhost:8001/hello
didispace.com
$ curl localhost:8001/hello
Blocked by Sentinel (flow limiting)
```

可以看到，快速的调用两次`/hello`接口之后，第三次调用被限流了。

## 代码示例

本文介绍内容的客户端代码，示例读者可以通过查看下面仓库中的`alibaba-sentinel-rate-limiting`项目：

- *Github：*[https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- *Gitee：*[https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)