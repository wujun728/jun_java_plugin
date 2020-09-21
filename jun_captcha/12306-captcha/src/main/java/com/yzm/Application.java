package com.yzm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.yzm.common.checkthread.CheckThread;
import com.yzm.util.SpringUtil;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableWebMvc
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
public class Application implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private CheckThread checkThread;

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class);
		ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
		SpringUtil.setApplicationContext(configurableApplicationContext);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("系统启动完成，开始初始化线程");
		Thread thread = new Thread(checkThread);
		thread.start();

	}

}
