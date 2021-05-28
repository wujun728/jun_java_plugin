package cn.com.bluemoon.redis.repository;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson装配各种模式
 * @author Wujun
 * @date 2019年1月23日 下午3:14:07
 *
 */
@Configuration
@ConditionalOnClass(Config.class)
public class RedissonAutoConfiguration {
	
	@Autowired
    private RedissonProperties redssionProperties;
	
	/**
     * 哨兵模式自动装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="spring.redis.master-name")
    RedissonClient redissonSentinel() {
    	String[] nodes = redssionProperties.getSentinelAddresses();
    	for(int i=0;i<nodes.length;i++) {
    		nodes[i] = "redis://"+nodes[i];
    	}
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers()
        		.addSentinelAddress(nodes)
                .setMasterName(redssionProperties.getMasterName())
                .setTimeout(redssionProperties.getTimeout())
                .setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize());
        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 单机模式自动装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="spring.redis.host")
    RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress("redis://" + redssionProperties.getHost() + ":" + redssionProperties.getPort())
                .setTimeout(redssionProperties.getTimeout())
                .setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize());
        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }
        return Redisson.create(config);
    }
    
    /**
     * 集群装配模式
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="spring.redis.cluster.nodes")
    RedissonClient redissonCluster() {
    	String[] nodes = redssionProperties.getCluster().get("nodes").split(",");
    	for(int i=0;i<nodes.length;i++) {
    		nodes[i] = "redis://"+nodes[i];
    	}
    	Config config = new Config();
    	ClusterServersConfig serverConfig = config.useClusterServers()
    			.setScanInterval(2000)
    			.addNodeAddress(nodes)
    			.setTimeout(redssionProperties.getTimeout())
    			.setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize())
                .setPassword(redssionProperties.getPassword());
    	if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }
        return Redisson.create(config);
    }
    
    

}
