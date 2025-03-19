package io.github.wujun728.common.redis.constant;

/**
 * redis 工具常量
 *
 */
public class RedisToolsConstant {
    private RedisToolsConstant() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * single Redis
     */
    public final static int SINGLE = 1 ;

    /**
     * Redis cluster
     */
    public final static int CLUSTER = 2 ;
}
