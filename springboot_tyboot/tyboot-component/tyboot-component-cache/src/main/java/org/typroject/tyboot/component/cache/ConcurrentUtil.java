package org.typroject.tyboot.component.cache;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: ConcurrentUtil.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  用redis实现的全局锁,可实现并发控制
 * 
 *  Notes:
 *  $Id: ConcurrentUtil.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
public class ConcurrentUtil
{


	public static final long OPERATION_TOKEN_EXPIRE = 60L;

	private static String MONITOR = "MONITOR";

	public static String getUUID()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	/**
	 * TODO.以全局分布式同步锁的方式执行操作,用于控制规模较小的并发
	 * @param entityKey	全局同步锁的Key
	 * @param callee
	 * @return
	 */
	public static <T> T runWithAsynLock(String entityKey,Callable<T> callee){
		asynLock(entityKey);
		T result = null;
		try {
			try {
				result = callee.call();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage(),e.getCause());
			}
		} finally{
			unlock(entityKey);
		}
		return result;
	}


	/**
	 * TODO.以全局分布式同步锁的方式执行操作,用于控制规模较小的并发
	 * @param entityKey	全局同步锁的Key
	 * @param callee
	 * @return
	 */
	public static <T> T runWithExclusiveLock(String entityKey,Callable<T> callee){
		exclusiveLock(entityKey);
		T result = null;
		try {
			try {
				result = callee.call();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage(),e.getCause());
			}
		} finally{
			unlock(entityKey);
		}
		return result;
	}




	/**
	 * TODO.获取全局锁
	 * @param entityKey	全局锁的Key
	 * @throws InterruptedException
	 */
	private static void asynLock(String entityKey) {
		while (true) {
			if(Redis.getRedisTemplate().opsForValue().setIfAbsent(entityKey, MONITOR))
			{
				Redis.getRedisTemplate().expire(entityKey, OPERATION_TOKEN_EXPIRE, TimeUnit.SECONDS);
				break;
			}
			else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new RuntimeException(e.getMessage(),e.getCause());
				}
			}
		}
	}


	/**
	 * TODO.获取全局排他锁
	 * @param entityKey	全局排他锁的Key
	 * @throws InterruptedException
	 */
	private static void exclusiveLock(String entityKey)
	{

		if(Redis.getRedisTemplate().opsForValue().setIfAbsent(entityKey, MONITOR))
		{
			Redis.getRedisTemplate().expire(entityKey, OPERATION_TOKEN_EXPIRE, TimeUnit.SECONDS);
		}
		else {
			throw new RuntimeException("重复的操作.");
		}
	}

	/**
	 * TODO.释放全局锁
	 * @param entityKey	全局锁的Key
	 */
	private static void unlock(String entityKey){
		Redis.getRedisTemplate().delete(entityKey);
	}
}
