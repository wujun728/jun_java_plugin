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

import net.oschina.j2cache.CacheProviderHolder;
import net.oschina.j2cache.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * 缓存集群策略接口
 * @author Wujun
 */
public interface ClusterPolicy {

    Logger log = LoggerFactory.getLogger(ClusterPolicy.class);

    /**
     * 连接到集群
     * @param props j2cache 配置信息
     */
    void connect(Properties props);


    /**
     * 发送消息
     * @param cmd command to send
     */
    void publish(Command cmd);

    /**
     * 发送清除缓存的命令
     * @param region 区域名称
     * @param keys   缓存键值
     */
    default void sendEvictCmd(String region, String...keys) {
        publish(new Command(Command.OPT_EVICT_KEY, region, keys));
    }

    /**
     * 发送清除整个缓存区域的命令
     * @param region 区域名称
     */
    default void sendClearCmd(String region) {
        publish(new Command(Command.OPT_CLEAR_KEY, region));
    }

    /**
     * 断开集群连接
     */
    void disconnect();

    /**
     * 删除本地某个缓存条目
     * @param region 区域名称
     * @param keys   缓存键值
     */
    default void evict(String region, String... keys) {
        CacheProviderHolder.getLevel1Cache(region).evict(keys);
    }

    /**
     * 清除本地整个缓存区域
     * @param region 区域名称
     */
    default void clear(String region) {
        CacheProviderHolder.getLevel1Cache(region).clear();
    }

    /**
     * 处理缓存事件逻辑
     * @param cmd the received command
     */
    default void handleCommand(Command cmd) {
        try {
            if (cmd == null || cmd.isLocal())
                return;

            switch (cmd.getOperator()) {
                case Command.OPT_JOIN:
                    log.info("Node-"+cmd.getSrc() + " joined !");
                    break;
                case Command.OPT_EVICT_KEY:
                    this.evict(cmd.getRegion(), cmd.getKeys());
                    log.debug("Received cache evict message, region=" + cmd.getRegion() + ",key=" + String.join(",", cmd.getKeys()));
                    break;
                case Command.OPT_CLEAR_KEY:
                    this.clear(cmd.getRegion());
                    log.debug("Received cache clear message, region=" + cmd.getRegion());
                    break;
                case Command.OPT_QUIT:
                    log.info("Node-"+cmd.getSrc() + " quit !");
                    break;
                default:
                    log.warn("Unknown message type = " + cmd.getOperator());
            }
        } catch (Exception e) {
            log.error("Failed to handle received msg", e);
        }
    }
}
