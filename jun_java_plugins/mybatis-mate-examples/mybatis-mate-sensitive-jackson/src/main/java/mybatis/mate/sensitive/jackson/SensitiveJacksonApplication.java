package mybatis.mate.sensitive.jackson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SensitiveJacksonApplication {

    // 测试访问 http://localhost:8080/info ，http://localhost:8080/list
    public static void main(String[] args) {
        SpringApplication.run(SensitiveJacksonApplication.class, args);
    }
}

