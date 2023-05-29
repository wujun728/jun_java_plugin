package mybatis.mate.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShardingApplication {

    // 测试访问 http://localhost:8080/test
    public static void main(String[] args) {
        SpringApplication.run(ShardingApplication.class, args);
    }
}

