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
package net.ymate.platform.core.lang;

import java.io.Serializable;

/**
 * 结对对象类型
 *
 * @author 刘镇 (suninformation@163.com) on 2010-4-17 上午12:07:42
 * @version 1.0
 */
public class PairObject<K, V> implements Serializable, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = -6239279408656130276L;

    /**
     * 对数据中的键
     */
    private K key;

    /**
     * 对数据中的值
     */
    private V value;

    public PairObject() {
    }

    public PairObject(K key) {
        this.key = key;
    }

    public PairObject(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PairObject) {
            PairObject<?, ?> _o = (PairObject<?, ?>) obj;
            if (this.getKey().equals(_o.getKey()) && this.getValue().equals(_o.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{" + "key : '" + this.key + "', value : '" + this.value + "'}";
    }

}
