/*
 * Copyright 2007-2015 the original author or authors.
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
package net.ymate.platform.persistence.mongodb.support;

import com.mongodb.BasicDBObject;
import net.ymate.platform.persistence.mongodb.IBsonable;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/22 下午8:46
 * @version 1.0
 */
public final class OrderBy implements IBsonable {

    private BasicDBObject __orderBy;

    private OrderBy() {
        this.__orderBy = new BasicDBObject();
    }

    public static OrderBy create() {
        return new OrderBy();
    }

    public OrderBy desc(String key) {
        this.__orderBy.put(key, com.mongodb.operation.OrderBy.DESC);
        return this;
    }

    public OrderBy asc(String key) {
        this.__orderBy.put(key, com.mongodb.operation.OrderBy.ASC);
        return this;
    }

    public BasicDBObject toBson() {
        return this.__orderBy;
    }
}
