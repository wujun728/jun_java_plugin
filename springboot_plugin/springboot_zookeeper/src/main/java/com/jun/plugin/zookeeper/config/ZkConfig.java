package com.jun.plugin.zookeeper.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jun.plugin.zookeeper.config.props.ZkProps;

/**
 * <p>
 * Zookeeper配置类
 * </p>
 *
 * @package: com.xkcoding.zookeeper.config
 * @description: Zookeeper配置类
 * @author: yangkai.shen
 * @date: Created in 2018-12-27 14:45
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
@EnableConfigurationProperties(ZkProps.class)
public class ZkConfig {
    private final ZkProps zkProps;

    @Autowired
    public ZkConfig(ZkProps zkProps) {
        this.zkProps = zkProps;
    }

    @Bean
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zkProps.getTimeout(), zkProps.getRetry());
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkProps.getUrl(), retryPolicy);
        client.start();
        return client;
    }
}
