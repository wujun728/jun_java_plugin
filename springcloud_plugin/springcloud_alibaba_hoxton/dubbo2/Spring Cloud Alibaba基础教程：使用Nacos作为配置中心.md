# Spring Cloud Alibaba基础教程：使用Nacos作为配置中心

**原创**

 [2019-01-27](https://blog.didispace.com/spring-cloud-alibaba-3/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

通过本教程的前两篇：

- [《Spring Cloud Alibaba基础教程：使用Nacos实现服务注册与发现》](http://blog.didispace.com/spring-cloud-alibaba-1/)
- [《Spring Cloud Alibaba基础教程：支持的几种服务消费方式（RestTemplate、WebClient、Feign）》](http://blog.didispace.com/spring-cloud-alibaba-2/)

我们已经学会了，如何利用Nacos实现服务的注册与发现。同时，也介绍了在Spring Cloud中，我们可以使用的几种不同编码风格的服务消费方式。接下来，我们再来学习一下Nacos的另外一个重要能力：配置管理。

## 简介

Nacos除了实现了服务的注册发现之外，还将配置中心功能整合在了一起。通过Nacos的配置管理功能，我们可以将整个架构体系内的所有配置都集中在Nacos中存储。这样做的好处，在以往的教程中介绍Spring Cloud Config时也有提到，主要有以下几点：

- 分离的多环境配置，可以更灵活的管理权限，安全性更高
- 应用程序的打包更为纯粹，以实现一次打包，多处运行的特点（[《云原声应用的12要素》之一](http://blog.didispace.com/12factor-zh-cn/)）

Nacos的配置管理模型与淘宝开源的配置中心Diamond类似，基础层面都通过`DataId`和`Group`来定位配置内容，除此之外还增加了很多其他的管理功能。

## 快速入门

下面我们通过一个简单的例子来介绍如何在Nacos中创建配置内容以及如何在Spring Cloud应用中加载Nacos的配置信息。

### 创建配置

第一步：进入Nacos的控制页面，在配置列表功能页面中，点击右上角的“+”按钮，进入“新建配置”页面，如下图填写内容：

[![img](https://blog.didispace.com/images/pasted-144.png)](https://blog.didispace.com/images/pasted-144.png)

其中：

- `Data ID`：填入`alibaba-nacos-config-client.properties`
- `Group`：不修改，使用默认值`DEFAULT_GROUP`
- `配置格式`：选择`Properties`
- `配置内容`：应用要加载的配置内容，这里仅作为示例，做简单配置，比如：`didispace.title=spring-cloud-alibaba-learning`

### 创建应用

**第一步**：创建一个Spring Boot应用，可以命名为：`alibaba-nacos-config-client`。

**第二步**：编辑`pom.xml`，加入必要的依赖配置，比如：

```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.5.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Finchley.SR1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>0.2.2.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.2</version>
        <optional>true</optional>
    </dependency>
</dependencies>
```

上述内容主要三部分：

- `parent`：定义spring boot的版本
- `dependencyManagement`：spring cloud的版本以及spring cloud alibaba的版本，由于spring cloud alibaba还未纳入spring cloud的主版本管理中，所以需要自己加入
- `dependencies`：当前应用要使用的依赖内容。这里主要新加入了Nacos的配置客户端模块：`spring-cloud-starter-alibaba-nacos-config`。由于在`dependencyManagement`中已经引入了版本，所以这里就不用指定具体版本了。

> 可以看到，这个例子中并没有加入nacos的服务发现模块，所以这两个内容是完全可以独立使用的

**第三步**：创建应用主类，并实现一个HTTP接口：

```
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Slf4j
    @RestController
    @RefreshScope
    static class TestController {

        @Value("${didispace.title:}")
        private String title;

        @GetMapping("/test")
        public String hello() {
            return title;
        }

    }

}
```

内容非常简单，`@SpringBootApplication`定义是个Spring Boot应用；还定义了一个Controller，其中通过`@Value`注解，注入了key为`didispace.title`的配置（默认为空字符串），这个配置会通过`/test`接口返回，后续我们会通过这个接口来验证Nacos中配置的加载。另外，这里还有一个比较重要的注解`@RefreshScope`，主要用来让这个类下的配置内容支持动态刷新，也就是当我们的应用启动之后，修改了Nacos中的配置内容之后，这里也会马上生效。

**第四步**：创建配置文件`bootstrap.properties`，并配置服务名称和Nacos地址

```
spring.application.name=alibaba-nacos-config-client
server.port=8001

spring.cloud.nacos.config.server-addr=127.0.0.1:8848
```

> **注意**：这里必须使用`bootstrap.properties`。同时，`spring.application.name`值必须与上一阶段Nacos中创建的配置Data Id匹配（除了.properties或者.yaml后缀）。

**第五步**：启动上面创建的应用。

```
2019-01-27 18:29:43.497  INFO 93597 --- [           main] o.s.c.a.n.c.NacosPropertySourceBuilder   : Loading nacos data, dataId: 'alibaba-nacos-config-client.properties', group: 'DEFAULT_GROUP'
2019-01-27 18:29:43.498  INFO 93597 --- [           main] b.c.PropertySourceBootstrapConfiguration : Located property source: CompositePropertySource {name='NACOS', propertySources=[NacosPropertySource {name='alibaba-nacos-config-client.properties'}]}
```

在启动的时候，我们可以看到类似上面的日志信息，这里会输出应用程序要从Nacos中获取配置的dataId和group。如果在启动之后，发现配置信息没有获取到的时候，可以先从这里着手，看看配置加载的目标是否正确。

**第六步**：验证配置获取和验证动态刷新

用curl或者postman等工具，访问接口: `localhost:8001/test`，一切正常的话，将返回Nacos中配置的`spring-cloud-alibaba-learning`。然后，再通过Nacos页面，修改这个内容，点击发布之后，再访问接口，可以看到返回结果变了。

同时，在应用的客户端，我们还能看到如下日志：

```
2019-01-27 18:39:14.162  INFO 93597 --- [-127.0.0.1_8848] o.s.c.e.event.RefreshEventListener       : Refresh keys changed: [didispace.title]
```

在Nacos中修改了Key，在用到这个配置的应用中，也自动刷新了这个配置信息。

## 参考资料

- [Nacos官方文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)
- [Nacos源码分析](http://www.iocoder.cn/Nacos/good-collection/?vip)

## 代码示例

本文示例读者可以通过查看下面仓库的中的`alibaba-nacos-config-client`项目：

- *Github：*[https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- *Gitee：*[https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)