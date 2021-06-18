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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 使用 fastjson 进行对象的 JSON 格式序列化
 *
 * @author Wujun
 */
public class FastjsonSerializer implements Serializer {

    @Override
    public String name() {
        return "fastjson";
    }

    @Override
    public byte[] serialize(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteClassName).getBytes();
    }

    @Override
    public Object deserialize(byte[] bytes) {
        return JSON.parse(new String(bytes), Feature.SupportAutoType);
    }

}
