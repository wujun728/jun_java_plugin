package mybatis.mate.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DataAuditApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataAuditApplication.class, args);
    }
}

