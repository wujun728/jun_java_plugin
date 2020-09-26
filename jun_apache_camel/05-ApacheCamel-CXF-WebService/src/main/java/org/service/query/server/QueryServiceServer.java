package org.service.query.server;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
import org.service.query.QueryServicePortImpl;

import com.camel.webservice.server.CamelReleaseServerMain;

public class QueryServiceServer {

	public static final Logger logger = Logger.getLogger(QueryServiceServer.class);

	public void internalQueryService() throws Exception {

		String address = CamelReleaseServerMain.SERVICE_ADDRESS;

		Endpoint.publish(address, new QueryServicePortImpl());

		System.out.println("Camel 内部 WebService 发布成功 , address : " + address);

	}

}
