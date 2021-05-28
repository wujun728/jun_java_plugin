package cn.com.bluemoon.redis.lock;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import cn.com.bluemoon.common.exception.IllegalReentrantException;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 不可重入分布式锁,基于redis实现
 * <p>
 * Created by Guoqing on 18/11/22.
 * 变更了加锁与释放锁的过程，通过jedis的操作实现，防止出现死锁等问题
 */
public class DistributedExclusiveRedisLock implements Lock, Serializable {
    private static final long serialVersionUID = -7118885188373628439L;

	private RedisTemplate redisTemplate;
	
	private Jedis jedis;

    /**
     * 控制锁颗粒度的参数
     * <p>
     * 不建议使用全局锁,具体应用中推荐指定对应的Key,把锁的颗粒度减小,利于性能
     */
    private String lockKey = "distributed_global_lock";
    
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final Long RELEASE_SUCCESS = 1L;

    private String uuid;

    private boolean isOccupy;

    // 单位 默认10秒
    private long expires = 30L;

    public DistributedExclusiveRedisLock(RedisTemplate template, Jedis jedis) {
        this.redisTemplate = template;
        this.jedis = jedis;
    }

    public DistributedExclusiveRedisLock(RedisTemplate template, String lockKey, Jedis jedis) {
        this.lockKey = lockKey;
        this.redisTemplate = template;
        this.jedis = jedis;
    }

    /**
     * 获取锁方法,获取不到会被阻塞
     */
    @SuppressWarnings("unchecked")
	@Override
    public void lock() {
        if (isOccupy) {
            throw new IllegalReentrantException("锁不可重入,请检查代码");
        }
        isOccupy = true;
        uuid = UUID.randomUUID().toString();
        boolean isAcquired;
        for (; ; ) {
            isAcquired = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    /*return connection.setNX(lockKey.getBytes(), uuid.getBytes())
                            && connection.expire(lockKey.getBytes(), expires);*/
                	/**
                	 * 更新了1.0版本中，setnx成功之后程序崩溃导致的死锁的问题
                	 */
                	String result = jedis.set(lockKey, uuid, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expires);
                	if (LOCK_SUCCESS.equals(result)) {
                        return true;
                    }
                    return false;
                }
            });
            if (isAcquired)
                return;
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    /**
     * 尝试获取锁,支持超时中断 (暂未实现)
     *
     * @param time
     * @param unit
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    /**
     * 解锁并释放资源
     * <p>
     * 超时后的资源被释放掉,避免误删,这里务必校验uuid
     */
	@Override
    public void unlock() {
    	if (!isOccupy)
            return;
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(uuid));

        if (RELEASE_SUCCESS.equals(result)) {
        	isOccupy = false;
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }
}
