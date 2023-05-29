package com.jun.plugin.util4j.cache.map;
import java.util.ArrayList;  
import java.util.Collection;  
import java.util.LinkedHashMap;  
import java.util.concurrent.locks.Lock;  
import java.util.concurrent.locks.ReentrantLock;  
import java.util.Map;  
  
  
/** 
 * 类说明：利用LinkedHashMap实现简单的缓存， 必须实现removeEldestEntry方法，具体参见JDK文档 
 * @param <K> 
 * @param <V> 
 */  
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {  
    /**
	 * 
	 */
	private static final long serialVersionUID = -764563178543166915L;

	static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
	private int initialCapacity = 1 << 30;  
  
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;  
  
    private final Lock lock = new ReentrantLock();  

    public LRULinkedHashMap() {  
        this(DEFAULT_INITIAL_CAPACITY);  
    }  
    
	public LRULinkedHashMap(int initialCapacity) {  
        super(initialCapacity, DEFAULT_LOAD_FACTOR, true);  
        this.initialCapacity = initialCapacity;  
    }  
  
    @Override  
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {  
        return size() > initialCapacity;  
    }  
    @Override  
    public boolean containsKey(Object key) {  
        try {  
            lock.lock();  
            return super.containsKey(key);  
        } finally {  
            lock.unlock();  
        }  
    }  
  
      
    @Override  
    public V get(Object key) {  
        try {  
            lock.lock();  
            return super.get(key);  
        } finally {  
            lock.unlock();  
        }  
    }  
  
    @Override  
    public V put(K key, V value) {  
        try {  
            lock.lock();  
            return super.put(key, value);  
        } finally {  
            lock.unlock();  
        }  
    }  
  
    public int size() {  
        try {  
            lock.lock();  
            return super.size();  
        } finally {  
            lock.unlock();  
        }  
    }  
  
    public void clear() {  
        try {  
            lock.lock();  
            super.clear();  
        } finally {  
            lock.unlock();  
        }  
    }  
  
    public Collection<Map.Entry<K, V>> getAll() {  
        try {  
            lock.lock();  
            return new ArrayList<Map.Entry<K, V>>(super.entrySet());  
        } finally {  
            lock.unlock();  
        }  
    }  
}  