package com.jun.plugin.util4j.cache.map;

import java.util.Map;

/**
 * 键值对具有生命周期的map
 * @author Administrator
 */
public interface AssistTimedMap<K,V> extends Map<K,V>{

	/**
	 * 存放一个键值对,该键值超时访问自动删除
	 * @param key
	 * @param value
	 * @param tAssister 超时助理
	 * @param rAssister 移除助理
	 * @return
	 */
	public V put(K key,V value,TimeOutAssister<K,V> tAssister,RemoveAssister<K, V> rAssister);
	
	/**
	 * 存放一个键值对
	 * @param key
	 * @param value
	 * @return
	 */
	default V put(K key,V value){
		return put(key, value,null, null);
	}
	
	/**
	 * 存放一个键值对,该键值超时访问自动删除
	 * @param key
	 * @param value
	 * @param ttlMills 超时毫秒
	 * @return
	 */
	default V put(K key,V value,long ttlMills){
		return put(key, value, new TtlAssister<K,V>(ttlMills), null);
	}
	
	/**
	 * 存放一个键值对,该键值超时访问自动删除
	 * @param key
	 * @param value
	 * @param ttlMills 超时毫秒
	 * @return
	 */
	default V put(K key,V value,long ttlMills,RemoveAssister<K, V> rAssister){
		return put(key, value, new TtlAssister<K,V>(ttlMills), rAssister);
	}
	
	public V getBy(K key);
	
	public V removeBy(K key);
	
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
	 * 定期超时
	 * @author juebanlin
	 * @param <K>
	 * @param <V>
	 */
	public static class TtlAssister<K,V> implements TimeOutAssister<K,V>{
		
		private long ttl;
		public TtlAssister(long ttl) {
			this.ttl=ttl;
		}
		
		@Override
		public boolean isTimeOut(K key, V value, long addTimeMills, long lastActiveTimeMills) {
			long now=System.currentTimeMillis();
			if(ttl>0)
			{
				return now>=lastActiveTimeMills+ttl;
			}else
			{//永不过期
				return false;
			}
		}
	}
	
	/**
	 * 超时助理
	 * @author Administrator
	 * @param <K>
	 * @param <V>
	 */
	@FunctionalInterface
	public static interface TimeOutAssister<K,V>{
		/**
		 * 是否超时
		 * @param key 
		 * @param value
		 * @param addTimeMills 添加时间戳
		 * @param lastActiveTimeMills 上次访问时间
		 */
		public boolean isTimeOut(K key,V value,long addTimeMills,long lastActiveTimeMills);
	}
	
	/**
	 * 移除助理
	 * @author Administrator
	 * @param <K>
	 * @param <V>
	 */
	@FunctionalInterface
	public static interface RemoveAssister<K,V>{
		
		/**
		 * 移除后调用
		 * @param key 
		 * @param value
		 * @param timeOutRemove 是否是超时移除
		 */
		public void removed(K key,V value,boolean timeOutRemove);
	}
}
