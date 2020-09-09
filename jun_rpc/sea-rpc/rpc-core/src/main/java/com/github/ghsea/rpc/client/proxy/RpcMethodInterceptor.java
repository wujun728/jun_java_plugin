package com.github.ghsea.rpc.client.proxy;

import java.lang.reflect.Method;

import javax.naming.OperationNotSupportedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.github.ghsea.rpc.client.NettyClient;
import com.github.ghsea.rpc.client.NettyClientFactory;
import com.github.ghsea.rpc.client.locator.ServiceLocator;
import com.github.ghsea.rpc.client.locator.ZkServiceLocatorImpl;
import com.github.ghsea.rpc.common.modle.NodeId;
import com.github.ghsea.rpc.common.modle.ReferenceProfile;
import com.github.ghsea.rpc.common.modle.ServiceProfile;
import com.github.ghsea.rpc.core.RpcRequest;
import com.github.ghsea.rpc.core.RpcRequest.RpcRequestBuilder;

public class RpcMethodInterceptor implements MethodInterceptor {

	private ServiceLocator serviceLocator;

	private ReferenceProfile reference;

	private Logger log = LoggerFactory.getLogger(RpcMethodInterceptor.class);

	public RpcMethodInterceptor(ReferenceProfile reference) {
		this.serviceLocator = new ZkServiceLocatorImpl(reference);
		this.reference = reference;
	}

	@Override
	public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		RpcRequest request = newRpcRequest(method, args, reference);
		ServiceProfile service = this.serviceLocator.locateService();
		NodeId node = service.getNodeId();
		NettyClient client = NettyClientFactory.getInstance().getNettyClient(node);
		switch (reference.getCallType()) {
		case SYNC:
			Object ret = client.request(request);
			log.debug("RPC Client response result for request:{}", ret);
			return ret;
		case ASYNC:
			Object fut = client.requestAsync(request);
			log.debug("RPC Client response result for requestAsync:{}", fut);
			return fut;
		default:
			throw new OperationNotSupportedException("CallType " + reference.getCallType() + " is not supported");
		}
	}

	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	private static RpcRequest newRpcRequest(Method method, Object[] arg, final ReferenceProfile profile) {
		RpcRequestBuilder builder = new RpcRequestBuilder();
		return builder.pool(profile.getPool()).service(profile.getService()).method(method.getName()).parameters(arg)
				.timeout(profile.getTimeoutMs()).build();
	}

}
