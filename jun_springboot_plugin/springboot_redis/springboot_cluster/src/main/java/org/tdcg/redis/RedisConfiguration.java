package org.tdcg.redis;


import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Set;

/**
 * @Title: RedisConfiguration
 * @Package: org.tdcg.redis
 * @Description: redis初始化配置
 * @Author: 二东 <zwd_1222@126.com>
 * @date: 2016/10/24
 * @Version: V1.0
 */
@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {

    @Bean(name = "jedisCluster")
    @ConfigurationProperties(prefix = "myCluster.cluster")
    public JedisClusterFactory jedisCluster(
            @Value("${myCluster.cluster.host}") String host,
            @Value("${myCluster.cluster.connectionTimeout}") int connectionTimeout,
            @Value("${myCluster.cluster.soTimeout}") int soTimeout,
            @Value("${myCluster.cluster.maxRedirections}") int maxRedirections) {
        JedisClusterFactory jedisClusterFactory = new JedisClusterFactory();
        jedisClusterFactory.setConnectionTimeout(connectionTimeout);
        jedisClusterFactory.setSoTimeout(soTimeout);
        jedisClusterFactory.setMaxRedirections(maxRedirections);
        String[] split = host.split(",");
        Set<String> hosts = Sets.newHashSet();
        Collections.addAll(hosts, split);
        jedisClusterFactory.setJedisClusterNodes(hosts);
        return jedisClusterFactory;
    }
}

