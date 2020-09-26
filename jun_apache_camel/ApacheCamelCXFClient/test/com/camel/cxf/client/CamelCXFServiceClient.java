package com.camel.cxf.client;

import com.camel.cxf.server.CamelCXFServiceImplService;
import com.camel.cxf.server.CamelCXFServiceInter;

public class CamelCXFServiceClient {

	public static void main(String[] args) {

		CamelCXFServiceImplService camelCXFServiceImplService = new CamelCXFServiceImplService();

		CamelCXFServiceInter camelCXFServiceInter = camelCXFServiceImplService.getCamelCXFServiceImplPort();

		String str1 = camelCXFServiceInter.queryInfomation("cyx1");

		String str2 = camelCXFServiceInter.sayHello("cyx2");

		System.out.println("str1 : " + str1);
		System.out.println("str2 : " + str2);

	}

}
