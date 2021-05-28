package com.zengtengpeng.properties;

import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 多节点配置
 */
public class MultipleServerConfig {

    private String loadBalancer = "org.redisson.connection.balancer.RoundRobinLoadBalancer";
    private Integer slaveConnectionMinimumIdleSize = 32;
    private Integer slaveConnectionPoolSize = 64;
    private Integer failedSlaveReconnectionInterval = 3000;
    private Integer failedSlaveCheckInterval = 180000;
    private Integer masterConnectionMinimumIdleSize = 32;
    private Integer masterConnectionPoolSize = 64;
    private ReadMode readMode=ReadMode.SLAVE;
    private SubscriptionMode subscriptionMode=SubscriptionMode.SLAVE;
    private Integer subscriptionConnectionMinimumIdleSize=1;
    private Integer subscriptionConnectionPoolSize=50;
    private Long dnsMonitoringInterval=5000L;

    private List<String> nodeAddresses = new ArrayList();

    //集群,哨兵,云托管
    private Integer scanInterval = 1000;

    //哨兵模式,云托管,主从
    private Integer database = 0;

    //哨兵模式
    private String masterName;

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(String loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public Integer getSlaveConnectionMinimumIdleSize() {
        return slaveConnectionMinimumIdleSize;
    }

    public Integer getSlaveConnectionPoolSize() {
        return slaveConnectionPoolSize;
    }

    public Integer getFailedSlaveReconnectionInterval() {
        return failedSlaveReconnectionInterval;
    }

    public Integer getFailedSlaveCheckInterval() {
        return failedSlaveCheckInterval;
    }

    public Integer getMasterConnectionMinimumIdleSize() {
        return masterConnectionMinimumIdleSize;
    }

    public Integer getMasterConnectionPoolSize() {
        return masterConnectionPoolSize;
    }

    public ReadMode getReadMode() {
        return readMode;
    }

    public SubscriptionMode getSubscriptionMode() {
        return subscriptionMode;
    }

    public Integer getSubscriptionConnectionMinimumIdleSize() {
        return subscriptionConnectionMinimumIdleSize;
    }

    public Integer getSubscriptionConnectionPoolSize() {
        return subscriptionConnectionPoolSize;
    }

    public Long getDnsMonitoringInterval() {
        return dnsMonitoringInterval;
    }

    public List<String> getNodeAddresses() {
        return nodeAddresses;
    }

    public void setNodeAddresses(List<String> nodeAddresses) {
        this.nodeAddresses = nodeAddresses;
    }

    public Integer getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(Integer scanInterval) {
        this.scanInterval = scanInterval;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }


    public void setSlaveConnectionMinimumIdleSize(Integer slaveConnectionMinimumIdleSize) {
        this.slaveConnectionMinimumIdleSize = slaveConnectionMinimumIdleSize;
    }

    public void setSlaveConnectionPoolSize(Integer slaveConnectionPoolSize) {
        this.slaveConnectionPoolSize = slaveConnectionPoolSize;
    }

    public void setFailedSlaveReconnectionInterval(Integer failedSlaveReconnectionInterval) {
        this.failedSlaveReconnectionInterval = failedSlaveReconnectionInterval;
    }

    public void setFailedSlaveCheckInterval(Integer failedSlaveCheckInterval) {
        this.failedSlaveCheckInterval = failedSlaveCheckInterval;
    }

    public void setMasterConnectionMinimumIdleSize(Integer masterConnectionMinimumIdleSize) {
        this.masterConnectionMinimumIdleSize = masterConnectionMinimumIdleSize;
    }

    public void setMasterConnectionPoolSize(Integer masterConnectionPoolSize) {
        this.masterConnectionPoolSize = masterConnectionPoolSize;
    }

    public void setReadMode(ReadMode readMode) {
        this.readMode = readMode;
    }

    public void setSubscriptionMode(SubscriptionMode subscriptionMode) {
        this.subscriptionMode = subscriptionMode;
    }

    public void setSubscriptionConnectionMinimumIdleSize(Integer subscriptionConnectionMinimumIdleSize) {
        this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
    }

    public void setSubscriptionConnectionPoolSize(Integer subscriptionConnectionPoolSize) {
        this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
    }

    public void setDnsMonitoringInterval(Long dnsMonitoringInterval) {
        this.dnsMonitoringInterval = dnsMonitoringInterval;
    }
}
