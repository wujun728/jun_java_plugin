package cn.kiiwii.framework.dubbo.consumer;

import cn.kiiwii.framework.dubbo.DubboProperties;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhong on 2016/11/22.
 */
@Configuration
@EnableConfigurationProperties({DubboProperties.class})
public class DubboComsumerConfiguration {

    @Autowired
    private DubboProperties dubboProperties;

     /**
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

    /**
     * 注入dubbo注册中心配置,基于zookeeper
     *
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

    /**
     * dubbo消费
     *
     * @param applicationConfig
     * @param registryConfig
     * @return
     */
    @Bean(name="defaultConsumer")
    @ConditionalOnMissingBean
    public ConsumerConfig consumerConfig(ApplicationConfig applicationConfig, RegistryConfig registryConfig) {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setApplication(applicationConfig);
        consumerConfig.setRegistry(registryConfig);
        consumerConfig.setOwner(dubboProperties.getConsumer().getOwner());
        consumerConfig.setMonitor(dubboProperties.getConsumer().getMonitor());
        consumerConfig.setDefault(dubboProperties.getConsumer().getDefault());
        consumerConfig.setTimeout(dubboProperties.getConsumer().getTimeout());
        consumerConfig.setActives(dubboProperties.getConsumer().getActives());
        consumerConfig.setAsync(dubboProperties.getConsumer().getAsync());
        consumerConfig.setCache(dubboProperties.getConsumer().getCache());
        consumerConfig.setCallbacks(dubboProperties.getConsumer().getCallbacks());
        consumerConfig.setCheck(dubboProperties.getConsumer().getCheck());
        consumerConfig.setCluster(dubboProperties.getConsumer().getCluster());
        consumerConfig.setConnections(dubboProperties.getConsumer().getConnections());
        consumerConfig.setFilter(dubboProperties.getConsumer().getFilter());
        consumerConfig.setGeneric(dubboProperties.getConsumer().getGeneric());
        consumerConfig.setGroup(dubboProperties.getConsumer().getGroup());
        consumerConfig.setVersion(dubboProperties.getConsumer().getVersion());
        return consumerConfig;
    }
}
