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

import java.io.IOException;

/**
 * 对象序列化接口
 *
 * @author Wujun
 */
public interface Serializer {

	/**
	 * 序列化器的名称，该方法仅用于打印日志的时候显示
	 * @return 返回序列化器名称
	 */
	String name();

	/**
	 * 对象序列化到字节数组
	 * @param obj  待序列化的对象
	 * @return 返回序列化数据
	 * @throws IOException io exception
	 */
	byte[] serialize(Object obj) throws IOException ;

	/**
	 * 反序列化到对象
	 * @param bytes  反序列化的数据
	 * @return 返回序列化对象
	 * @throws IOException io exception
	 */
	Object deserialize(byte[] bytes) throws IOException ;
	
}
