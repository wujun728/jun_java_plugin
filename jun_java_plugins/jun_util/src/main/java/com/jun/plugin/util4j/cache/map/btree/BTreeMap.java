package com.jun.plugin.util4j.cache.map.btree;

import java.util.AbstractMap;
import java.util.Set;

/**
 * 优化节点非必要属性的内存占用
 * @author juebanlin
 */
public class BTreeMap<K,V> extends AbstractMap<K, V> implements BitTreeMap<K,V>{

	private final BTree<V> tree=new BTree<>();
	
	static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
	@Override
	public V write(int key, V value) {
		return tree.write(key, value);
	}

	@Override
	public V read(int key) {
		return tree.read(key);
	}
	
	@Override
	public void forEach(BitConsumer<V> consumer) {
		tree.forEach(consumer);
	}

	@Override
	public V put(K key, V value) {
		return write(key, value);
	}
	
	@Override
	public V get(Object key) {
		return readBy(key);
	}
	
	@Override
	public V read(K key) {
		return readBy(key);
	}

	@Override
	public void clear() {
		tree.clear();
	}
	
	@Override
	public int size() {
		return tree.size();
	}
	
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public V write(K key, V value) {
		return write(hash(key), value);
	}

	@Override
	public V readBy(Object key) {
		return read(hash(key));
	}
}
