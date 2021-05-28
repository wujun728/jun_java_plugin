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
package net.oschina.j2cache.memcached;

import net.oschina.j2cache.*;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Memcached 缓存管理
 *
 * @author Wujun
 */
public class XmemcachedCacheProvider implements CacheProvider {

    private static final Logger log = LoggerFactory.getLogger(XmemcachedCacheProvider.class);
    private MemcachedClient client ;

    private static final ConcurrentHashMap<String, Level2Cache> regions = new ConcurrentHashMap();

    @Override
    public String name() {
        return "memcached";
    }

    @Override
    public void start(Properties props) {

        long ct = System.currentTimeMillis();

        String servers = props.getProperty("servers");
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(servers));
        builder.setCommandFactory(new BinaryCommandFactory());
        boolean needAuth = username != null && password != null && username.trim().length() > 0 && password.trim().length() > 0;
        if(needAuth)
            builder.addAuthInfo(AddrUtil.getOneAddress(servers), AuthInfo.typical(username, password));

        builder.setConnectionPoolSize(Integer.valueOf(props.getProperty("connectionPoolSize")));
        builder.setConnectTimeout(Long.valueOf(props.getProperty("connectTimeout")));
        builder.setHealSessionInterval(Long.valueOf(props.getProperty("healSessionInterval")));
        builder.setMaxQueuedNoReplyOperations(Integer.valueOf(props.getProperty("maxQueuedNoReplyOperations")));
        builder.setOpTimeout(Long.valueOf(props.getProperty("opTimeout")));
        builder.setSanitizeKeys("true".equalsIgnoreCase(props.getProperty("sanitizeKeys")));

        try {
            client = builder.build();

            log.info(String.format("Memcached client starts with servers(%s),auth(%s),pool-size(%s),time(%dms)",
                    servers,
                    needAuth,
                    builder.getConfiguration().getSelectorPoolSize(),
                    System.currentTimeMillis() - ct
            ));
        } catch (IOException e) {
            log.error("Failed to connect to memcached", e);
        }
    }

    @Override
    public int level() {
        return CacheObject.LEVEL_2;
    }

    @Override
    public Cache buildCache(String region, CacheExpiredListener listener) {
        return regions.computeIfAbsent(region, v -> new MemCache(region, client));
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
    public void stop() {
        try {
            this.regions().clear();
            this.client.shutdown();
        } catch (IOException e) {
            log.error("Failed to disconnect to memcached", e);
        }
    }
}
