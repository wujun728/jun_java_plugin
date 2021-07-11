package com.buxiaoxia;

import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by xw on 2017/2/20.
 * 2017-02-20 16:51
 */
@Data
@ConfigurationProperties("test")
@EnableDiscoveryClient
@SpringBootApplication
public class Application implements CommandLineRunner {

	private String conf1;

	private String conf2;

	private String conf3;


	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("如果spring.cloud.consul.config.format是YAML方式，那么下面的值为null");
		System.out.println("从[config/application/test]获取conf1配置值：" + this.getConf1());
		System.out.println("从[config/application/test]获取conf2配置值：" + this.getConf2());
		System.out.println("从[config/application/test]获取conf2配置值：" + this.getConf3());
		System.out.println("=========================================");
		System.out.println("如果spring.cloud.consul.config.format是YAML方式，" +
				"需要在consul上指定数据库的配置，配置内容同spring-boot-jpa一致");
	}
}
