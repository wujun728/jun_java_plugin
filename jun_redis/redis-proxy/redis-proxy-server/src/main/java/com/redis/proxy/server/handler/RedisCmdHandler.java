/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.server.handler;

import com.google.common.collect.Maps;
import com.redis.proxy.common.consts.CacheConsts;
import com.redis.proxy.server.mgr.JedisManager;
import com.redis.proxy.net.exceptions.RedisException;
import com.redis.proxy.net.handler.RdsMsgHandler;
import com.redis.proxy.net.resps.BulkRdsResp;
import com.redis.proxy.net.resps.ErrorRdsResp;
import com.redis.proxy.net.resps.IntegerRdsResp;
import com.redis.proxy.net.resps.MultiBulkRdsResp;
import com.redis.proxy.net.resps.RdsResp;
import com.redis.proxy.net.resps.StatusRdsResp;
import com.talk51.redis.proxy.jedis.pool.ProxyShardedJedisPool;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryJedisCluster;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * redis命令实现类
 *
 * @author zhanggaofeng
 */
@Service
public class RedisCmdHandler extends RdsMsgHandler {

        @Autowired
        private JedisManager jedisMgr;

        public RedisCmdHandler() {
                super(CacheConsts.SERVER_PORT, CacheConsts.SERVER_MIN_THREADS, CacheConsts.SERVER_MAX_THREADS);
        }

