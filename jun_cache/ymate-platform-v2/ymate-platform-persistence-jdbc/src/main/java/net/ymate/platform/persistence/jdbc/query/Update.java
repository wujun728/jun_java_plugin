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
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.base.IEntity;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Update语句对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/12 下午6:02
 * @version 1.0
 */
public final class Update {

    private List<String> __tables;

    private Fields __fields;

    private List<Join> __joins;

    private Where __where;

    public static Update create(String prefix, Class<? extends IEntity> entityClass, String alias) {
        return new Update(prefix, EntityMeta.createAndGet(entityClass).getEntityName(), alias);
    }

    public static Update create(String prefix, Class<? extends IEntity> entityClass) {
        return new Update(prefix, EntityMeta.createAndGet(entityClass).getEntityName(), null);
    }

    public static Update create(Class<? extends IEntity> entityClass) {
        return new Update(null, EntityMeta.createAndGet(entityClass).getEntityName(), null);
    }

    public static Update create(String prefix, String tableName, String alias) {
        return new Update(prefix, tableName, alias);
    }

    public static Update create(String tableName, String alias) {
        return new Update(null, tableName, alias);
    }

    public static Update create(String tableName) {
        return new Update(null, tableName, null);
    }

    private Update(String prefix, String tableName, String alias) {
        this.__tables = new ArrayList<String>();
        this.__fields = Fields.create();
        this.__joins = new ArrayList<Join>();
        //
        table(prefix, tableName, alias);
    }

    public Update table(String tableName, String alias) {
        return table(null, tableName, alias);
    }

    public Update table(String tableName) {
        return table(null, tableName, null);
    }

    public Update table(String prefix, String from, String alias) {
        if (StringUtils.isNotBlank(prefix)) {
            from = prefix.concat(from);
        }
        if (StringUtils.isNotBlank(alias)) {
            from = from.concat(" ").concat(alias);
        }
        this.__tables.add(from);
        return this;
    }

    public Fields fields() {
        return this.__fields;
    }

    public Update field(String prefix, String field) {
        this.__fields.add(prefix, field);
        return this;
    }

    public Update field(String prefix, String field, String alias) {
        this.__fields.add(prefix, field, alias);
        return this;
    }

    public Update field(String field) {
        this.__fields.add(field);
        return this;
    }

    public Update field(Fields fields) {
        this.__fields.add(fields);
        return this;
    }

    public Update field(String prefix, Fields fields) {
        for (String _field : fields.fields()) {
            this.__fields.add(prefix, _field);
        }
        return this;
    }

    public Update join(Join join) {
        __joins.add(join);
        where().param(join.params());
        return this;
    }

    public Update where(Where where) {
        where().where(where);
        return this;
    }

    public Params getParams() {
        return where().getParams();
    }

    public Update param(Object param) {
        where().param(param);
        return this;
    }

    public Update param(Params params) {
        where().param(params);
        return this;
    }

    public Where where() {
        if (this.__where == null) {
            this.__where = Where.create();
        }
        return __where;
    }

    @Override
    public String toString() {
        StringBuilder __updateSB = new StringBuilder("UPDATE ")
                .append(StringUtils.join(__tables, ", "));
        //
        for (Join _join : __joins) {
            __updateSB.append(" ").append(_join);
        }
        //
        __updateSB.append(" SET ");
        boolean _flag = false;
        for (String _field : __fields.fields()) {
            if (_flag) {
                __updateSB.append(", ");
            }
            //
            __updateSB.append(_field);
            //
            if (!_field.contains("=")) {
                __updateSB.append(" = ?");
            }
            //
            _flag = true;
        }
        //
        if (__where != null) {
            __updateSB.append(" ").append(__where);
        }
        return __updateSB.toString();
    }

    public SQL toSQL() {
        return SQL.create(this);
    }
}
