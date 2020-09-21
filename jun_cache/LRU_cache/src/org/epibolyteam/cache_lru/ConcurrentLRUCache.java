package org.epibolyteam.cache_lru;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 并发的LRU缓存，二维数组存储。
 * 	<li>采用hash算法先将key分组，然后存放于对应的{@link ConcurrentHashMap}中，减少key值冲突带来的查询缓慢。</li>
 * 
 * 	<li>{@link ConcurrentHashMap} 中设置accessOrder为true</li>
 * 
 * 	<li>accessOrder the ordering mode - true for access-order, false for insertion-order</li>
 * @author DarkIdiot
 *
 * @param <K>
 * @param <V>
 */
public class ConcurrentLRUCache<K, V> {

	protected final SynchronizedLRUMap<K, V>[] table;

	public ConcurrentLRUCache(int row, int col) {
		this(row, col, true);
	}

	public ConcurrentLRUCache(int row, int col, boolean fixedCapacity) {
		table = SynchronizedLRUMap.newArray(row);
		for (int i = 0; i < table.length; i++) {
			table[i] = new SynchronizedLRUMap<K, V>(col, fixedCapacity);
		}
	}

	public V get(K key) {
		return table[hash(key)].get(key);
	}

	public V put(K key, V value) {
		return table[hash(key)].put(key, value);
	}

	public V remove(K key) {
		return table[hash(key)].remove(key);
	}

	public boolean containsKey(K key) {
		return table[hash(key)].containsKey(key);
	}

	protected int hash(K key) {
		return Math.abs(key.hashCode()) % table.length;
	}

	public SynchronizedLRUMap<K, V>[] getTable() {
		return table;
	}

	public int size() {
		int size = 0;
		for (SynchronizedLRUMap<K, V> map : table) {
			size += map.size();
		}
		return size;
	}

	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i].clear();
		}
	}
}
