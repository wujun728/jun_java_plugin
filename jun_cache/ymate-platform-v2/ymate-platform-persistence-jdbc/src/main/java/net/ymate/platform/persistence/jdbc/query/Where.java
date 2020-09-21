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

import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.Params;
import org.apache.commons.lang.StringUtils;

/**
 * Where条件及参数对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/7 下午1:19
 * @version 1.0
 */
public final class Where {

    /**
     * SQL条件对象
     */
    private Cond __cond;

    private GroupBy __groupBy;

    private OrderBy __orderBy;

    public static Where create() {
        return new Where();
    }

    public static Where create(String whereCond) {
        return new Where(whereCond);
    }

    public static Where create(Cond cond) {
        return new Where(cond);
    }

    private Where() {
        __orderBy = OrderBy.create();
        __cond = Cond.create();
    }

    private Where(String whereCond) {
        this();
        __cond.cond(whereCond);
    }

    private Where(Cond cond) {
        __orderBy = OrderBy.create();
        __cond = cond;
    }

    public Where where(Where where) {
        __cond.cond(where.cond());
        __orderBy.orderBy(where.orderBy());
        //
        if (where.groupBy() != null) {
            if (__groupBy != null) {
                __groupBy.fields().add(where.groupBy().fields());
                __groupBy.having().cond(where.groupBy().having());
            } else {
                __groupBy = where.groupBy();
            }
        }
        return this;
    }

    public Cond cond() {
        return __cond;
    }

    public GroupBy groupBy() {
        return __groupBy;
    }

    public OrderBy orderBy() {
        return __orderBy;
    }

    /**
     * @return 此方法仅返回只读参数集合, 若要维护参数请调用where().param(...)相关方法
     */
    public Params getParams() {
        Params _p = Params.create().add(__cond.params());
        if (__groupBy != null && __groupBy.having() != null) {
            _p.add(__groupBy.having().params());
        }
        return _p;
    }

    public String toSQL() {
        StringBuilder _whereSB = new StringBuilder("");
        if (__cond != null && StringUtils.isNotBlank(__cond.toString())) {
            _whereSB.append("WHERE ").append(__cond.toString());
        }
        if (__groupBy != null) {
            _whereSB.append(" ").append(__groupBy);
        }
        return _whereSB.toString();
    }

    public Where param(Object param) {
        __cond.param(param);
        return this;
    }

    public Where param(Params params) {
        __cond.param(params);
        return this;
    }

    public Where groupBy(String field) {
        groupBy(Fields.create().add(field));
        return this;
    }

    public Where groupBy(Fields fields) {
        __groupBy = GroupBy.create(fields);
        return this;
    }

    public Where groupBy(String prefix, String field) {
        __groupBy = GroupBy.create(prefix, field);
        return this;
    }

    public Where groupBy(GroupBy groupBy) {
        __groupBy = groupBy;
        return this;
    }

    public Where having(Cond cond) {
        __groupBy.having(cond);
        return this;
    }

    public Where orderAsc(String field) {
        __orderBy.asc(field);
        return this;
    }

    public Where orderAsc(String prefix, String field) {
        __orderBy.asc(prefix, field);
        return this;
    }

    public Where orderDesc(String field) {
        __orderBy.desc(field);
        return this;
    }

    public Where orderDesc(String prefix, String field) {
        __orderBy.desc(prefix, field);
        return this;
    }

    @Override
    public String toString() {
        return toSQL() + " " + __orderBy;
    }
}
