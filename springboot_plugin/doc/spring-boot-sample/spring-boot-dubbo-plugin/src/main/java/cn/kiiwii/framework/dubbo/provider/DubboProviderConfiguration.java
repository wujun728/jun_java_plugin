package cn.kiiwii.framework.dubbo.provider;

import cn.kiiwii.framework.dubbo.DubboProperties;
import com.alibaba.dubbo.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by zhong on 2016/11/22.
 */
@Configuration
@EnableConfigurationProperties({DubboProperties.class})
@ConditionalOnMissingClass
@Order(value = -1)
public class DubboProviderConfiguration {

    @Autowired
    private DubboProperties dubboProperties;

    /**
    * 注入dubbo注册中心配置,基于zookeeper
    * @return
    */
    @Bean
    @ConditionalOnMissingBean
    public RegistryConfig registryConfig() {
        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol(dubboProperties.getRegistry().getProtocol());
        registry.setAddress(dubboProperties.getRegistry().getAddress());
        registry.setVersion(dubboProperties.getRegistry().getVersion());
        registry.setUsername(dubboProperties.getRegistry().getUsername());
        registry.setPassword(dubboProperties.getRegistry().getPassword());
        registry.setGroup(dubboProperties.getRegistry().getGroup());
        registry.setCheck(dubboProperties.getRegistry().getCheck());
        registry.setClient(dubboProperties.getRegistry().getClient());
        registry.setCluster(dubboProperties.getRegistry().getCluster());
        registry.setDefault(dubboProperties.getRegistry().getDefault());
        registry.setDynamic(dubboProperties.getRegistry().getDynamic());
        registry.setFile(dubboProperties.getRegistry().getFile());
        registry.setPort(dubboProperties.getRegistry().getPort());
        registry.setRegister(dubboProperties.getRegistry().getRegister());
        registry.setServer(dubboProperties.getRegistry().getServer());
        registry.setSubscribe(dubboProperties.getRegistry().getSubscribe());
        registry.setTimeout(dubboProperties.getRegistry().getTimeout());
        registry.setTransporter(dubboProperties.getRegistry().getTransporter());
        return registry;
    }

    /* *
      * 注入dubbo上下文
      *
      * @return
    */
    @Bean
    @ConditionalOnMissingBean
    public ApplicationConfig applicationConfig(RegistryConfig registryConfig) {
        // 当前应用配置
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboProperties.getApplication().getName());
        applicationConfig.setVersion(dubboProperties.getApplication().getVersion());
        applicationConfig.setArchitecture(dubboProperties.getApplication().getArchitecture());
        applicationConfig.setCompiler(dubboProperties.getApplication().getCompiler());
        applicationConfig.setDefault(dubboProperties.getApplication().getDefault());
        applicationConfig.setEnvironment(dubboProperties.getApplication().getEnvironment());
        applicationConfig.setLogger(dubboProperties.getApplication().getLogger());
        applicationConfig.setMonitor(dubboProperties.getApplication().getArchitecture());
        applicationConfig.setOrganization(dubboProperties.getApplication().getOrganization());
        applicationConfig.setOwner(dubboProperties.getApplication().getOwner());
        applicationConfig.setRegistry(registryConfig);
        return applicationConfig;
    }

    /*   *
        * 默认基于dubbo协议提供服务
        *
        * @return

   */
    @Bean
    @ConditionalOnMissingBean
    public ProtocolConfig protocolConfig() {
        // 服务提供者协议配置
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(dubboProperties.getProtocol().getName());
        protocolConfig.setPort(dubboProperties.getProtocol().getPort());
        protocolConfig.setAccepts(dubboProperties.getProtocol().getAccepts());
        protocolConfig.setAccesslog(dubboProperties.getProtocol().getAccesslog());
        protocolConfig.setBuffer(dubboProperties.getProtocol().getBuffer());
        protocolConfig.setCharset(dubboProperties.getProtocol().getCharset());
        protocolConfig.setClient(dubboProperties.getProtocol().getClient());
        protocolConfig.setCodec(dubboProperties.getProtocol().getCodec());
        protocolConfig.setContextpath(dubboProperties.getProtocol().getContextpath());
        protocolConfig.setDefault(dubboProperties.getProtocol().getDefault());
        protocolConfig.setDispatcher(dubboProperties.getProtocol().getDispatcher());
        protocolConfig.setExchanger(dubboProperties.getProtocol().getExchanger());
        protocolConfig.setExtension(dubboProperties.getProtocol().getExtension());
        protocolConfig.setHeartbeat(dubboProperties.getProtocol().getHeartbeat());
        protocolConfig.setHost(dubboProperties.getProtocol().getHost());
        protocolConfig.setIothreads(dubboProperties.getProtocol().getIothreads());
        protocolConfig.setKeepAlive(dubboProperties.getProtocol().getKeepAlive());
        protocolConfig.setNetworker(dubboProperties.getProtocol().getNetworker());
        protocolConfig.setOptimizer(dubboProperties.getProtocol().getOptimizer());
        protocolConfig.setPayload(dubboProperties.getProtocol().getPayload());
        protocolConfig.setPrompt(dubboProperties.getProtocol().getPrompt());
        protocolConfig.setQueues(dubboProperties.getProtocol().getQueues());
        protocolConfig.setRegister(dubboProperties.getProtocol().getRegister());
        protocolConfig.setSerialization(dubboProperties.getProtocol().getSerialization());
        protocolConfig.setServer(dubboProperties.getProtocol().getServer());
        protocolConfig.setStatus(dubboProperties.getProtocol().getStatus());
        protocolConfig.setTelnet(dubboProperties.getProtocol().getTelnet());
        protocolConfig.setThreadpool(dubboProperties.getProtocol().getThreadpool());
        protocolConfig.setThreads(dubboProperties.getProtocol().getThreads());
        protocolConfig.setTransporter(dubboProperties.getProtocol().getTransporter());
        return protocolConfig;
    }

    /*   *
        * dubbo服务提供
        *
        * @param applicationConfig
        * @param registryConfig
        * @param protocolConfig
        * @return

   */
    @Bean
    @ConditionalOnMissingBean
    public ProviderConfig providerConfig(ApplicationConfig applicationConfig, RegistryConfig registryConfig, ProtocolConfig protocolConfig) {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(3000);
        providerConfig.setRetries(3);
        providerConfig.setDelay(300);
        providerConfig.setApplication(applicationConfig);
        providerConfig.setRegistry(registryConfig);
        providerConfig.setProtocol(protocolConfig);
        return providerConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public MonitorConfig monitorConfig() {
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setAddress(dubboProperties.getMonitor().getAddress());
        monitorConfig.setGroup(dubboProperties.getMonitor().getGroup());
        monitorConfig.setPassword(dubboProperties.getMonitor().getPassword());
        monitorConfig.setProtocol(dubboProperties.getMonitor().getProtocol());
        monitorConfig.setUsername(dubboProperties.getMonitor().getUsername());
        monitorConfig.setVersion(dubboProperties.getMonitor().getVersion());
        monitorConfig.setDefault(dubboProperties.getMonitor().getDefault());
        return monitorConfig;
    }
}
