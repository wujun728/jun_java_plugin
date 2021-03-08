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
package com.baomidou.redisson;

import lombok.Data;
import org.redisson.client.codec.Codec;
import org.redisson.config.TransportMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.redis.redisson")
@Data
public class RedissonProperties {

    private String yaml;
    private String json;

    private int threads = 0;
    private int nettyThreads = 0;
    private Codec codec;
    private boolean referenceEnabled = true;
    private TransportMode transportMode = TransportMode.NIO;
    private long lockWatchdogTimeout = 30000L;
    private boolean keepPubSubOrder = true;
    private boolean useScriptCache = false;
}
