package com.itstyle;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 支付主控(启动的时候一定要配置好支付宝、微信以及银联相关参数)
 * 创建者 科帮网
 * 创建时间 2017年7月27日
 * 启动   java -jar spring-boot-pay.jar --server.port=8886 
 * linux 下 后台启动  nohup java -jar spring-boot-pay.jar --server.port=8886 & 
 * ============================
 * 2018-10-10 更新说明：
 * 1）原当当 Dubbox 2.8.4 替换为 Dubbo 2.6.2
 * 2）原spring-context-dubbo.xml 配置 替换为 dubbo-spring-boot-starter 2.0.0
 * 3）原 zkclient 0.6 替换为 curator-recipes 4.0.1
 * 4）原 zookeeper 3.4.6 升级为 zookeeper 3.5.3
 * ============================
 * 2020-05-07 更新说明：
 * 1）Dubbo 2.6.2 升级为 2.7.3
 * 2）dubbo-spring-boot-starter 2.0.0 升级为 2.7.3
 */
@EnableDubbo(scanBasePackages  = "com.itstyle.modules")
@SpringBootApplication
public class Application {

    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
        LOGGER.info("支付项目启动");
	}
}