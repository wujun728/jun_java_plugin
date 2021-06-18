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
package net.oschina.j2cache.session;

import java.io.*;

/**
 * 对象序列化，为了确保兼容性，使用标准的 Java 序列化方法
 *
 * @author Wujun
 */
public class Serializer {

	public static byte[] write(Object obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (ObjectOutputStream oos = new ObjectOutputStream(baos)){
			oos.writeObject(obj);
			return baos.toByteArray();
		}
	}

	public static Object read(byte[] bytes) throws IOException , ClassNotFoundException {
		if(bytes == null || bytes.length == 0)
			return null;
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		try (ObjectInputStream ois = new ObjectInputStream(bais)){
			return ois.readObject();
		}
	}

}
