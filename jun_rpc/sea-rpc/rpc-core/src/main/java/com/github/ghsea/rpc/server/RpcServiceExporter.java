package com.github.ghsea.rpc.server;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteExporter;

import com.github.ghsea.rpc.common.modle.NodeId;
import com.github.ghsea.rpc.common.modle.ServiceProfile;

public class RpcServiceExporter extends RemoteExporter implements InitializingBean, DisposableBean, BeanNameAware {

	/**
	 * 从配置文件读取
	 */
	private String pool;

	/**
	 * 从配置文件读取
	 */
	private String version;
	
	/**
	 * 自动读取配置的Spring Bean id作为服务名
	 */
	private String serviceName;
	
	
	private String serviceTarget;

	private ServiceProfile profile;

	@Override
	public void afterPropertiesSet() throws Exception {
		Server server = ServerFactory.getInstance();
		NodeId nodeId = new NodeId(server.getHost(), server.getPort());
		this.profile = new ServiceProfile(pool, serviceName, version, nodeId);
		server.register(profile);
	}

	@Override
	public void destroy() throws Exception {
		Server server = ServerFactory.getInstance();
		server.unregister(profile);
	}

	@Override
	public void setBeanName(String name) {
		this.serviceName = BeanFactoryUtils.originalBeanName(name);
	}

	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public ServiceProfile getProfile() {
		return profile;
	}

	public void setProfile(ServiceProfile profile) {
		this.profile = profile;
	}

	public String getServiceTarget() {
		return serviceTarget;
	}

	public void setServiceTarget(String serviceTarget) {
		this.serviceTarget = serviceTarget;
	}

}
