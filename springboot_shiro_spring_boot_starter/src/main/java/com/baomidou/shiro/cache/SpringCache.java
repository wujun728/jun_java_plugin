package com.baomidou.shiro.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.cache.Cache.ValueWrapper;

/**
 * springCache对原生shiroCache的包装
 *
 * @author Wujun
 */
@SuppressWarnings("unchecked")
public class SpringCache<K, V> implements Cache<K, V> {

  private final org.springframework.cache.Cache cache;

  SpringCache(org.springframework.cache.Cache cache) {
    if (cache == null) {
      throw new IllegalArgumentException("Cache argument cannot be null.");
    }
    this.cache = cache;
  }

  @Override
  public V get(K key) throws CacheException {
    if (key == null) {
      return null;
    } else {
      ValueWrapper valueWrapper = cache.get(key);
      if (valueWrapper == null) {
        return null;
      } else {
        return (V) valueWrapper.get();
      }
    }
  }

  @Override
  public V put(K key, V value) throws CacheException {
    try {
      ValueWrapper previous = cache.putIfAbsent(key, value);
      return previous == null ? null : (V) previous.get();
    } catch (Throwable t) {
      throw new CacheException(t);
    }
  }

  @Override
  public V remove(K key) throws CacheException {
    V previous = get(key);
    cache.evict(key);
    return previous;
  }

  @Override
  public void clear() throws CacheException {
    cache.clear();
  }

  @Override
  public int size() {
    Object nativeCache = cache.getNativeCache();
    if (nativeCache instanceof ConcurrentMap) {
      return ((ConcurrentMap) (nativeCache)).size();
    }
    if (nativeCache instanceof net.sf.ehcache.Cache) {
      return ((net.sf.ehcache.Cache) (nativeCache)).getSize();
    }
    throw new CacheException("Spring Native Cache Not Realize，Native Cache [" + nativeCache + "]");
  }

  @Override
  public Set<K> keys() {
    Object nativeCache = cache.getNativeCache();
    if (nativeCache instanceof ConcurrentMap) {
      return ((ConcurrentMap) (nativeCache)).keySet();
    }
    if (nativeCache instanceof net.sf.ehcache.Cache) {
      List<K> list = ((net.sf.ehcache.Cache) (nativeCache)).getKeys();
      return new HashSet<>(list);
    }
    throw new CacheException("Spring Native Cache Not Realize，Native Cache [" + nativeCache + "]");
  }

  @Override
  public Collection<V> values() {
    Object nativeCache = cache.getNativeCache();
    if (nativeCache instanceof ConcurrentMap) {
      return ((ConcurrentMap) (nativeCache)).values();
    }
    if (nativeCache instanceof net.sf.ehcache.Cache) {
      List<K> keys = ((net.sf.ehcache.Cache) (nativeCache)).getKeys();
      List<V> result = new ArrayList<>(keys.size());
      for (K key : keys) {
        result.add(get(key));
      }
      return result;
    }
    throw new CacheException("Spring Native Cache Not Realize，Native Cache [" + nativeCache + "]");
  }

}
