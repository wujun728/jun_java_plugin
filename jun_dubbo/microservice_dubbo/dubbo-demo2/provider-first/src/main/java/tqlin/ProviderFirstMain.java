package tqlin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
/**
 * 使用 providers.xml 配置
 */
@ImportResource(value = {"classpath:provider-first.xml"})
public class ProviderFirstMain {
    public static void main(String[] args) {
        SpringApplication.run(ProviderFirstMain.class, args);
    }
}