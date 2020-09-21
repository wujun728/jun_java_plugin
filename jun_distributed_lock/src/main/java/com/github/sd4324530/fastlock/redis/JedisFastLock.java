package com.github.sd4324530.fastlock.redis;

import com.github.sd4324530.fastlock.FastLock;
import com.github.sd4324530.fastlock.FastLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * 基于redis集群实现的分布式锁
 * @author peiyu
 */
public class JedisFastLock implements FastLock {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JedisCluster jedis;

    private final String lockKey;

    /**
     * 用于记录各个线程获取锁的超时时间，用于判断是否需要主动释放锁
     */
    private static final ThreadLocal<Long> threadCache = new ThreadLocal<Long>();

    /**
     * 默认锁超时时间为2秒，防止出现死锁
     */
    private long lockTimeout = 2000;

    public JedisFastLock(JedisCluster jedis, String lockKey) {
        this.jedis = jedis;
        this.lockKey = lockKey;
    }

    @Override
    public void lock() {
        while(!tryLock()) {
            waitMilli();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        lock();
    }

    @Override
    public boolean tryLock() {
        try {
            getLock();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long waitTime = unit.toMillis(time);
        long timeout = System.currentTimeMillis() + waitTime;
        do {
            long now = System.currentTimeMillis();
            if(now > timeout) {
                if(tryLock()) {
                    return true;
                }
            } else {
                return false;
            }
            waitMilli();
        } while (true);
    }

    @Override
    public void unlock() {
        //如果还没有过超时时间，则主动释放锁，如果已经超时，其他线程已经可以获取该锁了，无需主动释放
        Long lockTimeout = this.getLockTimeout();
        if(null != lockTimeout) {
            if(System.currentTimeMillis() < lockTimeout) {
                log.debug("开始释放锁....");
                threadCache.remove();
                Long del = this.jedis.del(this.lockKey);
                log.debug("主动释放锁成功:{}", del);
            }
        } else {
            log.debug("已经超时，本次锁已经自动释放....");
        }
    }

    @Override
    public Condition newCondition() {
        throw new FastLockException("暂未支持");
    }

    @Override
    public Long getLockTimeout() {
        return threadCache.get();
    }

    @Override
    public String getKey() {
        return this.lockKey;
    }

    public void setTimeout(Long timeout) {
        this.lockTimeout = timeout;
    }


    private void getLock() {
        long value = System.currentTimeMillis() + this.lockTimeout;
        Long setnx = this.jedis.setnx(this.lockKey, String.valueOf(value));
        //设置锁失败，锁已经存在
        if(0 == setnx) {
            String time = this.jedis.get(this.lockKey);
            if(null != time) {
                long timeout = Long.parseLong(time);
                long now = System.currentTimeMillis();
                //已经超时，可以获取锁
                if(timeout < now) {
                    long newTimeout = System.currentTimeMillis() + this.lockTimeout;
                    String time2 = this.jedis.getSet(this.lockKey, String.valueOf(newTimeout));
                    if(null != time2) {
                        long timeout2 = Long.parseLong(time2);
                        //说明锁被别人取走了，本次获取锁失败
                        if(timeout2 != timeout) {
                            throw new FastLockException("锁未被释放，无法获取锁！");
                        } else {
                            threadCache.set(newTimeout);
                        }
                    } else {
                        threadCache.set(newTimeout);
                    }
                }
                //无法获取锁
                else {
                    throw new FastLockException("锁未被释放，无法获取锁！");
                }
            }
            //锁已经被释放，可以获取锁
            else {
                long newTimeout = System.currentTimeMillis() + this.lockTimeout;
                String time2 = this.jedis.getSet(this.lockKey, String.valueOf(newTimeout));
                if(null != time2) {
                    throw new FastLockException("锁未被释放，无法获取锁！");
                } else {
                    threadCache.set(newTimeout);
                }
            }
        }
        //设置锁成功，同时获取锁
        else {
            threadCache.set(value);
        }
    }

    /**
     * 休眠1毫秒
     */
    private void waitMilli() {
        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("休眠异常", e);
        }
    }
}
