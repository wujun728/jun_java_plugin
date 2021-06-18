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
package net.oschina.j2cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * J2Cache configurations
 * @author Wujun
 */
public class J2CacheConfig {

    private final static Logger log = LoggerFactory.getLogger(J2CacheConfig.class);

    private Properties properties = new Properties();
    private Properties broadcastProperties = new Properties();
    private Properties l1CacheProperties = new Properties();
    private Properties l2CacheProperties = new Properties();

    private String broadcast;
    private String l1CacheName;
    private String l2CacheName;
    private String serialization;
    private boolean syncTtlToRedis;
    private boolean defaultCacheNullObject;

    public final static J2CacheConfig initFromConfig(String configResource) throws IOException {
        try (InputStream stream = getConfigStream(configResource)){
            return initFromConfig(stream);
        }
    }

    public final static J2CacheConfig initFromConfig(File configFile) throws IOException {
        try (FileInputStream stream = new FileInputStream(configFile)) {
            return initFromConfig(stream);
        }
    }

    public final static J2CacheConfig initFromConfig(InputStream stream) throws IOException {
        J2CacheConfig config = new J2CacheConfig();
        config.properties.load(stream);
        config.serialization = trim(config.properties.getProperty("j2cache.serialization"));
        config.broadcast = trim(config.properties.getProperty("j2cache.broadcast"));
        config.l1CacheName = trim(config.properties.getProperty("j2cache.L1.provider_class"));
        config.l2CacheName = trim(config.properties.getProperty("j2cache.L2.provider_class"));
        config.syncTtlToRedis = !"false".equalsIgnoreCase(trim(config.properties.getProperty("j2cache.sync_ttl_to_redis")));
        config.defaultCacheNullObject = "true".equalsIgnoreCase(trim(config.properties.getProperty("j2cache.default_cache_null_object")));

        String l2_config_section = trim(config.properties.getProperty("j2cache.L2.config_section"));
        if (l2_config_section == null || l2_config_section.trim().equals(""))
            l2_config_section = config.l2CacheName;

        config.broadcastProperties = config.getSubProperties(config.broadcast);
        config.l1CacheProperties = config.getSubProperties(config.l1CacheName);
        config.l2CacheProperties = config.getSubProperties(l2_config_section);
        return config;
    }

    /**
     * read sub properties by prefix
     * @param i_prefix  prefix of config
     * @return properties without prefix
     */
    public Properties getSubProperties(String i_prefix) {
        Properties props = new Properties();
        final String prefix = i_prefix + '.';
        properties.forEach((k,v) -> {
            String key = (String)k;
            if(key.startsWith(prefix))
                props.setProperty(key.substring(prefix.length()), trim((String)v));
        });
        return props;
    }

    /**
     * get j2cache properties stream
     * @return
     */
    private static InputStream getConfigStream(String resource) {
        log.info("Load J2Cache Config File : [{}].", resource);
        InputStream configStream = J2Cache.class.getResourceAsStream(resource);
        if(configStream == null)
            configStream = J2Cache.class.getClassLoader().getParent().getResourceAsStream(resource);
        if(configStream == null)
            throw new CacheException("Cannot find " + resource + " !!!");
        return configStream;
    }

    public void dump(PrintStream writer) {
        writer.printf("j2cache.serialization = %s%n", this.serialization);
        writer.printf("[%s]%n",this.broadcast);
        broadcastProperties.list(writer);
        writer.printf("[%s]%n",this.l1CacheName);
        l1CacheProperties.list(writer);
        writer.printf("[%s]%n",this.l2CacheName);
        l2CacheProperties.list(writer);
    }

    public Properties getProperties() {
        return properties;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public boolean isSyncTtlToRedis() {
        return syncTtlToRedis;
    }

    public void setSyncTtlToRedis(boolean syncTtlToRedis) {
        this.syncTtlToRedis = syncTtlToRedis;
    }

    public boolean isDefaultCacheNullObject() {
        return defaultCacheNullObject;
    }

    public void setDefaultCacheNullObject(boolean defaultCacheNullObject) {
        this.defaultCacheNullObject = defaultCacheNullObject;
    }

    public String getL1CacheName() {
        return l1CacheName;
    }

    public void setL1CacheName(String provider1) {
        this.l1CacheName = provider1;
    }

    public String getL2CacheName() {
        return l2CacheName;
    }

    public void setL2CacheName(String provider2) {
        this.l2CacheName = provider2;
    }

    public String getSerialization() {
        return serialization;
    }

    public void setSerialization(String serialization) {
        this.serialization = serialization;
    }

    public Properties getBroadcastProperties() {
        return broadcastProperties;
    }

    public void setBroadcastProperties(Properties broadcastProperties) {
        this.broadcastProperties = broadcastProperties;
    }

    public Properties getL1CacheProperties() {
        return l1CacheProperties;
    }

    public void setL1CacheProperties(Properties l1CacheProperties) {
        this.l1CacheProperties = l1CacheProperties;
    }

    public Properties getL2CacheProperties() {
        return l2CacheProperties;
    }

    public void setL2CacheProperties(Properties l2CacheProperties) {
        this.l2CacheProperties = l2CacheProperties;
    }

    private static String trim(String str) {
        return (str != null) ? str.trim() : null;
    }
}
