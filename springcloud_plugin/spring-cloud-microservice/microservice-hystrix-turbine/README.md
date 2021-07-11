# 把多个hystrix.stream聚合到一个控制页面

一、pom依赖
    
    <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-turbine</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-netflix-turbine</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>

二、配置文件

    server.port=8031
    spring.application.name=microservice-hystrix-turbine
    security.basic.enabled=false
    turbine.app-config=microservice-consumer-feign,microservice-consumer-ribbon
    turbine.cluster-name-expression=new String("default")
    eureka.client.serviceUrl.defaultZone=http://discovery:8761/eureka/

三、启动类

    @SpringBootApplication
    @EnableTurbine
    public class TurbineApplication {
        public static void main(String[] args) {
            new SpringApplicationBuilder(TurbineApplication.class).web(true).run(args);
        }
    }
    
测试

1、访问Hystrix Dashboard：http://localhost:8030/hystrix.stream，
2、将http://localhost:8031/turbine.stream输⼊到其上的输⼊框，并随意指定⼀个Title

