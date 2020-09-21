package org.epibolyteam.cache_lru;

public class SynchronizedLRUMap<K, V> extends EliminatedMap<K, V> {

	private static final long serialVersionUID = -5532993834851884317L;

	public SynchronizedLRUMap(int maxCapacity) {
		this(maxCapacity, true);
	}

	public SynchronizedLRUMap(int maxCapacity, boolean fixedCapacity) {
		super(maxCapacity, true, fixedCapacity);
	}

	@SuppressWarnings("unchecked")
	static final <K, V> SynchronizedLRUMap<K, V>[] newArray(int sz) {
		return new SynchronizedLRUMap[sz];
	}

	public synchronized V get(Object key) {
		return super.get(key);
	}

	public synchronized V put(K key, V value) {
		return super.put(key, value);
	}

	public synchronized V remove(Object key) {
		return super.remove(key);
	}

	@Override
	public synchronized boolean containsKey(Object key) {
		return super.containsKey(key);
	}
}
