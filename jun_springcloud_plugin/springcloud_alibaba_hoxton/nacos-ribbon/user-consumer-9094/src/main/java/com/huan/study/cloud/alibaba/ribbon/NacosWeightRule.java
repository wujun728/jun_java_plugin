package com.huan.study.cloud.alibaba.ribbon;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @author huan.fu 2020/10/24 - 15:30
 */
@Slf4j
public class NacosWeightRule extends AbstractLoadBalancerRule {
    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Resource
    private NacosServiceManager nacosServiceManager;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {
        DynamicServerListLoadBalancer<NacosServer> loadBalancer = (DynamicServerListLoadBalancer<NacosServer>) getLoadBalancer();
        String serviceName = loadBalancer.getName();
        NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
        try {
            Instance instance = namingService.selectOneHealthyInstance(serviceName, true);
            log.info("select service name:[{}] instance ip:[{}] port:[{}]", serviceName, instance.getIp(), instance.getPort());
            return new NacosServer(instance);
        } catch (NacosException e) {
            log.warn("获取可用的[{}]服务失败", serviceName, e);
            return null;
        }
    }
}
