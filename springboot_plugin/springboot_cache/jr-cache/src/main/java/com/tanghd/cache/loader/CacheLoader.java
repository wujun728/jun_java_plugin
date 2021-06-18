package com.tanghd.cache.loader;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tanghd.cache.builder.CacheBuilder;

public interface CacheLoader {
    <T> T get(String key, Class<T> clz, CacheBuilder<T> builder) throws Exception;

    <T> List<T> getList(String key, Class<T> clz, CacheBuilder<List<T>> builder) throws Exception;

    <T> Set<T> getSet(String key, Class<T> clz, CacheBuilder<Set<T>> builder) throws Exception;

    <K, V> Map<K, V> getMap(String key, Class<K> keyClz, Class<V> valueClz, CacheBuilder<Map<K, V>> builder)
            throws Exception;

    <T> void refresh(final String key, final CacheBuilder<T> builder) throws Exception;

    void delete(String key, CacheBuilder<?> builder) throws Exception;
}
