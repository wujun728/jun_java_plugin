/**
 * Copyright (c) 2015-2017
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

import java.io.IOException;

import org.xerial.snappy.Snappy;

/**
 * 在 @FSTSerializer 的基础上增加 snappy ，也就是压缩
 */
public class FstSnappySerializer implements Serializer {

	private final Serializer inner;

	public FstSnappySerializer() {
		this(new FSTSerializer());
	}

	public FstSnappySerializer(Serializer innerSerializer) {
		this.inner = innerSerializer;
	}
	
	
	@Override
	public String name() {
		return "fst-snappy";
	}
	
	@Override
	public byte[] serialize(Object obj) throws IOException {
		return Snappy.compress(inner.serialize(obj));
	}

	@Override
	public Object deserialize(byte[] bytes) throws IOException {
		if (bytes == null || bytes.length == 0)
			return null;
		return inner.deserialize(Snappy.uncompress(bytes));
	}
}
