package mybatis.mate.sm.mysql.aes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EncryptMysqlAesApplication {

    // 测试访问 http://localhost:8080/test
    // 测试访问 http://localhost:8080/test2
    public static void main(String[] args) {
        SpringApplication.run(EncryptMysqlAesApplication.class, args);
    }
}

