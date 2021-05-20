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
package net.oschina.j2cache.cluster;

import net.oschina.j2cache.CacheException;
import net.oschina.j2cache.lettuce.LettuceCacheProvider;
import net.oschina.j2cache.redis.RedisPubSubClusterPolicy;

import java.util.Properties;

/**
 * 集群策略工厂
 * @author Wujun
 */
public class ClusterPolicyFactory {

    private ClusterPolicyFactory(){}

    /**
     * 初始化集群消息通知机制
     * @param broadcast  j2cache.broadcast value
     * @param props  broadcast configuations
     * @return ClusterPolicy instance
     */
    public final static ClusterPolicy init(String broadcast, Properties props) {
        ClusterPolicy policy;
        if ("redis".equalsIgnoreCase(broadcast))
            policy = ClusterPolicyFactory.redis(props);
        else if ("jgroups".equalsIgnoreCase(broadcast))
            policy = ClusterPolicyFactory.jgroups(props);
        else if ("rabbitmq".equalsIgnoreCase(broadcast))
            policy = ClusterPolicyFactory.rabbitmq(props);
        else if ("rocketmq".equalsIgnoreCase(broadcast))
            policy = ClusterPolicyFactory.rocketmq(props);
        else if ("lettuce".equalsIgnoreCase(broadcast))
            policy = ClusterPolicyFactory.lettuce(props);
        else if ("none".equalsIgnoreCase(broadcast))
            policy = new NoneClusterPolicy();
        else
            policy = ClusterPolicyFactory.custom(broadcast, props);
        return policy;
    }

    /**
     * 使用 Redis 订阅和发布机制，该方法只能调用一次
     * @param props 框架配置
     * @return 返回 Redis 集群策略的实例
     */
    private final static ClusterPolicy redis(Properties props) {
        String name = props.getProperty("channel");
        RedisPubSubClusterPolicy policy = new RedisPubSubClusterPolicy(name, props);
        policy.connect(props);
        return policy;
    }

    /**
     * 使用 JGroups 组播机制
     * @param props 框架配置
     * @return 返回 JGroups 集群策略的实例
     */
    private final static ClusterPolicy jgroups(Properties props) {
        String name = props.getProperty("channel.name");
        JGroupsClusterPolicy policy = new JGroupsClusterPolicy(name, props);
        policy.connect(props);
        return policy;
    }

    /**
     * 使用 RabbitMQ 消息机制
     * @param props 框架配置
     * @return 返回 RabbitMQ 集群策略的实例
     */
    private final static ClusterPolicy rabbitmq(Properties props) {
        RabbitMQClusterPolicy policy = new RabbitMQClusterPolicy(props);
        policy.connect(props);
        return policy;
    }

    private final static ClusterPolicy rocketmq(Properties props) {
        RocketMQClusterPolicy policy = new RocketMQClusterPolicy(props);
        policy.connect(props);
        return policy;
    }

    private final static ClusterPolicy lettuce(Properties props) {
        LettuceCacheProvider policy = new LettuceCacheProvider();
        policy.connect(props);
        return policy;
    }

    /**
     * 加载自定义的集群通知策略
     * @param classname
     * @param props
     * @return
     */
    private final static ClusterPolicy custom(String classname, Properties props) {
        try {
            ClusterPolicy policy = (ClusterPolicy)Class.forName(classname).newInstance();
            policy.connect(props);
            return policy;
        } catch (Exception e) {
            throw new CacheException("Failed in load custom cluster policy. class = " + classname, e);
        }
    }

}
