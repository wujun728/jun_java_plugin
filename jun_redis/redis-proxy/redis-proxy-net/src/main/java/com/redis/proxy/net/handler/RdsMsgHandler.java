/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.net.handler;

import com.redis.proxy.net.exceptions.RedisException;
import com.redis.proxy.net.resps.InlineRdsResp;
import com.redis.proxy.net.resps.RdsResp;
import com.redis.proxy.net.server.Server;
import java.nio.charset.Charset;

/**
 * redis handler 抽象父类
 *
 * @author zhanggaofeng
 */
public abstract class RdsMsgHandler {

        private final Server server;

        protected RdsMsgHandler(int port, int minThreads, int maxThreads) {
                server = new Server(port, minThreads, maxThreads, this);
                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                        @Override
                        public void run() {
                                if (server != null) {
                                        server.destory();
                                }
                        }
                }));
        }

        protected byte[] getBytes(String key) {
                return key.getBytes(Charset.forName("UTF-8"));
        }

        protected String getString(byte[] data) {
                return new String(data, Charset.forName("UTF-8"));
        }

        public RdsResp ping() {
                return new InlineRdsResp("PONG");
        }
        
        public abstract RdsResp get(String key) throws RedisException;

        public abstract RdsResp set(String key, byte[] value) throws RedisException;

        public abstract RdsResp setEx(String key, int second, byte[] value) throws RedisException;

        public abstract RdsResp setNx(String key, byte[] value) throws RedisException;

        public abstract RdsResp getSet(String key, byte[] value) throws RedisException;

        public abstract RdsResp del(String key) throws RedisException;

        public abstract RdsResp exists(String key) throws RedisException;

        public abstract RdsResp expire(String key, int value) throws RedisException;

        public abstract RdsResp expireAt(String key, int value) throws RedisException;

        public abstract RdsResp ttl(String key) throws RedisException;

        public abstract RdsResp decr(String key) throws RedisException;

        public abstract RdsResp decrBy(String key, long value) throws RedisException;

        public abstract RdsResp incr(String key) throws RedisException;

        public abstract RdsResp incrBy(String key, long value) throws RedisException;

        public abstract RdsResp hSet(String key, byte[] field, byte[] value) throws RedisException;

        public abstract RdsResp hMSet(String key, byte[][] value) throws RedisException;

        public abstract RdsResp hGet(String key, byte[] field) throws RedisException;

        public abstract RdsResp hMGet(String key, byte[][] field) throws RedisException;

        public abstract RdsResp hGetAll(String key) throws RedisException;

        public abstract RdsResp hDel(String key, byte[][] field) throws RedisException;

        public abstract RdsResp hExists(String key, byte[] field) throws RedisException;

        public abstract RdsResp hIncrBy(String key, byte[] field, long value) throws RedisException;

        public abstract RdsResp hKeys(String key) throws RedisException;

        public abstract RdsResp hLen(String key) throws RedisException;

        public abstract RdsResp hSetNx(String key, byte[] field, byte[] value) throws RedisException;

        public abstract RdsResp hVals(String key) throws RedisException;

        public abstract RdsResp lPush(String key, byte[][] value) throws RedisException;

        public abstract RdsResp rPush(String key, byte[][] value) throws RedisException;

        public abstract RdsResp lPop(String key) throws RedisException;

        public abstract RdsResp rPop(String key) throws RedisException;

        public abstract RdsResp bLPop(String key, int timeout) throws RedisException;

        public abstract RdsResp bRPop(String key, int timeout) throws RedisException;

        public abstract RdsResp lLen(String key) throws RedisException;

        public abstract RdsResp lRange(String key, long start, long end) throws RedisException;

        public abstract RdsResp lRem(String key, long count, byte[] value) throws RedisException;

        public abstract RdsResp lSet(String key, long index, byte[] value) throws RedisException;

        public abstract RdsResp lTrim(String key, long start, long end) throws RedisException;

        public abstract RdsResp sAdd(String key, byte[][] value) throws RedisException;

        public abstract RdsResp sCard(String key) throws RedisException;

        public abstract RdsResp sisMember(String key, byte[] value) throws RedisException;

        public abstract RdsResp sMembers(String key) throws RedisException;

        public abstract RdsResp sPop(String key) throws RedisException;

        public abstract RdsResp sRem(String key, byte[][] value) throws RedisException;

        public abstract RdsResp zAdd(String key, byte[][] value) throws RedisException;

        public abstract RdsResp zCard(String key) throws RedisException;

        public abstract RdsResp zCount(String key, byte[] min, byte[] max) throws RedisException;

        public abstract RdsResp zIncrBy(String key, double score, byte[] value) throws RedisException;

        public abstract RdsResp zRange(String key, long stat, long stop) throws RedisException;

        public abstract RdsResp zRangeByScore(String key, byte[][] value) throws RedisException;

        public abstract RdsResp zRank(String key, byte[] value) throws RedisException;

        public abstract RdsResp zRem(String key, byte[][] value) throws RedisException;

        public abstract RdsResp zRemRangeByRank(String key, long start, long stop) throws RedisException;

        public abstract RdsResp zRemRangeByScore(String key, byte[] min, byte[] max) throws RedisException;

        public abstract RdsResp zRevRange(String key, long start, long stop) throws RedisException;

        public abstract RdsResp zRevRangeByScore(String key, byte[][] value) throws RedisException;

        public abstract RdsResp zRevRank(String key, byte[] value) throws RedisException;

        public abstract RdsResp zScore(String key, byte[] value) throws RedisException;
}
