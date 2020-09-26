package com.camel.webservice.server;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfComponent;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.PropertyConfigurator;

public class CamelReleaseServiceMain {

	private static final String ROUTER_ADDRESS = "http://localhost:8088/CamelCXFService/queryService";

	private static final String SERVICE_CLASS = "serviceClass=com.camel.webservice.server.CamelCXFServiceInter";

	private static final String WSDL_LOCATION = "wsdlURL=F:\\Company\\jinyue\\study\\ApacheCamelDemo\\04-ApacheCamel-CXF-WebService\\wsdl\\queryService.wsdl";
//	private static final String WSDL_LOCATION = "wsdlURL=http://localhost:9022/camel-cxf/greeter-service?wsdl";

	private static final String ROUTER_ENDPOINT_URI = "cxf://" + ROUTER_ADDRESS + "?" + SERVICE_CLASS + "&"
			+ WSDL_LOCATION + "&dataFormat=POJO";

	private static final String SERVICE_ADDRESS = "http://localhost:9022/camel-cxf/greeter-service?wsdl";

	public static void main(String[] args) {

		PropertyConfigurator.configure("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties");
		PropertyConfigurator.configureAndWatch("F:/Company/jinyue/study/ApacheCamelDemo/01-ApacheCamel-HelloWorld/conf/log4j.properties", 1000);

		System.out.println("ROUTER_ENDPOINT_URI : " + ROUTER_ENDPOINT_URI);

		CamelContext context = new DefaultCamelContext();

		Server server = new Server();

		try {

			server.start();

			context.addRoutes(new RouteBuilder() {
				public void configure() {

					CxfComponent cxfComponent = new CxfComponent(getContext());

					CxfEndpoint serviceEndpoint = new CxfEndpoint(SERVICE_ADDRESS, cxfComponent);

					serviceEndpoint.setServiceClass(CamelCXFServiceInter.class);

					from(ROUTER_ENDPOINT_URI).to("log:CamelCxfExample?showExchangeId=true").to(serviceEndpoint);

					// from(ROUTER_ENDPOINT_URI).process(new Processor() {
					//
					// @Override
					// public void process(Exchange exchange) throws Exception {
					// System.out.println("exchange : " + exchange);
					// System.out.println("exchange.getIn() : " +
					// exchange.getIn());
					// System.out.println("exchange.getIn(String.class) : " +
					// exchange.getIn(String.class));
					// System.out.println("exchange.getIn().getBody() : " +
					// exchange.getIn().getBody());
					// }
					// }).to("log:cxfdemocamelexample?showExchangeId=true");
				}
			});

			String address = ROUTER_ADDRESS;

			System.out.println("address : " + address + "?wsdl");
			context.start();

			// 通用没有具体业务意义的代码，只是为了保证主线程不退出
			synchronized (CamelReleaseServiceMain.class) {
				CamelReleaseServiceMain.class.wait();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			System.exit(0);
		}

	}

}
