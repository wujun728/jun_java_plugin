/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.cache.impl;

import net.ymate.platform.cache.IKeyGenerator;
import net.ymate.platform.cache.ISerializer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/3 下午1:43
 * @version 1.0
 */
public class DefaultKeyGenerator implements IKeyGenerator {

    private ISerializer __serializer;

    public void init(ISerializer serializer) {
        if (serializer == null) {
            __serializer = new DefaultSerializer();
        } else {
            __serializer = serializer;
        }
    }

    public Serializable generateKey(Method method, Object[] params) throws Exception {
        // [className:methodName:{serializeStr}]
        StringBuilder __keyGenBuilder = new StringBuilder();
        __keyGenBuilder
                .append("[").append(method.getDeclaringClass().getName())
                .append(":").append(method.getName()).append("{");
        String _paramsB64 = Base64.encodeBase64String(__serializer.serialize(params));
        __keyGenBuilder.append(_paramsB64).append("}]");
        return DigestUtils.md5Hex(__keyGenBuilder.toString());
    }
}
