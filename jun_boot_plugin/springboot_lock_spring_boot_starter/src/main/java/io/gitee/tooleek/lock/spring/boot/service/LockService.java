package io.gitee.tooleek.lock.spring.boot.service;

import io.gitee.tooleek.lock.spring.boot.core.LockKey;

/**
 * 锁服务
 * @author Wujun
 *
 */
public interface LockService {
	
	/**
	 * 添加key
	 * @param lockKey
	 */
	public void setLockKey(LockKey lockKey);
	/**
	 * 加锁
	 */
	public void lock() throws Exception;
	
	/**
	 * 解锁
	 */
	public void release();

}
