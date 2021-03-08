package com.gosalelab.aspect;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Wujun
 */
public class CommonJoinPointConfig {
    @Pointcut("@annotation(com.gosalelab.annotation.CacheInject)")
    public void cacheInjectAspectExecution() {
    }

    @Pointcut("@annotation(com.gosalelab.annotation.CacheEvict)")
    public void cacheEvictAspectExecution() {
    }
}
