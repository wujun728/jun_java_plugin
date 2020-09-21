package com.github.sd4324530.fastlock;

import java.util.concurrent.locks.Lock;

/**
 * 分布式锁接口，支持超时，有效防止死锁
 * 实现java.util.concurrent.locks.Lock接口
 * @author peiyu
 */
public interface FastLock extends Lock {

    /**
     * 获取超时时间
     * @return 超时时间
     */
    Long getLockTimeout();

    /**
     * 获取当前锁的Key
     * @return key
     */
    String getKey();
}
