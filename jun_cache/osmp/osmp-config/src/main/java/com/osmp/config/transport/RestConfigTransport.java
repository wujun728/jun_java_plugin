/*   
 * Project: OSMP
 * FileName: RestConfigTransport.java
 * version: V1.0
 */
package com.osmp.config.transport;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.osmp.config.manager.ConfigServiceManager;

@Path(value = "/")
public class RestConfigTransport implements IRestConfigTransport, InitializingBean {

	private ConfigServiceManager serviceManager;

	public void setServiceManager(ConfigServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(serviceManager, "serviceManager未初始化...");
	}

	/**
     * 
     */
	@GET
	@Path(value = "/refresh/{target}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getForRefresh(@PathParam("target") String target) {
		return postForRefresh(target);
	}

	/**
     * 
     */
	@POST
	@Path(value = "/refresh/{target}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postForRefresh(@PathParam("target") String target) {
	    serviceManager.update(target);
		return Response.ok().entity("{\"code\":\"0\"}").build();
	}

	@GET
	@Path(value = "/getdata/{target}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response postForGetData(@PathParam("target") String target, @Context HttpServletRequest request) {
		Map map = request.getParameterMap();
		Object obj = serviceManager.getData(target, map);
		return Response.ok().entity(obj)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + ";charset=UTF-8").build();
	}

}
