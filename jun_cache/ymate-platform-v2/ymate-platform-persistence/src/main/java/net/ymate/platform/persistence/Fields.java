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
package net.ymate.platform.persistence;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 字段过滤对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/7 下午4:06
 * @version 1.0
 */
public final class Fields {

    /**
     * 字段名称集合
     */
    private List<String> __fields;

    /**
     * 是否为排除字段集合
     */
    private boolean __excluded;

    /**
     * @param prefix 字段名前缀
     * @param field  字段名
     * @return 组合后的字段名称
     */
    public static String field(String prefix, String field) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return field;
    }

    public static Fields create(String... fields) {
        return new Fields(fields);
    }

    private Fields(String... fields) {
        this.__fields = new ArrayList<String>();
        if (fields != null && fields.length > 0) {
            this.__fields.addAll(Arrays.asList(fields));
        }
    }

    public Fields add(String prefix, String field) {
        this.__fields.add(field(prefix, field));
        return this;
    }

    public Fields add(String prefix, String field, String alias) {
        field = field(prefix, field);
        if (StringUtils.isNotBlank(alias)) {
            field = field.concat(" ").concat(alias);
        }
        this.__fields.add(field);
        return this;
    }

    public Fields add(String field) {
        this.__fields.add(field);
        return this;
    }

    public Fields add(IFunction func) {
        this.__fields.add(func.build());
        return this;
    }

    public Fields add(Fields fields) {
        this.__fields.addAll(fields.fields());
        this.__excluded = fields.isExcluded();
        return this;
    }

    public Fields add(Collection<String> fields) {
        this.__fields.addAll(fields);
        return this;
    }

    public Fields excluded(boolean excluded) {
        this.__excluded = excluded;
        return this;
    }

    public boolean isExcluded() {
        return __excluded;
    }

    public List<String> fields() {
        return this.__fields;
    }

    public String[] toArray() {
        return __fields.toArray(new String[__fields.size()]);
    }
}
