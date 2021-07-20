package io.gitee.tooleek.lock.spring.boot.constant;

/**
 * 负载均衡算法类型
 *
 * @author Wujun
 * @version 1.1.0
 * @apiNote 知识改变命运，技术改变世界
 * @since 2018-12-23 15:27
 */
public interface LoadBalancerTypeConstant {

    String RANDOM_LOAD_BALANCER = "RandomLoadBalancer";

    String ROUND_ROBIN_LOAD_BALANCER = "RoundRobinLoadBalancer";

    String WEIGHTED_ROUND_ROBIN_BALANCER = "WeightedRoundRobinBalancer";

}
