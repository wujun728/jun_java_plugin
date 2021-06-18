# Spring-redis
  随着Nosql分布式数据库的不断出现，各种非关系型数据库的重要性日益涌现。redis是一个在内存中运行的键值数据库，是一个业界闻名的分布式缓存。本项目基于Spring平台，整合redis数据库，为我们的业务逻辑添加redis数据缓存的功能。主要特性如下：
  
  1.redis的连接基于Spring-data-redis模块，官网：http://projects.spring.io/spring-data-redis/
  
  2.界面的功能与项目http://git.oschina.net/shenzhanwang/SSM 一模一样，只是在业务逻辑层ActorServiceImpl中添加了缓存支持；
  
  3.一般读取方法需要添加@Cacheable，而删除需要使用@CacheEvict，添加和修改使用@CachePut，以防止失效的缓存数据在前端进行了展示；
  
  4.为了不引起歧义，缓存的名称保持与方法名相同；
  
  5.为了表明一个操作是读取缓存数据还是拉取数据库数据，方法体中有控制台输出，如果是读取缓存则不会有控制台输出:
       ![输入图片说明](http://git.oschina.net/uploads/images/2016/1203/114814_d2e20090_1110335.jpeg "在这里输入图片标题")
       
 6.当访问了一次actor列表的页面后，我们看到redis数据库中新增了记录：
     ![输入图片说明](http://git.oschina.net/uploads/images/2016/1203/114840_6b798e23_1110335.jpeg "在这里输入图片标题")
     
 7.以下是添加了缓存的页面：
  ![输入图片说明](http://git.oschina.net/uploads/images/2016/1203/114855_61a29982_1110335.jpeg "在这里输入图片标题")
### redis的事务
Redis 事务可以一次执行多个命令， 并且带有以下两个重要的保证：
- 事务是一个单独的隔离操作：事务中的所有命令都会序列化、按顺序地执行。事务在执行的过程中，不会被其他客户端发送来的命令请求所打断。
- 事务是一个原子操作：事务中的命令要么全部被执行，要么全部都不执行。

一个事务从开始到执行会经历以下三个阶段：
- 开始事务。
- 命令入队。
- 执行事务。
redis的WATCH命令可用于监控变量，事务执行时若监控的变量发生变化，则事务执行失败。这个过程类似于一种乐观锁。
### redis的应用场景
- 缓存数据库的行，减少数据库的压力
- 集中式地管理session，避免负载均衡后无法登录
- 实现分布式锁，解决并发问题

### 附录：个人作品索引目录（持续更新）

#### 基础篇:职业化，从做好OA系统开始
1. [Spring boot整合Mybatis实现增删改查（支持多数据源）](https://gitee.com/shenzhanwang/SSM)![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
2. [Struts2,Hibernate,Spring三大框架的整合实现增删改查](https://gitee.com/shenzhanwang/S2SH)
3. [Spring,SpringMVC和Hibernate的整合实现增删改查](https://gitee.com/shenzhanwang/SSH)
4. [Spring平台整合activiti工作流引擎实现OA开发](https://gitee.com/shenzhanwang/Spring-activiti)![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
5. [Spring发布与调用REST风格的WebService](https://gitee.com/shenzhanwang/Spring-REST)
6. [Spring整合Apache Shiro框架，实现用户管理和权限控制](https://gitee.com/shenzhanwang/Spring-shiro)
7. [使用Spring security做权限控制](https://gitee.com/shenzhanwang/spring-security-demo)
8. [Spring整合Jasig CAS框架实现单点登录](https://gitee.com/shenzhanwang/Spring-cas-sso)
#### 中级篇：中间件的各种姿势
9. [Spring连接mongoDB数据库实现增删改查](https://gitee.com/shenzhanwang/Spring-mongoDB)
10. [Spring连接Redis实现缓存](https://gitee.com/shenzhanwang/Spring-redis)
11. [Spring连接图存数据库Neo4j实现增删改查](https://gitee.com/shenzhanwang/Spring-neo4j)
12. [Spring平台整合消息队列ActiveMQ实现发布订阅、生产者消费者模型（JMS）](https://gitee.com/shenzhanwang/Spring-activeMQ)
13. [Spring整合消息队列RabbitMQ实现四种消息模式（AMQP）](https://gitee.com/shenzhanwang/Spring-rabbitMQ)
14. Spring框架的session模块实现集中式session管理 [购买](http://t.cn/Ai80zekN)
15. [Spring整合websocket实现即时通讯](https://gitee.com/shenzhanwang/Spring-websocket)![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")
16. 使用Spring boot整合mybatis,rabbitmq,redis,mongodb实现增删改查 [购买](http://t.cn/Ai8Yh8Oy)
17. [Spring MVC整合FastDFS客户端实现文件上传](https://gitee.com/shenzhanwang/Spring-fastdfs)
18. 23种设计模式，源码、注释、使用场景 [购买](http://t.cn/Ai8Y7tEF)
19. [使用ETL工具Kettle的实例](https://gitee.com/shenzhanwang/Kettle-demo)
20. Git指南和分支管理策略 [购买](http://t.cn/Ai8Y7948)
21. 使用数据仓库进行OLAP数据分析（Mysql+Kettle+Zeppelin） ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")[购买](http://t.cn/Ai8Y7dVD)
#### 高级篇：架构之美
22. [zookeeper原理、架构、使用场景和可视化](https://gitee.com/shenzhanwang/zookeeper-practice)
23. Spring boot整合Apache dubbo v2.7.5实现分布式服务治理（SOA架构） ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题") [购买](https://dwz.lc/beP9N33)
>  包含组件Spring boot v2.2.2+Dubbo v2.7.5+Nacos v1.1.1
<a href="https://images.gitee.com/uploads/images/2020/0114/084731_fd0b7a82_1110335.gif" target="_blank">效果图</a>
24. 使用Spring Cloud Alibaba v2.1.0实现微服务架构（MSA架构）![输入图片说明](https://img.shields.io/badge/-%E6%8B%9B%E7%89%8C-yellow.svg)   [购买](https://dwz.lc/IdmrHzd)
>  包含组件Nacos+Feign+Gateway+Ribbon+Sentinel+Zipkin
<a href="https://images.gitee.com/uploads/images/2020/0106/201827_ac61db63_1110335.gif" target="_blank">效果图</a>
25. 使用jenkins+centos+git+maven搭建持续集成环境自动化部署分布式服务 [购买](http://t.cn/Ai8YZbaX)
26. 使用docker+compose+jenkins+gitlab+spring cloud实现微服务的编排、持续集成和动态扩容 [购买](http://t.cn/Ai8YZCYK)
27. 使用FastDFS搭建分布式文件系统（高可用、负载均衡）[购买](http://t.cn/Ai8YZePu)
28. 搭建高可用nginx集群和Tomcat负载均衡 [购买](http://t.cn/Ai8Ywlr8)
29. 搭建可扩展的ActiveMQ高可用集群 [购买](http://t.cn/Ai8YAbA8)
30. 实现Mysql数据库的主从复制、读写分离、分表分库、负载均衡和高可用 [购买](http://t.cn/Ai8YAOAK)
31. 搭建高可用redis集群实现分布式缓存 [购买](http://t.cn/Ai8Y2NQy)
32. [Spring boot整合Elastic search实现全文检索](https://gitee.com/shenzhanwang/Spring-elastic_search) ![输入图片说明](https://img.shields.io/badge/-%E6%8B%9B%E7%89%8C-yellow.svg "在这里输入图片标题")
#### 特别篇：分布式事务和并发控制
33. 基于可靠消息最终一致性实现分布式事务（activeMQ）[购买](http://t.cn/Ai8YLPBL)
34. Spring boot dubbo整合seata实现分布式事务![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题") [购买](https://dwz.lc/csO0rp2)
> 包含组件nacos v1.1.0 + seata v0.7.1 +spring boot dubbo v2.7.5
<a href="https://images.gitee.com/uploads/images/2020/0119/112233_62a33a77_1110335.gif" target="_blank">效果图</a>
35. Spring cloud alibaba v2.1.0整合seata实现分布式事务 ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")[购买](https://dwz.lc/0T8KCTC)
> 包含组件nacos v1.1.0 + seata v0.7.1 +spring cloud alibaba v2.1.0
<a href="https://images.gitee.com/uploads/images/2020/0119/134408_ee14a016_1110335.gif" target="_blank">效果图</a>
36. 决战高并发：数据库锁机制和事务隔离级别的实现![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题") [购买](http://t.cn/Ai8YyAQE)
37. 决战高并发：使用redis实现分布式锁  ![输入图片说明](https://img.shields.io/badge/-%E7%B2%BE%E5%93%81-orange.svg "在这里输入图片标题")[购买](http://t.cn/Ai8Y4bER)
38. 决战高并发：使用zookeeper实现分布式锁 [购买](http://t.cn/Ai8Y4Cuq)
39. 决战高并发：Java多线程编程实例 [购买](http://t.cn/Ai8Y4s0r)
40. 决战高并发：使用netty实现高性能NIO通信 [购买](http://t.cn/Ai8Ybq3e)
### 快捷入口
<a href="http://t.cn/Ai8YycFz" target="_blank">我的网店</a>

<a href="https://www.jianguofaka.com/details/B1143645" target="_blank">全套大礼包2020年版</a>
