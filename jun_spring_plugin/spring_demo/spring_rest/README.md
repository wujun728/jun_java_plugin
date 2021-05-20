# Spring-REST
    如果说Web Service是一种特殊的RPC，那么REST Service又是一种特殊的Web Service，目前已成为分布式通信的重要手段。本项目基于Spring框架，完成REST（表现层状态转移）风格的接口的发布(Server)与接收(Client)。特性如下：
    
    
Server端：
    
    1.基于SpringMVC的@RequestMapping的属性method配置GET,POST,DELETE,PUT四种HTTP请求，对应于CRUD的四种操作；
    
    2.前端页面发送四种HTTP请求，可通过Jquery的ajax方法完成；
    
    3.前后台通过json通信，对于POST和PUT请求，ajax需要提交json字符串作为数据，后台用@RequestBody接收json转为java对象；
    
    4.已配置阿里巴巴的Fastjson工具作为消息转换器，controller返回的对象自动转json字符串到前端；
    
    5.Server端使用SSM框架，需要用mysql建立表格actor持久化数据：
    
      CREATE TABLE `actor` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `name` varchar(45) DEFAULT NULL,
        `age` int(11) DEFAULT NULL,
        PRIMARY KEY (`id`)
      ) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
      
     6.Server端入口：
     
       http://localhost:8080/Spring-REST-Server/rest
       
  Cilent端:
    
    1.客户端使用Spring的RestTemplate的API发送GET,POST,DELETE,PUT请求，底层是通过HttpClient实现远程调用；
    
    2.注意POST和PUT方法，发送的请求包含了HTTP头设置，否则容易出415的错误；
    
    3.前端页面提交json数据到后台的模板为：
    
     $.ajax({  
            url : "actors",  
            type : "POST/DELETE/PUT",  
            data : JSON.stringify(json), //转JSON字符串  
            dataType: 'json',  
            contentType:'application/json;charset=UTF-8', //contentType很重要
            success : function(result) { });
  

如果是GET请求，直接$.get(...)即可;

4.Client端入口：
    
      http://localhost:8080/Spring-REST-Client/rest
      
    效果图：
     ![输入图片说明](http://git.oschina.net/uploads/images/2017/0209/143942_9a9acde4_1110335.jpeg "在这里输入图片标题")
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
