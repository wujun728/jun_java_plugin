package com.kind.redis.callback;

import redis.clients.jedis.ShardedJedis;

public abstract class RedisNoResultCall implements RedisCallback<Object> {

    public Object doInRedis(ShardedJedis shardedJedis) throws Exception {
        call(shardedJedis);
        return null;
    }

    public abstract void call(ShardedJedis shardedJedis);

}
