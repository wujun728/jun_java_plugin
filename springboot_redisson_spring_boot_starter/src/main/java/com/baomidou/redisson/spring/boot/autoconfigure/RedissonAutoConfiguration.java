/**
 * Copyright © 2018 organization baomidou
 * <pre>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <pre/>
 */
package com.baomidou.redisson.spring.boot.autoconfigure;

import com.baomidou.redisson.RedissonProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Slf4j
@Configuration
@EnableConfigurationProperties({RedisProperties.class, RedissonProperties.class})
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(value = "spring.redis.enabled", matchIfMissing = true)
public class RedissonAutoConfiguration {

    private final RedisProperties redisProperties;

    private final RedissonProperties redissonProperties;

    public RedissonAutoConfiguration(RedisProperties redisProperties, RedissonProperties redissonProperties) {
        this.redisProperties = redisProperties;
        this.redissonProperties = redissonProperties;
    }

    @Bean(destroyMethod = "shutdown")
    RedissonClient redissonClient() throws IOException {
        Config config;

        String yaml = redissonProperties.getYaml();
        String json = redissonProperties.getJson();
        if (!StringUtils.isEmpty(yaml)) {
            config = Config.fromYAML(new ClassPathResource(yaml).getInputStream());
        } else if (!StringUtils.isEmpty(json)) {
            config = Config.fromJSON(new ClassPathResource(json).getInputStream());
        } else {
            config = new Config();
            config.setThreads(redissonProperties.getThreads());
            config.setNettyThreads(redissonProperties.getNettyThreads());
            config.setCodec(redissonProperties.getCodec());
            config.setReferenceEnabled(redissonProperties.isReferenceEnabled());
            config.setTransportMode(redissonProperties.getTransportMode());
            config.setLockWatchdogTimeout(redissonProperties.getLockWatchdogTimeout());
            config.setKeepPubSubOrder(redissonProperties.isKeepPubSubOrder());
            config.setUseScriptCache(redissonProperties.isUseScriptCache());

            boolean ssl = redisProperties.isSsl();

            RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
            RedisProperties.Cluster cluster = redisProperties.getCluster();
            if (sentinel != null) {
                List<String> nodes = sentinel.getNodes();
                config.useSentinelServers()
                        .setMasterName(sentinel.getMaster())
                        .addSentinelAddress(formatNode(ssl, nodes));
            } else if (cluster != null) {
                config.useClusterServers()
                        .addNodeAddress(formatNode(ssl, cluster.getNodes()));
            } else {
                config.useSingleServer().setAddress(ssl ? "rediss://" : "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
            }
        }
        return Redisson.create(config);

    }

    private String[] formatNode(boolean ssl, List<String> nodes) {
        return nodes.stream().map(node -> ssl ? "rediss://" + node : "redis://" + node)
                .distinct().toArray(String[]::new);
    }
}
