package com.zhaodui.springboot;

import com.alibaba.druid.support.logging.Log;
import com.zhaodui.springboot.common.controller.ExceptionController;
import lombok.extern.slf4j.Slf4j;
 import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import tk.mybatis.spring.annotation.MapperScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Wujun
 */
@Slf4j
@SpringBootApplication
@MapperScan({"com.zhaodui.springboot.system.mapper","com.zhaodui.springboot.buse.mapper"})
public class SpringbootApplication {

	public static void main(String[] args) throws UnknownHostException {
//		SpringApplication.run(SpringbootApplication.class, args);

		ConfigurableApplicationContext application = SpringApplication.run(SpringbootApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application  is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "----------------------------------------------------------");

    }

}
