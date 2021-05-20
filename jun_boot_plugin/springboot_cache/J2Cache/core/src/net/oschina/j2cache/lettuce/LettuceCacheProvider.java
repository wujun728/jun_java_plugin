/**
 * Copyright (c) 2015-2017, Winter Lau (javayou@gmail.com), wendal.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oschina.j2cache.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import net.oschina.j2cache.*;
import net.oschina.j2cache.cluster.ClusterPolicy;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  使用 Lettuce 进行 Redis 的操作
 *
 *  配置信息：
 *
 *  lettuce.namespace =
 *  lettuce.storage = generic
 *  lettuce.scheme = redis|rediss|redis-sentinel
 *  lettuce.hosts = 127.0.0.1:6379
 *  lettuce.password =
 *  lettuce.database = 0
 *  lettuce.sentinelMasterId =
 *
 * @author Wujun
 */
public class LettuceCacheProvider extends RedisPubSubAdapter<String, String> implements CacheProvider, ClusterPolicy {

    private static RedisClient redisClient;
    private StatefulRedisPubSubConnection<String, String> pubsub;
    private String storage;

    private String channel;
    private String namespace;

    private static final ConcurrentHashMap<String, Level2Cache> regions = new ConcurrentHashMap();

    @Override
    public String name() {
        return "lettuce";
    }

    @Override
    public int level() {
        return CacheObject.LEVEL_2;
    }

    @Override
    public Cache buildCache(String region, CacheExpiredListener listener) {
        return regions.computeIfAbsent(region, v -> "hash".equalsIgnoreCase("hash")?new LettuceHashCache(this.namespace, region, redisClient):new LettuceGenericCache(this.namespace, region, redisClient));
    }

    @Override
    public Cache buildCache(String region, long timeToLiveInSeconds, CacheExpiredListener listener) {
        return buildCache(region, listener);
    }

    @Override
    public Collection<CacheChannel.Region> regions() {
        return Collections.emptyList();
    }

    @Override
    public void start(Properties props) {
        this.namespace = props.getProperty("namespace");
        this.storage = props.getProperty("storage", "generic");
        this.channel = props.getProperty("channel", "j2cache");

        String scheme = props.getProperty("scheme", "redis");
        String hosts = props.getProperty("hosts", "127.0.0.1:6379");
        String password = props.getProperty("password");
        int database = Integer.parseInt(props.getProperty("database", "0"));
        String sentinelMasterId = props.getProperty("sentinelMasterId");

        String redis_url = String.format("%s://%s@%s/%d#%s", scheme, password, hosts, database, sentinelMasterId);

        redisClient = RedisClient.create(redis_url);
    }

    @Override
    public void stop() {
        regions.clear();
        redisClient.shutdown();
    }

    @Override
    public void connect(Properties props) {
        long ct = System.currentTimeMillis();

        this.channel = props.getProperty("channel", "j2cache");
        this.publish(Command.join());

        this.pubsub = redisClient.connectPubSub();
        this.pubsub.addListener(this);
        RedisPubSubAsyncCommands<String, String> async = this.pubsub.async();
        async.subscribe(this.channel);

        log.info("Connected to redis channel:" + this.channel + ", time " + (System.currentTimeMillis()-ct) + " ms.");
    }

    @Override
    public void message(String channel, String message) {
        Command cmd = Command.parse(message);
        handleCommand(cmd);
    }

    @Override
    public void publish(Command cmd) {
        try (StatefulRedisPubSubConnection<String, String> connection = redisClient.connectPubSub()){
            RedisPubSubCommands<String, String> sync = connection.sync();
            sync.publish(this.channel, cmd.json());
        }
    }

    @Override
    public void disconnect() {
        try {
            this.publish(Command.quit());
            super.unsubscribed(this.channel, 1);
        } finally {
            this.pubsub.close();
        }
    }
}
