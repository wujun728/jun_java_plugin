# 服务发现

一、pom文件

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka-server</artifactId>
    </dependency>
    
二、配置文件

1、单节点的eureka配置
   
    #eureka 服务端口
    server.port=8761
    spring.application.name=microservice-discovery-eureka
    eureka.instance.hostname=discovery
    eureka.instance.leaseRenewalIntervalInSeconds=1000
    
    #当前服务不需要到eureka server上注册
    eureka.client.register-with-eureka=false
    eureka.client.fetch-registry=false
    eureka.dashboard.enabled=true
    eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
    
2、多借点的eureka配置

a)节点一的配置

    server.port=8761
    spring.application.name=microservice-discovery-eureka
    eureka.instance.hostname=discovery
    eureka.instance.leaseRenewalIntervalInSeconds=1000
    eureka.client.service-url.defaultZone=http://discovery2:8762/eureka/
    
b)节点二的配置
    
    server.port=8762
    spring.application.name=microservice-discovery-eureka
    eureka.instance.leaseRenewalIntervalInSeconds=1000
    eureka.instance.hostname=discovery2
    eureka.client.service-url.defaultZone=http://discovery:8761/eureka/
    
三、启动类
    
    @SpringBootApplication
    @EnableEurekaServer
    public class DiscoveryApplication {
        public static void main(String[] args) {
            SpringApplication.run(DiscoveryApplication.class, args);
        }
    }