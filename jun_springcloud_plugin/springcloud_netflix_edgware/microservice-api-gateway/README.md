# Zuul 实现的 API Gateway
向外部系统提供REST API

1、启动服务发现服务
2、启动服务提供者服务

一、配置pom

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zuul</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
    
二、配置文件

    server.port=8050
    spring.application.name=microservice-api-gateway
    eureka.client.serviceUrl.defaultZone=http://discovery:8761/eureka/
    eureka.instance.hostname=gateway

如果是多节点的Eureka

    eureka.client.serviceUrl.defaultZone=http://discovery:8761/eureka/,http://discovery2:8762/eureka/

三、启动类

    @SpringBootApplication
    @EnableZuulProxy
    public class ZuulApiGatewayApplication {
        public static void main(String[] args) {
            SpringApplication.run(ZuulApiGatewayApplication.class, args);
        }
    }
    
访问的规律,访问

    http://GATEWAY:GATEWAY_PORT/想要访问的Eureka服务id的小写/**
    例：http://localhost:8050/microservice-provider-user/1

相当于

    http://想要访问的Eureka服务id的小写:该服务端./**
    例：http://localhost:8000/1
    


