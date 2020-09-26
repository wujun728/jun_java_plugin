/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.client.factory;

import com.google.common.collect.Maps;
import com.redis.proxy.common.zk.ZkSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.I0Itec.zkclient.IZkChildListener;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * proxy connecton manager
 *
 * @author zhanggaofeng
 */
public class ProxyRdsConnFactory implements IZkChildListener {

        /**
         * zk session
         */
        private final ZkSession session;
        private final int proxyMinIdle;
        private final int proxyMaxTotal;
        /**
         * list读写锁
         */
        private ReadWriteLock listLock = new ReentrantReadWriteLock();
        /**
         * 代理连接集合
         */
        private final List<JedisPool> list = new ArrayList<>();
        private final Map<String, JedisPool> templatesMap = Maps.newConcurrentMap();

        /**
         *
         * @param zkhost zk地址
         * @param connectionTimeout zk 连接超时时间
         * @param sessionTimeout zk 会话时间
         * @param proxyMinIdle 连接池最小保持个数
         * @param proxyMaxTotal 连接池最大个数
         */
        public ProxyRdsConnFactory(String zkhost, int connectionTimeout, int sessionTimeout, int proxyMinIdle, int proxyMaxTotal) {
                this(zkhost, "default", connectionTimeout, sessionTimeout, proxyMinIdle, proxyMaxTotal);
        }

        /**
         *
         * @param zkhost zk地址
         * @param group zk 代理组
         * @param connectionTimeout zk 连接超时时间
         * @param sessionTimeout zk 会话时间
         * @param proxyMinIdle 连接池最小保持个数
         * @param proxyMaxTotal 连接池最大个数
         */
        public ProxyRdsConnFactory(String zkhost, String group, int connectionTimeout, int sessionTimeout, int proxyMinIdle, int proxyMaxTotal) {
                this.proxyMinIdle = proxyMinIdle;
                this.proxyMaxTotal = proxyMaxTotal;
                session = new ZkSession(zkhost, connectionTimeout, sessionTimeout);
                /**
                 * 销毁钩子
                 */
                final ProxyRdsConnFactory proxyMgr = this;
                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                        @Override
                        public void run() {
                                if (session == null) {
                                        /**
                                         * 取消订阅节点变化
                                         */
                                        session.unsubscribeProxyChildChanges(proxyMgr);
                                        session.destory();
                                }
                        }
                }));
                /**
                 * 订阅节点变化
                 */
                try {
                        handleChildChange(session.getProxysPath(), session.subscribeProxyChildChanges(this));
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
        }

        /**
         * 销毁
         */
        public void destory() {
                if (session != null) {
                        session.destory();
                }
                for (JedisPool pool : templatesMap.values()) {
                        pool.destroy();
                }
        }

        /**
         *
         * @param key
         * @return
         */
        public Jedis getPool(String key) {
                int hash = Math.abs(key.hashCode());
                listLock.readLock().lock();
                try {
                        int size = list.size();
                        if (size <= 0) {
                                return null;
                        }
                        return list.get(hash % size).getResource();
                } finally {
                        listLock.readLock().unlock();
                }
        }

        @Override
        public void handleChildChange(String string, List<String> newHosts) throws Exception {

                Set<String> oldHosts = templatesMap.keySet();
                for (String host : oldHosts) {
                        /**
                         * 删除
                         */
                        if (!newHosts.contains(host)) {
                                JedisPool pool = templatesMap.remove(host);
                                if (pool != null) {
                                        listLock.writeLock().lock();
                                        try {
                                                list.remove(pool);
                                        } finally {
                                                listLock.writeLock().unlock();
                                        }
                                        pool.close();
                                }
                        }
                }

                for (String host : newHosts) {
                        /**
                         * 增加
                         */
                        if (!oldHosts.contains(host)) {
                                JedisPoolConfig poolConfig = new JedisPoolConfig();
                                poolConfig.setMaxTotal(proxyMaxTotal);
                                poolConfig.setMaxIdle(proxyMinIdle);
                                poolConfig.setMaxWaitMillis(60000);
                                poolConfig.setTestWhileIdle(true);
                                poolConfig.setTimeBetweenEvictionRunsMillis(5000);
                                poolConfig.setMinEvictableIdleTimeMillis(300000);
                                String[] infos = host.split(":");
                                JedisPool pool = new JedisPool(poolConfig, infos[0], Integer.parseInt(infos[1]));
                                templatesMap.put(host, pool);
                                listLock.writeLock().lock();
                                try {
                                        list.add(pool);
                                } finally {
                                        listLock.writeLock().unlock();
                                }
                        }
                }
        }

}
