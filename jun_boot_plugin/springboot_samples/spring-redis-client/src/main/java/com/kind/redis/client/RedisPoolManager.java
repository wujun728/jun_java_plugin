package com.kind.redis.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisPoolManager {

    private ShardedJedisPool shardedJedisPool;

    public RedisPoolManager(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    public ShardedJedis getReids() {
        return shardedJedisPool.getResource();
    }

    public ShardedJedisPool getPool() {
        return this.shardedJedisPool;
    }

    public void close(ShardedJedis shardedJedis) {
        if (shardedJedis != null) {
            shardedJedis.disconnect();
        }
    }

    public Jedis getJedis(byte[] key) {
        return getReids().getShard(key);
    }

    /**
     * <pre>
     * 收回资源
     * </pre>
     *
     * @param shardedJedis
     * @param recovery
     */
    public void recovery(ShardedJedis shardedJedis, boolean recovery) {
        if (recovery) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        } else {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

    /**
     * 创建一个分布式ID
     *
     * @param shardedJedis
     * @return
     */
    public boolean ping(ShardedJedis shardedJedis) {
        shardedJedis.pipelined();
        return false;
    }

}
