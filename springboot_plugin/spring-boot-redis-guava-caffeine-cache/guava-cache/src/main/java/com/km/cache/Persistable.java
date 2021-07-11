package com.km.cache;

/**
 * <p>可持久化的</p>
 * Created by zhezhiyong@163.com on 2017/9/25.
 */
public interface Persistable<K, V> {
    V load(K k) throws Exception;
}
