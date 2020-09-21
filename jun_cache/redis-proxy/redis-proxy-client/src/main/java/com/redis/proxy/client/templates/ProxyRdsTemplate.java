/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.client.templates;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.redis.proxy.client.factory.ProxyRdsConnFactory;
import com.redis.proxy.client.keys.RdsKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * redis操作模板
 *
 * @author zhanggaofeng
 */
public class ProxyRdsTemplate extends RdsTemplate {

        private final Logger logger = LoggerFactory.getLogger(ProxyRdsTemplate.class);

        public ProxyRdsTemplate(ProxyRdsConnFactory connFactory) {
                super(connFactory);
        }

        public ProxyRdsTemplate(ProxyRdsConnFactory connFactory, boolean needCompress, int compressShold) {
                super(connFactory, needCompress, compressShold);
        }

        @Override
        public <V> V get(RdsKey key) {
                byte[] data = null;
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                try {
                        data = jedis.get(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                } finally {
                        jedis.close();
                }
                return (V) this.decoder(data, key.getClazz());
        }

        @Override
        public boolean set(RdsKey key, Object value) {
                byte[] data = this.encoder(value);
                if (data == null || data.length == 0) {
                        return false;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        boolean result = "OK".equals(jedis.set(key.getKey(), data));
                        if (result && key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public boolean setEx(RdsKey key, int second, Object value) {
                byte[] data = this.encoder(value);
                if (data == null || data.length == 0) {
                        return false;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        boolean result = "OK".equals(jedis.setex(key.getKey(), second, data));
                        if (result && key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public boolean setNx(RdsKey key, Object value) {
                byte[] data = this.encoder(value);
                if (data == null || data.length == 0) {
                        return false;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        boolean result = jedis.setnx(key.getKey(), data) == 1L;
                        if (result && key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public <V> V getSet(RdsKey key, Object value) {
                byte[] data = this.encoder(value);
                if (data == null || data.length == 0) {
                        return null;
                }
                byte[] resultArr = null;
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                try {
                        resultArr = jedis.getSet(key.getKey(), data);
                        if (resultArr != null && resultArr.length > 0 && key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                return (V) decoder(resultArr, key.getClazz());
        }

        @Override
        public boolean del(RdsKey key) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        return jedis.del(key.getKey()) > 0L;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public boolean exists(RdsKey key) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        return jedis.exists(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public boolean expire(RdsKey key, int value) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        return jedis.expire(key.getKey(), value) > 0;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public boolean expireAt(RdsKey key, long value) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        return jedis.expireAt(key.getKey(), value) > 0;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long ttl(RdsKey key) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.ttl(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long decr(RdsKey key) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        Long result = jedis.decr(key.getKey());
                        if (key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long decrBy(RdsKey key, long value) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        Long result = jedis.decrBy(key.getKey(), value);
                        if (key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long incr(RdsKey key) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        Long result = jedis.incr(key.getKey());
                        if (key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long incrBy(RdsKey key, long value) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        Long result = jedis.incrBy(key.getKey(), value);
                        if (key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        /**
         *
         * @param key
         * @param field
         * @param value
         * @return -1错误，0覆盖旧值，1新值
         */
        @Override
        public Long hSet(RdsKey key, String field, Object value) {
                byte[] data = this.encoder(value);
                if (data == null || data.length == 0) {
                        return -1L;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return -1L;
                }
                try {
                        Long result = jedis.hset(key.getKey(), getBytes(field), data);
                        if (key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return -1L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public boolean hMSet(RdsKey key, Map<String, Object> value) {
                if (value == null || value.isEmpty()) {
                        return false;
                }
                Map<byte[], byte[]> data = Maps.newHashMap();
                Set<String> fields = value.keySet();
                for (String field : fields) {
                        byte[] arr = encoder(value.get(field));
                        if (arr == null) {
                                continue;
                        }
                        data.put(getBytes(field), arr);
                }
                if (data.isEmpty()) {
                        return false;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        boolean result = "OK".equals(jedis.hmset(key.getKey(), data));
                        if (result && key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public <V> V hGet(RdsKey key, String field) {
                byte[] resultArr = null;
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                try {
                        resultArr = jedis.hget(key.getKey(), getBytes(field));
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                return (V) decoder(resultArr, key.getClazz());
        }

        @Override
        public <V> List<V> hMGet(RdsKey key, String... fields) {
                if (fields == null || fields.length == 0) {
                        return null;
                }
                byte[][] data = new byte[fields.length][];
                for (int idx = 0; idx < fields.length; idx++) {
                        data[idx] = getBytes(fields[idx]);
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                List<byte[]> resultList = null;
                try {
                        resultList = jedis.hmget(key.getKey(), data);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                if (resultList == null || resultList.isEmpty()) {
                        return null;
                }
                V v = null;
                List<V> objList = new ArrayList<>(resultList.size());
                for (byte[] ele : resultList) {
                        v = (V) decoder(ele, key.getClazz());
                        if (v == null) {
                                continue;
                        }
                        objList.add(v);
                }
                return objList;
        }

        @Override
        public <V> Map<String, V> hGetAll(RdsKey key) {
                Map<byte[], byte[]> resultMap = null;
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                try {
                        resultMap = jedis.hgetAll(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                if (resultMap == null || resultMap.isEmpty()) {
                        return null;
                }
                Map<String, V> objMap = Maps.newHashMap();
                V v = null;
                Set<byte[]> keysArr = resultMap.keySet();
                for (byte[] keyArr : keysArr) {
                        if (keyArr == null || keyArr.length == 0) {
                                continue;
                        }
                        v = (V) decoder(resultMap.get(keyArr), key.getClazz());
                        if (v == null) {
                                continue;
                        }
                        objMap.put(getString(keyArr), v);
                }
                return objMap;
        }

        @Override
        public Long hDel(RdsKey key, String... fields) {
                byte[][] fieldsArr = new byte[fields.length][];
                for (int idx = 0; idx < fields.length; idx++) {
                        fieldsArr[idx] = getBytes(fields[idx]);
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.hdel(key.getKey(), fieldsArr);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public boolean hExists(RdsKey key, String field) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        return jedis.hexists(key.getKey(), getBytes(field));
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long hIncrBy(RdsKey key, String field, long value) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        Long result = jedis.hincrBy(key.getKey(), getBytes(field), value);
                        if (key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Set<String> hKeys(RdsKey key) {
                Set<byte[]> resultSet = null;
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                try {
                        resultSet = jedis.keys(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                if (resultSet == null || resultSet.isEmpty()) {
                        return null;
                }
                Set<String> objSet = new HashSet<>(resultSet.size());
                for (byte[] resultEle : resultSet) {
                        objSet.add(getString(resultEle));
                }
                return objSet;
        }

        @Override
        public Long hLen(RdsKey key) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.hlen(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public boolean hSetNx(RdsKey key, String field, Object value) {
                byte[] data = encoder(value);
                if (data == null || data.length == 0) {
                        return false;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        boolean result = jedis.hsetnx(key.getKey(), getBytes(field), data) == 1L;
                        if (result && key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public <V> List<V> hVals(RdsKey key) {
                List<byte[]> resultList = null;
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                try {
                        resultList = jedis.hvals(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                if (resultList == null || resultList.isEmpty()) {
                        return null;
                }
                List<V> objList = Lists.newArrayList();
                V v = null;
                for (byte[] resultEle : resultList) {
                        v = (V) decoder(resultEle, key.getClazz());
                        if (v == null) {
                                continue;
                        }
                        objList.add(v);
                }
                return objList;
        }

        @Override
        public Long lPush(RdsKey key, Object... values) {
                if (values == null || values.length == 0) {
                        return 0L;
                }
                byte[][] listVals = new byte[values.length][];
                for (int idx = 0; idx < values.length; idx++) {
                        listVals[idx] = encoder(values[idx]);
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        Long result = jedis.lpush(key.getKey(), listVals);
                        if (key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long rPush(RdsKey key, Object... values) {
                if (values == null || values.length == 0) {
                        return 0L;
                }
                byte[][] listVals = new byte[values.length][];
                for (int idx = 0; idx < values.length; idx++) {
                        listVals[idx] = encoder(values[idx]);
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        Long result = jedis.rpush(key.getKey(), listVals);
                        if (key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public <V> V lPop(RdsKey key) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                byte[] resultVArr = null;
                try {
                        resultVArr = jedis.lpop(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                return (V) decoder(resultVArr, key.getClazz());
        }

        @Override
        public <V> V rPop(RdsKey key) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                byte[] resultVArr = null;
                try {
                        resultVArr = jedis.rpop(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                return (V) decoder(resultVArr, key.getClazz());
        }

        @Override
        public <V> V bLPop(RdsKey key, int timeout) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                List<byte[]> resultList = null;
                try {
                        resultList = jedis.blpop(timeout, new byte[][]{key.getKey()});
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                if (resultList == null || resultList.isEmpty()) {
                        return null;
                }
                return (V) decoder(resultList.get(0), key.getClazz());
        }

        @Override
        public <V> V bRPop(RdsKey key, int timeout) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                List<byte[]> resultList = null;
                try {
                        resultList = jedis.brpop(timeout, new byte[][]{key.getKey()});
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                if (resultList == null || resultList.isEmpty()) {
                        return null;
                }
                return (V) decoder(resultList.get(0), key.getClazz());
        }

        @Override
        public Long lLen(RdsKey key) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.llen(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public <V> List<V> lRange(RdsKey key, long start, long end) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                List<byte[]> resultList = null;
                try {
                        resultList = jedis.lrange(key.getKey(), start, end);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                if (resultList == null || resultList.isEmpty()) {
                        return null;
                }
                V v = null;
                List<V> objList = Lists.newArrayList();
                for (byte[] resultEle : resultList) {
                        v = (V) decoder(resultEle, key.getClazz());
                        if (v == null) {
                                continue;
                        }
                        objList.add(v);
                }
                return objList;
        }

        @Override
        public Long lRem(RdsKey key, long count, Object value) {
                byte[] data = encoder(value);
                if (data == null || data.length == 0) {
                        return 0L;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.lrem(key.getKey(), count, data);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public boolean lSet(RdsKey key, long index, Object value) {
                byte[] data = encoder(value);
                if (data == null || data.length == 0) {
                        return false;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        boolean result = "OK".equals(jedis.lset(key.getKey(), index, data));
                        if (result && key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public boolean lTrim(RdsKey key, long start, long end) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        return "OK".equals(jedis.ltrim(key.getKey(), start, end));
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long sAdd(RdsKey key, Object... values) {
                if (values == null || values.length == 0) {
                        return 0L;
                }
                byte[][] listVals = new byte[values.length][];
                for (int idx = 0; idx < values.length; idx++) {
                        listVals[idx] = encoder(values[idx]);
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        Long result = jedis.sadd(key.getKey(), listVals);
                        if (result != null && result > 0 && key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long sCard(RdsKey key) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.scard(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public boolean sisMember(RdsKey key, Object value) {
                byte[] data = encoder(value);
                if (data == null || data.length == 0) {
                        return false;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return false;
                }
                try {
                        return jedis.sismember(key.getKey(), data);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return false;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public <V> Set<V> sMembers(RdsKey key) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                Set<byte[]> resultList = null;
                try {
                        resultList = jedis.smembers(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                if (resultList == null || resultList.isEmpty()) {
                        return null;
                }
                V v = null;
                Set<V> objList = Sets.newHashSet();
                for (byte[] resultEle : resultList) {
                        v = (V) decoder(resultEle, key.getClazz());
                        if (v == null) {
                                continue;
                        }
                        objList.add(v);
                }
                return objList;
        }

        @Override
        public <V> Set<V> sPop(RdsKey key, long count) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                Set<byte[]> resultList = null;
                try {
                        resultList = jedis.spop(key.getKey(), count);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                if (resultList == null || resultList.isEmpty()) {
                        return null;
                }
                V v = null;
                Set<V> objList = Sets.newHashSet();
                for (byte[] resultEle : resultList) {
                        v = (V) decoder(resultEle, key.getClazz());
                        if (v == null) {
                                continue;
                        }
                        objList.add(v);
                }
                return objList;
        }

        @Override
        public Long sRem(RdsKey key, Object... values) {
                if (values == null || values.length == 0) {
                        return 0L;
                }
                byte[][] listVals = new byte[values.length][];
                for (int idx = 0; idx < values.length; idx++) {
                        listVals[idx] = encoder(values[idx]);
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.srem(key.getKey(), listVals);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long zAdd(RdsKey key, Map<Object, Double> values) {
                if (values == null || values.isEmpty()) {
                        return 0L;
                }
                Map<byte[], Double> data = Maps.newHashMap();
                Set<Object> fields = values.keySet();
                for (Object field : fields) {
                        byte[] arr = encoder(field);
                        if (arr == null) {
                                continue;
                        }
                        data.put(arr, values.get(field));
                }
                if (data.isEmpty()) {
                        return 0L;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        Long result = jedis.zadd(key.getKey(), data);
                        if (result != null && result > 0 && key.getDelay() > 0) {
                                jedis.expire(key.getKey(), key.getDelay());
                        }
                        return result;
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long zCard(RdsKey key) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.zcard(key.getKey());
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long zCount(RdsKey key, double min, double max) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.zcount(key.getKey(), min, max);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Double zIncrBy(RdsKey key, double score, Object value) {
                byte[] data = encoder(value);
                if (data == null || data.length == 0) {
                        return 0D;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0D;
                }
                try {
                        return jedis.zincrby(key.getKey(), score, data);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0D;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public <V> Set<V> zRange(RdsKey key, long stat, long stop) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                Set<byte[]> resultSet = null;
                try {
                        resultSet = jedis.zrange(key.getKey(), stat, stop);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                Set<V> objSet = Sets.newHashSet();
                V v = null;
                for (byte[] resultEle : resultSet) {
                        v = (V) decoder(resultEle, key.getClazz());
                        if (v == null) {
                                continue;
                        }
                        objSet.add(v);
                }
                return objSet;
        }

        @Override
        public <V> Set<V> zRangeByScore(RdsKey key, double min, double max, int offset, int count) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                Set<byte[]> resultSet = null;
                try {
                        resultSet = jedis.zrangeByScore(key.getKey(), min, max, offset, count);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                Set<V> objSet = Sets.newHashSet();
                V v = null;
                for (byte[] resultEle : resultSet) {
                        v = (V) decoder(resultEle, key.getClazz());
                        if (v == null) {
                                continue;
                        }
                        objSet.add(v);
                }
                return objSet;
        }

        @Override
        public Long zRank(RdsKey key, Object value) {
                byte[] data = encoder(value);
                if (data == null || data.length == 0) {
                        return 0L;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.zrank(key.getKey(), data);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long zRem(RdsKey key, Object... values) {
                if (values == null || values.length == 0) {
                        return 0L;
                }
                byte[][] listVals = new byte[values.length][];
                for (int idx = 0; idx < values.length; idx++) {
                        listVals[idx] = encoder(values[idx]);
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.zrem(key.getKey(), listVals);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long zRemRangeByRank(RdsKey key, long start, long stop) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.zremrangeByRank(key.getKey(), start, stop);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Long zRemRangeByScore(RdsKey key, double min, double max) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.zremrangeByScore(key.getKey(), min, max);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public <V> Set<V> zRevRange(RdsKey key, long start, long stop) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                Set<byte[]> resultSet = null;
                try {
                        resultSet = jedis.zrevrange(key.getKey(), start, stop);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                Set<V> objSet = Sets.newHashSet();
                V v = null;
                for (byte[] resultEle : resultSet) {
                        v = (V) decoder(resultEle, key.getClazz());
                        if (v == null) {
                                continue;
                        }
                        objSet.add(v);
                }
                return objSet;
        }

        @Override
        public <V> Set<V> zRevRangeByScore(RdsKey key, double min, double max, int offset, int count) {
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return null;
                }
                Set<byte[]> resultSet = null;
                try {
                        resultSet = jedis.zrevrangeByScore(key.getKey(), min, max, offset, count);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return null;
                } finally {
                        jedis.close();
                }
                Set<V> objSet = Sets.newHashSet();
                V v = null;
                for (byte[] resultEle : resultSet) {
                        v = (V) decoder(resultEle, key.getClazz());
                        if (v == null) {
                                continue;
                        }
                        objSet.add(v);
                }
                return objSet;
        }

        @Override
        public Long zRevRank(RdsKey key, Object value) {
                byte[] data = encoder(value);
                if (data == null || data.length == 0) {
                        return 0L;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0L;
                }
                try {
                        return jedis.zrevrank(key.getKey(), data);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0L;
                } finally {
                        jedis.close();
                }
        }

        @Override
        public Double zScore(RdsKey key, Object value) {
                byte[] data = encoder(value);
                if (data == null || data.length == 0) {
                        return 0D;
                }
                Jedis jedis = connFactory.getPool(key.getKeyStr());
                if (jedis == null) {
                        logger.error("###### 没有找到redis代理的连接! ######");
                        return 0D;
                }
                try {
                        return jedis.zscore(key.getKey(), data);
                } catch (Exception e) {
                        logger.error(e.getMessage());
                        return 0D;
                } finally {
                        jedis.close();
                }
        }

}
