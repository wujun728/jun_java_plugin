package cn.springmvc.jpa.common.memcached;

import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;

import org.springframework.stereotype.Component;

/**
 * @author Wujun
 *
 */
@Component
public class MemcachedFactory {

    // @Resource(name = "memcachedClient")
    private MemcachedClient cachedClient;

    private static final int expire = 3600;

    public Future<Boolean> set(String key, Object value) {
        return cachedClient.set(key, expire, value);
    }

    public Future<Boolean> set(String key, int expire, Object value) {
        return cachedClient.set(key, expire, value);
    }

    public Future<Boolean> replace(String key, Object value) {
        return cachedClient.replace(key, expire, value);
    }

    public Future<Boolean> replace(String key, int expire, Object value) {
        return cachedClient.replace(key, expire, value);
    }

    public Object get(String key) {
        return cachedClient.get(key);
    }

}