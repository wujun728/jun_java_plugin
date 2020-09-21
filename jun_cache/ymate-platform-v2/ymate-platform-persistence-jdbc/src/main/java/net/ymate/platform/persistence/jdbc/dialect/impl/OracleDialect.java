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
package net.ymate.platform.persistence.jdbc.dialect.impl;

import net.ymate.platform.core.util.ExpressionUtils;
import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.IShardingable;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.base.IEntity;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.dialect.AbstractDialect;
import org.apache.commons.lang.StringUtils;

/**
 * Oracle数据库方言接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-30 下午01:55:13
 * @version 1.0
 */
public class OracleDialect extends AbstractDialect {

    public OracleDialect() {
//        this.identifierQuote_begin = this.identifierQuote_end = "\"";
    }

    public String getName() {
        return JDBC.DATABASE.ORACLE.name();
    }

    @Override
    public String getSequenceNextValSql(String sequenceName) {
        return sequenceName.concat(".nextval");
    }

    @Override
    public String buildInsertSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable, Fields fields) {
        EntityMeta _meta = EntityMeta.createAndGet(entityClass);
        ExpressionUtils _exp = ExpressionUtils.bind("INSERT INTO ${table_name} (${fields}) VALUES (${values})")
                .set("table_name", buildTableName(prefix, _meta, shardingable));
        //
        Fields _fields = Fields.create();
        Fields _values = Fields.create();
        for (String _fieldName : (fields == null || fields.fields().isEmpty() ? _meta.getPropertyNames() : fields.fields())) {
            EntityMeta.PropertyMeta _propMeta = _meta.getPropertyByName(_fieldName);
            if (_propMeta.isAutoincrement()) {
                //  若主键指定了序列, 则该主建需加到字段集合中
                if (StringUtils.isNotBlank(_propMeta.getSequenceName())) {
                    _fields.add(_fieldName);
                    _values.add(getSequenceNextValSql(_propMeta.getSequenceName()));
                }
            } else {
                _fields.add(_fieldName);
                _values.add("?");
            }
        }
        __doValidProperty(_meta, _fields, false);
        return _exp.set("fields", __doGenerateFieldsFormatStr(_fields, null, null)).set("values", StringUtils.join(_values.fields(), ", ")).getResult();
    }

    @Override
    public String buildPagedQuerySQL(String originSql, int page, int pageSize) {
        StringBuilder _returnValue = new StringBuilder(originSql.length() + 100);
        int _limit = ((page - 1) * pageSize);
        if (pageSize == 0) {
            _returnValue.append("SELECT * FROM ( ").append(originSql).append(" ) WHERE rownum <= ").append(Integer.toString(_limit));
        } else {
            _returnValue.append("SELECT * FROM ( SELECT row_.*, rownum rownum_ FROM ( ").append(originSql);
            _returnValue.append(" ) row_ ) WHERE rownum_ > ").append(Integer.toString(_limit)).append(" AND rownum_ <= ").append(Integer.toString(_limit + pageSize));
        }
        return _returnValue.toString();
    }
}
