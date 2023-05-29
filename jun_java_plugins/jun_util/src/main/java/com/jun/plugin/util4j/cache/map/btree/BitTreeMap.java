package com.jun.plugin.util4j.cache.map.btree;

public interface BitTreeMap<K,V> extends BitTree<V>{

	/**
	 * 保存数据
	 * @param key
	 * @param value
	 * @return
	 */
	public V write(K key,V value);
	
	/**
	 * 读取数据
	 * @param key
	 * @return
	 */
	public V read(K key);
	
	/**
	 * 读取数据
	 * @param key
	 * @return
	 */
	public V readBy(Object key);
}
