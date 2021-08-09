# Spring Cloud实战小贴士：版本依赖关系

**原创**

 [2017-01-23](https://blog.didispace.com/spring-cloud-tips-1/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

去年在博客上连载了[《Spring Cloud构建微服务架构》](http://blog.didispace.com/categories/Spring-Cloud/)的系列博文，虽然这部分内容得到了不少关注者们的支持，但是不得不说这些内容只是适用于Spring Cloud入门阶段对各个组件的初步认识。所以，今年除了将会继续更新《Spring Cloud构建微服务架构》系列的连载之外，准备再开一个新系列：《SpringCloud实战小贴士》，该系列文章内容将会聚焦在下面三个点上：

- 常见问题的解析
- 构建使用的技巧
- 实战设计的思考

## 开篇：Spring Cloud的版本依赖关系

之前在[《聊聊Spring Cloud版本的那些事儿》](http://blog.didispace.com/springcloud-version/)一文中，我们已经介绍了Spring Cloud版本命名的由来以及版本号的规则，并列举了各个版本的依赖内容，以帮助我们选择合适的版本进行微服务实践。

由于Spring Cloud的发展速度非常快，版本的更新非常频繁，同时成体系化的中文文档与教程又比较缺乏，所以很多初学者在搜索了网上的文章进行Spring Cloud的初次尝试时，经常会因为没有关注它的版本依赖关系而引发一些问题。比如平时被问最多的一个问题，在使用Feign的时候为什么报了如下错误：

```
org.springframework.core.annotation.AnnotationConfigurationException: Attribute 'value' in annotation [org.springframework.cloud.netflix.feign.FeignClient] must be declared as an @AliasFor [serviceId], not [name].
```

由于《Spring Cloud构建微服务架构》系列博文的例子都采用了Brixton版本，在介绍[《Spring Cloud构建微服务架构（二）服务消费者》](http://blog.didispace.com/springcloud2/)中的Feign时候也使用了它，而该版本的基础Spring Boot版本是1.3.x，很多初学者可能因为一些原因，比如：现有应用使用Spring Boot 1.4.x实现或者自身喜欢紧跟潮流，这个时候就会出现上面的问题。

所以，我们在选择Spring Boot与Spring Cloud版本的时候，还是需要尽可能的按照Spring Cloud官方版本依赖关系来使用：

- Angel版本对应Spring Boot 1.2.x
- Brixton版本对应Spring Boot 1.3.x
- Camden版本对应Spring Boot 1.4.x

就个人而言，推荐使用目前最新的Camden版本与Spring Boot 1.4.x的组合。首先，不光光是Spring Boot版本提升带来的一些新功能，另外也由于Spring Cloud的组件版本也有提升，比如Brixton版本中的Spring Cloud Netflix采用了1.1.x，而Camden中采用了1.2.x，这两个版本之间还有不少区别的，在1.2.x中提供了更多实用功能，比如：之前在[《为Spring Cloud Ribbon配置请求重试（Camden.SR2+）》](http://blog.didispace.com/spring-cloud-ribbon-failed-retry/)一文中提到的RestTemplate的请求重试、关于Zuul的一些头信息优化等。