package com.camel.webservice.server;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfComponent;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.service.query.QueryServiceInter;
import org.service.query.server.QueryServiceServer;

public class CamelReleaseServerMain {

	public static final Logger logger = Logger.getLogger(CamelReleaseServerMain.class);

	// Apache Camel 路由对外发布的WebService URL
	private static final String ROUTER_ADDRESS = "http://localhost:9080/server/queryservice";

	// Apache Camel 内部发布的WebService URL
	public static final String SERVICE_ADDRESS = "http://localhost:9010/server/queryservice";

	// WebService 接口全类名
	private static final String SERVICE_CLASS = "serviceClass=org.service.query.QueryServiceInter";

	// WSDL文档地址
	private static final String WSDL_LOCATION = "wsdlURL=wsdl/queryInfo.wsdl";

	// Apache Camel 路由路径
	private static final String ROUTER_ENDPOINT_URI = "cxf://" + ROUTER_ADDRESS + "?" + SERVICE_CLASS + "&"
			+ WSDL_LOCATION + "&dataFormat=POJO";

	public static void main(String[] args) {

		// 日志
		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		// Camel 上下文
		CamelContext context = new DefaultCamelContext();

		try {

			// 内部WebService启动
			QueryServiceServer queryServiceServer = new QueryServiceServer();
			queryServiceServer.internalQueryService();

			// Camel 添加WebService 路由
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {

					CxfComponent cxfComponent = new CxfComponent(getContext());
					CxfEndpoint serviceEndpoint = new CxfEndpoint(SERVICE_ADDRESS, cxfComponent);
					serviceEndpoint.setServiceClass(QueryServiceInter.class);

					from(ROUTER_ENDPOINT_URI).to("log:CamelReleaseServerMain?showExchangeId=true").to(serviceEndpoint);

				}
			});

			System.out.println("Camel 外部 WebService ： " + ROUTER_ADDRESS);
			context.start();

			// 通用没有具体业务意义的代码，只是为了保证主线程不退出
			synchronized (CamelReleaseServerMain.class) {
				CamelReleaseServerMain.class.wait();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
