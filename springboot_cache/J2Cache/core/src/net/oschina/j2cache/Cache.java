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

import java.util.Collection;
import java.util.Map;

/**
 * Cache Data Operation Interface
 *
 * @author Wujun
 */
public interface Cache {

	/**
	 * Get an item from the cache, nontransactionally
	 * 
	 * @param key cache key
	 * @return the cached object or null
	 */
	<V> V get(String key) ;

	/**
	 * 批量获取缓存对象
	 * @param keys cache keys
	 * @return return key-value objects
	 */
	<V> Map<String, V> get(Collection<String> keys);

	/**
	 * 判断缓存是否存在
	 * @param key cache key
	 * @return true if key exists
	 */
	default boolean exists(String key) {
		return get(key) != null;
	}
	
	/**
	 * Add an item to the cache, nontransactionally, with
	 * failfast semantics
	 *
	 * @param key cache key
	 * @param value cache value
	 */
	<V> void put(String key, V value);

	/**
	 * 批量插入数据
	 * @param elements objects to be put in cache
	 */
	<V> void put(Map<String, V> elements);

	/**
	 * Return all keys
	 *
	 * @return 返回键的集合
	 */
	Collection<String> keys() ;
	
	/**
	 * Remove items from the cache
	 *
	 * @param keys Cache key
	 */
	void evict(String...keys);

	/**
	 * Clear the cache
	 */
	void clear();

}
