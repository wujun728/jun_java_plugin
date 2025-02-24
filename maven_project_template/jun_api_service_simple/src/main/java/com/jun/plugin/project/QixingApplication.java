package com.jun.plugin.project;

import java.net.Inet4Address;
import java.net.InetAddress;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class) // 多数据源 (exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.jun.plugin.**.mapper")
//@ComponentScan(basePackages = "com.jun.plugin")
@ServletComponentScan(basePackages = {"com.jun.plugin.**.filter"})
public class QixingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws Exception {
//      SpringApplication application = new SpringApplication(SpringbootApplication.class);
//		application.setBannerMode(Banner.Mode.OFF);
//		application.run(args);
		System.setProperty("spring.devtools.restart.enabled", "true");
		ConfigurableApplicationContext application = SpringApplication.run(QixingApplication.class, args);
		Environment env = application.getEnvironment();
		InetAddress inetAddress = Inet4Address.getLocalHost();
		String hostAddress = inetAddress.getHostAddress();
		String serverPort = env.getProperty("server.port");
		String serverPath = env.getProperty("spring.application.name");
		String url = env.getProperty("spring.datasource.url");

		log.info("项目启动成功！访问地址: http://{}:{}/{}", hostAddress, serverPort, serverPath);
		log.info("本机地址: http://localhost:{}", serverPort);
	}

	@Override // 为了打包springboot项目
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			System.err.println("beanNames size = "+ beanNames.length);

			for (String beanName : beanNames) {
				//System.err.println(beanName);
			}

		};
	}
}


