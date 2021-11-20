package com.jun.plugin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.jun.plugin")
// @EnableTransactionManagement // 开启事务管理
@MapperScan("com.**.dao")
public class AppApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AppApplication.class);
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(AppApplication.class).web(true).run(args);
	}

}
