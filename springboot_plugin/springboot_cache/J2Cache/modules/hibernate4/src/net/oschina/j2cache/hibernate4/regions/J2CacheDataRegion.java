/**
 * Copyright (c) 2015-2017.
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
package net.oschina.j2cache.hibernate4.regions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.oschina.j2cache.CacheObject;
import net.oschina.j2cache.hibernate4.CacheRegion;
import net.oschina.j2cache.hibernate4.strategy.J2CacheAccessStrategyFactory;
import net.oschina.j2cache.hibernate4.util.Timestamper;

public abstract class J2CacheDataRegion implements Region {

    private static final Logger LOG = LoggerFactory.getLogger(J2CacheDataRegion.class);
    private static final String CACHE_LOCK_TIMEOUT_PROPERTY = "hibernate.cache_lock_timeout";
    private static final int DEFAULT_CACHE_LOCK_TIMEOUT = 60000;

    private final CacheRegion cache;
    private final J2CacheAccessStrategyFactory accessStrategyFactory;
    private final int cacheLockTimeout;

    J2CacheDataRegion(J2CacheAccessStrategyFactory accessStrategyFactory, CacheRegion cache, Properties properties) {
        this.accessStrategyFactory = accessStrategyFactory;
        this.cache = cache;
        final String timeout = properties.getProperty(CACHE_LOCK_TIMEOUT_PROPERTY, Integer.toString(DEFAULT_CACHE_LOCK_TIMEOUT));
        this.cacheLockTimeout = Timestamper.ONE_MS * Integer.decode(timeout);
    }

    protected CacheRegion getCache() {
        return cache;
    }

    public CacheRegion getJ2Cache() {
        return getCache();
    }

    protected J2CacheAccessStrategyFactory getAccessStrategyFactory() {
        return accessStrategyFactory;
    }

    public String getName() {
        return getCache().getName();
    }

    public void destroy() throws CacheException {
        try {
            getCache().clear();
        } catch (IllegalStateException e) {
            LOG.debug("This can happen if multiple frameworks both try to shutdown ehcache", e);
        }
    }

    public long getSizeInMemory() {
        return -1;
    }

    @Override
    public long getElementCountInMemory() {
        return -1;
    }

    @Override
    public long getElementCountOnDisk() {
        return -1;
    }

    @Override
    public Map toMap() {
        try {
            Map<Object, Object> result = new HashMap<Object, Object>();
            for (Object key : cache.keys()) {
                CacheObject e = cache.get(key);
                if (e != null) {
                    result.put(key, e.getValue());
                }
            }
            return result;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public long nextTimestamp() {
        return Timestamper.next();
    }

    @Override
    public int getTimeout() {
        return cacheLockTimeout;
    }

    @Override
    public boolean contains(Object key) {
        return false;
    }

}