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
import org.nustaq.serialization.coders.FSTJsonFieldNames;
import org.nustaq.serialization.serializers.FSTDateSerializer;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * 使用 FST 的 JSON 对象序列化
 * 用法：
 *
 * j2cache.serialization = json
 * json.map.list = java.util.Arrays$ArrayList
 * json.map.person = net.oschina.j2cache.demo.Person
 *
 * @author Wujun
 */
public class FstJSONSerializer implements Serializer {

    private static final FSTConfiguration conf = FSTConfiguration.createJsonConfiguration();
    private static final String PREFIX = "map.";

    public FstJSONSerializer(Properties props) {
        conf.setJsonFieldNames(new FSTJsonFieldNames("@type", "@object", "@stype", "@seq", "@enum", "@value", "@ref"));
        conf.registerCrossPlatformClassMapping("list", "java.util.Arrays$ArrayList");
        conf.registerSerializer(Timestamp.class, new FSTDateSerializer(), true);
        conf.registerSerializer(Date.class, new FSTDateSerializer(), true);
        if(props != null)
            props.forEach((k,v) -> {
                String key = (String)k;
                String value = (String)v;
                if(key.startsWith(PREFIX) && value != null && value.trim().length() > 0)
                    conf.registerCrossPlatformClassMapping(key.substring(PREFIX.length()), value.trim());
            });
    }

    @Override
    public String name() {
        return "json";
    }

    @Override
    public byte[] serialize(Object obj) {
        return conf.asByteArray(obj);
    }

    @Override
    public Object deserialize(byte[] bytes) {
        return conf.asObject(bytes);
    }

}