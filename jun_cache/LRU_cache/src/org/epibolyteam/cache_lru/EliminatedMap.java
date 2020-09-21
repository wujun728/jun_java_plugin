package org.epibolyteam.cache_lru;

import java.util.LinkedHashMap;
import java.util.Map;

public class EliminatedMap<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 7435394934719454002L;

	private final int maxCapacity;

	/**
	 * @param maxCapacity
	 *            max size of this map
	 * @param eliminateOrder
	 *            true for LRU, false for FIFO
	 */
	public EliminatedMap(int maxCapacity, boolean eliminateOrder) {
		this(maxCapacity, eliminateOrder, true);
	}

	public EliminatedMap(int maxCapacity, boolean eliminateOrder, boolean fixedCapacity) {
		super(fixedCapacity ? maxCapacity : 16, fixedCapacity ? 1.01f : 0.75f, eliminateOrder);
		this.maxCapacity = maxCapacity;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > maxCapacity;
	}

}
