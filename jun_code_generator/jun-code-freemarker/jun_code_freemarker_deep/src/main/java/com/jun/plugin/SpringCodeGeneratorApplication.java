package com.jun.plugin;

import java.net.Inet4Address;
import java.net.InetAddress;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan({"com.jun.plugin.**.mapper","com.jun.plugin.online.dao","com.jun.plugin.generate.modular.mapper"})
@ComponentScan(basePackages = { "com.jun.plugin.common","com.jun.plugin.rest" ,"com.jun.plugin.generate","io.github.wujun728"})
public class SpringCodeGeneratorApplication  extends SpringBootServletInitializer  {

	public static void main(String[] args) throws Exception {
//      SpringApplication application = new SpringApplication(SpringbootApplication.class);
//		application.setBannerMode(Banner.Mode.OFF);
//		application.run(args);
		ConfigurableApplicationContext application = SpringApplication.run(SpringCodeGeneratorApplication.class, args);
		Environment env = application.getEnvironment();
		InetAddress inetAddress = Inet4Address.getLocalHost();
		String hostAddress = inetAddress.getHostAddress();
		String serverPort = env.getProperty("server.port");
		String serverPath = env.getProperty("server.servlet.context-path");
		log.info("项目启动成功！访问地址: http://{}:{}/{}", hostAddress, serverPort, serverPath);
		log.info("本机地址: http://localhost:{}", serverPort);
	}

	@Override // 为了打包springboot项目
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}


}
