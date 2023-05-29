package com.jun.plugin.mybatis;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.jun.plugin.mybatis")
@MapperScan(value = "com.jun.plugin.mybatis.mapper")
public class RunApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RunApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(RunApplication.class, args);
	}
}
