/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.client.templates;

import com.redis.proxy.client.io.KryoObjectInput;
import com.redis.proxy.client.io.KryoObjectOutput;
import com.redis.proxy.client.factory.ProxyRdsConnFactory;
import com.redis.proxy.client.keys.RdsKey;
import com.redis.proxy.common.utils.CompressUtil;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作模板父类
 *
 * @author Wujun
 */
public abstract class RdsTemplate {

        protected final ProxyRdsConnFactory connFactory;
        private final boolean needCompress;
        private final int compressShold;

        /**
         *
         * @param connFactory 代理连接管理池
         */
        protected RdsTemplate(ProxyRdsConnFactory connFactory) {
                this.connFactory = connFactory;
                this.needCompress = true;
                this.compressShold = 100;
        }

        /**
         *
         * @param connMgr 代理连接管理池
         * @param needCompress 是否需要压缩
         * @param compressShold 压缩的阀值
         */
        protected RdsTemplate(ProxyRdsConnFactory connMgr, boolean needCompress, int compressShold) {
                this.connFactory = connMgr;
                this.needCompress = needCompress;
                this.compressShold = compressShold;
        }

        protected byte[] getBytes(String value) {
                return value.getBytes(Charset.forName("UTF-8"));
        }

        protected String getString(byte[] value) {
                return new String(value, Charset.forName("UTF-8"));
        }

        public void destory() {
                if (connFactory != null) {
                        connFactory.destory();
                }
        }

        /**
         * kryo序列化
         *
         * @param obj
         * @return
         */
        private byte[] kryoEncoder(Object obj) {
                KryoObjectOutput out = new KryoObjectOutput();
                try {
                        out.writeObject(obj);
                        return out.flushBuffer();
                } catch (IOException ex) {
                        ex.printStackTrace();
                        return null;
                } finally {
                        out.cleanup();
                }
        }

        /**
         * kryo反序列化
         *
         * @param <T>
         * @param data
         * @param clazz
         * @return
         */
        private <T> T kryoDecoder(byte[] data, Class<T> clazz) {

                if (data == null || data.length <= 0) {
                        return null;
                }
                KryoObjectInput in = new KryoObjectInput(data);
                try {
                        return in.readObject(clazz);
                } catch (Exception ex) {
                        ex.printStackTrace();
                } finally {
                        in.cleanup();
                }

                return null;
        }

        /**
         * 编码redis存储对象
         *
         * @param value
         * @return
         */
        protected byte[] encoder(Object value) {
                if (value == null) {
                        return null;
                }
                byte[] data = kryoEncoder(value);
                if (needCompress) {
                        return CompressUtil.compress(data, compressShold);
                } else {
                        return data;
                }
        }

        /**
         * 解码redis存储对象
         *
         * @param <T>
         * @param data
         * @param clazz
         * @return
         */
        protected <T> T decoder(byte[] data, Class<T> clazz) {
                if (data == null || data.length == 0) {
                        return null;
                }
                if (needCompress) {
                        return kryoDecoder(CompressUtil.decompress(data), clazz);
                } else {
                        return kryoDecoder(data, clazz);
                }
        }

        public abstract <V> V get(RdsKey key);

        public abstract boolean set(RdsKey key, Object value);

        public abstract boolean setEx(RdsKey key, int second, Object value);

        public abstract boolean setNx(RdsKey key, Object value);

        public abstract <V> V getSet(RdsKey key, Object value);

        public abstract boolean del(RdsKey key);

        public abstract boolean exists(RdsKey key);

        public abstract boolean expire(RdsKey key, int value);

        public abstract boolean expireAt(RdsKey key, long value);

        public abstract Long ttl(RdsKey key);

        public abstract Long decr(RdsKey key);

        public abstract Long decrBy(RdsKey key, long value);

        public abstract Long incr(RdsKey key);

        public abstract Long incrBy(RdsKey key, long value);

        public abstract Long hSet(RdsKey key, String field, Object value);

        public abstract boolean hMSet(RdsKey key, Map<String, Object> value);

        public abstract <V> V hGet(RdsKey key, String field);

        public abstract <V> List<V> hMGet(RdsKey key, String... fields);

        public abstract <V> Map<String, V> hGetAll(RdsKey key);

        public abstract Long hDel(RdsKey key, String... fields);

        public abstract boolean hExists(RdsKey key, String field);

        public abstract Long hIncrBy(RdsKey key, String field, long value);

        public abstract Set<String> hKeys(RdsKey key);

        public abstract Long hLen(RdsKey key);

        public abstract boolean hSetNx(RdsKey key, String field, Object value);

        public abstract <V> List<V> hVals(RdsKey key);

        public abstract Long lPush(RdsKey key, Object... values);

        public abstract Long rPush(RdsKey key, Object... values);

        public abstract <V> V lPop(RdsKey key);

        public abstract <V> V rPop(RdsKey key);

        public abstract <V> V bLPop(RdsKey key, int timeout);

        public abstract <V> V bRPop(RdsKey key, int timeout);

        public abstract Long lLen(RdsKey key);

        public abstract <V> List<V> lRange(RdsKey key, long start, long end);

        public abstract Long lRem(RdsKey key, long count, Object value);

        public abstract boolean lSet(RdsKey key, long index, Object value);

        public abstract boolean lTrim(RdsKey key, long start, long end);

        public abstract Long sAdd(RdsKey key, Object... values);

        public abstract Long sCard(RdsKey key);

        public abstract boolean sisMember(RdsKey key, Object value);

        public abstract <V> Set<V> sMembers(RdsKey key);

        public abstract <V> Set<V> sPop(RdsKey key, long count);

        public abstract Long sRem(RdsKey key, Object... values);

        public abstract Long zAdd(RdsKey key, Map<Object, Double> values);

        public abstract Long zCard(RdsKey key);

        public abstract Long zCount(RdsKey key, double min, double max);

        public abstract Double zIncrBy(RdsKey key, double score, Object value);

        public abstract <V> Set<V> zRange(RdsKey key, long stat, long stop);

        public abstract <V> Set<V> zRangeByScore(RdsKey key, double min, double max, int offset, int count);

        public abstract Long zRank(RdsKey key, Object value);

        public abstract Long zRem(RdsKey key, Object... values);

        public abstract Long zRemRangeByRank(RdsKey key, long start, long stop);

        public abstract Long zRemRangeByScore(RdsKey key, double min, double max);

        public abstract <V> Set<V> zRevRange(RdsKey key, long start, long stop);

        public abstract <V> Set<V> zRevRangeByScore(RdsKey key, double min, double max, int offset, int count);

        public abstract Long zRevRank(RdsKey key, Object value);

        public abstract Double zScore(RdsKey key, Object value);
}
