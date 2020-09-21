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
package net.ymate.platform.cache;

import java.io.Serializable;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/3 下午2:42
 * @version 1.0
 */
public class CacheElement implements Serializable {

    private Object __object;

    private long __lastUpdateTime;

    private int __timeout;

    public CacheElement() {
        __lastUpdateTime = System.currentTimeMillis();
    }

    public CacheElement(final Object object) {
        __object = object;
        __lastUpdateTime = System.currentTimeMillis();
    }

    public CacheElement(final Object object, final int timeout) {
        this(object);
        __timeout = timeout;
    }

    public Object getObject() {
        return __object;
    }

    public void setObject(Object object) {
        __object = object;
    }

    public long getLastUpdateTime() {
        return __lastUpdateTime;
    }

    public int getTimeout() {
        return __timeout;
    }

    public void setTimeout(int timeout) {
        this.__timeout = timeout;
    }

    public CacheElement touch() {
        __lastUpdateTime = System.currentTimeMillis();
        return this;
    }

    public boolean isExpired() {
        return ((System.currentTimeMillis() - __lastUpdateTime) >= __timeout * 1000);
    }
}
