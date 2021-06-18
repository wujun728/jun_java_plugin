package com.zengtengpeng.configuration;

import com.zengtengpeng.aop.LockAop;
import com.zengtengpeng.aop.MQAop;
import com.zengtengpeng.mq.RedissonMQListener;
import com.zengtengpeng.operation.RedissonBinary;
import com.zengtengpeng.operation.RedissonCollection;
import com.zengtengpeng.operation.RedissonObject;
import com.zengtengpeng.properties.MultipleServerConfig;
import com.zengtengpeng.properties.RedissonProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.*;
import org.redisson.connection.balancer.LoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(value = RedissonProperties.class)
@ConditionalOnClass(RedissonProperties.class)
public class RedissonConfiguration {

    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    @ConditionalOnMissingBean(LockAop.class)
    public LockAop lockAop() {
        return new LockAop();
    }
    @Bean
    @ConditionalOnMissingBean(MQAop.class)
    public MQAop MQAop() {
        return new MQAop();
    }

    @Bean
    @ConditionalOnMissingBean(RedissonBinary.class)
    public RedissonBinary RedissonBinary() {
        return new RedissonBinary();
    }

    @Bean
    @ConditionalOnMissingBean(RedissonObject.class)
    public RedissonObject RedissonObject() {
        return new RedissonObject();
    }

