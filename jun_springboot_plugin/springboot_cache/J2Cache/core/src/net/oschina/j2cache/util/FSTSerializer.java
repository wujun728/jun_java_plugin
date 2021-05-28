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
package net.oschina.j2cache.util;

import org.nustaq.serialization.FSTConfiguration;

/**
 * 使用 FST 实现序列化
 *
 * @author Wujun
 */
public class FSTSerializer implements Serializer {

	private FSTConfiguration fstConfiguration ;

	public FSTSerializer() {
		fstConfiguration = FSTConfiguration.getDefaultConfiguration();
		fstConfiguration.setClassLoader(Thread.currentThread().getContextClassLoader());
	}

	@Override
	public String name() {
		return "fst";
	}

	@Override
	public byte[] serialize(Object obj) {
		return fstConfiguration.asByteArray(obj);
	}

	@Override
	public Object deserialize(byte[] bytes) {
		return fstConfiguration.asObject(bytes);
	}

}
