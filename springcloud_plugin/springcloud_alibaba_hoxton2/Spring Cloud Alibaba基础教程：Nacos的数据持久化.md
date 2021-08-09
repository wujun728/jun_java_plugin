# Spring Cloud Alibaba基础教程：Nacos的数据持久化

**原创**

 [2019-02-16](https://blog.didispace.com/spring-cloud-alibaba-4/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

前情回顾：

- [《Spring Cloud Alibaba基础教程：使用Nacos实现服务注册与发现》](http://blog.didispace.com/spring-cloud-alibaba-1/)
- [《Spring Cloud Alibaba基础教程：支持的几种服务消费方式》](http://blog.didispace.com/spring-cloud-alibaba-2/)
- [《Spring Cloud Alibaba基础教程：使用Nacos作为配置中心》](http://blog.didispace.com/spring-cloud-alibaba-3/)
- [《Spring Cloud Alibaba基础教程：Nacos配置的加载规则详解》](http://blog.didispace.com/spring-cloud-alibaba-nacos-config-1/)
- [《Spring Cloud Alibaba基础教程：Nacos配置的多环境管理》](http://blog.didispace.com/spring-cloud-alibaba-nacos-config-2/)
- [《Spring Cloud Alibaba基础教程：Nacos配置的多文件加载与共享配置》](http://blog.didispace.com/spring-cloud-alibaba-nacos-config-3/)

通过之前几篇关于Nacos的博文，对于Nacos分别作为服务注册中心以及配置中心时，与Spring Cloud体系结合的基础使用方法已经介绍完毕了。下面我们再用几篇博文从生产部署的角度，介绍Nacos的相关内容。本文我们将具体说说Nacos的数据存储以及生产配置的推荐。

## 数据持久化

在之前的教程中，我们对于Nacos服务端自身并没有做过什么特殊的配置，一切均以默认的单机模式运行，完成了上述所有功能的学习。但是，Nacos的单机运行模式仅适用于学习与测试环境，对于有高可用要求的生产环境显然是不合适的。那么，我们是否可以直接启动多个单机模式的Nacos，然后客户端指定多个Nacos节点就可以实现高可用吗？答案是否定的。

在搭建Nacos集群之前，我们需要先修改Nacos的数据持久化配置为MySQL存储。默认情况下，Nacos使用嵌入式数据库实现数据的存储。所以，如果启动多个默认配置下的Nacos节点，数据存储是存在一致性问题的。为了解决这个问题，Nacos采用了集中式存储的方式来支持集群化部署，目前只要支持MySQL的存储。

配置Nacos的MySQL存储只需要下面三步：

**第一步**：安装数据库，版本要求：5.6.5+

**第二步**：初始化MySQL数据库，数据库初始化文件：`nacos-mysql.sql`，该文件可以在Nacos程序包下的`conf`目录下获得。执行完成后可以得到如下图所示的表结构：

[![img](https://blog.didispace.com/images/pasted-149.png)](https://blog.didispace.com/images/pasted-149.png)

**第三步**：修改`conf/application.properties`文件，增加支持MySQL数据源配置，添加（目前只支持mysql）数据源的url、用户名和密码。配置样例如下：

```
spring.datasource.platform=mysql

db.num=1
db.url.0=jdbc:mysql://localhost:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=root
db.password=
```

到这里，Nacos数据存储到MySQL的配置就完成了，可以尝试继续用单机模式启动Nacos。然后再根据之前学习的Nacos配置中心的用法来做一些操作，配合MySQL工具就可以看到数据已经写入到数据库中了。下一篇，我们将继续

## 深入思考

关于Nacos数据的持久化实现，与其他的中间件相比，在实现上并没有采用分布式算法来解决一致性问题，而是采用了比较常规的集中化存储来实现。由于采用单一数据源的方式，直接解决了分布式一致性问题，所以从学习成本的角度上来说，Nacos的实现原理会更容易被理解和接受。但是，从部署的负责度和硬件投入成本上来说，与etcd、consul、zookeeper这些通过算法方式解决一致性问题的中间件相比，就显得不足了。

同时，在引入MySQL的存储时，由于多了一个中间件的存在，整个Nacos系统的整体可用性一定是会所有下降的。所以为了弥补可用性的下降，在生产上MySQL的高可用部署也是必须的，成本再次提高。不论如何提高，可用性都难以达到100%，所以这种方式，不论如何提升存储的可用性，理论上都会对Nacos集群的自身可用性造成微小的下降。

以上思考主要从理论上，粗略讨论的，并没有经过详细的成本评估与可用性计算。所以，对于实际应用场景下，可能这些成本的增加和可用性的降低并没有那么多大的影响。同时，Spring Cloud Alibaba下使用的各开源组件都有对应的商业产品，在没有足够运维人力的团队下，使用对应的商业产品可能从各方面都会更加划算。

## 参考资料

- [Nacos官方文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)

## 代码示例

本文介绍内容的客户端代码，示例读者可以通过查看下面仓库中的`alibaba-nacos-config-client`项目：

- *Github：*[https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- *Gitee：*[https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)