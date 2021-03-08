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
import java.util.Properties;

/**
 * Support for pluggable caches.
 * @author Wujun
 */
public interface CacheProvider {

	/**
	 * 缓存的标识名称
	 * @return return cache provider name
	 */
	String name();

	/**
	 * 缓存的层级
	 * @return current provider level
	 */
	int level();

	default boolean isLevel(int level) {
		return (level() & level) == level;
	}
	
	/**
	 * Configure the cache
	 *
	 * @param regionName the name of the cache region
	 * @param listener listener for expired elements
	 * @return return cache instance
	 */
	Cache buildCache(String regionName, CacheExpiredListener listener);

	/**
	 * Configure the cache with timeToLiveInMills
	 * @param region cache region name
	 * @param timeToLiveInSeconds time to live in second
	 * @param listener listener for expired elements
	 * @return return cache instance
	 */
	Cache buildCache(String region, long timeToLiveInSeconds, CacheExpiredListener listener);

	/**
	 * Remove a cache region
	 * @param region cache region name
	 */
	default void removeCache(String region) {}

	/**
	 * Return all channels defined in first level cache
	 * @return all regions name
	 */
	Collection<CacheChannel.Region> regions();

	/**
	 * Callback to perform any necessary initialization of the underlying cache implementation
	 * during SessionFactory construction.
	 *
	 * @param props current configuration settings.
	 */
	void start(Properties props);

	/**
	 * Callback to perform any necessary cleanup of the underlying cache implementation
	 * during SessionFactory.close().
	 */
	void stop();

}
