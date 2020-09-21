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
 * @author 刘镇 (suninformation@163.com) on 15/5/7 下午6:15
 * @version 1.0
 */
public final class Params {

    /**
     * 参数集合
     */
    private List<Object> __params;

    public static String wrapQuote(String param) {
        return wrapQuote(param, "'");
    }

    public static String wrapQuote(String param, String quote) {
        quote = StringUtils.trimToEmpty(quote);
        return quote + StringUtils.trimToEmpty(param) + quote;
    }

    public static Params create(Object... params) {
        return new Params(params);
    }

    private Params(Object... params) {
        this.__params = new ArrayList<Object>();
        if (params != null && params.length > 0) {
            this.__params.addAll(Arrays.asList(params));
        }
    }

    public List<Object> params() {
        return this.__params;
    }

    public Params add(Object param) {
        if (param instanceof Collection) {
            this.__params.addAll((Collection<?>) param);
        } else if (param != null && param.getClass().isArray()) {
            this.__params.addAll(Arrays.asList((Object[]) param));
        } else {
            this.__params.add(param);
        }
        return this;
    }

    public Params add(Params params) {
        this.__params.addAll(params.params());
        return this;
    }

    public Params add(Collection<Object> params) {
        this.__params.addAll(params);
        return this;
    }

    public Object[] toArray() {
        return __params.toArray(new Object[__params.size()]);
    }
}
