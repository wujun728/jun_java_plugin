package com.github.ghsea.rpc.common.modle;

import com.alibaba.fastjson.JSON;
import com.github.ghsea.rpc.client.constants.CallTypeEnum;
import com.github.ghsea.rpc.client.locator.loadbalancer.LoadBalanceTypeEnum;
import com.google.common.base.Preconditions;

/**
 * 客户端配置
 * 
 * @author ghsea 2017-3-19下午3:26:28
 */
public class ReferenceProfile extends BaseProfile {

	private static final long serialVersionUID = -2647957921561841324L;

	private String group;

	private int timeoutMs;

	private int retryTimes;

	private CallTypeEnum callType;

	private LoadBalanceTypeEnum loadBalanceType = LoadBalanceTypeEnum.ROBIN_ROUND;

	public static class ClientProfileBuilder {
		private String pool;
		private String group;
		private String version;
		private String service;
		private int timeoutMs = 5000;
		private int retryTimes = 5;

		private CallTypeEnum callType;

		public ClientProfileBuilder(String pool, String group, String service, String version) {
			this.pool = pool;
			this.group = group;
			this.version = version;
			this.service = service;
		}

		public ClientProfileBuilder timeoutMs(int timeoutMs) {
			Preconditions.checkArgument(timeoutMs > 0);
			this.timeoutMs = timeoutMs;
			return this;
		}

		public ClientProfileBuilder retryTimes(int retryTimes) {
			Preconditions.checkArgument(retryTimes > 0);
			this.retryTimes = retryTimes;
			return this;
		}

		public ClientProfileBuilder callType(CallTypeEnum callType) {
			this.callType = callType;
			return this;
		}

		public ReferenceProfile build() {
			return new ReferenceProfile(this);
		}
	}

	private ReferenceProfile(ClientProfileBuilder builder) {
		super(builder.pool, builder.service, builder.version);
		this.timeoutMs = builder.timeoutMs;
		this.retryTimes = builder.retryTimes;
		this.group = builder.group;
		this.callType = builder.callType;
	}

	public int getTimeoutMs() {
		return timeoutMs;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public LoadBalanceTypeEnum getLoadBalanceType() {
		return loadBalanceType;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public String getGroup() {
		return group;
	}

	public CallTypeEnum getCallType() {
		return callType;
	}

}
