/**
 * Copyright (c) 2015-2017, Winter Lau (javayou@gmail.com).
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
package net.oschina.j2cache.redis;

import net.oschina.j2cache.cluster.ClusterPolicy;
import net.oschina.j2cache.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Pool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 使用 Redis 的订阅和发布进行集群中的节点通知
 * 该策略器使用 j2cache.properties 中的 redis 配置自行保持两个到 redis 的连接用于发布和订阅消息（并在失败时自动重连）
 * @author Wujun
 */
public class RedisPubSubClusterPolicy extends JedisPubSub implements ClusterPolicy {

    private final static Logger log = LoggerFactory.getLogger(RedisPubSubClusterPolicy.class);

    private Pool<Jedis> client;
    private String channel;

    public RedisPubSubClusterPolicy(String channel, Properties props){
        this.channel = channel;
        int timeout = Integer.parseInt((String)props.getOrDefault("timeout", "2000"));
        String password = props.getProperty("password");
        if(password != null && password.trim().length() == 0)
            password = null;

        int database = Integer.parseInt(props.getProperty("database", "0"));

        JedisPoolConfig config = RedisUtils.newPoolConfig(props, null);

        String node = props.getProperty("channel.host");
        if(node == null || node.trim().length() == 0)
            node = props.getProperty("hosts");

        if("sentinel".equalsIgnoreCase(props.getProperty("mode"))){
            Set<String> hosts = new HashSet();
            hosts.addAll(Arrays.asList(node.split(",")));
            String masterName = props.getProperty("cluster_name", "j2cache");
            this.client = new JedisSentinelPool(masterName, hosts, config, timeout, password, database);
        }
        else {
            node = node.split(",")[0]; //取第一台主机
            String[] infos = node.split(":");
            String host = infos[0];
            int port = (infos.length > 1)?Integer.parseInt(infos[1]):6379;
            this.client = new JedisPool(config, host, port, timeout, password, database);
        }
    }

    /**
     * 加入 Redis 的发布订阅频道
     */
    @Override
    public void connect(Properties props) {
        long ct = System.currentTimeMillis();

        this.publish(Command.join());

        Thread subscribeThread = new Thread(()-> {
            //当 Redis 重启会导致订阅线程断开连接，需要进行重连
            while(true) {
                try (Jedis jedis = client.getResource()){
                    jedis.subscribe(this, channel);
                    log.info("Disconnect to redis channel: " + channel);
                    break;
                } catch (JedisConnectionException e) {
                    log.error("Failed connect to redis, reconnect it.", e);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie){
                        break;
                    }
                }
            }
        }, "RedisSubscribeThread");

        subscribeThread.setDaemon(true);
        subscribeThread.start();

        log.info("Connected to redis channel:" + channel + ", time " + (System.currentTimeMillis()-ct) + " ms.");
    }

    /**
     * 退出 Redis 发布订阅频道
     */
    @Override
    public void disconnect() {
        try {
            this.publish(Command.quit());
            if(this.isSubscribed())
                this.unsubscribe();
        } finally {
            this.client.close();
            //subscribeThread will auto terminate
        }
    }

    @Override
    public void publish(Command cmd) {
        try (Jedis jedis = client.getResource()) {
            jedis.publish(channel, cmd.json());
        }
    }

    /**
     * 当接收到订阅频道获得的消息时触发此方法
     * @param channel 频道名称
     * @param message 消息体
     */
    @Override
    public void onMessage(String channel, String message) {
        Command cmd = Command.parse(message);
        handleCommand(cmd);
    }

}
