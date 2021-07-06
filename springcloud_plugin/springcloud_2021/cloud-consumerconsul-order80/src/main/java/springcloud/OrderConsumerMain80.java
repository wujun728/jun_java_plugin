package springcloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderConsumerMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderConsumerMain80.class, args);
    }
}
