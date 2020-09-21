/*   
 * Project: OSMP
 * FileName: RestPlatformTransport.java
 * version: V1.0
 */
package com.osmp.http.transport;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.osmp.http.define.ResponseBody;
import com.osmp.http.define.RtCodeConst;
import com.osmp.http.service.DataServiceProxy;
import com.osmp.http.service.ServiceFactoryManager;
import com.osmp.http.tool.ExceptionHandleProvider;
import com.osmp.intf.define.config.FrameConst;
import com.osmp.intf.define.model.InvocationDefine;
import com.osmp.intf.define.model.ServiceContext;
import com.osmp.utils.base.JSONUtils;
import com.osmp.utils.net.RequestInfoHelper;

/**
 * 
 * Description:统一服务接口REST
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:16:20上午10:51:30
 */
public class RestPlatformTransport implements IRestPlatformTransport, InitializingBean {
	private Logger logger = LoggerFactory.getLogger(RestPlatformTransport.class);
	private ServiceFactoryManager serviceFactoryManager;
	private ExceptionHandleProvider exceptionHandle;

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(serviceFactoryManager, "PlatformServiceTransport property dataServiceManager not set..");
	}

	public void setServiceFactoryManager(ServiceFactoryManager serviceFactoryManager) {
		this.serviceFactoryManager = serviceFactoryManager;
	}

	public void setExceptionHandle(ExceptionHandleProvider exceptionHandle) {
		this.exceptionHandle = exceptionHandle;
	}

	@POST
	@Path(value = "/{svcName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dataPost(@FormParam(FrameConst.SOURCE_NAME) String source,
			@PathParam("svcName") String interfaceName, @FormParam(FrameConst.PARAMETER_NAME) String parameter,
			@Context HttpServletRequest request) {
		return data(source, interfaceName, parameter, request);
	}

	@GET
	@Path(value = "/{svcName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response data(@QueryParam(FrameConst.SOURCE_NAME) String source,
			@PathParam("svcName") String interfaceName, @QueryParam(FrameConst.PARAMETER_NAME) String parameter,
			@Context HttpServletRequest request) {

		ResponseBody res = new ResponseBody();
		// 获取对应服务
		InvocationDefine define = serviceFactoryManager.getInvocationDefine(interfaceName);
		if (define == null) {
			res.setCode(RtCodeConst.ERR_CODE);
			res.setMessage("未找到服务:" + interfaceName);
			logger.warn("未找到服务:" + interfaceName);
			return Response.ok().entity(res)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + ";charset=UTF-8").build();
		}
		// 接口参数验证
		Map<String, String> sourceMap = JSONUtils.jsonString2Map(source);
		if (sourceMap == null || sourceMap.get(FrameConst.CLIENT_FROM) == null) {
			res.setCode(RtCodeConst.ERR_CODE);
			res.setMessage("接口参数非法,服务名:" + interfaceName);
			logger.warn("接口参数非法,服务名:" + interfaceName);
			return Response.ok().entity(res)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + ";charset=UTF-8").build();
		}
		sourceMap.put(FrameConst.CLIENT_IP, RequestInfoHelper.getRemoteIp(request));
		// 调用服务
		ServiceContext serviceContext = new ServiceContext(sourceMap, interfaceName, parameter);

		DataServiceProxy serviceProxy = new DataServiceProxy(define, serviceContext);
		Object result = null;
		try {
			result = serviceProxy.execute();
		} catch (Throwable e) {
			e.printStackTrace();
			StringBuffer sb = new StringBuffer();
			sb.append("ipaddress : " + sourceMap.get(FrameConst.CLIENT_IP) + "\n");
			sb.append("source : " + sourceMap.get(FrameConst.CLIENT_FROM) + "\n");
			sb.append("serviceName : " + interfaceName + "\n");
			sb.append("parameter : " + parameter + "\n");
			return exceptionHandle.toResponse(e, sb.toString());
		}
		if (serviceProxy.isError()) {
			res.setCode(RtCodeConst.ERR_CODE);
			res.setMessage(result != null ? result.toString() : "");
		} else {
			res.setCode(RtCodeConst.SUCC_CODE);
			res.setData(result);
		}
		logger.info("接口调用:【来源:" + source + ", 接口名称:" + interfaceName + ", 参数:" + parameter + "】返回:【" + res.toString()
				+ "】");
		return Response.ok().entity(res)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + ";charset=UTF-8").build();
	}

	@GET
	@Path(value = "/task")
	@Produces(MediaType.APPLICATION_JSON)
	public Response task(String source, String interfaceName, String parameter) {
		return null;
	}

}
