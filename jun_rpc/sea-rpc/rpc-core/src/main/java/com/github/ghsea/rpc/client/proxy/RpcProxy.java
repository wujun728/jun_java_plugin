package com.github.ghsea.rpc.client.proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.github.ghsea.rpc.client.constants.CallTypeEnum;
import com.github.ghsea.rpc.common.modle.ReferenceProfile;
import com.github.ghsea.rpc.common.modle.ReferenceProfile.ClientProfileBuilder;

public class RpcProxy<T> implements FactoryBean<T>, InitializingBean {

	/**
	 * 以下属性读取配置文件
	 */
	protected String targetService;
	protected String version;

	protected String targetInterface;
	protected String poolName;

	protected String groupName;

	protected Integer timeoutMs;

	protected Integer retryTimes;

	protected CallTypeEnum callType = CallTypeEnum.SYNC;

	// ******************以下属性计算得来
	private Class<T> targetInterfaceClz;

	private ReferenceProfile profile;

	@Override
	public T getObject() throws Exception {
		return RpcProxyFactory.newProxy(targetInterfaceClz, targetService, profile);
	}

	@Override
	public Class<T> getObjectType() {
		return targetInterfaceClz;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ClientProfileBuilder builder = new ClientProfileBuilder(poolName, groupName, targetService, version);
		if (timeoutMs != null) {
			builder.timeoutMs(timeoutMs);
		}
		if (retryTimes != null) {
			builder.retryTimes(retryTimes);
		}

		builder.callType(callType);
		profile = builder.build();

		targetInterfaceClz = (Class<T>) (Class.forName(targetInterface));
	}

	public String getTargetService() {
		return targetService;
	}

	public void setTargetService(String targetService) {
		this.targetService = targetService;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTargetInterface() {
		return targetInterface;
	}

	public void setTargetInterface(String targetInterface) {
		this.targetInterface = targetInterface;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getTimeoutMs() {
		return timeoutMs;
	}

	public void setTimeoutMs(Integer timeoutMs) {
		this.timeoutMs = timeoutMs;
	}

	public Integer getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}

	public Class<T> getTargetInterfaceClz() {
		return targetInterfaceClz;
	}

	public void setTargetInterfaceClz(Class<T> targetInterfaceClz) {
		this.targetInterfaceClz = targetInterfaceClz;
	}

	public ReferenceProfile getProfile() {
		return profile;
	}

	public void setProfile(ReferenceProfile profile) {
		this.profile = profile;
	}

	public CallTypeEnum getCallType() {
		return callType;
	}

	public void setCallType(CallTypeEnum callType) {
		this.callType = callType;
	}

}
