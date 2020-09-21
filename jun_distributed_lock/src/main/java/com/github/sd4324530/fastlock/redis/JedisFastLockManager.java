package com.github.sd4324530.fastlock.redis;

import com.github.sd4324530.fastlock.FastLock;
import com.github.sd4324530.fastlock.FastLockManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author peiyu
 */
public class JedisFastLockManager implements FastLockManager {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JedisCluster cluster;

    private final Map<String, FastLock> lockCache = new HashMap<String, FastLock>();

    public JedisFastLockManager(Set<HostAndPort> hosts) {
        this.cluster = new JedisCluster(hosts);
    }

    public JedisFastLockManager(JedisCluster cluster) {
        this.cluster = cluster;
    }

    @Override
    public FastLock getLock(String key) {
        return getLock(key, 2000);
    }

    @Override
    public FastLock getLock(String key, long timeout) {
        FastLock lock = this.lockCache.get(key);
        if(null == lock) {
            synchronized (this) {
                lock = this.lockCache.get(key);
                if(null == lock) {
                    lock = new JedisFastLock(this.cluster, key);
                    ((JedisFastLock) lock).setTimeout(timeout);
                    this.lockCache.put(key, lock);
                }
            }
        }
        return lock;
    }
}
