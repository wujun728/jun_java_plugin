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
package net.oschina.j2cache.hibernate4;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.hibernate.HibernateException;

import java.io.IOException;
import java.io.Serializable;

public class J2CacheCacheRegion implements CacheRegion {

    private CacheChannel cacheChannel;
    private String region;

    public J2CacheCacheRegion(CacheChannel channel, String region) {
        this.cacheChannel = channel;
        this.region = region;
    }

    @Override
    public String getName() {
        return this.region;
    }

    @Override
    public void clear() {
        this.cacheChannel.clear(this.region);
    }

    @Override
    public CacheObject get(Object key) {
        return this.cacheChannel.get(this.region, (String) key);
    }

    @Override
    public void put(Object key, Object value) {
            this.cacheChannel.set(this.region, (String) key, value);
    }

    @Override
    public void evict(Object key) {
        this.cacheChannel.evict(this.region, (String)key);
    }

    public Iterable<? extends Object> keys() {
        return this.cacheChannel.keys(this.region);
    }
}