    @Bean
    @ConditionalOnMissingBean(RedissonCollection.class)
    public RedissonCollection RedissonCollection() {
        return new RedissonCollection();
    }

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        Config config=new Config();
        try {
            config.setCodec((Codec) Class.forName(redissonProperties.getCodec()).newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        config.setTransportMode(redissonProperties.getTransportMode());
        if(redissonProperties.getThreads()!=null){
            config.setThreads(redissonProperties.getThreads());
        }
        if(redissonProperties.getNettyThreads()!=null){
            config.setNettyThreads(redissonProperties.getNettyThreads());
        }
        config.setReferenceEnabled(redissonProperties.getReferenceEnabled());
        config.setLockWatchdogTimeout(redissonProperties.getLockWatchdogTimeout());
        config.setKeepPubSubOrder(redissonProperties.getKeepPubSubOrder());
        config.setDecodeInExecutor(redissonProperties.getDecodeInExecutor());
        config.setUseScriptCache(redissonProperties.getUseScriptCache());
        config.setMinCleanUpDelay(redissonProperties.getMinCleanUpDelay());
        config.setMaxCleanUpDelay(redissonProperties.getMaxCleanUpDelay());

        MultipleServerConfig multipleServerConfig = redissonProperties.getMultipleServerConfig();

        switch (redissonProperties.getModel()){
            case SINGLE:
                SingleServerConfig singleServerConfig = config.useSingleServer();
                com.zengtengpeng.properties.SingleServerConfig param = redissonProperties.getSingleServerConfig();
                singleServerConfig.setAddress(prefixAddress(param.getAddress()));
                singleServerConfig.setConnectionMinimumIdleSize(param.getConnectionMinimumIdleSize());
                singleServerConfig.setConnectionPoolSize(param.getConnectionPoolSize());
                singleServerConfig.setDatabase(param.getDatabase());
                singleServerConfig.setDnsMonitoringInterval(param.getDnsMonitoringInterval());
                singleServerConfig.setSubscriptionConnectionMinimumIdleSize(param.getSubscriptionConnectionMinimumIdleSize());
                singleServerConfig.setSubscriptionConnectionPoolSize(param.getSubscriptionConnectionPoolSize());
                singleServerConfig.setClientName(redissonProperties.getClientName());
                singleServerConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
                singleServerConfig.setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout());
                singleServerConfig.setKeepAlive(redissonProperties.getKeepAlive());
                singleServerConfig.setPassword(redissonProperties.getPassword());
                singleServerConfig.setPingConnectionInterval(redissonProperties.getPingConnectionInterval());
                singleServerConfig.setRetryAttempts(redissonProperties.getRetryAttempts());
                singleServerConfig.setRetryInterval(redissonProperties.getRetryInterval());
                singleServerConfig.setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification());
                singleServerConfig.setSslKeystore(redissonProperties.getSslKeystore());
                singleServerConfig.setSslKeystorePassword(redissonProperties.getSslKeystorePassword());
                singleServerConfig.setSslProvider(redissonProperties.getSslProvider());
                singleServerConfig.setSslTruststore(redissonProperties.getSslTruststore());
                singleServerConfig.setSslTruststorePassword(redissonProperties.getSslTruststorePassword());
                singleServerConfig.setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection());
                singleServerConfig.setTcpNoDelay(redissonProperties.getTcpNoDelay());
                singleServerConfig.setTimeout(redissonProperties.getTimeout());
                break;
            case CLUSTER:
                ClusterServersConfig clusterServersConfig = config.useClusterServers();
                clusterServersConfig.setScanInterval(multipleServerConfig.getScanInterval());
                clusterServersConfig.setSlaveConnectionMinimumIdleSize(multipleServerConfig.getSlaveConnectionMinimumIdleSize());
                clusterServersConfig.setSlaveConnectionPoolSize(multipleServerConfig.getSlaveConnectionPoolSize());
                clusterServersConfig.setFailedSlaveReconnectionInterval(multipleServerConfig.getFailedSlaveReconnectionInterval());
                clusterServersConfig.setFailedSlaveCheckInterval(multipleServerConfig.getFailedSlaveCheckInterval());
                clusterServersConfig.setMasterConnectionMinimumIdleSize(multipleServerConfig.getMasterConnectionMinimumIdleSize());
                clusterServersConfig.setMasterConnectionPoolSize(multipleServerConfig.getMasterConnectionPoolSize());
                clusterServersConfig.setReadMode(multipleServerConfig.getReadMode());
                clusterServersConfig.setSubscriptionMode(multipleServerConfig.getSubscriptionMode());
                clusterServersConfig.setSubscriptionConnectionMinimumIdleSize(multipleServerConfig.getSubscriptionConnectionMinimumIdleSize());
                clusterServersConfig.setSubscriptionConnectionPoolSize(multipleServerConfig.getSubscriptionConnectionPoolSize());
                clusterServersConfig.setDnsMonitoringInterval(multipleServerConfig.getDnsMonitoringInterval());
                try {
                    clusterServersConfig.setLoadBalancer((LoadBalancer) Class.forName(multipleServerConfig.getLoadBalancer()).newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                for (String nodeAddress : multipleServerConfig.getNodeAddresses()) {
                    clusterServersConfig.addNodeAddress(prefixAddress(nodeAddress));
                }
                clusterServersConfig.setClientName(redissonProperties.getClientName());
                clusterServersConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
                clusterServersConfig.setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout());
                clusterServersConfig.setKeepAlive(redissonProperties.getKeepAlive());
                clusterServersConfig.setPassword(redissonProperties.getPassword());
                clusterServersConfig.setPingConnectionInterval(redissonProperties.getPingConnectionInterval());
                clusterServersConfig.setRetryAttempts(redissonProperties.getRetryAttempts());
                clusterServersConfig.setRetryInterval(redissonProperties.getRetryInterval());
                clusterServersConfig.setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification());
                clusterServersConfig.setSslKeystore(redissonProperties.getSslKeystore());
                clusterServersConfig.setSslKeystorePassword(redissonProperties.getSslKeystorePassword());
                clusterServersConfig.setSslProvider(redissonProperties.getSslProvider());
                clusterServersConfig.setSslTruststore(redissonProperties.getSslTruststore());
                clusterServersConfig.setSslTruststorePassword(redissonProperties.getSslTruststorePassword());
                clusterServersConfig.setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection());
                clusterServersConfig.setTcpNoDelay(redissonProperties.getTcpNoDelay());
                clusterServersConfig.setTimeout(redissonProperties.getTimeout());
                break;
            case SENTINEL:
                SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
                sentinelServersConfig.setDatabase(multipleServerConfig.getDatabase());
                sentinelServersConfig.setMasterName(multipleServerConfig.getMasterName());
                sentinelServersConfig.setScanInterval(multipleServerConfig.getScanInterval());
                sentinelServersConfig.setSlaveConnectionMinimumIdleSize(multipleServerConfig.getSlaveConnectionMinimumIdleSize());
                sentinelServersConfig.setSlaveConnectionPoolSize(multipleServerConfig.getSlaveConnectionPoolSize());
                sentinelServersConfig.setFailedSlaveReconnectionInterval(multipleServerConfig.getFailedSlaveReconnectionInterval());
                sentinelServersConfig.setFailedSlaveCheckInterval(multipleServerConfig.getFailedSlaveCheckInterval());
                sentinelServersConfig.setMasterConnectionMinimumIdleSize(multipleServerConfig.getMasterConnectionMinimumIdleSize());
                sentinelServersConfig.setMasterConnectionPoolSize(multipleServerConfig.getMasterConnectionPoolSize());
                sentinelServersConfig.setReadMode(multipleServerConfig.getReadMode());
                sentinelServersConfig.setSubscriptionMode(multipleServerConfig.getSubscriptionMode());
                sentinelServersConfig.setSubscriptionConnectionMinimumIdleSize(multipleServerConfig.getSubscriptionConnectionMinimumIdleSize());
                sentinelServersConfig.setSubscriptionConnectionPoolSize(multipleServerConfig.getSubscriptionConnectionPoolSize());
                sentinelServersConfig.setDnsMonitoringInterval(multipleServerConfig.getDnsMonitoringInterval());
                try {
                    sentinelServersConfig.setLoadBalancer((LoadBalancer) Class.forName(multipleServerConfig.getLoadBalancer()).newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                for (String nodeAddress : multipleServerConfig.getNodeAddresses()) {
                    sentinelServersConfig.addSentinelAddress(prefixAddress(nodeAddress));
                }
                sentinelServersConfig.setClientName(redissonProperties.getClientName());
                sentinelServersConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
                sentinelServersConfig.setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout());
                sentinelServersConfig.setKeepAlive(redissonProperties.getKeepAlive());
                sentinelServersConfig.setPassword(redissonProperties.getPassword());
                sentinelServersConfig.setPingConnectionInterval(redissonProperties.getPingConnectionInterval());
                sentinelServersConfig.setRetryAttempts(redissonProperties.getRetryAttempts());
                sentinelServersConfig.setRetryInterval(redissonProperties.getRetryInterval());
                sentinelServersConfig.setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification());
                sentinelServersConfig.setSslKeystore(redissonProperties.getSslKeystore());
                sentinelServersConfig.setSslKeystorePassword(redissonProperties.getSslKeystorePassword());
                sentinelServersConfig.setSslProvider(redissonProperties.getSslProvider());
                sentinelServersConfig.setSslTruststore(redissonProperties.getSslTruststore());
                sentinelServersConfig.setSslTruststorePassword(redissonProperties.getSslTruststorePassword());
                sentinelServersConfig.setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection());
                sentinelServersConfig.setTcpNoDelay(redissonProperties.getTcpNoDelay());
                sentinelServersConfig.setTimeout(redissonProperties.getTimeout());
                break;
            case REPLICATED:
                ReplicatedServersConfig replicatedServersConfig = config.useReplicatedServers();
                replicatedServersConfig.setDatabase(multipleServerConfig.getDatabase());
                replicatedServersConfig.setScanInterval(multipleServerConfig.getScanInterval());
                replicatedServersConfig.setSlaveConnectionMinimumIdleSize(multipleServerConfig.getSlaveConnectionMinimumIdleSize());
                replicatedServersConfig.setSlaveConnectionPoolSize(multipleServerConfig.getSlaveConnectionPoolSize());
                replicatedServersConfig.setFailedSlaveReconnectionInterval(multipleServerConfig.getFailedSlaveReconnectionInterval());
                replicatedServersConfig.setFailedSlaveCheckInterval(multipleServerConfig.getFailedSlaveCheckInterval());
                replicatedServersConfig.setMasterConnectionMinimumIdleSize(multipleServerConfig.getMasterConnectionMinimumIdleSize());
                replicatedServersConfig.setMasterConnectionPoolSize(multipleServerConfig.getMasterConnectionPoolSize());
                replicatedServersConfig.setReadMode(multipleServerConfig.getReadMode());
                replicatedServersConfig.setSubscriptionMode(multipleServerConfig.getSubscriptionMode());
                replicatedServersConfig.setSubscriptionConnectionMinimumIdleSize(multipleServerConfig.getSubscriptionConnectionMinimumIdleSize());
                replicatedServersConfig.setSubscriptionConnectionPoolSize(multipleServerConfig.getSubscriptionConnectionPoolSize());
                replicatedServersConfig.setDnsMonitoringInterval(multipleServerConfig.getDnsMonitoringInterval());
                try {
                    replicatedServersConfig.setLoadBalancer((LoadBalancer) Class.forName(multipleServerConfig.getLoadBalancer()).newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                for (String nodeAddress : multipleServerConfig.getNodeAddresses()) {
                    replicatedServersConfig.addNodeAddress(prefixAddress(nodeAddress));
                }
                replicatedServersConfig.setClientName(redissonProperties.getClientName());
                replicatedServersConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
                replicatedServersConfig.setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout());
                replicatedServersConfig.setKeepAlive(redissonProperties.getKeepAlive());
                replicatedServersConfig.setPassword(redissonProperties.getPassword());
                replicatedServersConfig.setPingConnectionInterval(redissonProperties.getPingConnectionInterval());
                replicatedServersConfig.setRetryAttempts(redissonProperties.getRetryAttempts());
                replicatedServersConfig.setRetryInterval(redissonProperties.getRetryInterval());
                replicatedServersConfig.setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification());
                replicatedServersConfig.setSslKeystore(redissonProperties.getSslKeystore());
                replicatedServersConfig.setSslKeystorePassword(redissonProperties.getSslKeystorePassword());
                replicatedServersConfig.setSslProvider(redissonProperties.getSslProvider());
                replicatedServersConfig.setSslTruststore(redissonProperties.getSslTruststore());
                replicatedServersConfig.setSslTruststorePassword(redissonProperties.getSslTruststorePassword());
                replicatedServersConfig.setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection());
                replicatedServersConfig.setTcpNoDelay(redissonProperties.getTcpNoDelay());
                replicatedServersConfig.setTimeout(redissonProperties.getTimeout());
                break;
            case MASTERSLAVE:
                MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers();
                masterSlaveServersConfig.setDatabase(multipleServerConfig.getDatabase());
                masterSlaveServersConfig.setSlaveConnectionMinimumIdleSize(multipleServerConfig.getSlaveConnectionMinimumIdleSize());
                masterSlaveServersConfig.setSlaveConnectionPoolSize(multipleServerConfig.getSlaveConnectionPoolSize());
                masterSlaveServersConfig.setFailedSlaveReconnectionInterval(multipleServerConfig.getFailedSlaveReconnectionInterval());
                masterSlaveServersConfig.setFailedSlaveCheckInterval(multipleServerConfig.getFailedSlaveCheckInterval());
                masterSlaveServersConfig.setMasterConnectionMinimumIdleSize(multipleServerConfig.getMasterConnectionMinimumIdleSize());
                masterSlaveServersConfig.setMasterConnectionPoolSize(multipleServerConfig.getMasterConnectionPoolSize());
                masterSlaveServersConfig.setReadMode(multipleServerConfig.getReadMode());
                masterSlaveServersConfig.setSubscriptionMode(multipleServerConfig.getSubscriptionMode());
                masterSlaveServersConfig.setSubscriptionConnectionMinimumIdleSize(multipleServerConfig.getSubscriptionConnectionMinimumIdleSize());
                masterSlaveServersConfig.setSubscriptionConnectionPoolSize(multipleServerConfig.getSubscriptionConnectionPoolSize());
                masterSlaveServersConfig.setDnsMonitoringInterval(multipleServerConfig.getDnsMonitoringInterval());
                try {
                    masterSlaveServersConfig.setLoadBalancer((LoadBalancer) Class.forName(multipleServerConfig.getLoadBalancer()).newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                int index=0;
                for (String nodeAddress : multipleServerConfig.getNodeAddresses()) {
                    if(index++==0){
                        masterSlaveServersConfig.setMasterAddress(prefixAddress(nodeAddress));
                    }else{
                        masterSlaveServersConfig.addSlaveAddress(prefixAddress(nodeAddress));
                    }
                }
                masterSlaveServersConfig.setClientName(redissonProperties.getClientName());
                masterSlaveServersConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
                masterSlaveServersConfig.setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout());
                masterSlaveServersConfig.setKeepAlive(redissonProperties.getKeepAlive());
                masterSlaveServersConfig.setPassword(redissonProperties.getPassword());
                masterSlaveServersConfig.setPingConnectionInterval(redissonProperties.getPingConnectionInterval());
                masterSlaveServersConfig.setRetryAttempts(redissonProperties.getRetryAttempts());
                masterSlaveServersConfig.setRetryInterval(redissonProperties.getRetryInterval());
                masterSlaveServersConfig.setSslEnableEndpointIdentification(redissonProperties.getSslEnableEndpointIdentification());
                masterSlaveServersConfig.setSslKeystore(redissonProperties.getSslKeystore());
                masterSlaveServersConfig.setSslKeystorePassword(redissonProperties.getSslKeystorePassword());
                masterSlaveServersConfig.setSslProvider(redissonProperties.getSslProvider());
                masterSlaveServersConfig.setSslTruststore(redissonProperties.getSslTruststore());
                masterSlaveServersConfig.setSslTruststorePassword(redissonProperties.getSslTruststorePassword());
                masterSlaveServersConfig.setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection());
                masterSlaveServersConfig.setTcpNoDelay(redissonProperties.getTcpNoDelay());
                masterSlaveServersConfig.setTimeout(redissonProperties.getTimeout());
                break;

        }
        return Redisson.create(config);
    }

    private String prefixAddress(String address){
        if(!StringUtils.isEmpty(address)&&!address.startsWith("redis")){
           return "redis://"+address;
        }
        return address;
    }

}
