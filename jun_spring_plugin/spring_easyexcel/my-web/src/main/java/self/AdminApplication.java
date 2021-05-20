package self;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *  容器启动(要放到能扫描到处理类的根目录中)
 */
@SpringBootApplication
@ComponentScan(basePackages = {"self"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }

}
