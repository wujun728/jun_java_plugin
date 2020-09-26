package com.camel.cxf.server.test;

import com.camel.cxf.server.CamelCXFServiceImplService;
import com.camel.cxf.server.CamelCXFServiceInter;

public class TestCamelCXFServiceClient {

	public static void main(String[] args) {

		CamelCXFServiceImplService camelCXFServiceImplService = new CamelCXFServiceImplService();

		CamelCXFServiceInter camelCXFServiceInter = camelCXFServiceImplService.getCamelCXFServiceImplPort();

		String result = camelCXFServiceInter.queryInfomation("cyx00000");
		String result2 = camelCXFServiceInter.sayHello("cccccccc");

		System.out.println("result : " + result);
		System.out.println("result2 : " + result2);

	}

}
