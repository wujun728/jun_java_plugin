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
package net.ymate.platform.persistence.jdbc.dialect;

import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.ExpressionUtils;
import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.IShardingRule;
import net.ymate.platform.persistence.IShardingable;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.base.IEntity;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 数据库方言接口抽象实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-30 下午01:55:13
 * @version 1.0
 */
public abstract class AbstractDialect implements IDialect {

    protected static final String __LINE_END_FLAG = ",\n";

    /**
     * 引用标识符-开始
     */
    protected String identifierQuote_begin = "";

    /**
     * 引用标识符-结束
     */
    protected String identifierQuote_end = "";

    public String wrapIdentifierQuote(String origin) {
        return identifierQuote_begin.concat(origin).concat(identifierQuote_end);
    }

    public Object[] getGeneratedKey(Statement statement) throws SQLException {
        // 检索由于执行此 Statement 对象而创建的所有自动生成的键
        List<Object> _ids = new ArrayList<Object>();
        ResultSet _keyRSet = statement.getGeneratedKeys();
        try {
            while (_keyRSet.next()) {
                _ids.add(_keyRSet.getObject(1));
            }
        } finally {
            _keyRSet.close();
        }
        return _ids.toArray();
    }

    public String getSequenceNextValSql(String sequenceName) {
        throw new UnsupportedOperationException();
    }

    public String buildPagedQuerySQL(String originSql, int page, int pageSize) {
        throw new UnsupportedOperationException();
    }

