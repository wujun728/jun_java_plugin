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
package net.oschina.j2cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import net.oschina.j2cache.Level1Cache;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Caffeine cache
 *
 * @author Wujun
 */
public class CaffeineCache implements Level1Cache {

    private Cache<String, Object> cache;
    private long size ;
    private long expire ;

    public CaffeineCache(Cache<String, Object> cache, long size, long expire) {
        this.cache = cache;
        this.size = size;
        this.expire = expire;
    }

    @Override
    public long ttl() {
        return expire;
    }

    @Override
    public long size() { return size; }

	@Override
	@SuppressWarnings("unchecked")
    public <V> V get(String key) {
        return (V) cache.getIfPresent(key);
    }

	@Override
	@SuppressWarnings("unchecked")
    public <V> Map<String, V> get(Collection<String> keys) {
        return (Map<String, V>) cache.getAllPresent(keys);
    }

    @Override
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public <V> void put(Map<String, V> elements) {
        cache.putAll(elements);
    }

    @Override
    public Collection<String> keys() {
        return cache.asMap().keySet();
    }

    @Override
    public void evict(String...keys) {
        cache.invalidateAll(Arrays.asList(keys));
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }
}
