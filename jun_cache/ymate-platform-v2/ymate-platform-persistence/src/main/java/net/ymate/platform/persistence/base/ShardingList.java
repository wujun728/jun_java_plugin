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
package net.ymate.platform.persistence.base;

import net.ymate.platform.persistence.IShardingable;

import java.util.ArrayList;

/**
 * @author 刘镇 (suninformation@163.com) on 16/7/4 下午6:39
 * @version 1.0
 */
public class ShardingList<T> extends ArrayList<ShardingList.ShardingElement<T>> {

    public static class ShardingElement<T> implements IShardingable {

        private T element;

        private Object shardingParam;

        public ShardingElement(T element, Object shardingParam) {
            this.element = element;
            this.shardingParam = shardingParam;
        }

        public T getElement() {
            return element;
        }

        public Object getShardingParam() {
            return shardingParam;
        }
    }
}
