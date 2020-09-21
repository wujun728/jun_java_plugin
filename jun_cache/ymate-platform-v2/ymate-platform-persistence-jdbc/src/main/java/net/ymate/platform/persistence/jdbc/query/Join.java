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

import net.ymate.platform.persistence.Params;
import org.apache.commons.lang.StringUtils;

/**
 * 连接查询语句对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/12 下午6:04
 * @version 1.0
 */
public final class Join {

    private String __from;

    private String __alias;

    private Cond __on;

    public static Join inner(String from) {
        return inner(null, from);
    }

    public static Join inner(Select select) {
        Join _target = inner(null, select.toString());
        _target.params().add(select.getParams());
        return _target;
    }

    public static Join inner(String prefix, String from) {
        return new Join("INNER JOIN", prefix, from);
    }

    //

    public static Join left(String from) {
        return left(null, from);
    }

    public static Join left(Select select) {
        Join _target = left(null, select.toString());
        _target.params().add(select.getParams());
        return _target;
    }

    public static Join left(String prefix, String from) {
        return new Join("LEFT JOIN", prefix, from);
    }

    //

    public static Join right(String from) {
        return right(null, from);
    }

    public static Join right(Select select) {
        Join _target = right(null, select.toString());
        _target.params().add(select.getParams());
        return _target;
    }

    public static Join right(String prefix, String from) {
        return new Join("RIGHT JOIN", prefix, from);
    }

    private Join(String type, String prefix, String from) {
        if (StringUtils.isNotBlank(prefix)) {
            from = prefix.concat(from);
        }
        __from = type.concat(" ").concat(from);
        __on = Cond.create();
    }

    public Join alias(String alias) {
        __alias = alias;
        return this;
    }

    public Join on(Cond cond) {
        __on.cond(cond);
        return this;
    }

    public Params params() {
        return __on.params();
    }

    @Override
    public String toString() {
        __alias = StringUtils.trimToNull(__alias);
        if (__alias == null) {
            __alias = "";
        } else {
            __alias = " ".concat(__alias);
        }
        return __from + __alias + " ON " + __on;
    }
}
