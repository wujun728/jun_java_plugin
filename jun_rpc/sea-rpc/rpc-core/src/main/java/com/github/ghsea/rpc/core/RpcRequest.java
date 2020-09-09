package com.github.ghsea.rpc.core;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.ghsea.rpc.common.util.IpUtils;
import com.github.ghsea.rpc.core.message.RpcRequestMsg;
import com.google.common.base.Preconditions;

/**
 * 
 * @author ghsea 2017-2-4下午9:47:40
 */
public class RpcRequest implements Serializable {

	private static final long serialVersionUID = -3487270093713312180L;

	private String poolName;

	private RpcRequestMsg msg;

	// TODO 可以根据service name配置
	private transient long timeout = 5000;

	private AtomicInteger cnt = new AtomicInteger(0);

	protected RpcRequest() {

	}

	public static class RpcRequestBuilder {

		private String pool;

		private String service;

		private String method;

		private Object[] parameters;

		// TODO 可以根据service name配置
		private transient long timeout = 5000;

		public RpcRequestBuilder() {
		}

		public RpcRequestBuilder pool(String pool) {
			this.pool = pool;
			return this;
		}

		public RpcRequestBuilder service(String service) {
			this.service = service;
			return this;
		}

		public RpcRequestBuilder method(String method) {
			this.method = method;
			return this;
		}

		public RpcRequestBuilder parameters(Object[] parameters) {
			this.parameters = parameters;
			return this;
		}

		public RpcRequestBuilder timeout(long timeout) {
			this.timeout = timeout;
			return this;
		}

		public RpcRequest build() {
			Preconditions.checkArgument(service != null);
			Preconditions.checkArgument(method != null);
			return new RpcRequest(this);
		}
	}

	private RpcRequest(RpcRequestBuilder builder) {
		this.poolName = builder.pool;
		this.timeout = builder.timeout;

		RpcRequestMsg msg = new RpcRequestMsg();
		msg.setService(builder.service);
		msg.setMethod(builder.method);
		msg.setParameters(builder.parameters);
		msg.setRequestId(createRequestId());
		this.msg = msg;
	}

	private String createRequestId() {
		int count = cnt.incrementAndGet();
		if (cnt.get() < 0) {
			synchronized (this) {
				if (cnt.get() < 0) {
					count = 0;
					cnt.set(0);
				}
			}
		}

		StringBuffer sb = new StringBuffer();
		sb.append(IpUtils.getLocalIp()).append("@").append(poolName).append("-").append(System.currentTimeMillis())
				.append("-").append(count);
		return sb.toString();
	}

	public String getPoolName() {
		return poolName;
	}

	public long getTimeout() {
		return timeout;
	}

	public RpcRequestMsg getMsg() {
		return msg;
	}

}
