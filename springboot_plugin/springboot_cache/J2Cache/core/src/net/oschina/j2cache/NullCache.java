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
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 空的缓存Provider
 * @author Wujun
 */
public class NullCache implements Level1Cache, Level2Cache {

	@Override
	public long ttl() {
		return -1;
	}

	@Override
	public long size() {
		return 0;
	}

	@Override
	public <V> V get(String key) {
		return null;
	}

	@Override
	public void put(String key, Object value) {
	}

	@Override
	public Collection<String> keys() {
		return Collections.emptyList();
	}

	@Override
	public <V> Map<String, V> get(Collection<String> keys) {
		return Collections.emptyMap();
	}

	@Override
	public boolean exists(String key) {
		return false;
	}

	@Override
	public <V> void put(Map<String, V> elements)  {
	}

	@Override
	public byte[] getBytes(String key) {
		return null;
	}

	@Override
	public List<byte[]> getBytes(Collection<String> key) {
		return Collections.emptyList();
	}

	@Override
	public void setBytes(String key, byte[] bytes) {
	}

	@Override
	public void setBytes(Map<String, byte[]> bytes) {
	}

	@Override
	public void evict(String... keys) {
	}

	@Override
	public void clear() {

	}
}
