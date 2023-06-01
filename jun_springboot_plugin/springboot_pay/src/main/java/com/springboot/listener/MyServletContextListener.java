package com.springboot.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.springboot.util.PropKit;

@WebListener
public class MyServletContextListener implements ServletContextListener {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MyServletContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// 初始化系统文件
		PropKit.use("pay-info.properties");
		logger.info("liting: contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("liting: contextDestroyed");

	}

}
