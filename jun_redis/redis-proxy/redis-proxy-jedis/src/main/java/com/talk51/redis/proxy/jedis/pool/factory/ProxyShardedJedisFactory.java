/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talk51.redis.proxy.jedis.pool.factory;

import com.talk51.redis.proxy.jedis.pool.alarm.RedisAlarm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Hashing;

/**
 *
 * @author Wujun
 */
public class ProxyShardedJedisFactory implements PooledObjectFactory<ShardedJedis> {

        private final List<JedisShardInfo> shards;
        private final Hashing algo;
        private final RedisAlarm alarm;
        private final Pattern keyTagPattern;
        private final ReentrantReadWriteLock disableLock = new ReentrantReadWriteLock();
        /**
         * 不可用节点存储
         */
        private final Map<ShardedJedis, Map<Jedis, Boolean>> disableMap = new HashMap<>();

        /**
         *
         * @param shards 集群redis信息
         * @param algo 集群hash
         * @param alarm 报警
         * @param keyTagPattern
         */
        public ProxyShardedJedisFactory(List<JedisShardInfo> shards, Hashing algo, RedisAlarm alarm, Pattern keyTagPattern) {
                this.shards = shards;
                this.algo = algo;
                this.alarm = alarm;
                this.keyTagPattern = keyTagPattern;
        }

        /**
         * 是否包含jedis分片
         *
         * @param shard
         * @param jedis
         * @return
         */
        public boolean containJedis(ShardedJedis shard, Jedis jedis) {
                disableLock.readLock().lock();
                try {
                        if (!disableMap.containsKey(shard)) {
                                return false;
                        }
                        if (!disableMap.get(shard).containsKey(jedis)) {
                                return false;
                        }
                        return true;
                } finally {
                        disableLock.readLock().unlock();
                }
        }

        /**
         * 增加不可用的redis分片
         *
         * @param shard
         * @param jedis
         * @param isAlarm 是否需要报警
         */
        public void addDisableJedis(ShardedJedis shard, Jedis jedis, boolean isAlarm) {
                disableLock.writeLock().lock();
                try {
                        Map<Jedis, Boolean> jedisMap = disableMap.get(shard);
                        if (jedisMap == null) {
                                jedisMap = new HashMap<>();
                                disableMap.put(shard, jedisMap);
                        }
                        jedisMap.put(jedis, Boolean.TRUE);
                        if (isAlarm && alarm != null) {
                                alarm.disconnectAlarm(jedis);
                        }
                } finally {
                        disableLock.writeLock().unlock();
                }
        }

        /**
         * 检测尝试连接不可用的redis分片
         *
         */
        public void reConnectDisableJedis() {
                disableLock.readLock().lock();
                try {
                        for (Map<Jedis, Boolean> jedisMap : disableMap.values()) {
                                for (Jedis jedis : jedisMap.keySet()) {
                                        try {
                                                jedis.disconnect();
                                        } catch (Exception e) {
                                        }
                                        try {
                                                jedis.connect();
                                        } catch (Exception e) {
                                        }
                                }
                        }
                } finally {
                        disableLock.readLock().unlock();
                }
        }

        /**
         * 移除不可用的redis分片
         *
         * @param shard
         * @param jedis
         * @param isAlarm 是否需要报警
         */
        public void removeDisableJedis(ShardedJedis shard, Jedis jedis, boolean isAlarm) {

                disableLock.writeLock().lock();
                try {
                        Map<Jedis, Boolean> jedisMap = disableMap.get(shard);
                        if (jedisMap == null) {
                                return;
                        }
                        jedisMap.remove(jedis);
                        if (jedisMap.isEmpty()) {
                                disableMap.remove(shard);
                        }
                        if (isAlarm && alarm != null) {
                                alarm.reconnectAlarm(jedis);
                        }
                } finally {
                        disableLock.writeLock().unlock();
                }
        }

        @Override
        public PooledObject<ShardedJedis> makeObject() throws Exception {
                ShardedJedis shard = new ShardedJedis(shards, algo, keyTagPattern);
                return new DefaultPooledObject<>(shard);
        }

        @Override
        public void destroyObject(PooledObject<ShardedJedis> pooledShardedJedis) throws Exception {
                final ShardedJedis shard = pooledShardedJedis.getObject();
                for (Jedis jedis : shard.getAllShards()) {
                        removeDisableJedis(shard, jedis, false);
                        try {
                                try {
                                        jedis.quit();
                                } catch (Exception e) {
                                }
                                jedis.disconnect();
                        } catch (Exception e) {
                        }
                }
        }

        @Override
        public boolean validateObject(PooledObject<ShardedJedis> pooledShardedJedis) {

                ShardedJedis shard = pooledShardedJedis.getObject();
                for (Jedis jedis : shard.getAllShards()) {
                        boolean isEnable = true;
                        try {
                                if (!jedis.ping().equals("PONG")) {
                                        isEnable = false;
                                }
                        } catch (Exception e) {
                                isEnable = false;
                        }
                        /**
                         * 当前节点可用
                         */
                        if (isEnable) {
                                if (containJedis(shard, jedis)) {
                                        removeDisableJedis(shard, jedis, true);
                                }
                        } else {
                                if (!containJedis(shard, jedis)) {
                                        addDisableJedis(shard, jedis, true);
                                }
                        }
                }
                reConnectDisableJedis();
                return true;
        }

        @Override
        public void activateObject(PooledObject<ShardedJedis> p) throws Exception {

        }

        @Override
        public void passivateObject(PooledObject<ShardedJedis> p) throws Exception {

        }
}
