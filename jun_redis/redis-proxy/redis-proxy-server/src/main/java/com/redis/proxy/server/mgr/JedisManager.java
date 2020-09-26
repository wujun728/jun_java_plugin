/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.server.mgr;

import com.redis.proxy.server.alarm.ProxyRdsAlarmService;
import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.redis.proxy.common.consts.CacheConsts;
import com.talk51.redis.proxy.jedis.pool.ProxyShardedJedisPool;
import com.talk51.redis.proxy.jedis.pool.alarm.RedisAlarm;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PreDestroy;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryJedisCluster;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.util.JedisURIHelper;

/**
 * redis config 类
 *
 * @author zhanggaofeng
 */
@Service
@Scope("singleton")
@DisconfFile(filename = "redis.properties")
@DisconfUpdateService(classes = {JedisManager.class})
public class JedisManager extends AbstDisconf implements IDisconfUpdate {

        private final Logger logger = LoggerFactory.getLogger(JedisManager.class);
        private final Map<String, ProxyShardedJedisPool> poolMap = Maps.newConcurrentMap();
        private final Map<String, BinaryJedisCluster> clusterMap = Maps.newConcurrentMap();
        private final Map<String, String> hostMap = Maps.newConcurrentMap();
        private final RedisAlarm alarm;

