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
 * @author Wujun
 */
public class NullCacheProvider implements CacheProvider {

	private final static NullCache cache = new NullCache();

	@Override
	public String name() {
		return "none";
	}

	@Override
	public int level() {
		return CacheObject.LEVEL_1 | CacheObject.LEVEL_2;
	}

	@Override
	public Cache buildCache(String regionName, CacheExpiredListener listener) throws CacheException {
		return cache;
	}

	@Override
	public Cache buildCache(String region, long timeToLiveInSeconds, CacheExpiredListener listener) {
		return cache;
	}

	@Override
	public void start(Properties props) throws CacheException {
	}

	@Override
	public Collection<CacheChannel.Region> regions() {
		return null;
	}

	@Override
	public void stop() {
	}

}
