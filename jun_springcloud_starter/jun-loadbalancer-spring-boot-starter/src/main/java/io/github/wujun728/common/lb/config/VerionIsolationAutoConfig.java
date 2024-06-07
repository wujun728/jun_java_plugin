package io.github.wujun728.common.lb.config;

import io.github.wujun728.common.constant.ConfigConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Import;

/**
 * 
 *
 * @author jarvis create by 2022/4/10
 */
@LoadBalancerClients(defaultConfiguration = VersionLoadBalancerConfig.class)
@ConditionalOnProperty(prefix = ConfigConstants.CONFIG_LOADBALANCE_ISOLATION, name = "enabled", havingValue = "true", matchIfMissing = false)
@Import({VersionRegisterBeanPostProcessor.class})
public class VerionIsolationAutoConfig {
}
