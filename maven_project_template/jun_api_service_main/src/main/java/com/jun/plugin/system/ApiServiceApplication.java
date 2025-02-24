package com.jun.plugin.system;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

import cn.hutool.core.lang.Console;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class) // 多数据源 (exclude = DruidDataSourceAutoConfigure.class)
@MapperScan({"com.jun.plugin.**.mapper","com.bjc.lcp.**.mapper"})
@ComponentScan(basePackages = {"com.bjc.lcp","com.jun.plugin"})
@ServletComponentScan(basePackages = {"com.jun.plugin.**.filter"})
public class ApiServiceApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(ApiServiceApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n\t" +
                        "Knife4j: \thttp://localhost:{}/doc.html\n" +
                        "SwaggerUI: \thttp://localhost:{}/swagger-ui.html\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("server.port"),
                env.getProperty("server.port"));
        Console.log(env.getProperty("server.port"));
        log.info("profiles = " + SpringUtil.getProperty("spring.profiles.active"));
        log.info("url = " + SpringUtil.getProperty("spring.datasource.url"));
        log.info("context = " + SpringUtil.getProperty("spring.groovy-api.context"));
        //SpringApplication.run(GroovyDemoApplication.class, args);
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