        @Override
        public RdsResp get(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                byte[] result = null;
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                result = jedis.get(getBytes(key));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        result = cluster.get(getBytes(key));
                }
                if (result == null) {
                        return BulkRdsResp.NIL_REPLY;
                } else {
                        return new BulkRdsResp(result);
                }
        }

        @Override
        public RdsResp set(String key, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new StatusRdsResp(jedis.set(getBytes(key), value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new StatusRdsResp(cluster.set(getBytes(key), value));
                }
        }

        @Override
        public RdsResp setEx(String key, int second, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new StatusRdsResp(jedis.setex(getBytes(key), second, value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new StatusRdsResp(cluster.setex(getBytes(key), second, value));
                }
        }

        @Override
        public RdsResp setNx(String key, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.setnx(getBytes(key), value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.setnx(getBytes(key), value));
                }
        }

        @Override
        public RdsResp getSet(String key, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                byte[] result = null;
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                result = jedis.getSet(getBytes(key), value);
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        result = cluster.getSet(getBytes(key), value);
                }
                if (result == null) {
                        return BulkRdsResp.NIL_REPLY;
                } else {
                        return new BulkRdsResp(result);
                }
        }

        @Override
        public RdsResp del(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.del(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.del(getBytes(key)));
                }
        }

        @Override
        public RdsResp exists(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.exists(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.exists(getBytes(key)));
                }
        }

        @Override
        public RdsResp expire(String key, int value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.expire(getBytes(key), value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.expire(getBytes(key), value));
                }
        }

        @Override
        public RdsResp expireAt(String key, int value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.expireAt(getBytes(key), value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.expireAt(getBytes(key), value));
                }
        }

        @Override
        public RdsResp ttl(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.ttl(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.ttl(getBytes(key)));
                }
        }

        @Override
        public RdsResp decr(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.decr(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.decr(getBytes(key)));
                }
        }

        @Override
        public RdsResp decrBy(String key, long value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.decrBy(key, value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.decrBy(getBytes(key), value));
                }
        }

        @Override
        public RdsResp incr(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.incr(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.incr(getBytes(key)));
                }
        }

        @Override
        public RdsResp incrBy(String key, long value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.incrBy(key, value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.incrBy(getBytes(key), value));
                }
        }

        @Override
        public RdsResp hSet(String key, byte[] field, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.hset(getBytes(key), field, value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.hset(getBytes(key), field, value));
                }
        }

        @Override
        public RdsResp hMSet(String key, byte[][] value) throws RedisException {
                Map<byte[], byte[]> map = Maps.newHashMap();
                for (int i = 0; i < value.length;) {
                        map.put(value[i++], value[i++]);
                }
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new StatusRdsResp(jedis.hmset(getBytes(key), map));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new StatusRdsResp(cluster.hmset(getBytes(key), map));
                }
        }

        @Override
        public RdsResp hGet(String key, byte[] field) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new BulkRdsResp(jedis.hget(getBytes(key), field));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new BulkRdsResp(cluster.hget(getBytes(key), field));
                }
        }

        @Override
        public RdsResp hMGet(String key, byte[][] field) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new MultiBulkRdsResp(jedis.hmget(getBytes(key), field));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new MultiBulkRdsResp(cluster.hmget(getBytes(key), field));
                }
        }

        @Override
        public RdsResp hGetAll(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                Map<byte[], byte[]> map = null;
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                map = jedis.hgetAll(getBytes(key));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        map = cluster.hgetAll(getBytes(key));
                }
                if (map == null || map.isEmpty()) {
                        return MultiBulkRdsResp.EMPTY;
                }
                return new MultiBulkRdsResp(map);
        }

        @Override
        public RdsResp hDel(String key, byte[][] field) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.hdel(getBytes(key), field));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.hdel(getBytes(key), field));
                }
        }

        @Override
        public RdsResp hExists(String key, byte[] field) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.hexists(getBytes(key), field));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.hexists(getBytes(key), field));
                }
        }

        @Override
        public RdsResp hIncrBy(String key, byte[] field, long value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.hincrBy(getBytes(key), field, value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.hincrBy(getBytes(key), field, value));
                }
        }

        @Override
        public RdsResp hKeys(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new MultiBulkRdsResp(jedis.hkeys(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new MultiBulkRdsResp(cluster.hkeys(getBytes(key)));
                }
        }

        @Override
        public RdsResp hLen(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.hlen(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.hlen(getBytes(key)));
                }
        }

        @Override
        public RdsResp hSetNx(String key, byte[] field, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.hsetnx(getBytes(key), field, value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.hsetnx(getBytes(key), field, value));
                }
        }

        @Override
        public RdsResp hVals(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                Collection<byte[]> coll = null;
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                coll = jedis.hvals(getBytes(key));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        coll = cluster.hvals(getBytes(key));
                }
                if (coll == null || coll.isEmpty()) {
                        return MultiBulkRdsResp.EMPTY;
                }
                return new MultiBulkRdsResp(coll);
        }

        @Override
        public RdsResp lPush(String key, byte[][] vlaue) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.lpush(getBytes(key), vlaue));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.lpush(getBytes(key), vlaue));
                }
        }

        @Override
        public RdsResp rPush(String key, byte[][] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.rpush(getBytes(key), value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.rpush(getBytes(key), value));
                }
        }

        @Override
        public RdsResp lPop(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new BulkRdsResp(jedis.lpop(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new BulkRdsResp(cluster.lpop(getBytes(key)));
                }
        }

        @Override
        public RdsResp rPop(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new BulkRdsResp(jedis.rpop(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new BulkRdsResp(cluster.rpop(getBytes(key)));
                }
        }

        @Override
        public RdsResp bLPop(String key, int timeout) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new MultiBulkRdsResp(jedis.blpop(timeout, key));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new MultiBulkRdsResp(cluster.blpop(timeout, getBytes(key)));
                }
        }

        @Override
        public RdsResp bRPop(String key, int timeout) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        Jedis jedis = pool.getJedis(shard, key);
                        if (jedis == null) {
                                return ErrorRdsResp.SHARD_DISABLE_RESP;
                        }
                        try {
                                return new MultiBulkRdsResp(jedis.brpop(timeout, key));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new MultiBulkRdsResp(cluster.brpop(timeout, getBytes(key)));
                }
        }

        @Override
        public RdsResp lLen(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.llen(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.llen(getBytes(key)));
                }
        }

        @Override
        public RdsResp lRange(String key, long start, long end) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new MultiBulkRdsResp(jedis.lrange(getBytes(key), start, end));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new MultiBulkRdsResp(cluster.lrange(getBytes(key), start, end));
                }
        }

        @Override
        public RdsResp lRem(String key, long count, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.lrem(getBytes(key), count, value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.lrem(getBytes(key), count, value));
                }
        }

        @Override
        public RdsResp lSet(String key, long index, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new StatusRdsResp(jedis.lset(getBytes(key), index, value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new StatusRdsResp(cluster.lset(getBytes(key), index, value));
                }
        }

        @Override
        public RdsResp lTrim(String key, long start, long end) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        Jedis jedis = pool.getJedis(shard, key);
                        try {
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new StatusRdsResp(jedis.ltrim(getBytes(key), start, end));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new StatusRdsResp(cluster.ltrim(getBytes(key), start, end));
                }
        }

        @Override
        public RdsResp sAdd(String key, byte[][] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.sadd(getBytes(key), value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.sadd(getBytes(key), value));
                }
        }

        @Override
        public RdsResp sCard(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.scard(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.scard(getBytes(key)));
                }
        }

        @Override
        public RdsResp sisMember(String key, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.sismember(getBytes(key), value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.sismember(getBytes(key), value));
                }
        }

        @Override
        public RdsResp sMembers(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new MultiBulkRdsResp(jedis.smembers(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new MultiBulkRdsResp(cluster.smembers(getBytes(key)));
                }
        }

        @Override
        public RdsResp sPop(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new BulkRdsResp(jedis.spop(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new BulkRdsResp(cluster.spop(getBytes(key)));
                }
        }

        @Override
        public RdsResp sRem(String key, byte[][] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.srem(getBytes(key), value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.srem(getBytes(key), value));
                }
        }

        @Override
        public RdsResp zAdd(String key, byte[][] value) throws RedisException {
                if (value.length == 0 || !(value.length % 2 == 0)) {
                        return new ErrorRdsResp("wrong number of args for 'zdd' command");
                }
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                if (value.length == 2) {
                                        return new IntegerRdsResp(jedis.zadd(getBytes(key), Double.parseDouble(getString(value[0])), value[1]));
                                } else {
                                        Map<byte[], Double> map = Maps.newHashMap();
                                        for (int i = 0; i < value.length;) {
                                                map.put(value[i++], Double.parseDouble(getString(value[i++])));
                                        }
                                        return new IntegerRdsResp(jedis.zadd(getBytes(key), map));
                                }
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        if (value.length == 2) {
                                return new IntegerRdsResp(cluster.zadd(getBytes(key), Double.parseDouble(getString(value[0])), value[1]));
                        } else {
                                Map<byte[], Double> map = Maps.newHashMap();
                                for (int i = 0; i < value.length;) {
                                        map.put(value[i++], Double.parseDouble(getString(value[i++])));
                                }
                                return new IntegerRdsResp(cluster.zadd(getBytes(key), map));
                        }
                }
        }

        @Override
        public RdsResp zCard(String key) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        if (pool == null) {
                                return ErrorRdsResp.NO_TARGETS_RESP;
                        }
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.zcard(getBytes(key)));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.zcard(getBytes(key)));
                }
        }

        @Override
        public RdsResp zCount(String key, byte[] min, byte[] max) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.zcount(getBytes(key), min, max));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.zcount(getBytes(key), min, max));
                }
        }

        @Override
        public RdsResp zIncrBy(String key, double score, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new BulkRdsResp(jedis.zincrby(getBytes(key), score, value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new BulkRdsResp(cluster.zincrby(getBytes(key), score, value));
                }
        }

        @Override
        public RdsResp zRange(String key, long start, long stop) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new MultiBulkRdsResp(jedis.zrange(getBytes(key), start, stop));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new MultiBulkRdsResp(cluster.zrange(getBytes(key), start, stop));
                }
        }

        @Override
        public RdsResp zRangeByScore(String key, byte[][] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                if (value.length == 2) {
                                        return new MultiBulkRdsResp(jedis.zrangeByScore(getBytes(key), value[0], value[1]));
                                } else if (value.length == 5) {
                                        return new MultiBulkRdsResp(jedis.zrangeByScore(getBytes(key), value[0], value[1], Integer.parseInt(getString(value[3])), Integer.parseInt(getString(value[4]))));
                                } else {
                                        return new ErrorRdsResp("wrong number of args for 'zrangebyscore' command");
                                }
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        if (value.length == 2) {
                                return new MultiBulkRdsResp(cluster.zrangeByScore(getBytes(key), value[0], value[1]));
                        } else if (value.length == 5) {
                                return new MultiBulkRdsResp(cluster.zrangeByScore(getBytes(key), value[0], value[1], Integer.parseInt(getString(value[3])), Integer.parseInt(getString(value[4]))));
                        } else {
                                return new ErrorRdsResp("wrong number of args for 'zrangebyscore' command");
                        }
                }
        }

        @Override
        public RdsResp zRank(String key, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.zrank(getBytes(key), value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.zrank(getBytes(key), value));
                }
        }

        @Override
        public RdsResp zRem(String key, byte[][] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.zrem(getBytes(key), value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.zrem(getBytes(key), value));
                }
        }

        @Override
        public RdsResp zRemRangeByRank(String key, long start, long stop) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.zremrangeByRank(getBytes(key), start, stop));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.zremrangeByRank(getBytes(key), start, stop));
                }
        }

        @Override
        public RdsResp zRemRangeByScore(String key, byte[] min, byte[] max) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.zremrangeByScore(getBytes(key), min, max));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.zremrangeByScore(getBytes(key), min, max));
                }
        }

        @Override
        public RdsResp zRevRange(String key, long start, long stop) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new MultiBulkRdsResp(jedis.zrevrange(getBytes(key), start, stop));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new MultiBulkRdsResp(cluster.zrevrange(getBytes(key), start, stop));
                }
        }

        @Override
        public RdsResp zRevRangeByScore(String key, byte[][] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                if (value.length == 2) {
                                        return new MultiBulkRdsResp(jedis.zrevrangeByScore(getBytes(key), value[0], value[1]));
                                } else if (value.length == 5) {
                                        return new MultiBulkRdsResp(jedis.zrevrangeByScore(getBytes(key), value[0], value[1], Integer.parseInt(getString(value[3])), Integer.parseInt(getString(value[4]))));
                                } else {
                                        return new ErrorRdsResp("wrong number of args for 'zrangebyscore' command");
                                }
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        if (value.length == 2) {
                                return new MultiBulkRdsResp(cluster.zrevrangeByScore(getBytes(key), value[0], value[1]));
                        } else if (value.length == 5) {
                                return new MultiBulkRdsResp(cluster.zrevrangeByScore(getBytes(key), value[0], value[1], Integer.parseInt(getString(value[3])), Integer.parseInt(getString(value[4]))));
                        } else {
                                return new ErrorRdsResp("wrong number of args for 'zrangebyscore' command");
                        }
                }
        }

        @Override
        public RdsResp zRevRank(String key, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new IntegerRdsResp(jedis.zrevrank(getBytes(key), value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new IntegerRdsResp(cluster.zrevrank(getBytes(key), value));
                }
        }

        @Override
        public RdsResp zScore(String key, byte[] value) throws RedisException {
                Object object = jedisMgr.getObject(key);
                if (object == null) {
                        return ErrorRdsResp.NO_TARGETS_RESP;
                }
                if (object instanceof ProxyShardedJedisPool) {
                        ProxyShardedJedisPool pool = (ProxyShardedJedisPool) object;
                        ShardedJedis shard = pool.getResource();
                        try {
                                Jedis jedis = pool.getJedis(shard, key);
                                if (jedis == null) {
                                        return ErrorRdsResp.SHARD_DISABLE_RESP;
                                }
                                return new BulkRdsResp(jedis.zscore(getBytes(key), value));
                        } finally {
                                pool.returnResource(shard);
                        }
                } else {
                        BinaryJedisCluster cluster = (BinaryJedisCluster) object;
                        return new BulkRdsResp(cluster.zscore(getBytes(key), value));
                }
        }
}
