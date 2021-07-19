package com.kind.redis.callback;

import redis.clients.jedis.ShardedJedis;

public interface RedisCallback<T> {

    T doInRedis(ShardedJedis shardedJedis) throws Exception;
}
