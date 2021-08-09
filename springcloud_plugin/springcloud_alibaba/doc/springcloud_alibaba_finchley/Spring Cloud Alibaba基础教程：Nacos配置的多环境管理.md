# Spring Cloud Alibaba基础教程：Nacos配置的多环境管理

**原创**

 [2019-01-30](https://blog.didispace.com/spring-cloud-alibaba-nacos-config-2/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

前情回顾：

- [《Spring Cloud Alibaba基础教程：使用Nacos实现服务注册与发现》](http://blog.didispace.com/spring-cloud-alibaba-1/)
- [《Spring Cloud Alibaba基础教程：支持的几种服务消费方式》](http://blog.didispace.com/spring-cloud-alibaba-2/)
- [《Spring Cloud Alibaba基础教程：使用Nacos作为配置中心》](http://blog.didispace.com/spring-cloud-alibaba-3/)
- [《Spring Cloud Alibaba基础教程：Nacos配置的加载规则详解》](http://blog.didispace.com/spring-cloud-alibaba-nacos-config-1/)

通过之前两篇对Nacos配置管理功能的介绍，已经学会了在Nacos中如何加入配置以及Spring Cloud应用如何通过配置来加载到对应的内容。接下来，我们讨论一个在使用配置中心时，都需要关注的一个问题：多环境的配置如何实现与管理？

## 多环境管理

在Nacos中，本身有多个不同管理级别的概念，包括：`Data ID`、`Group`、`Namespace`。只要利用好这些层级概念的关系，就可以根据自己的需要来实现多环境的管理。

下面，我就来介绍一下，可以使用的几种实现方式：

### 使用`Data ID`与`profiles`实现

`Data ID`在Nacos中，我们可以理解为就是一个Spring Cloud应用的配置文件名。通过上一篇[《Spring Cloud Alibaba基础教程：Nacos配置的加载规则详解》](http://blog.didispace.com/spring-cloud-alibaba-nacos-config-1/)，我们知道默认情况下`Data ID`的名称格式是这样的：`${spring.application.name}.properties`，即：以Spring Cloud应用命名的properties文件。

实际上，`Data ID`的规则中，还包含了环境逻辑，这一点与Spring Cloud Config的设计类似。我们在应用启动时，可以通过`spring.profiles.active`来指定具体的环境名称，此时客户端就会把要获取配置的`Data ID`组织为：`${spring.application.name}-${spring.profiles.active}.properties`。

> 实际上，更原始且最通用的匹配规则，是这样的：`${spring.cloud.nacos.config.prefix}`-`${spring.profile.active}`.`${spring.cloud.nacos.config.file-extension}`。而上面的结果是因为`${spring.cloud.nacos.config.prefix}`和`${spring.cloud.nacos.config.file-extension}`都使用了默认值。

**动手试一试**

我们可以用[《Spring Cloud Alibaba基础教程：使用Nacos作为配置中心》](http://blog.didispace.com/spring-cloud-alibaba-3/)一文中的列子（可在文末仓库中获取）为基础，体验一下这种区分环境的配置方式。

**第一步**：先在Nacos中，根据这个规则，创建两个不同环境的配置内容。比如：

[![img](https://blog.didispace.com/images/pasted-145.png)](https://blog.didispace.com/images/pasted-145.png)

如上图，我们为`alibaba-nacos-config-client`应用，定义了DEV和TEST的两个独立的环境配置。我们可以在里面定义不同的内容值，以便后续验证是否真实加载到了正确的配置。

**第二步**：在`alibaba-nacos-config-client`应用的配置文件中，增加环境配置：`spring.profiles.active=DEV`

**第三步**：启动应用，我们可以看到日志中打印了，加载的配置文件：

```
2019-01-30 15:25:18.216  INFO 96958 --- [           main] o.s.c.a.n.c.NacosPropertySourceBuilder   : Loading nacos data, dataId: 'alibaba-nacos-config-client-DEV.properties', group: 'DEFAULT_GROUP'
```

### 使用`Group`实现

`Group`在Nacos中是用来对`Data ID`做集合管理的重要概念。所以，如果我们把一个环境的配置视为一个集合，那么也就可以实现不同环境的配置管理。对于`Group`的用法并没有固定的规定，所以我们在实际使用的时候，需要根据我们的具体需求，可以是架构运维上对多环境的管理，也可以是业务上对不同模块的参数管理。为了避免冲突，我们需要在架构设计之初，做好一定的规划。这里，我们先来说说如何用`Group`来实现多环境配置管理的具体实现方式。

**动手试一试**

**第一步**：先在Nacos中，通过区分`Group`来创建两个不同环境的配置内容。比如：

[![img](https://blog.didispace.com/images/pasted-146.png)](https://blog.didispace.com/images/pasted-146.png)

如上图，我们为`alibaba-nacos-config-client`应用，定义了DEV环境和TEST环境的两个独立的配置，这两个匹配与上一种方法不同，它们的`Data ID`是完全相同的，只是`GROUP`不同。

**第二步**：在`alibaba-nacos-config-client`应用的配置文件中，增加`Group`的指定配置：`spring.cloud.nacos.config.group=DEV_GROUP`

**第三步**：启动应用，我们可以看到日志中打印了，加载的配置文件：

```
2019-01-30 15:55:23.718  INFO 3216 --- [main] o.s.c.a.n.c.NacosPropertySourceBuilder   : Loading nacos data, dataId: 'alibaba-nacos-config-client.properties', group: 'DEV_GROUP'
```

### 使用`Namespace`实现

`Namespace`在本系列教程中，应该还是第一次出现。先来看看官方的概念说明：用于进行租户粒度的配置隔离。不同的命名空间下，可以存在相同的`Group`或`Data ID`的配置。`Namespace`的常用场景之一是不同环境的配置的区分隔离，例如：开发测试环境和生产环境的资源（如配置、服务）隔离等。

在官方的介绍中，就介绍了利用其可以作为环境的隔离使用，下面我们就来试一下吧！

**动手试一试**

**第一步**：先在Nacos中，根据环境名称来创建多个`Namespace`。比如：

[![img](https://blog.didispace.com/images/pasted-147.png)](https://blog.didispace.com/images/pasted-147.png)

**第二步**：在配置列表的最上方，可以看到除了`Public`之外，多了几个刚才创建的`Namepsace`。分别在`DEV`和`TEST`空间下为`alibaba-nacos-config-client`应用创建配置内容：

[![img](https://blog.didispace.com/images/pasted-148.png)](https://blog.didispace.com/images/pasted-148.png)

**第三步**：在`alibaba-nacos-config-client`应用的配置文件中，增加`Namespace`的指定配置，比如：`spring.cloud.nacos.config.namespace=83eed625-d166-4619-b923-93df2088883a`。

> 这里需要注意namespace的配置不是使用名称，而是使用Namespace的ID。

**第四步**：启动应用，通过访问`localhost:8001/test`接口，验证一下返回内容是否正确。这种方式下，目前版本的日志并不会输出与`Namespace`相关的信息，所以还无法以此作为加载内容的判断依据。

## 深入思考

上面我们分别利用Nacos配置管理功能中的几个不同纬度来实现多环境的配置管理。从结果上而言，不论用哪一种方式，都能够胜任需求，但是哪一种最好呢？

**第一种**：通过`Data ID`与`profile`实现。

- *优点*：这种方式与Spring Cloud Config的实现非常像，用过Spring Cloud Config的用户，可以毫无违和感的过渡过来，由于命名规则类似，所以要从Spring Cloud Config中做迁移也非常简单。
- *缺点*：这种方式在项目与环境多的时候，配置内容就会显得非常混乱。配置列表中会看到各种不同应用，不同环境的配置交织在一起，非常不利于管理。
- *建议*：项目不多时使用，或者可以结合`Group`对项目根据业务或者组织架构做一些拆分规划。

**第二种**：通过`Group`实现。

- *优点*：通过`Group`按环境讲各个应用的配置隔离开。可以非常方便的利用`Data ID`和`Group`的搜索功能，分别从应用纬度和环境纬度来查看配置。
- *缺点*：由于会占用`Group`纬度，所以需要对`Group`的使用做好规划，毕竟与业务上的一些配置分组起冲突等问题。
- *建议*：这种方式虽然结构上比上一种更好一些，但是依然可能会有一些混乱，主要是在`Group`的管理上要做好规划和控制。

**第三种**：通过`Namespace`实现。

- *优点*：官方建议的方式，通过`Namespace`来区分不同的环境，释放了`Group`的自由度，这样可以让`Group`的使用专注于做业务层面的分组管理。同时，Nacos控制页面上对于`Namespace`也做了分组展示，不需要搜索，就可以隔离开不同的环境配置，非常易用。
- *缺点*：没有啥缺点，可能就是多引入一个概念，需要用户去理解吧。
- *建议*：直接用这种方式长远上来说会比较省心。虽然可能对小团队而言，项目不多，第一第二方式也够了，但是万一后面做大了呢？

> 注意：不论用哪一种方式实现。对于指定环境的配置（`spring.profiles.active=DEV`、`spring.cloud.nacos.config.group=DEV_GROUP`、`spring.cloud.nacos.config.namespace=83eed625-d166-4619-b923-93df2088883a`），都不要配置在应用的`bootstrap.properties`中。而是在发布脚本的启动命令中，用`-Dspring.profiles.active=DEV`的方式来动态指定，会更加灵活！。

## 参考资料

- [Nacos官方文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)

## 代码示例

本文示例读者可以通过查看下面仓库的中的`alibaba-nacos-config-client`项目：

- *Github：*[https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- *Gitee：*[https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)