package com.itstyle.web;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 科帮网  小柒 
 * http://www.52itstyle.com
 * 启动方式一：运行main方法启动即可
 * 启动方式二：右键 run as maven打包 然后命令行 切换到项目 target 下 执行 java -jar springBoot_demo-0.0.1-SNAPSHOT.jar 
 * 
 */
@SpringBootApplication
@Controller
public class App {
	private static final Logger logger = Logger.getLogger(App.class);

	@RequestMapping("/")
    public String   greeting() {
        return "index";
    }
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		logger.info("项目启动 ");
	}
}
