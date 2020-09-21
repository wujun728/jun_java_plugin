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

import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.Page;
import net.ymate.platform.persistence.Params;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.base.IEntity;
import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.dialect.IDialect;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Select语句对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/12 下午5:59
 * @version 1.0
 */
public final class Select {

    private static final Log _LOG = LogFactory.getLog(Select.class);

    private List<String> __froms;

    private Fields __fields;

    private List<Join> __joins;

    private Where __where;

    private List<Union> __unions;

    private String __alias;

    private boolean __distinct;

    private IDBLocker __dbLocker;

    private Page __page;

    private IDialect __dialect;

    public static Select create(Class<? extends IEntity> entityClass) {
        return new Select(null, EntityMeta.createAndGet(entityClass).getEntityName(), null);
    }

    public static Select create(String prefix, Class<? extends IEntity> entityClass) {
        return new Select(prefix, EntityMeta.createAndGet(entityClass).getEntityName(), null);
    }

    public static Select create(Class<? extends IEntity> entityClass, String alias) {
        return new Select(null, EntityMeta.createAndGet(entityClass).getEntityName(), alias);
    }

    public static Select create(String prefix, Class<? extends IEntity> entityClass, String alias) {
        return new Select(prefix, EntityMeta.createAndGet(entityClass).getEntityName(), alias);
    }

    public static Select create(Select select) {
        Select _target = new Select(null, select.toString(), null);
        _target.where().param(select.getParams());
        return _target;
    }

    public static Select create(String prefix, String from, String alias) {
        return new Select(prefix, from, alias);
    }

    public static Select create(String from, String alias) {
        return new Select(null, from, alias);
    }

    public static Select create(String from) {
        return new Select(null, from, null);
    }

    private Select(String prefix, String from, String alias) {
        this.__froms = new ArrayList<String>();
        this.__fields = Fields.create();
        this.__joins = new ArrayList<Join>();
        this.__unions = new ArrayList<Union>();
        //
        from(prefix, from, alias);
    }

    public Select from(Class<? extends IEntity> entityClass) {
        return from(null, EntityMeta.createAndGet(entityClass).getEntityName(), null);
    }

    public Select from(Class<? extends IEntity> entityClass, String alias) {
        return from(null, EntityMeta.createAndGet(entityClass).getEntityName(), null);
    }

    public Select from(String prefix, Class<? extends IEntity> entityClass, String alias) {
        return from(prefix, EntityMeta.createAndGet(entityClass).getEntityName(), alias);
    }

    public Select from(Select select) {
        Select _target = from(null, select.toString(), null);
        _target.where().param(select.getParams());
        return _target;
    }

    public Select from(String tableName, String alias) {
        return from(null, tableName, alias);
    }

    public Select from(String tableName) {
        return from(null, tableName, null);
    }

    public Select from(String prefix, String from, String alias) {
        if (StringUtils.isNotBlank(prefix)) {
            from = prefix.concat(from);
        }
        if (StringUtils.isNotBlank(alias)) {
            from = from.concat(" ").concat(alias);
        }
        this.__froms.add(from);
        return this;
    }

    public Fields fields() {
        return this.__fields;
    }

    public Select field(String field) {
        this.__fields.add(field);
        return this;
    }

    public Select field(String prefix, String field) {
        this.__fields.add(prefix, field);
        return this;
    }

    public Select field(String prefix, String field, String alias) {
        this.__fields.add(prefix, field, alias);
        return this;
    }

    public Select field(Fields fields) {
        this.__fields.add(fields);
        return this;
    }

    public Select field(String prefix, Fields fields) {
        for (String _field : fields.fields()) {
            this.__fields.add(prefix, _field);
        }
        return this;
    }

    public Select join(Join join) {
        __joins.add(join);
        where().param(join.params());
        return this;
    }

    public Select union(Union union) {
        __unions.add(union);
        where().param(union.select().getParams());
        return this;
    }

    public Select where(Where where) {
        where().where(where);
        return this;
    }

    public Params getParams() {
        return where().getParams();
    }

    public Where where() {
        if (this.__where == null) {
            this.__where = Where.create();
        }
        return __where;
    }

    /**
     * 设置Select语句的别名
     *
     * @param alias 别名
     * @return 返回当前Select对象
     */
    public Select alias(String alias) {
        this.__alias = alias;
        return this;
    }

    public Select distinct() {
        __distinct = true;
        return this;
    }

    public Select forUpdate(IDBLocker dbLocker) {
        __dbLocker = dbLocker;
        return this;
    }

    public Select page(Page page) {
        __page = page;
        return this;
    }

    public Select page(IDialect dialect, Page page) {
        __page = page;
        __dialect = dialect;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder _selectSB = new StringBuilder("SELECT ");
        if (__distinct) {
            _selectSB.append("DISTINCT ");
        }
        if (__fields.fields().isEmpty()) {
            _selectSB.append(" * ");
        } else {
            _selectSB.append(StringUtils.join(__fields.fields(), ", "));
        }
        _selectSB.append(" FROM ").append(StringUtils.join(__froms, ", "));
        //
        for (Join _join : __joins) {
            _selectSB.append(" ").append(_join);
        }
        //
        if (__where != null) {
            _selectSB.append(" ").append(__where.toString());
        }
        //
        for (Union _union : __unions) {
            _selectSB.append(" UNION ");
            if (_union.isAll()) {
                _selectSB.append("ALL ");
            }
            _selectSB.append(_union.select());
        }
        _selectSB.append(" ");
        //
        if (__page != null) {
            if (__dialect == null) {
                IConnectionHolder _connHolder = null;
                try {
                    _connHolder = JDBC.get().getDefaultConnectionHolder();
                    __dialect = _connHolder.getDialect();
                } catch (Exception e) {
                    _LOG.warn("", RuntimeUtils.unwrapThrow(e));
                } finally {
                    if (_connHolder != null) {
                        _connHolder.release();
                    }
                }
            }
            if (__dialect != null) {
                _selectSB = new StringBuilder(__dialect.buildPagedQuerySQL(_selectSB.toString(), __page.page(), __page.pageSize())).append(" ");
            }
        }
        //
        if (StringUtils.isNotBlank(__alias)) {
            return "(".concat(_selectSB.toString()).concat(") ").concat(__alias);
        }
        //
        if (__dbLocker != null) {
            _selectSB.append(__dbLocker.toSQL());
        }
        return _selectSB.toString();
    }

    public SQL toSQL() {
        return SQL.create(this);
    }
}
