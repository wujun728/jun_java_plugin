/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talk51.redis.proxy.jedis.pool;

import com.talk51.redis.proxy.jedis.pool.alarm.RedisAlarm;
import com.talk51.redis.proxy.jedis.pool.factory.ProxyShardedJedisFactory;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;

/**
 * 代理redis连接池封装 =======================================================
 * 解决：shardedJedis当一个redis分片服务不可用时导致所有redis分片服务不可用的问题
 * 效果：当一个redis分片服务不可用时，往该分片服务写和读数据失败异常，心跳服务不断尝试重新连接直到恢复
 * 缺点：服务起来时，可能造成缓存数据和数据库不一致，建议清空redis或设置短暂的过期时间
 *
 * @author zhanggaofeng
 */
public class ProxyShardedJedisPool extends Pool<ShardedJedis> {

        private ProxyShardedJedisFactory factory;

        public ProxyShardedJedisPool(GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards, RedisAlarm alarm) {
                this(poolConfig, shards, alarm, null);
        }

        public ProxyShardedJedisPool(final GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards, RedisAlarm alarm, Pattern keyTagPattern) {
                super(poolConfig, new ProxyShardedJedisFactory(shards, Hashing.MURMUR_HASH, alarm, keyTagPattern));
                this.factory = (ProxyShardedJedisFactory) this.internalPool.getFactory();
        }

        @Override
        public void returnResource(ShardedJedis shard) {
                super.returnResource(shard);
        }

        @Override
        public ShardedJedis getResource() {
                ShardedJedis jedis = super.getResource();
                jedis.setDataSource(this);
                return jedis;
        }

        /**
         * 根据key获取选择的分片并判断是否可用
         *
         * @param shard
         * @param key
         * @return
         */
        public Jedis getJedis(ShardedJedis shard, String key) {
                Jedis jedis = shard.getShard(key);
                if (factory.containJedis(shard, jedis)) {
                        return null;
                }
                return jedis;
        }

}
