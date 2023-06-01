# Spring Cloud Alibaba到底坑不坑？

**原创**

 [2019-04-06](https://blog.didispace.com/bo-kengdie-spring-cloud-alibaba/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

之前我发过一篇[《说说我为什么看好Spring Cloud Alibaba》](http://blog.didispace.com/spring-cloud-alibaba-significance/)，然后这两天有网友给我转了这篇文章[《坑爹项目spring-cloud-alibaba，我们也来一个》](https://juejin.im/post/5ca723696fb9a05e20221c78)，问我的看法是怎么样的，聊天时候简单说了一下。今天在家休息，抽空整理一下内容，逐点说一下我的看法，主要还是觉得这篇文章博眼球的成分高一些，因为这篇文章的解读与之前其他某些自媒体发布的《Eureka 2.0 开源工作宣告停止，继续使用风险自负》一文有异曲同工之“妙”，如果读者没有真正的理解Spring Cloud与Spring Cloud Alibaba，就很有可能会对它们有什么误解，然后产生这样的想法：

- 感觉很有道理，这东西真垃圾
- 标题很燃，必须转发

下面具体来说说该文章中，那些我认为不太正确的解读：

### 第一点：远程调用RPC

**看看这篇文章的解读：**

> SpringCloud默认的是Feign和Ribbon，主要是提供了远程调用请求和解析，以及负载均衡的功能。客观点来说，如果不用这两个组件，就会越来越四不像，干脆也别叫SpringCloud了，所以替换不得。
> RPC会大量使用动态代理的功能，将你的字符串或者配置（因为网络传输方便）搞成动态的接口。
>
> 你也可以写一个RPC进行集成，有很多教程教你手撸一个。
>
> 爸爸版的集成了个dubbo，dubbo就是个RPC。所以你一用这玩意，其他的一些关键组件也得跟着全套的换，组件就不叫组件了!

作者认为Spring Cloud的负载均衡和远程调用必须使用Feign和Ribbon，这是Spring Cloud的默认实现。如果换成Dubbo，就是四不像了。

**说说我的想法：**

第一点：Dubbo在融入Spring Cloud的时候，真的就是四不像吗？如果真正看过Spring Cloud Alibaba以及理解Spring Cloud Common中的抽象的话，这个问题根本就不用去讨论。Spring Cloud Alibaba Dubbo在实现的时候是兼容Feign的编程模型的。有兴趣的读者可以看看小马哥在该项目中的案例：

Github地址：https://github.com/spring-cloud-incubator/spring-cloud-alibaba/tree/master/spring-cloud-alibaba-examples/spring-cloud-alibaba-dubbo-examples

第二点：Feign和Ribbon并不是Spring Cloud的标准，它们也只是Netflix OSS中的组件。对于负载均衡，大家可以了解一下`spring-cloud-loadbalancer`，它现在是Spring Cloud Common的一部分，这才是真正的标准。对于Spring Cloud Alibaba在整合Dubbo的时候兼容Feign客户端，已经是非常有用户意识了。

Github地址：https://github.com/spring-cloud-incubator/spring-cloud-loadbalancer

**所以，作者到底有没有看过Spring Cloud Alibaba Dubbo的方案？**

## 第二点：注册中心

**看看这篇文章的解读：**

> 服务注册中心是微服务的另外一个必备组件，用来协调服务提供者和调用者的相互发现，SpringCloud默认的注册中心是Eureka。
>
> 爸爸版的用的是Nacos。Nacos的更新目前来看还是比较活跃的，但真没有必要集成在一个Cloud中。Nacos最好的方式还是独立发布，然后维护一个starter。开发者可以按照自己公司的环境进行有选择性的集成或替换。集成一个组件的成本是比较低的，远远低于删掉一堆自以为是的功能。
>
> SpringCloud还可以选择Zookeeper，或者Consul，甚至Etcd等，进行注册中心的搭建。目前，Eureka宣布不再维护后，Consul应该是首要选择。
>
> Consul自带Dashboard和ACL，能够看到大多数你所关心的信息。为了能够集成在我们公司的体系中，你可能会开发一些后台管理功能，进行更多的控制。这部分开发简单，只需要做个界面，直接通过API读取Consul的数据就可以了。

**说说我的想法：**

第一点：注册中心的选择。对于Eureka不再更新之后，到底选择使用哪个并没有完全的最优解，存在即合理，选择适合自己团队（技术栈、使用成本）的，才是最需要考虑的点。

第二点：作者建议“Nacos最好的方式还是独立发布，然后维护一个starter”。这确实是一个很好的建议，但是这点我就奇怪了，作者到底有没有看过Nacos？Nacos目前就是独立发布的，Spring Cloud Alibaba对Nacos的支持，只是Nacos在客户端应用中，针对Spring Cloud用户的一种应用方式而已。

**所以，作者到底有没有看过Spring Cloud Alibaba Nacos的方案？**

## 第三点：熔断、限流

**看看这篇文章的解读：**

> 这部分已经被炒作成微服务体系的必备组件，但扪心自问，这个功能对于中小型的应用可能就是一个摆设。但我们还是要搞的，因为这是个卖点。
>
> SpringCloud默认的组件是Hystrix，提供了多线程和信号量来控制的不同方式。可惜的是Hystrix也宣布不再维护了，官方推荐的替换版本是resilience4j。
>
> 熔断限流功能其实是非常简单的，同事花了一周时间就撸了个足够用的组件。这部分的主要设计在于能够简单的应用，最好是能够通过后台配置实时生效。
>
> 爸爸版的是Sentinel，虽然也带了个后台，但是并没有和注册中心进行集成，搞了个不伦不类。
>
> 我要用Sentinel，我自己集成就好了，用你个大头鬼。

**说说我的想法：**

第一点：我觉得作者能碰到一个能撸出熔断、限流框架和配置管理的同事，还是非常幸运的。但是并不是所有的团队都有人可以做这些，所以我觉得有这样的开源项目不管放在什么时候，都是对行业有益的。你不用没啥问题，但是并不代表对别人没用，并不代表这个项目不够优秀。

第二点：对于作者所说的，没有与注册中心集成，搞得不伦不类。这里的不伦不类，一直没能Get到作者的点。。。不知道是不是有点“为赋新词强说愁”的感觉？个人在对比Hystrix和Sentinel的时候，还是觉得有非常多要比Hystrix做得更好的地方的。

当然真正应用到自己的架构体系中，通常都是需要做一些适配、自定义等工作的。但是，对于开源产品的扩展，从来都不是用来抨击开源项目的核心原因。

## 总结

现在技术圈有个怪现象，自从一些技术自媒体人开始分享自己如何通过分享技术来赚钱开始，催生出了越来越多的技术自媒体。

然后就出现了这样的奇葩现象：

- 没有做过面试官的人在分享如何应对面试
- 没有做过架构师的人在分享如何成为架构师
- 没有赚到钱的人在分享如何赚钱
- 不是中产的人在分享如何成为中产
- …

不可否认，做技术自媒体是可以赚钱。但是单纯为了赚钱的技术自媒体，生搬硬套那些大V们分享的赚钱方法，为了追求流量，会使用夸大表述、扭曲事实、传播侵权内容、编故事博取同情等手段来获得关注和转发。这使得很多技术内容的分享就变得不那么纯粹了，甚至会对读者造成对技术内容的误解。

我没有能力去控制那些自媒体发布这些不实的内容，但是在我了解的范围内，还是尽力输出一些我的理解和思考。希望可以给这些误读内容不同的声音，能够引起读者的注意，从而希望大家可以多一些自己的思考。

当然，我的观点也不一定都是对的，所以不管读者看到什么内容，一定要保持自己的思考。当你发现网上有内容发生冲突的时候，唯一可以解决的方式不是选择一方去相信，还是要自己去深入研究，去验证哪一个观点才是正确的。

最后，声明一点：我不是Spring Cloud Alibaba的成员，也不是阿里系公司的员工。对于Spring Cloud Alibaba的支持，只是作为一名奋斗在一线的程序员所产生的思考。