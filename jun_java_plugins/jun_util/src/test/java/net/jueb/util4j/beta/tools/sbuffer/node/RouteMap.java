package net.jueb.util4j.beta.tools.sbuffer.node;

public interface RouteMap<K,V> {

	/**
	 * 根据key放入value,返回原来的value
	 * @param key
	 * @param value
	 * @return
	 */
	public V put(K key,V value);
	
	public V get(K key);
}
