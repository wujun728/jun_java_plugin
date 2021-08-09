# Spring Cloud Alibaba基础教程：Nacos配置的加载规则详解

**原创**

 [2019-01-29](https://blog.didispace.com/spring-cloud-alibaba-nacos-config-1/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

前情回顾：

- [《Spring Cloud Alibaba基础教程：使用Nacos实现服务注册与发现》](http://blog.didispace.com/spring-cloud-alibaba-1/)
- [《Spring Cloud Alibaba基础教程：支持的几种服务消费方式（RestTemplate、WebClient、Feign）》](http://blog.didispace.com/spring-cloud-alibaba-2/)
- [《Spring Cloud Alibaba基础教程：使用Nacos作为配置中心》](http://blog.didispace.com/spring-cloud-alibaba-3/)

上一篇，我们学习了如何在Nacos中创建配置，以及如何使用Spring Cloud Alibaba的Nacos客户端模块来加载配置。在入门例子中，我们只配置了Nacos的地址信息，没有配置任何其他与配置加载相关的其他内容。所以，接下来准备分几篇说说大家问的比较多的一些实际使用的问题或疑问。

## 加载规则

在[《Spring Cloud Alibaba基础教程：使用Nacos作为配置中心》](http://blog.didispace.com/spring-cloud-alibaba-3/)一文中，我们的例子完全采用了默认配置完成。所以，一起来看看Spring Cloud Alibaba Nacos模块默认情况下是如何加载配置信息的。

首先，回顾一下，我们在入门例子中，Nacos中创建的配置内容是这样的：

- `Data ID`：alibaba-nacos-config-client.properties
- `Group`：DEFAULT_GROUP

拆解一下，主要有三个元素，它们与具体应用的配置内容对应关系如下：

- Data ID中的`alibaba-nacos-config-client`：对应客户端的配置`spring.cloud.nacos.config.prefix`，默认值为`${spring.application.name}`，即：服务名
- Data ID中的`properties`：对应客户端的配置`spring.cloud.nacos.config.file-extension`，默认值为`properties`
- Group的值`DEFAULT_GROUP`：对应客户端的配置`spring.cloud.nacos.config.group`，默认值为`DEFAULT_GROUP`

在采用默认值的应用要加载的配置规则就是：`Data ID=${spring.application.name}.properties`，`Group=DEFAULT_GROUP`。

下面，我们做一些假设例子，方便大家理解这些配置之间的关系：

**例子一**：如果我们不想通过服务名来加载，那么可以增加如下配置，就会加载到`Data ID=example.properties`，`Group=DEFAULT_GROUP`的配置内容了：

```
spring.cloud.nacos.config.prefix=example
```

**例子二**：如果我们想要加载yaml格式的内容，而不是Properties格式的内容，那么可以通过如下配置，实现加载`Data ID=example.yaml`，`Group=DEFAULT_GROUP`的配置内容了：

```
spring.cloud.nacos.config.prefix=example
spring.cloud.nacos.config.file-extension=yaml
```

**例子三**：如果我们对配置做了分组管理，那么可以通过如下配置，实现加载`Data ID=example.yaml`，`Group=DEV_GROUP`的配置内容了：

```
spring.cloud.nacos.config.prefix=example
spring.cloud.nacos.config.file-extension=yaml
spring.cloud.nacos.config.group=DEV_GROUP
```

## 深入思考

上面，我们具体介绍了在Nacos中添加的各种配置与Spring Cloud应用中客户端配置的对照关系。对于`spring.cloud.nacos.config.prefix`和`spring.cloud.nacos.config.file-extension`来说，没有太多的花样可以去揣摩，大部分用户默认配置就可以使用，或者通过`spring.cloud.nacos.config.file-extension`修改下配置格式的后缀。

但是对于`spring.cloud.nacos.config.group`的配置来说，还是可以派一些特殊的作用，比如：用它来区分不同的产品组下各个应用的配置内容（解决可能应用名冲突的问题）、或者用它来区分不同用途的配置内容、再或者用它来区分不同环境的配置（Nacos下的配置纬度很多，我们可以通过不同的手段来实现多环境的配置，后面会专门写一篇如何实现多环境的配置）等。

如果您对`spring.cloud.nacos.config.group`还有什么其他妙用，欢迎留言分享您的使用方案。

## 参考资料

- [Nacos官方文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)

## 代码示例

本系列教程的代码案例，都可以通过下面的仓库查看：

- *Github：*[https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- *Gitee：*[https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)