        @Autowired
        public JedisManager(ProxyRdsAlarmService alarmService) {
                this.alarm = alarmService;
                try {
                        this.fileLoad();
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
        }

        /**
         * 获取模块对应的redis连接
         *
         * @param redisKey
         * @return
         */
        public Object getObject(String redisKey) {
                int pos = redisKey.indexOf('_');
                if (pos <= 0) {
                        logger.error("key no contains module name ; key = " + redisKey);
                        return null;
                }
                String moduleName = redisKey.substring(0, pos);
                /**
                 * 检查分片模式
                 */
                Object result = poolMap.get(moduleName);
                if (result == null) {
                        /**
                         * 检查集群模式
                         */
                        result = clusterMap.get(moduleName);
                }
                return result;
        }

        @PreDestroy
        public void destory() {
                for (ProxyShardedJedisPool pool : poolMap.values()) {
                        pool.close();
                }
        }

        @Override
        public void reload() throws Exception {
                this.fileLoad();
        }

        @Override
        protected void updateCallBack() {

                Set<String> newModules = Sets.newHashSet(Arrays.asList(getString("modules", "").split(",")));
                Set<String> oldModules = hostMap.keySet();
                for (String moduleName : oldModules) {
                        // 删除旧的模块
                        if (!newModules.contains(moduleName)) {
                                hostMap.remove(moduleName);
                                ProxyShardedJedisPool pool = poolMap.remove(moduleName);
                                if (pool != null) {
                                        pool.close();
                                }
                                BinaryJedisCluster cluster = clusterMap.remove(moduleName);
                                if (cluster != null) {
                                        try {
                                                cluster.close();
                                        } catch (IOException ex) {
                                        }
                                }
                        }
                }
                for (String moduleName : newModules) {
                        // 增加新的
                        if (!oldModules.contains(moduleName)) {
                                /**
                                 * 检查shard方式
                                 */
                                String hostStr = getString("module." + moduleName + ".shards");
                                if (!StringUtils.isEmpty(hostStr)) {
                                        try {
                                                addShardModuleCache(moduleName, hostStr);
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                        continue;
                                }
                                /**
                                 * 检查cluster方式
                                 */
                                hostStr = getString("module." + moduleName + ".clusters");
                                if (!StringUtils.isEmpty(hostStr)) {
                                        try {
                                                addClusterModuleCache(moduleName, hostStr);
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                        continue;
                                }
                        }
                }
                // 检查缓存配置变化
                for (String moduleName : oldModules) {
                        String oldHostStr = hostMap.get(moduleName);
                        String hostStr = getString("module." + moduleName + ".shards");
                        if (StringUtils.isEmpty(hostStr)) {
                                ProxyShardedJedisPool pool = poolMap.get(moduleName);
                                if (pool != null) {
                                        pool.close();
                                }
                        } else if (!hostStr.equals(oldHostStr)) {
                                addShardModuleCache(moduleName, hostStr);
                        }
                        hostStr = getString("module." + moduleName + ".clusters");
                        if (StringUtils.isEmpty(hostStr)) {
                                BinaryJedisCluster cluster = clusterMap.get(moduleName);
                                if (cluster != null) {
                                        try {
                                                cluster.close();
                                        } catch (Exception e) {
                                        }
                                }
                        } else if (!hostStr.equals(oldHostStr)) {
                                addClusterModuleCache(moduleName, hostStr);
                        }
                }
        }

        /**
         * 增加模块缓存---shard
         *
         * @param moduleName
         */
        private void addShardModuleCache(String moduleName, String hostStr) {
                List<JedisShardInfo> shards = Lists.newArrayList();
                String[] hosts = hostStr.split(",");
                for (String host : hosts) {
                        shards.add(new JedisShardInfo(URI.create(host)));
                }
                if (!shards.isEmpty()) {
                        JedisPoolConfig poolConfig = new JedisPoolConfig();
                        poolConfig.setMaxTotal(CacheConsts.SERVER_MAX_THREADS);
                        poolConfig.setMaxIdle(CacheConsts.SERVER_MAX_THREADS);
                        poolConfig.setMaxWaitMillis(getInt("redis.maxWaitMillis"));
                        poolConfig.setTestWhileIdle(true);
                        poolConfig.setTimeBetweenEvictionRunsMillis(getIntMinDefault("redis.timeBetweenEvictionRunsMillis", 5000));
                        poolConfig.setMinEvictableIdleTimeMillis(getInt("redis.minEvictableIdleTimeMillis"));
                        hostMap.put(moduleName, hostStr);
                        ProxyShardedJedisPool oldPool = poolMap.remove(moduleName);
                        poolMap.put(moduleName, new ProxyShardedJedisPool(poolConfig, shards, alarm));
                        if (oldPool != null) {
                                oldPool.close();
                        }
                }
        }

        /**
         * 增加模块缓存----cluster
         *
         * @param moduleName
         */
        private void addClusterModuleCache(String moduleName, String hostStr) {
                Set<HostAndPort> nodes = Sets.newHashSet();
                String[] hosts = hostStr.split(",");
                String pwd = null;
                for (String host : hosts) {
                        URI uri = URI.create(host);
                        nodes.add(new HostAndPort(uri.getHost(), uri.getPort()));
                        if (StringUtils.isEmpty(pwd)) {
                                pwd = JedisURIHelper.getPassword(uri);
                        }
                }
                if (!nodes.isEmpty()) {
                        JedisPoolConfig poolConfig = new JedisPoolConfig();
                        poolConfig.setMaxTotal(CacheConsts.SERVER_MAX_THREADS);
                        poolConfig.setMaxIdle(CacheConsts.SERVER_MAX_THREADS);
                        poolConfig.setMaxWaitMillis(getInt("redis.maxWaitMillis"));
                        poolConfig.setTestWhileIdle(true);
                        poolConfig.setTimeBetweenEvictionRunsMillis(getIntMinDefault("redis.timeBetweenEvictionRunsMillis", 5000));
                        poolConfig.setMinEvictableIdleTimeMillis(getInt("redis.minEvictableIdleTimeMillis"));
                        hostMap.put(moduleName, hostStr);
                        BinaryJedisCluster cluster = clusterMap.remove(moduleName);
                        clusterMap.put(moduleName, new BinaryJedisCluster(nodes, getInt("redis.cluster.connectionTimeout", 10000), getInt("redis.cluster.timeout", 10000), getInt("redis.cluster.maxAttempts", 2), pwd, poolConfig));
                        if (cluster != null) {
                                try {
                                        cluster.close();
                                } catch (Exception e) {
                                }
                        }
                }
        }

}
