package com.tanghd.cache.serialize;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Serializer {
    byte[] serialize(Object o) throws Exception;

    String serializeToString(Object o) throws Exception;

    <T> T deserialize(byte[] buf, Class<T> clz) throws Exception;

    <T> List<T> deserializeList(byte[] buf, Class<T> clz) throws Exception;

    <T> Set<T> deserializeSet(byte[] buf, Class<T> clz) throws Exception;

    <K, V> Map<K, V> deserializeMap(byte[] buf, Class<K> keyClz, Class<V> valueClz) throws Exception;

    <T> T deserialize(String content, Class<T> clz) throws Exception;

    <T> List<T> deserializeList(String content, Class<T> clz) throws Exception;

    <T> Set<T> deserializeSet(String content, Class<T> clz) throws Exception;

    <K, V> Map<K, V> deserializeMap(String content, Class<K> keyClz, Class<V> valueClz) throws Exception;

}
