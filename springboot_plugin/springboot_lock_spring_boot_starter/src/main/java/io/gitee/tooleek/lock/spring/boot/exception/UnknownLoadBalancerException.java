package io.gitee.tooleek.lock.spring.boot.exception;

/**
 * 未知负载均衡算法类型
 *
 * @author Wujun
 * @version 1.1.0
 * @apiNote 知识改变命运，技术改变世界
 * @since 2018-12-23 15:32
 */
public class UnknownLoadBalancerException extends RuntimeException {

    private static final long serialVersionUID = 5793677066428295913L;

    public UnknownLoadBalancerException() {
        super("未知负载均衡算法类型");
    }

}
