# feign 实现的消费者

一、pom配置

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-feign</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix</artifactId>
    </dependency>
    
二、配置文件

    server.port=8020
    spring.application.name=microservice-consumer-feign
    eureka.client.serviceUrl.defaultZone=http://discovery:8761/eureka/
    eureka.instance.hostname=feign

三、启动类

    @SpringBootApplication
    @EnableFeignClients
    @EnableDiscoveryClient
    @EnableCircuitBreaker
    public class FeignApplication {
        public static void main(String[] args) {
            SpringApplication.run(FeignApplication.class, args);
        }
    }
    
四、服务接口

    @FeignClient(name = "microservice-provider-user",fallback =FeignServiceFallback.class)
    public interface UserFeignService {
    
        @RequestMapping("/{id}")
        public User findByIdFeign(@RequestParam("id") Long id);
    }

五、熔断器的实现

    @Component
    public class FeignServiceFallback implements UserFeignService {
    
        private static final Logger logger = LoggerFactory.getLogger(FeignServiceFallback.class);
        /**
         * hystrix fallback⽅法
         * @param id id
         * @return 默认的⽤户
         */
        @Override
        public User findByIdFeign(Long id) {
            FeignServiceFallback.logger.info("异常发⽣，进⼊fallback⽅法，接收的参数：id = {}", id);
            User user = new User();
            user.setId(-1L);
            user.setUsername("default username");
            user.setAge(0);
            return user;
        }
    }
    
六、controller

    @RestController
    public class FeignController {
        @Autowired
        private UserFeignService userFeignService;
        @GetMapping("/feign/{id}")
        public User findByIdFeign(@PathVariable Long id) {
            User user = this.userFeignService.findByIdFeign(id);
            return user;
        }
    }

    