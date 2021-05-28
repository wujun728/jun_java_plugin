package com.reger.dubbo.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ModuleConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

@ConfigurationProperties(DubboProperties.targetName)
public class DubboProperties {
	public static final String targetName = "spring.dubbo";
	/**
	 * 应用基础信息
	 */
	private ApplicationConfig application=new ApplicationConfig();
	/**
	 * 应用注册中心
	 */
	private RegistryConfig registry = new RegistryConfig();
	/**
	 * 注解所在的包,多个用逗号分割
	 */
	private String basePackage;
	/**
	 * 公用的生产者配置
	 */
	private ProviderConfig provider = new ProviderConfig();
	/**
	 * 公用的消费者配置
	 */
	private ConsumerConfig consumer = new ConsumerConfig();
	/**
	 * 应用注册中心(应用配置中心,当需要配置多个是生效)
	 */
	private List<RegistryConfig> registrys;
	/**
	 * 应用模块定义
	 */
	private ModuleConfig module;
	/**
	 * 监控服务
	 */
	private MonitorConfig monitor;
	/**
	 * 应用使用的协议栈
	 */
	private ProtocolConfig protocol;
	/**
	 * 应用使用的协议栈(当需要配置超过一个时使用)
	 */
	private List<ProtocolConfig> protocols;
	/**
	 * 生产者发布服务
	 */
	private List<ServiceConfig<?>> services;
	/**
	 * 消费者定义服务注册
	 */
	private List<ReferenceConfig<?>> references;

	public DubboProperties() {
		registry.setClient("zkclient");
		registry.setAddress("127.0.0.1");
		registry.setPort(2181);
		registry.setProtocol("zookeeper");
		application.setQosEnable(false);
		application.setQosPort(0);
		application.setQosAcceptForeignIp(false);
	}

	public List<RegistryConfig> getRegistrys() {
		return registrys;
	}

	public void setRegistrys(List<RegistryConfig> registrys) {
		this.registrys = registrys;
	}

	public ApplicationConfig getApplication() {
		return application;
	}

	public void setApplication(ApplicationConfig application) {
		this.application = application;
	}

	public RegistryConfig getRegistry() {
		return registry;
	}

	public void setRegistry(RegistryConfig registry) {
		this.registry = registry;
	}

	public ModuleConfig getModule() {
		return module;
	}

	public void setModule(ModuleConfig module) {
		this.module = module;
	}

	public MonitorConfig getMonitor() {
		return monitor;
	}

	public void setMonitor(MonitorConfig monitor) {
		this.monitor = monitor;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public ProviderConfig getProvider() {
		return provider;
	}

	public void setProvider(ProviderConfig provider) {
		this.provider = provider;
	}

	public ConsumerConfig getConsumer() {
		return consumer;
	}

	public void setConsumer(ConsumerConfig consumer) {
		this.consumer = consumer;
	}

	public ProtocolConfig getProtocol() {
		return protocol;
	}

	public void setProtocol(ProtocolConfig protocol) {
		this.protocol = protocol;
	}

	public List<ProtocolConfig> getProtocols() {
		return protocols;
	}

	public void setProtocols(List<ProtocolConfig> protocols) {
		this.protocols = protocols;
	}

	public List<ServiceConfig<?>> getServices() {
		return services;
	}

	public void setServices(List<ServiceConfig<?>> services) {
		this.services = services;
	}

	public List<ReferenceConfig<?>> getReferences() {
		return references;
	}

	public void setReferences(List<ReferenceConfig<?>> references) {
		this.references = references;
	}

}
