# ribbon 实现的消费者

一、pom配置

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-ribbon</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix</artifactId>
    </dependency>
    
二、配置文件

    server.port=8011
    spring.application.name=microservice-consumer-ribbon
    eureka.client.serviceUrl.defaultZone=http://discovery:8761/eureka/
    eureka.instance.hostname=ribbon

三、启动类

    @SpringBootApplication
    @EnableDiscoveryClient
    @EnableCircuitBreaker
    public class RibbonApplication {
        @Bean
        @LoadBalanced
        public RestTemplate restTemplate(){
            return  new RestTemplate();
        }
        public static void main(String[] args) {
            SpringApplication.run(RibbonApplication.class,args);
        }
    }
    
四、服务接口及熔断器的实现

    @Service
    public class RibbonService {
    
        @Autowired
        private RestTemplate restTemplate;
        private static final Logger LOGGER = LoggerFactory.getLogger(RibbonService.class);
        @HystrixCommand(fallbackMethod = "findByIdFallback")
        public User findById(Long id) {
            return this.restTemplate.getForObject("http://microservice-provider-user/" + id, User.class);
        }
    
        public User findByIdFallback(Long id){
            RibbonService.LOGGER.info("异常发⽣，进⼊fallback⽅法，接收的参数：id = {}", id);
            User user = new User();
            user.setId(-1L);
            user.setUsername("default username");
            user.setAge(0);
            return user;
        }
    }

    
五、controller

    @RestController
    public class RibbonController {
    
        @Autowired
        private RibbonService ribbonService;
        @GetMapping("/ribbon/{id}")
        public User findById(@PathVariable Long id) {
            return this.ribbonService.findById(id);
        }
    }

    