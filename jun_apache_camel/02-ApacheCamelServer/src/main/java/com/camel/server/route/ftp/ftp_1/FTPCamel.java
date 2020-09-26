package com.camel.server.route.ftp.ftp_1;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * @author CYX
 * @time 2017年12月15日上午10:36:48
 */
public class FTPCamel {

	public static void main(String[] args) throws Exception {

		// 日志
		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		CamelContext context = new DefaultCamelContext();

		// 启动route
		context.start();

		// 将我们编排的一个完整消息路由过程，加入到上下文中

		// 本地文件上传至FTP服务器
//		context.addRoutes(new LocalToFTPRouteBuilder());

		// 从FTP服务器上下载文件至本地.
//		 context.addRoutes(new FTPToLocalRouteBuilder());

		// 从FTP服务器上下载文件，并根据正则表达式进行文件名过滤。
		 context.addRoutes(new FTPToLocalFiterFileNameRouteBuilder());

		// 通用没有具体业务意义的代码，只是为了保证主线程不退出
		synchronized (FTPCamel.class) {
			FTPCamel.class.wait();
		}

	}

}
