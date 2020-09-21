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
package net.ymate.platform.cache.support;

import java.io.Serializable;

/**
 * @author 刘镇 (suninformation@163.com) on 15/12/7 上午9:15
 * @version 1.0
 */
public class MultilevelKey implements Serializable {

    private Object __key;

    private boolean __master;

    public static MultilevelKey bind(Object keyObj) {
        if (keyObj instanceof MultilevelKey) {
            return (MultilevelKey) keyObj;
        }
        return new MultilevelKey(keyObj);
    }

    public MultilevelKey(Object key, boolean master) {
        __key = key;
        __master = master;
    }

    public MultilevelKey(Object key) {
        this(key, true);
    }

    public Object getKey() {
        return __key;
    }

    public boolean isMaster() {
        return __master;
    }

    @Override
    public String toString() {
        if (__key instanceof String || __key instanceof StringBuilder || __key instanceof StringBuffer || __key instanceof Number) {
            return __key.toString();
        }
        return __key + "";
    }
}
