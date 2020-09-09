package com.github.ghsea.rpc.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.GenericFutureListener;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.github.ghsea.rpc.core.exception.RpcServiceNotExistException;
import com.github.ghsea.rpc.core.message.RpcRequestMsg;
import com.github.ghsea.rpc.core.message.RpcResponseMsg;

/**
 * 具体处理Request的Handler,在此利用反射调用服务端方法，并返回response
 * 
 * @author ghsea 2017-4-9下午6:10:44
 */
public class RequestMsgHandle extends SimpleChannelInboundHandler<RpcRequestMsg> {

	private Logger log = LoggerFactory.getLogger(RequestMsgHandle.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMsg request) throws Exception {
		log.debug("Server received RpcRequestMsg: {}", request);

		String serviceName = request.getService();
		String methodName = request.getMethod();
		Object[] parameters = request.getParameters();
		final RpcResponseMsg response = new RpcResponseMsg(request.getRequestId());
		try {
			Object serviceProxy = RpcServerBootstrap.getBean(serviceName);
			if (serviceProxy == null) {
				throw new RpcServiceNotExistException(serviceName);
			}

			String serviceNameTarget = ((RpcServiceExporter) serviceProxy).getServiceTarget();
			Object serviceTarget = RpcServerBootstrap.getBean(serviceNameTarget);

			Method method = ReflectionUtils.findMethod(serviceTarget.getClass(), methodName);
			if (method == null) {
				throw new RpcServiceNotExistException(serviceName, methodName);
			}

			log.debug("RPC Server is to invoke {}.{}", serviceName, methodName);
			Object result = method.invoke(serviceTarget, parameters);
			response.setResult(result);
		} catch (Throwable th) {
			response.setCause(th);
		} finally {
			log.debug("RPC Server is to send RpcResponseMsg: {} ", response);
			ChannelFuture cf = ctx.pipeline().writeAndFlush(response);
			if (log.isDebugEnabled()) {
				cf.addListener(new GenericFutureListener<ChannelFuture>() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						if (future.isSuccess()) {
							log.debug("RPC Server sent RpcResponseMsg: {} ", response);
						} else {
							log.error("RPC Server failed in sending RpcResponseMsg.Cause:{}", future.cause());
						}
					}
				});
			}
		}
	}
}
