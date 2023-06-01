# springcloud2020（已完结）

#### 介绍
跟阳哥springcloud教程的个人手敲代码，如有错误，不吝赐教，已完结（2020年3月13日）！

#### 软件架构
springcloud最新


#### 更新时间

个人接口测试工具直接使用IDEA-->Tools-->HTTP Client-->Test Restful WebService工具

1.  2020年3月5日 更新支付模块

2.  2020年3月6日 重构时注意将lombok依赖复制到api-commons下时要注意删除optional选项，否则当该选项为true时，说明该依赖禁止依赖传递，则依赖api的模块不会依赖lombok。
    ```xml
            <!--    错误      -->
              <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <optional>true</optional>
              </dependency>
              <!--    正确      -->
               <dependency>
                  <groupId>org.projectlombok</groupId>
                  <artifactId>lombok</artifactId>
               </dependency>
    ```

- Eureka注册中心集群环境: 负载均衡，容错控制

- Eureka集群搭建
    - 1、修改hosts
      ```
        C:\Windows\System32\drivers\etc 
        127.0.0.1 eureka7001.com
        127.0.0.1 eureka7002.com
      ```
    
- Consul 下载：https://www.consul.io/downloads.html      
 相关命令：consul --version：查看版本信息
          consul agent -dev：运行部署consul
   
- 三个注册中心的异同点
  
    | 组件名    | 语言 | CAP  | 服务健康检查     | 对外暴露接口 | Spring  Cloud集成 |
    | --------- | ---- | ---- | ---------------- | ------------ | ----------------- |
    | Eureka    | Java | AP   | 可配支持健康检查 | HTTP         | 集成              |
    | Consul    | GO   | CP   | 支持健康检查     | HTTP、DNS    | 集成              |
    | Zookeeper | Java | CP   | 支持健康检查     | 客户端       | 集成              |
    
- Hystrix

  服务降级，避免级联故障，以提高分布式系统的弹性。

  https://github.com/Netflix/Hystrix

  1. Hystrix(断路器)重要概念：服务降级（fallback）、服务熔断（break）、服务限流（flowlimit，秒杀高并发）

- SpringCloud GateWay

  简介：服务请求网关，构建于SpringBoot2.0，Spring WebFlux，Project Reactor。提供一种简单而有效的方式对API进行路由，提供一些强大的过滤器功能，例如：熔断、限流、重试等。

  特点：动态路由、支持断言和过滤器、集成Hystrix的断路器功能、集成SpringCloud服务发现功能、请求限流功能、支持路径重写。

- Config：解决分布式系统面临的配置问题

    SpringCloud Config为微服务架构中的微服务提供几种化的外部配置支持，配置服务器为各个不同微服务应用的所有环境提供一个中心化的外部配置。

    ```markdown
    SpringCloud Config项目已移至gitee，地址为：https://gitee.com/exclusiver/springcloud-config，由于gitee未配置ssh环境，故ConfigCenterMain3344项目的yml配置文件需修改为：
    注意第12行的uri地址
    server:
      port: 3344
    spring:
      application:
        name: cloud-config-center
      cloud:
        config:
          server:
            git:
              uri: https://gitee.com/exclusiver/springcloud-config.git
              search-paths:
                - springcoud-config
          label: master
    eureka:
      client:
        service-url:
          defaultZone: http://localhost:7001/eureka
    ```

    

    bootstrap.yml:系统级，优先级更高

    

    动态刷新Post

    ```markdown
    curl -X POST "http://localhost:3355/actuator/refresh"
    ```

    

- Bus总线

    - docker安装rabbitmq

        ```markdown
        docker pull rabbitmq:management
        
        docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:management
        ```

        由于之前装过rabbitmq但是通过docker start启动报错：

        ```markdown
        错误：iptables failed: iptables --wait -t nat -A DOCKER&n
        解决：
        pkill docker 
        
        iptables -t nat -F 
        
        ifconfig docker0 down 
        
        brctl delbr docker0 
        
        docker -d 
        
        systmctl restart docker
        即可解决
        ```

        ```markdown
        批量通知：curl -X POST "http://localhost:3344/actuator/bus-refresh"
        
        若刷新显示403则参考：https://ask.csdn.net/questions/684123
        
        定点通知：curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3355"
        ```

        

- Stream

    屏蔽底层消息中间件的差异，降低切换成本，同一消息的编程模型

    常用注解：@Input、@OutPut、@StreamListener、@EnableBinding

    **不同组可重复消费，同组不会重复消费** group：XXX

    

- Sleuth

    zipkin下载地址：dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/

    运行：java -jar zipkin-server-2.12.9-exec.jar

    访问：http://localhost:9411/zipkin/

- SpringCloud Alibaba Nacos（Naming Configuration Service）

    官网：https://nacos.io/zh-cn/

    - 服务注册中心

    - 服务配置中心

    - 集群和持久化配置
    
    Nacos自带轮询负载均衡，可调整支持CP、AP
    
    ```markdown
    curl -X PUT '$NACOS_SERVER:8848/nacos/v1/ns/operator/swithes?entry=serverMode&value=CP'
    ```
    
    Namespace 、 Group 、 DataId
    
- Sentinel

    官网：https://github.com/alibaba/Sentinel

    下载：https://github.com/alibaba/Sentinel/releases

    **面向云原生微服务的流量控制、熔断降级组件**

    - 服务雪崩-预热
    - 服务降级
    - 服务熔断-RT、异常数、异常比例
    - 服务限流-[热点限流](https://github.com/alibaba/Sentinel/wiki/%E7%83%AD%E7%82%B9%E5%8F%82%E6%95%B0%E9%99%90%E6%B5%81)

    运行前提：Java8环境OK且8080端口不能被占用

    命令：java -jar sentinel-dashboard-1.7.0.jar

    访问：http://localhost:8080/

```json
[
    {
        "resource": "/rateLimit/byUrl",
        "limitApp": "default",
        "grade": 1,
        "count": 1,
        "strategy": 0,
        "controlBehavior": 0,
        "clusterMode": false
    }
]
```

- Seata

  分布式事务解决方案

  官网：https://seata.io/zh-cn/

  Transaction ID XID

  TC-事务协调者

  TM-事务管理者

  RM-资源管理者

  **使用**：本地：@Transaction，全局：@GlobalTransaction

### 完 结

