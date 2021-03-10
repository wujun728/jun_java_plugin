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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 使用 Kryo 实现序列化
 *
 * @author Wujun
 */
public class KryoSerializer implements Serializer {

    @Override
	public String name() {
		return "kryo";
	}

	@Override
	public byte[] serialize(Object obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (Output output = new Output(baos);){
			new Kryo().writeClassAndObject(output, obj);
			output.flush();
			return baos.toByteArray();
		}
	}

	@Override
	public Object deserialize(byte[] bits) {
		if(bits == null || bits.length == 0)
			return null;
		try (Input ois = new Input(new ByteArrayInputStream(bits))){
			return new Kryo().readClassAndObject(ois);
		}
	}
	
}
