package com.jun.plugin;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
@Slf4j
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.jun.plugin.**.mapper")
@ServletComponentScan(basePackages = {"com.jun.plugin.**.filter"})
public class ApiServiceApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext application = SpringApplication.run(ApiServiceApplication.class, args);

        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Api Test Json: \thttp://{}:{}/public/json\n\t" +
                        "Api Code Generator: \thttp://{}:{}/generator/list.html\n\t" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            System.err.println("beanNames size = "+ beanNames.length);
            for (String beanName : beanNames) {
                //System.err.println(beanName);
                /*
                 * Object bean = ctx.getBean(beanName); System.out.println(bean);
                 * System.out.println();
                 */
            }

        };
    }

}
