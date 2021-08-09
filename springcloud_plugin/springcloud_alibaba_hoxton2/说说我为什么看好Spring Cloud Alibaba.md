# 说说我为什么看好Spring Cloud Alibaba

**原创**

 [2019-03-29](https://blog.didispace.com/spring-cloud-alibaba-significance/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

最近对《Spring Cloud Alibaba基础教程》系列的催更比较多，说一下最近的近况：因为打算Spring Boot 2.x一起更新。所以一直在改博客Spring Boot专题页和Git仓库的组织。由于前端技术太过蹩脚，花了不少时间。大家不用担心，这个系列不会太监，因为我真心看好这个套件的未来，后续的更新也会继续赶上来。

今天就水更一篇吧，跟大家聊一下平时被问的比较多的一类问题，Spring Cloud Alibaba是什么，我为什么要写Spring Cloud Alibaba基础教程？

## Spring Cloud Alibaba是什么

**简介**

Spring Cloud Alibaba从名字上看，就知道一定跟Spring Cloud有关，但是我们为什么在Spring Cloud官方文档中看不到它的影子呢？因为它目前还是一个孵化项目，它的仓库也位于Spring Cloud孵化器中，Github地址：https://github.com/spring-cloud-incubator/spring-cloud-alibaba。

**版本关系**

虽然它还没有纳入Spring Cloud的主版本管理（Dalston、Edgware、Finchley、Greenwich这些），但是也已经发布了几个针对目前常用Spring Cloud版本的可用内容，也有一些公司已经将其用于生产环境。了解Spring Cloud的读者肯定知道，Spring Cloud的版本与Spring Boot的版本有着密切的关系，现在又多了一个Spring Cloud Alibaba，那么它们的关系是怎么样的呢？可以看看之前写过的这篇文章：[Spring Cloud Alibaba与Spring Boot、Spring Cloud之间不得不说的版本关系](http://blog.didispace.com/spring-cloud-alibaba-version/)

**功能特性：**

Spring Cloud Alibaba不是一个简单的组件，而是一个综合套件。其中涵盖了非常多的内容，包括：服务治理、配置管理、限流降级以及对阿里开源生态（Dubbo、RocketMQ等）支持的诸多组件。更多详细详细，读者可查阅其[官方文档](https://github.com/spring-cloud-incubator/spring-cloud-alibaba/blob/master/README-zh.md)。

## 为什么要写Spring Cloud Alibaba基础教程

首先，我们需要知道Spring Cloud Alibaba在Spring Cloud家族中的地位，它是一个套件，与Netflix OSS一样，涵盖了非常多的实用组件，其中也有不少内容存在重叠。

其次，我们需要知道Netflix OSS下的诸多重要组件先后宣布停止新功能开发的大背景，而Spring Cloud Alibaba是一个新生项目，正处于高速迭代中。对于未来，相信谁都会选。

再次，对于中国用户来说，Spring Cloud Alibaba还有一个非常特殊的意义：它将曾经红极一时的Dubbo，以及阿里巴巴的强力消息中间件RocketMQ融入Spring Cloud体系。还在纠结于如何让这些共存的团队，你们所面临过的各种困难与问题，马上就会迎刃而解。不用再烦恼是不是要扩展Dubbo的注册中心，还是自己为RocketMQ实现一套的Spring Cloud Stream的Binder等等问题。

最后，对于Spring Cloud Alibaba的上手学习成本如何呢？如果您已经是Spring Cloud的用户，那么恭喜您，在Spring Cloud Common的抽象和Spring Cloud Alibaba团队的努力下，你会非常容易、甚至不需要改变多少编码模式，就能适应它。如果您第一次接触Spring Cloud，那么也恭喜您，因为这是有史以来，中文文档最全的一个Spring Cloud组件了，相信机制的您一定也能很快的上手使用它！