# spring cloud 构建的简易微服务架构

前提：设置本地环境的hosts

    127.0.0.1 discovery discovery2 ribbon feign gateway
    
# 1、microservice-discovery-eureka 服务发现

详情查看项目 [microservice-discovery-eureka](microservice-discovery-eureka/)

# 2、microservice-provider-user 服务提供者

详情查看项目 [microservice-provider-user](microservice-provider-user/)

# 3、microservice-consumer-ribbon ribbon服务消费者

详情查看项目 [microservice-consumer-ribbon](microservice-consumer-ribbon/)

# 4、microservice-consumer-feign feign服务消费者

详情查看项目 [microservice-consumer-feign](microservice-consumer-feign/)

# 5、microservice-hystrix-dashboard hystrix熔断器的监控

详情查看项目 [microservice-hystrix-dashboard](microservice-hystrix-dashboard/)

# 6、microservice-hystrix-turbine 把多个hystrix.stream聚合到一个监控页面

详情查看项目 [microservice-hystrix-turbine](microservice-hystrix-turbine/)

# 7、microservice-config 配置中心

详情查看项目 [microservice-config](microservice-config/)

# 8、microservice-api-gateway API Gateway

详情查看项目 [microservice-api-gateway](microservice-api-gateway/)
