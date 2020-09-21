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
 * DELETE语句对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/12 下午6:03
 * @version 1.0
 */
public final class Delete {

    private List<String> __froms;

    private Fields __fields;

    private List<Join> __joins;

    private Where __where;

    public static Delete create(Class<? extends IEntity> entityClass) {
        return new Delete(null, EntityMeta.createAndGet(entityClass).getEntityName(), null);
    }

    public static Delete create(String prefix, Class<? extends IEntity> entityClass) {
        return new Delete(prefix, EntityMeta.createAndGet(entityClass).getEntityName(), null);
    }

    public static Delete create(Class<? extends IEntity> entityClass, String alias) {
        return new Delete(null, EntityMeta.createAndGet(entityClass).getEntityName(), alias);
    }

    public static Delete create(String prefix, Class<? extends IEntity> entityClass, String alias) {
        return new Delete(prefix, EntityMeta.createAndGet(entityClass).getEntityName(), alias);
    }

    public static Delete create(Select select) {
        Delete _target = new Delete(null, select.toString(), null);
        _target.where().param(select.getParams());
        return _target;
    }

    public static Delete create(String prefix, String tableName, String alias) {
        return new Delete(prefix, tableName, alias);
    }

    public static Delete create(String tableName, String alias) {
        return new Delete(null, tableName, alias);
    }

    public static Delete create(String tableName) {
        return new Delete(null, tableName, null);
    }

    private Delete(String prefix, String from, String alias) {
        this.__froms = new ArrayList<String>();
        this.__joins = new ArrayList<Join>();
        this.__fields = Fields.create();
        //
        this.from(prefix, from, alias);
    }

    public Delete from(Class<? extends IEntity> entityClass) {
        return from(null, EntityMeta.createAndGet(entityClass).getEntityName(), null);
    }

    public Delete from(Class<? extends IEntity> entityClass, String alias) {
        return from(null, EntityMeta.createAndGet(entityClass).getEntityName(), null);
    }

    public Delete from(String prefix, Class<? extends IEntity> entityClass, String alias) {
        return from(prefix, EntityMeta.createAndGet(entityClass).getEntityName(), alias);
    }

    public Delete from(Select select) {
        Delete _target = from(null, select.toString(), null);
        _target.where().param(select.getParams());
        return _target;
    }

    public Delete from(String tableName, String alias) {
        return from(null, tableName, alias);
    }

    public Delete from(String tableName) {
        return from(null, tableName, null);
    }

    public Delete from(String prefix, String from, String alias) {
        if (StringUtils.isNotBlank(prefix)) {
            from = prefix.concat(from);
        }
        if (StringUtils.isNotBlank(alias)) {
            from = from.concat(" ").concat(alias);
        }
        this.__froms.add(from);
        return this;
    }

    public Delete table(String tableName) {
        this.__fields.add(tableName);
        return this;
    }

    public Delete table(String prefix, String tableName) {
        this.__fields.add(prefix, tableName);
        return this;
    }

    public Delete join(Join join) {
        __joins.add(join);
        where().param(join.params());
        return this;
    }

    public Delete where(Where where) {
        where().where(where);
        return this;
    }

    public Params getParams() {
        return where().getParams();
    }

    public Delete param(Object param) {
        where().param(param);
        return this;
    }

    public Delete param(Params params) {
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
        StringBuilder _deleteSB = new StringBuilder("DELETE ");
        if (!__fields.fields().isEmpty()) {
            _deleteSB.append(StringUtils.join(__fields.fields(), ", "));
        }
        _deleteSB.append(" FROM ").append(StringUtils.join(__froms, ", "));
        //
        for (Join _join : __joins) {
            _deleteSB.append(" ").append(_join);
        }
        //
        if (__where != null) {
            _deleteSB.append(" ").append(__where);
        }
        return _deleteSB.toString();
    }

    public SQL toSQL() {
        return SQL.create(this);
    }
}
