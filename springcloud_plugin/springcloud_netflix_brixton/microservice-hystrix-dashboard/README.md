# Hystrix监控

一、pom文件

    <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>

二、配置文件

    server.port=8030
    spring.application.name=hystrix-dashboard
    
三、启动类

    @SpringBootApplication
    @EnableHystrixDashboard
    public class HystrixDashboardApplication {
        public static void main(String[] args) {
            new SpringApplicationBuilder(HystrixDashboardApplication.class).web(true).run(args);
        }
    }
    
测试步骤：
    1. 访问http://localhost:8030/hystrix.stream 可以查看Dashboard
    2. 在上⾯的输⼊框填⼊: http://想监控的服务:端⼝/hystrix.stream 进⾏测试
    注意：⾸先要先调⽤⼀下想监控的服务的API，否则将会显示⼀个空的图表

