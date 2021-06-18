package io.springside.springtime.jetty;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.springframework.context.ApplicationContext;

import io.springside.springtime.serializer.JsonSerializer;
import io.springside.springtime.serializer.Serializer;
import io.springside.springtime.service.ServiceDispatcher;
import io.springside.springtime.service.ServiceBeanRegistry;
import io.swagger.annotations.Api;

/**
 * Jetty的Handler.
 * 
 * 以服务化框架的方式直接处理/rpc 路径的请求.
 * 其他路径的请求，仍然交给WebServletContxt Handler处理.
 */
public class SpringTimeHandler extends AbstractHandler {

	public static final String RPC_PREFIX = "/rpc";

	private ServiceDispatcher dispatcher;
	private Serializer serializer = new JsonSerializer();

	public SpringTimeHandler(ApplicationContext applicationContext) {
		ServiceBeanRegistry serviceRegistry = new ServiceBeanRegistry();
		Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Api.class);
		for (Entry<String, Object> entry : beans.entrySet()) {
			serviceRegistry.add(RPC_PREFIX, entry.getKey(), entry.getValue());
		}

		dispatcher = new ServiceDispatcher(serviceRegistry);
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (!target.startsWith(RPC_PREFIX)) {
			return;
		}
		String path = target.toLowerCase();
		Serializer serializerForRequest = serializer;

		dispatcher.dispatch(path, serializerForRequest, baseRequest.getInputStream(), response.getOutputStream());

		response.setContentType(Serializer.JSON_TYPE);
		response.setStatus(HttpServletResponse.SC_OK);
		response.getOutputStream().flush();
		baseRequest.setHandled(true);
	}
}
