package com.jun.plugin.util4j.cache.map;

import java.util.Map;

/**
 * 键值对具有生命周期的map
 * @author Administrator
 */
public interface TimedMap<K,V> extends Map<K,V>{

	/**
	 * 存放一个键值对,该键值超时访问自动删除
	 * @param key
	 * @param value
	 * @param ttl 生命周期 <=0 永不过期,>0 过期时间
	 */
	public V put(K key,V value,long ttl);
	
	public V put(K key,V value,long ttl,EventListener<K, V> listener);
	
	public V getBy(K key);
	
	public V removeBy(K key);
	
	/**
	 * 更新最大不活动间隔时间
	 * @param key
	 * @param ttl 生命周期 <=0 永不过期,>0 过期时间
	 * @return
	 */
	public V updateTTL(K key,long ttl);
	
	/**
	 * >0 剩余过期时间
	 * =0 永不过期
	 * <0 不存在此键,或者已经过期
	 * @param key
	 * @return
	 */
	public long getExpireTime(K key);
	
	/**
	 * 清理过期,返回被清理的键值对
	 * 不需要不定时清理,建议在容量达到某大小时清理
	 * @return
	 */
	public Map<K,V> cleanExpire();
	
	/**
	 * 获取清理任务
	 * @return
	 */
	public Runnable getCleanTask();
	
	/**
	 * 给键值对加事件监听器
	 * @param key
	 * @param lisnener
	 * @return
	 */
	public V setEventListener(K key,EventListener<K,V> lisnener);
	
	/**
	 * 事件监听器
	 * @author Administrator
	 * @param <K>
	 * @param <V>
	 */
	@FunctionalInterface
	public static interface EventListener<K,V>{
		/**
		 * 移除后调用此方法
		 * @param key
		 * @param value
		 * @param expire 是否超时移除
		 */
		public void removed(K key,V value,boolean expire);
	}
}
