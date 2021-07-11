## spring cloud zipkin

**[zipkin](https://github.com/openzipkin/zipkin)** 是Twitter开发的一个可扩展的开源追踪框架,用于存储与查看追踪数据。具体不做过多介绍。可以百度谷歌了解

spring cloud 针对微服务的追踪也提供了一套的方案 *[Spring-Cloud-Sleuth](https://cloud.spring.io/spring-cloud-sleuth/)*。针对 zipkin 做了一次的封装。相关 Spring-Cloud-Sleuth 的文档可以查阅: https://cloud.spring.io/spring-cloud-sleuth/spring-cloud-sleuth.html

本样例是基于 spring-cloud-sleuth-zipkin ，使用 elasticsearch 做存储，支持http，rabbitMQ 写入日志。

