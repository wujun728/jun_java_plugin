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
import net.ymate.platform.persistence.IShardingable;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.base.IEntity;
import net.ymate.platform.persistence.base.Type;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.dialect.AbstractDialect;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * MySQL数据库方言接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-30 下午01:55:13
 * @version 1.0
 */
public class MySQLDialect extends AbstractDialect {

    public MySQLDialect() {
        this.identifierQuote_begin = this.identifierQuote_end = "`";
    }

    public String getName() {
        return JDBC.DATABASE.MYSQL.name();
    }

    @Override
    public String buildPagedQuerySQL(String originSql, int page, int pageSize) {
        int _limit = ((page - 1) * pageSize);
        if (pageSize == 0) {
            return originSql.concat(" limit ").concat(Integer.toString(_limit));
        } else {
            return originSql.concat(" limit ").concat(Integer.toString(_limit)).concat(", ").concat(Integer.toString(pageSize));
        }
    }

    @Override
    public String buildCreateSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable) {
        EntityMeta _meta = EntityMeta.createAndGet(entityClass);
        if (_meta != null) {
            ExpressionUtils _exp = ExpressionUtils.bind("CREATE TABLE ${table_name} (\n${fields} ${primary_keys} ${indexes}) ${comment} ")
                    .set("table_name", buildTableName(prefix, _meta, shardingable));
            if (StringUtils.isNotBlank(_meta.getComment())) {
                _exp.set("comment", "COMMENT='" + StringUtils.trimToEmpty(_meta.getComment()) + "'");
            } else {
                _exp.set("comment", "");
            }
            StringBuilder _tmpBuilder = new StringBuilder();
            // FIELDs
            List<EntityMeta.PropertyMeta> _propMetas = new ArrayList<EntityMeta.PropertyMeta>(_meta.getProperties());
            Collections.sort(_propMetas, new Comparator<EntityMeta.PropertyMeta>() {
                @Override
                public int compare(EntityMeta.PropertyMeta o1, EntityMeta.PropertyMeta o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            for (EntityMeta.PropertyMeta _propMeta : _propMetas) {
                _tmpBuilder.append("\t").append(wrapIdentifierQuote(_propMeta.getName())).append(" ");
                String _propType = "";
                if (!_propMeta.getType().equals(Type.FIELD.UNKNOW)) {
                    _propType = _propMeta.getType().name();
                } else {
                    _propType = __doGetColumnType(_propMeta.getField().getType());
                }
                if ("VARCHAR".equals(_propType) && _propMeta.getLength() > 2000) {
                    _propType = "TEXT";
                } else if ("BOOLEAN".equals(_propType) || "BIT".equals(_propType)) {
                    _propType = "SMALLINT";
                }
                boolean _needLength = true;
                if ("DATE".equals(_propType) || "TIME".equals(_propType) || "TIMESTAMP".equals(_propType) || "TEXT".equals(_propType)) {
                    _needLength = false;
                }
                _tmpBuilder.append(_propType);
                if (_needLength) {
                    _tmpBuilder.append("(").append(_propMeta.getLength());
                    if (_propMeta.getDecimals() > 0) {
                        _tmpBuilder.append(",").append(_propMeta.getDecimals());
                    }
                    _tmpBuilder.append(")");
                }
                if (_propMeta.isUnsigned()) {
                    if ("NUMERIC".equals(_propType) || "LONG".equals(_propType) || "FLOAT".equals(_propType)
                            || "DOUBLE".equals(_propType) || "SMALLINT".equals(_propType) || "TINYINT".equals(_propType)
                            || "DOUBLE".equals(_propType) || "INTEGER".equals(_propType)) {
                        _tmpBuilder.append(" unsigned ");
                    }
                }
                if (_propMeta.isNullable()) {
                    if (StringUtils.isNotBlank(_propMeta.getDefaultValue())) {
                        if ("@NULL".equals(_propMeta.getDefaultValue())) {
                            _tmpBuilder.append(" DEFAULT NULL");
                        } else {
                            _tmpBuilder.append(" DEFAULT '").append(_propMeta.getDefaultValue()).append("'");
                        }
                    }
                } else {
                    _tmpBuilder.append(" NOT NULL");
                }
                if (_propMeta.isAutoincrement()) {
                    _tmpBuilder.append(" AUTO_INCREMENT");
                }
                if (StringUtils.isNotBlank(_propMeta.getComment())) {
                    _tmpBuilder.append(" COMMENT '").append(_propMeta.getComment()).append("'");
                }
                _tmpBuilder.append(__LINE_END_FLAG);
            }
            _exp.set("fields", _tmpBuilder.length() > 2 ? _tmpBuilder.substring(0, _tmpBuilder.lastIndexOf(__LINE_END_FLAG)) : "");
            // PKs
            _tmpBuilder.setLength(0);
            for (String _key : _meta.getPrimaryKeys()) {
                _tmpBuilder.append(wrapIdentifierQuote(_key)).append(",");
            }
            if (_tmpBuilder.length() > 0) {
                _tmpBuilder.setLength(_tmpBuilder.length() - 1);
                _tmpBuilder.insert(0, ",\n\tPRIMARY KEY (").append(")");
                _exp.set("primary_keys", _tmpBuilder.toString());
            }
            // INDEXs
            _tmpBuilder.setLength(0);
            if (!_meta.getIndexes().isEmpty()) {
                _tmpBuilder.append(__LINE_END_FLAG);
                for (EntityMeta.IndexMeta _index : _meta.getIndexes()) {
                    if (!_index.getFields().isEmpty()) {
                        List<String> _idxFields = new ArrayList<String>(_index.getFields().size());
                        for (String _idxField : _index.getFields()) {
                            _idxFields.add(wrapIdentifierQuote(_idxField));
                        }
                        if (_index.isUnique()) {
                            _tmpBuilder.append("\tUNIQUE KEY ");
                        } else {
                            _tmpBuilder.append("\tINDEX ");
                        }
                        _tmpBuilder.append(wrapIdentifierQuote(_index.getName())).append(" (").append(StringUtils.join(_idxFields, ",")).append(")").append(__LINE_END_FLAG);
                    }
                }
                if (_tmpBuilder.length() > 2) {
                    _tmpBuilder.setLength(_tmpBuilder.length() - 2);
                }
            } else {
                _tmpBuilder.append("");
            }
            return _exp.set("indexes", _tmpBuilder.toString()).getResult();
        }
        return null;
    }

    @Override
    public String buildDropSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable) {
        EntityMeta _meta = EntityMeta.createAndGet(entityClass);
        if (_meta != null) {
            return "DROP TABLE IF EXISTS " + buildTableName(prefix, _meta, shardingable);
        }
        return null;
    }
}
