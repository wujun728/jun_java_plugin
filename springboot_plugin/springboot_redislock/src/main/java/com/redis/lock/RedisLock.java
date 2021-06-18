package com.redis.lock;

import com.redis.exception.RedisLockException;
import com.redis.lock.api.RLock;
import com.redis.lock.api.RedisCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Wujun
 * @version V1.0
 * @date 2017-09-26 14:18
 **/
public class RedisLock implements RLock {
	private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);
	private Thread thread;
	private String key;
	private UUID id = UUID.randomUUID();
	private Random random = new Random();
	private int unlockRetry;
	private RedisTemplate<String, Object> redisTemplate;
	//static final long spinForTimeoutThreshold = 1000L;

	public static RedisLock create(String key, RedisTemplate<String, Object> redisTemplate) {
		return create(key, redisTemplate, 1);
	}

	/**
	 * @param key         当前服务的别名
	 * @param unlockRetry 解锁重试次数
	 * @return
	 */
	public static RedisLock create(String key, RedisTemplate<String, Object> redisTemplate, int unlockRetry) {
		return new RedisLock(key, redisTemplate, unlockRetry);
	}

	private RedisLock(String key, RedisTemplate<String, Object> redisTemplate, int unlockRetry) {
		this.key = key;
		this.redisTemplate = redisTemplate;
		this.unlockRetry = unlockRetry;
	}

	/**
	 * 加锁
	 *
	 * @param expire   redis key timeout
	 * @param timeUnit the time unit of the timeout argument
	 * @throws InterruptedException
	 */
	@Override
	public void lock(long expire, TimeUnit timeUnit) throws InterruptedException {
		if (expire <= 0L) throw new IllegalArgumentException("expire time least gt zero");
		String field = getLockName(Thread.currentThread().getId());
		boolean result;
		for (; ; ) {
			result = RedisCommand.lock(redisTemplate, key, field, timeUnit.toMillis(expire));
			if (result) {
				thread = Thread.currentThread();
				return;
			} else {
				Thread.sleep(random.nextInt(10));
			}
		}
	}

	@Override
	public boolean tryLock(long expire, TimeUnit timeUnit) {
		String field = getLockName(Thread.currentThread().getId());
		boolean result = RedisCommand.lock(redisTemplate, key, field, timeUnit.toMillis(expire));
		if (result) {
			thread = Thread.currentThread();
			return true;
		}
		return false;
	}

	@Override
	public boolean tryLock(long timeout, long expire, TimeUnit timeUnit) throws InterruptedException {
		if (expire <= 0L) throw new IllegalArgumentException("expire time least gt zero");
		if (timeout <= 0L) throw new IllegalArgumentException("timeout time least gt zero");
		final long deadline = System.nanoTime() + timeUnit.toNanos(timeout);
		String field = getLockName(Thread.currentThread().getId());
		boolean result;
		for (; ; ) {
			result = RedisCommand.lock(redisTemplate, key, field, timeUnit.toMillis(expire));
			if (result) {
				thread = Thread.currentThread();
				return true;
			} else {
				long remaining = deadline - System.nanoTime();
				if (remaining <= 0L)
					return false;
				LockSupport.parkNanos(remaining);
			}
		}
	}

	@Override
	public boolean isHeldByCurrentThread() {
		return thread == Thread.currentThread();
	}

	@Override
	public boolean isLocked() {
		return RedisCommand.isLocked(redisTemplate, key, getLockName(Thread.currentThread().getId()));
	}

	@Override
	public void unlock() {
		if (thread != Thread.currentThread()) throw new IllegalMonitorStateException();
		String field = getLockName(Thread.currentThread().getId());
		for (int i = 0; i <= unlockRetry; i++) {
			try {
				RedisCommand.unlock(redisTemplate, key, field);
				break;
			} catch (Exception e) {
				logger.error("当前线程解锁异常,线程ID:{},error:{}", Thread.currentThread().getId(), e.getMessage());
			}
			if (unlockRetry == i) logger.warn("当前线程解锁异常,线程ID:{}", Thread.currentThread().getId());
		}
	}

	String getLockName(long threadId) {
		return this.id + ":" + threadId;
	}
}
