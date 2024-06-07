package com.jun.plugin.compile.execute;



/**
 * Utility class for small LRU caches.
 *
 */
public abstract class LRUCache<N, V> {

	private final int size;
	private V[] oa = null;

	public LRUCache(int size) {
		this.size = size;
	}

	public static void moveToFront(Object[] oa, int i) {
		Object ob = oa[i];
		for (int j = i; j > 0; j--)
			oa[j] = oa[j - 1];
		oa[0] = ob;
	}

	abstract protected V create(N name);

	abstract protected boolean hasName(V ob, N name);

	public V forName(N name) {
		if (oa == null) {
			oa = (V[]) new Object[size];
		} else {
			for (int i = 0; i < oa.length; i++) {
				V ob = oa[i];
				if (ob == null)
					continue;
				if (hasName(ob, name)) {
					if (i > 0)
						moveToFront(oa, i);
					return ob;
				}
			}
		}

		// Create a new object
		V ob = create(name);
		oa[oa.length - 1] = ob;
		moveToFront(oa, oa.length - 1);
		return ob;
	}

}