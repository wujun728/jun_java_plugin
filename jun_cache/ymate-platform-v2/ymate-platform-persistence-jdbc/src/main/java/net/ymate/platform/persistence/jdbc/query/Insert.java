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

/**
 * Insert语句对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/12 下午6:00
 * @version 1.0
 */
public final class Insert {

    private String __tableName;

    private Fields __fields;

    private Params __params;

    public static Insert create(String prefix, Class<? extends IEntity> entityClass) {
        return new Insert(StringUtils.defaultIfBlank(prefix, "").concat(EntityMeta.createAndGet(entityClass).getEntityName()));
    }

    public static Insert create(IEntity<?> entity) {
        return create(entity.getClass());
    }

    public static Insert create(Class<? extends IEntity> entityClass) {
        return new Insert(EntityMeta.createAndGet(entityClass).getEntityName());
    }

    public static Insert create(String tableName) {
        return new Insert(tableName);
    }

    private Insert(String tableName) {
        this.__tableName = tableName;
        this.__fields = Fields.create();
        this.__params = Params.create();
    }

    public Fields fields() {
        return this.__fields;
    }

    public Insert field(String prefix, String field, String alias) {
        this.__fields.add(prefix, field, alias);
        return this;
    }

    public Insert field(String prefix, String field) {
        this.__fields.add(prefix, field);
        return this;
    }

    public Insert field(String field) {
        this.__fields.add(field);
        return this;
    }

    public Insert field(Fields fields) {
        this.__fields.add(fields);
        return this;
    }

    public Insert field(String prefix, Fields fields) {
        for (String _field : fields.fields()) {
            this.__fields.add(prefix, _field);
        }
        return this;
    }

    public Params params() {
        return this.__params;
    }

    public Insert param(Object param) {
        this.__params.add(param);
        return this;
    }

    public Insert param(Params params) {
        this.__params.add(params);
        return this;
    }

    @Override
    public String toString() {
        return "INSERT INTO ".concat(__tableName).concat(" (")
                .concat(StringUtils.join(__fields.fields(), ", "))
                .concat(") VALUES (").concat(StringUtils.repeat("?", ", ", __params.params().size())).concat(")");
    }

    public SQL toSQL() {
        return SQL.create(this);
    }
}
