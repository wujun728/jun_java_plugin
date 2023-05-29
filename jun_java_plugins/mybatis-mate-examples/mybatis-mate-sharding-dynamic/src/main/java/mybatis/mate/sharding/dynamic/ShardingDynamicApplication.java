package mybatis.mate.sharding.dynamic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShardingDynamicApplication {

    // 本测试在 mybatis-mate-sharding 基础上测试，查看 UserController 类注释说明
    public static void main(String[] args) {
        SpringApplication.run(ShardingDynamicApplication.class, args);
    }
}