    public String buildCreateSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable) {
        throw new UnsupportedOperationException();
    }

    public String buildDropSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable) {
        throw new UnsupportedOperationException();
    }

    protected String __doGetColumnType(Class<?> clazz) {
        String _columnType = "VARCHAR";
        if (BigDecimal.class.equals(clazz)) {
            _columnType = "NUMERIC";
        } else if (Boolean.class.equals(clazz) || boolean.class.equals(clazz)) {
            _columnType = "BIT";
        } else if (Byte.class.equals(clazz) || byte.class.equals(clazz)) {
            _columnType = "TINYINT";
        } else if (Short.class.equals(clazz) || short.class.equals(clazz)) {
            _columnType = "SMALLINT";
        } else if (Integer.class.equals(clazz) || int.class.equals(clazz)) {
            _columnType = "INTEGER";
        } else if (Long.class.equals(clazz) || long.class.equals(clazz)) {
            _columnType = "BIGINT";
        } else if (Float.class.equals(clazz) || float.class.equals(clazz)) {
            _columnType = "FLOAT";
        } else if (Double.class.equals(clazz) || double.class.equals(clazz)) {
            _columnType = "DOUBLE";
        } else if (byte[].class.equals(clazz) || Byte[].class.equals(clazz)) {
            _columnType = "BINARY";
        } else if (java.sql.Date.class.equals(clazz) || java.util.Date.class.equals(clazz)) {
            _columnType = "DATE";
        } else if (java.sql.Time.class.equals(clazz)) {
            _columnType = "TIME";
        } else if (java.sql.Timestamp.class.equals(clazz)) {
            _columnType = "TIMESTAMP";
        } else if (java.sql.Blob.class.equals(clazz)) {
            _columnType = "BLOB";
        } else if (java.sql.Clob.class.equals(clazz)) {
            _columnType = "CLOB";
        }
        return _columnType;
    }

    /**
     * @param fields    字段名称集合
     * @param suffix    字段名称后缀，可选
     * @param separator 分隔符，可选，默认“, ”
     * @return 将字段名称集合转换成为采用separator分隔的字符串
     */
    protected String __doGenerateFieldsFormatStr(Fields fields, String suffix, String separator) {
        StringBuilder _fieldsSB = new StringBuilder();
        Iterator<String> _fieldsIt = fields.fields().iterator();
        suffix = StringUtils.defaultIfBlank(suffix, "");
        separator = StringUtils.defaultIfBlank(separator, ", ");
        while (_fieldsIt.hasNext()) {
            _fieldsSB.append(this.wrapIdentifierQuote(_fieldsIt.next())).append(suffix);
            if (_fieldsIt.hasNext()) {
                _fieldsSB.append(separator);
            }
        }
        return _fieldsSB.toString();
    }

    public String buildTableName(String prefix, EntityMeta entityMeta, IShardingable shardingable) {
        String _entityName = entityMeta.getEntityName();
        if (shardingable != null && entityMeta.getShardingRule() != null) {
            IShardingRule _rule = ClassUtils.impl(entityMeta.getShardingRule().value(), IShardingRule.class);
            if (_rule != null) {
                _entityName = _rule.getShardName(entityMeta.getEntityName(), shardingable.getShardingParam());
            }
        }
        return this.wrapIdentifierQuote(StringUtils.defaultIfBlank(prefix, "").concat(_entityName));
    }

    /**
     * 验证字段是否合法有效
     *
     * @param entityMeta    数据实体属性描述对象
     * @param fields        字段名称集合
     * @param isPrimaryKeys fields中存放的是否为主键
     */
    protected void __doValidProperty(EntityMeta entityMeta, Fields fields, boolean isPrimaryKeys) {
        if (isPrimaryKeys) {
            for (String _pkField : fields.fields()) {
                if (!entityMeta.isPrimaryKey(_pkField)) {
                    throw new IllegalArgumentException("'".concat(_pkField).concat("' isn't primary key field"));
                }
            }
        } else {
            for (String _field : fields.fields()) {
                if (!entityMeta.containsProperty(_field)) {
                    throw new IllegalArgumentException("'".concat(_field).concat("' isn't table field"));
                }
            }
        }
    }

    public String buildInsertSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable, Fields fields) {
        EntityMeta _meta = EntityMeta.createAndGet(entityClass);
        ExpressionUtils _exp = ExpressionUtils.bind("INSERT INTO ${table_name} (${fields}) VALUES (${values})")
                .set("table_name", buildTableName(prefix, _meta, shardingable));
        //
        Fields _fields = Fields.create();
        if (fields == null || fields.fields().isEmpty()) {
            _fields.add(_meta.getPropertyNames());
        } else {
            _fields.add(fields);
            __doValidProperty(_meta, _fields, false);
        }
        return _exp.set("fields", __doGenerateFieldsFormatStr(_fields, null, null)).set("values", StringUtils.repeat("?", ", ", _fields.fields().size())).getResult();
    }

    public String buildDeleteByPkSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable, Fields pkFields) {
        EntityMeta _meta = EntityMeta.createAndGet(entityClass);
        ExpressionUtils _exp = ExpressionUtils.bind("DELETE FROM ${table_name} WHERE ${pk}")
                .set("table_name", buildTableName(prefix, _meta, shardingable));
        //
        Fields _fields = Fields.create();
        if (pkFields == null || pkFields.fields().isEmpty()) {
            _fields.add(_meta.getPrimaryKeys());
        } else {
            _fields.add(pkFields);
            __doValidProperty(_meta, _fields, true);
        }
        return _exp.set("pk", __doGenerateFieldsFormatStr(_fields, " = ?", " and ")).getResult();
    }

    public String buildUpdateByPkSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable, Fields pkFields, Fields fields) {
        EntityMeta _meta = EntityMeta.createAndGet(entityClass);
        ExpressionUtils _exp = ExpressionUtils.bind("UPDATE ${table_name} SET ${fields} WHERE ${pk}")
                .set("table_name", buildTableName(prefix, _meta, shardingable));
        //
        Fields _fields = Fields.create();
        for (String _field : (fields == null || fields.fields().isEmpty()) ? _meta.getPropertyNames() : fields.fields()) {
            if (_meta.containsProperty(_field)) {
                if (_meta.isPrimaryKey(_field)) {
                    // 排除主键
                    continue;
                }
                _fields.add(_field);
            } else {
                throw new IllegalArgumentException("'".concat(_field).concat("' isn't table field"));
            }
        }
        _exp.set("fields", __doGenerateFieldsFormatStr(_fields, " = ?", null));
        //
        if (pkFields != null && !pkFields.fields().isEmpty()) {
            _fields = pkFields;
            __doValidProperty(_meta, _fields, true);
        } else {
            _fields = Fields.create().add(_meta.getPrimaryKeys());
        }
        return _exp.set("pk", __doGenerateFieldsFormatStr(_fields, " = ?", " and ")).getResult();
    }

    public String buildSelectByPkSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable, Fields pkFields, Fields fields) {
        EntityMeta _meta = EntityMeta.createAndGet(entityClass);
        ExpressionUtils _exp = ExpressionUtils.bind("SELECT ${fields} FROM ${table_name} WHERE ${pk}")
                .set("table_name", buildTableName(prefix, _meta, shardingable));
        //
        if (fields == null || fields.fields().isEmpty()) {
            fields = Fields.create().add(_meta.getPropertyNames());
        } else {
            __doValidProperty(_meta, fields, false);
        }
        _exp.set("fields", __doGenerateFieldsFormatStr(fields, null, null));
        //
        if (pkFields != null && !pkFields.fields().isEmpty()) {
            __doValidProperty(_meta, pkFields, true);
        } else {
            pkFields = Fields.create().add(_meta.getPrimaryKeys());
        }
        return _exp.set("pk", __doGenerateFieldsFormatStr(pkFields, " = ?", " and ")).getResult();
    }

    public String buildSelectSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable, Fields fields) {
        EntityMeta _meta = EntityMeta.createAndGet(entityClass);
        ExpressionUtils _exp = ExpressionUtils.bind("SELECT ${fields} FROM ${table_name}")
                .set("table_name", buildTableName(prefix, _meta, shardingable));
        //
        if (fields == null || fields.fields().isEmpty()) {
            fields = Fields.create().add(_meta.getPropertyNames());
        } else {
            __doValidProperty(_meta, fields, false);
        }
        return _exp.set("fields", __doGenerateFieldsFormatStr(fields, null, null)).getResult();
    }
}
