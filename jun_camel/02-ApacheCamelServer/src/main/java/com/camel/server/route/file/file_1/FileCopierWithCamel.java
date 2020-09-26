package com.camel.server.route.file.file_1;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.PropertyConfigurator;

/**
 * Apache Camel File组件<br>
 * copy文件,从一个目录,移动到另一个目录.
 * 
 * @author CYX
 *
 */
public class FileCopierWithCamel {

	public static void main(String[] args) throws Exception {

		// 日志
		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		CamelContext context = new DefaultCamelContext();

		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				// 不加延时时间参数,默认为500毫秒.

				/**
				 * 不加任何属性参数,inbox目录中的文件,会被直接移动到outbox目录中.
				 */
				from("file:D:\\A\\inbox").to("file:D:\\A\\outbox");

				/**
				 * noop参数：如果为true，则不会以任何方式移动或删除文件.<br>
				 * 如果noop = true，Camel也会设置idempotent = true，以避免重复使用同一个文件.<br>
				 * 简单的说：移动inbox目录中的文件到outbox目录中,inbox中的文件会保留下来并不会被删除.
				 */
				// from("file:D:\\A\\inbox?noop=true").to("file:D:\\A\\outbox");
			}
		});

		context.start();

		// 仅为了保证主线程不退出
		synchronized (FileCopierWithCamel.class) {
			FileCopierWithCamel.class.wait();
		}

	}

}
