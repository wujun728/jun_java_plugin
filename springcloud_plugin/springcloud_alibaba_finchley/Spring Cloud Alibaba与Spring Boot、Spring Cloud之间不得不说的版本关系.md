# Spring Cloud Alibaba与Spring Boot、Spring Cloud之间不得不说的版本关系

**原创**

 [2019-03-02](https://blog.didispace.com/spring-cloud-alibaba-version/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

这篇博文是临时增加出来的内容，主要是由于最近连载《Spring Cloud Alibaba基础教程》系列的时候，碰到读者咨询的大量问题中存在一个比较普遍的问题：版本的选择。其实这类问题，在之前写Spring Cloud基础教程的时候，就已经发过一篇[《聊聊Spring Cloud版本的那些事儿》](http://blog.didispace.com/springcloud-version/)，来说明Spring Boot和Spring Cloud版本之间的关系。

## Spring Cloud Alibaba现阶段版本的特殊性

现在的Spring Cloud Alibaba由于没有纳入到Spring Cloud的主版本管理中，所以我们需要自己去引入其版本信息，比如之前教程中的例子：

```
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
            <version>0.2.1.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

而不是像以往使用Spring Cloud的时候，直接引入Spring Cloud的主版本（Dalston、Edgware、Finchley、Greenwich这些）就可以的。我们需要像上面的例子那样，单独的引入`spring-cloud-alibaba-dependencies`来管理Spring Cloud Alibaba下的组件版本。

由于Spring Cloud基于Spring Boot构建，而Spring Cloud Alibaba又基于Spring Cloud Common的规范实现，所以当我们使用Spring Cloud Alibaba来构建微服务应用的时候，需要知道这三者之间的版本关系。

下表整理了目前Spring Cloud Alibaba的版本与Spring Boot、Spring Cloud版本的兼容关系：

| Spring Boot | Spring Cloud | Spring Cloud Alibaba |
| :---------- | :----------- | :------------------- |
| 2.1.x       | Greenwich    | 0.9.x                |
| 2.0.x       | Finchley     | 0.2.x                |
| 1.5.x       | Edgware      | 0.1.x                |
| 1.5.x       | Dalston      | 0.1.x                |

**以上版本对应内容根据当前情况实时调整修改，以方便用户了解他们的对应关系变化情况**

所以，不论您是在读我的[《Spring Boot基础教程》](http://blog.didispace.com/Spring-Boot基础教程/)、[《Spring Cloud基础教程》](http://blog.didispace.com/spring-cloud-learning/)还是正在连载的《Spring Cloud Alibaba系列教程》。当您照着博子的顺序，一步步做下来，但是没有调试成功的时候，强烈建议检查一下，您使用的版本是否符合上表的关系。

## 推荐：Spring Cloud Alibaba基础教程

- [《Spring Cloud Alibaba基础教程：使用Nacos实现服务注册与发现》](http://blog.didispace.com/spring-cloud-alibaba-1/)
- [《Spring Cloud Alibaba基础教程：支持的几种服务消费方式》](http://blog.didispace.com/spring-cloud-alibaba-2/)
- [《Spring Cloud Alibaba基础教程：使用Nacos作为配置中心》](http://blog.didispace.com/spring-cloud-alibaba-3/)
- [《Spring Cloud Alibaba基础教程：Nacos配置的加载规则详解》](http://blog.didispace.com/spring-cloud-alibaba-nacos-config-1/)
- [《Spring Cloud Alibaba基础教程：Nacos配置的多环境管理》](http://blog.didispace.com/spring-cloud-alibaba-nacos-config-2/)
- [《Spring Cloud Alibaba基础教程：Nacos配置的多文件加载与共享配置》](http://blog.didispace.com/spring-cloud-alibaba-nacos-config-3/)
- [《Spring Cloud Alibaba基础教程：Nacos的数据持久化》](http://blog.didispace.com/spring-cloud-alibaba-4/)
- [《Spring Cloud Alibaba基础教程：Nacos的集群部署》](http://blog.didispace.com/spring-cloud-alibaba-5/)

该系列教程的代码示例：

- *Github：*[https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- *Gitee：*[https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)