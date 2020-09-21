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
package net.ymate.platform.persistence.jdbc.query;

import org.apache.commons.lang.StringUtils;

/**
 * 排序对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/7 上午10:55
 * @version 1.0
 */
public final class OrderBy {

    private StringBuilder __orderBySB;

    public static OrderBy create() {
        return new OrderBy();
    }

    private OrderBy() {
        __orderBySB = new StringBuilder();
    }

    public OrderBy orderBy(OrderBy orderBy) {
        String _orderBy = StringUtils.substringAfter(orderBy.toSQL(), "ORDER BY ");
        if (StringUtils.isNotBlank(_orderBy)) {
            if (__orderBySB.length() > 0) {
                __orderBySB.append(", ");
            }
            __orderBySB.append(_orderBy);
        }
        return this;
    }

    public OrderBy asc(String field) {
        return asc(null, field);
    }

    public OrderBy asc(String prefix, String field) {
        if (__orderBySB.length() > 0) {
            __orderBySB.append(", ");
        }
        if (StringUtils.isNotBlank(prefix)) {
            __orderBySB.append(prefix).append(".");
        }
        __orderBySB.append(field);
        return this;
    }

    public OrderBy desc(String field) {
        return desc(null, field);
    }

    public OrderBy desc(String prefix, String field) {
        if (__orderBySB.length() > 0) {
            __orderBySB.append(", ");
        }
        if (StringUtils.isNotBlank(prefix)) {
            __orderBySB.append(prefix).append(".");
        }
        __orderBySB.append(field).append(" DESC");
        return this;
    }

    public String toSQL() {
        StringBuilder _returnSB = new StringBuilder();
        if (__orderBySB.length() > 0) {
            _returnSB.append("ORDER BY ").append(__orderBySB);
        }
        return _returnSB.toString();
    }

    @Override
    public String toString() {
        return toSQL();
    }
}
