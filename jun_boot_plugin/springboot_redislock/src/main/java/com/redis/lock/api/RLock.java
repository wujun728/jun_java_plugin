package com.redis.lock.api;

import com.redis.exception.RedisLockException;

import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @version V1.0
 * @date 2017-09-26 10:45
 **/
public interface RLock {
    /**
     * acquires the lock.
     *
     * @param expire   redis key timeout
     * @param timeUnit the time unit of the timeout argument
     */
    void lock(long expire, TimeUnit timeUnit) throws InterruptedException, RedisLockException;

    /**
     * acquires the lock if lock is free
     *
     * @param expire   redis key timeout
     * @param timeUnit the time unit of the timeout argument
     * @return
     */
    boolean tryLock(long expire, TimeUnit timeUnit) throws RedisLockException;

    /**
     * try to Acquires the lock
     *
     * @param timeout  the time to wait for the lock
     * @param expire   redis key timeout
     * @param timeUnit the time unit of the timeout argument
     * @return
     */
    boolean tryLock(long timeout, long expire, TimeUnit timeUnit) throws InterruptedException, RedisLockException;

    /**
     * check if current thread is owner
     *
     * @return
     */
    boolean isHeldByCurrentThread();

    /**
     * if any thread holds this lock
     *
     * @return
     */
    boolean isLocked() throws RedisLockException;

    void unlock() throws RedisLockException;
}
