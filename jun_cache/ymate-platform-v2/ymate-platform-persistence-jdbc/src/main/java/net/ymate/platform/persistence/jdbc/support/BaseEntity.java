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
package net.ymate.platform.persistence.jdbc.support;

import com.alibaba.fastjson.annotation.JSONField;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.IResultSet;
import net.ymate.platform.persistence.IShardingable;
import net.ymate.platform.persistence.Page;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.base.IEntity;
import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import net.ymate.platform.persistence.jdbc.ISession;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.impl.DefaultSession;
import net.ymate.platform.persistence.jdbc.query.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 实体模型接口抽象实现，提供基本数据库操作方法
 *
 * @author 刘镇 (suninformation@163.com) on 2013-7-16 下午5:22:15
 * @version 1.0
 */
public abstract class BaseEntity<Entity extends IEntity, PK extends Serializable> implements IEntity<PK> {

    private Class<Entity> __entityClass;

    private IConnectionHolder __connectionHolder;

    private IShardingable __shardingable;

    private String __dsName;

    /**
     * 构造器
     */
    @SuppressWarnings("unchecked")
    public BaseEntity() {
        __entityClass = (Class<Entity>) ClassUtils.getParameterizedTypes(getClass()).get(0);
    }

    @JSONField(serialize = false)
    public IConnectionHolder getConnectionHolder() {
        return this.__connectionHolder;
    }

    @JSONField(deserialize = false)
    public void setConnectionHolder(IConnectionHolder connectionHolder) {
        this.__connectionHolder = connectionHolder;
        // 每次设置将记录数据源名称，若设置为空将重置
        if (this.__connectionHolder != null) {
            this.__dsName = this.__connectionHolder.getDataSourceCfgMeta().getName();
        } else {
            this.__dsName = null;
        }
    }

    @JSONField(serialize = false)
    public IShardingable getShardingable() {
        return this.__shardingable;
    }

    @JSONField(deserialize = false)
    public void setShardingable(IShardingable shardingable) {
        this.__shardingable = shardingable;
    }

    @JSONField(serialize = false)
    public String getDataSourceName() {
        return this.__dsName;
    }

    @JSONField(deserialize = false)
    public void setDataSourceName(String dsName) {
        this.__dsName = StringUtils.trimToNull(dsName);
    }

    /**
     * @return 获取实体对象类型
     */
    protected Class<Entity> getEntityClass() {
        return this.__entityClass;
    }

    /**
     * @return 确保能够正确获取到数据库连接持有对象，即连接持有对象为null时尝试获取JDBC默认连接
     * @throws Exception 可能产生的异常
     */
    protected IConnectionHolder __doGetConnectionHolderSafed() throws Exception {
        if (this.__connectionHolder == null || this.__connectionHolder.getConnection() == null || this.__connectionHolder.getConnection().isClosed()) {
            if (StringUtils.isNotBlank(this.__dsName)) {
                this.__connectionHolder = JDBC.get().getConnectionHolder(this.__dsName);
            } else {
                this.__connectionHolder = JDBC.get().getDefaultConnectionHolder();
            }
        }
        return this.__connectionHolder;
    }

    public void entityCreate() throws Exception {
        ISession _session = new DefaultSession(__doGetConnectionHolderSafed());
        try {
            _session.executeForUpdate(SQL.create(_session.getConnectionHolder().getDialect().buildCreateSQL(this.__entityClass, _session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix(), this.getShardingable())));
        } finally {
            _session.close();
        }
    }

    public void entityDrop() throws Exception {
        ISession _session = new DefaultSession(__doGetConnectionHolderSafed());
        try {
            _session.executeForUpdate(SQL.create(_session.getConnectionHolder().getDialect().buildDropSQL(this.__entityClass, _session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix(), this.getShardingable())));
        } finally {
            _session.close();
        }
    }

    public Entity load() throws Exception {
        return load(null, null);
    }

    public Entity load(Fields fields) throws Exception {
        return load(fields, null);
    }

    public Entity load(IDBLocker dbLocker) throws Exception {
        return load(null, dbLocker);
    }

    public Entity load(Fields fields, IDBLocker dbLocker) throws Exception {
        ISession _session = new DefaultSession(__doGetConnectionHolderSafed());
        try {
            EntitySQL<Entity> _entitySQL = EntitySQL.create(this.getEntityClass());
            if (fields != null) {
                _entitySQL.field(fields);
            }
            if (dbLocker != null) {
                _entitySQL.forUpdate(dbLocker);
            }
            return _session.find(_entitySQL, this.getId(), this.getShardingable());
        } finally {
            _session.close();
        }
    }

    @SuppressWarnings("unchecked")
    public Entity save() throws Exception {
        ISession _session = new DefaultSession(__doGetConnectionHolderSafed());
        try {
            return _session.insert((Entity) this, this.getShardingable());
        } finally {
            _session.close();
        }
    }

    @SuppressWarnings("unchecked")
    public Entity save(Fields fields) throws Exception {
        ISession _session = new DefaultSession(__doGetConnectionHolderSafed());
        try {
            return _session.insert((Entity) this, fields, this.getShardingable());
        } finally {
            _session.close();
        }
    }

    public Entity saveOrUpdate() throws Exception {
        return saveOrUpdate(null);
    }

    @SuppressWarnings("unchecked")
    public Entity saveOrUpdate(Fields fields) throws Exception {
        ISession _session = new DefaultSession(__doGetConnectionHolderSafed());
        try {
            EntitySQL<Entity> _entitySQL = EntitySQL.create(this.getEntityClass());
            if (fields != null) {
                _entitySQL.field(fields);
            }
            Entity _t = _session.find(_entitySQL, this.getId(), this.getShardingable());
            if (_t == null) {
                return _session.insert((Entity) this, fields, this.getShardingable());
            }
            return _session.update((Entity) this, fields, this.getShardingable());
        } finally {
            _session.close();
        }
    }

    public Entity update() throws Exception {
        return update(null);
    }

    @SuppressWarnings("unchecked")
    public Entity update(Fields fields) throws Exception {
        ISession _session = new DefaultSession(__doGetConnectionHolderSafed());
        try {
            return _session.update((Entity) this, fields, this.getShardingable());
        } finally {
            _session.close();
        }
    }

    @SuppressWarnings("unchecked")
    public Entity delete() throws Exception {
        ISession _session = new DefaultSession(__doGetConnectionHolderSafed());
        try {
            if (_session.delete(this.getEntityClass(), this.getId(), this.getShardingable()) > 0) {
                return (Entity) this;
            }
            return null;
        } finally {
            _session.close();
        }
    }

    public IResultSet<Entity> find() throws Exception {
        return find(Where.create(buildEntityCond(this)), null, null, null);
    }

    public IResultSet<Entity> find(IDBLocker dbLocker) throws Exception {
        return find(Where.create(buildEntityCond(this)), null, null, dbLocker);
    }

    public IResultSet<Entity> find(Page page) throws Exception {
        return find(Where.create(buildEntityCond(this)), null, page, null);
    }

    public IResultSet<Entity> find(Page page, IDBLocker dbLocker) throws Exception {
        return find(Where.create(buildEntityCond(this)), null, page, dbLocker);
    }

    public IResultSet<Entity> find(Fields fields) throws Exception {
        return find(Where.create(buildEntityCond(this)), fields, null, null);
    }

    public IResultSet<Entity> find(Fields fields, IDBLocker dbLocker) throws Exception {
        return find(Where.create(buildEntityCond(this)), fields, null, dbLocker);
    }

    public IResultSet<Entity> find(Fields fields, Page page) throws Exception {
        return find(Where.create(buildEntityCond(this)), fields, page, null);
    }

    public IResultSet<Entity> find(Fields fields, Page page, IDBLocker dbLocker) throws Exception {
        return find(Where.create(buildEntityCond(this)), fields, page, dbLocker);
    }

    public IResultSet<Entity> find(Where where) throws Exception {
        return find(where, null, null, null);
    }

    public IResultSet<Entity> find(Where where, IDBLocker dbLocker) throws Exception {
        return find(where, null, null, dbLocker);
    }

    public IResultSet<Entity> find(Where where, Fields fields) throws Exception {
        return find(where, fields, null, null);
    }

    public IResultSet<Entity> find(Where where, Fields fields, IDBLocker dbLocker) throws Exception {
        return find(where, fields, null, dbLocker);
    }

    public IResultSet<Entity> find(Where where, Fields fields, Page page) throws Exception {
        return find(where, fields, page, null);
    }

    public IResultSet<Entity> find(Where where, Page page) throws Exception {
        return find(where, null, page, null);
    }

    public IResultSet<Entity> find(Where where, Page page, IDBLocker dbLocker) throws Exception {
        return find(where, null, page, dbLocker);
    }

    public IResultSet<Entity> find(Where where, Fields fields, Page page, IDBLocker dbLocker) throws Exception {
        ISession _session = new DefaultSession(__doGetConnectionHolderSafed());
        try {
            EntitySQL<Entity> _entitySQL = EntitySQL.create(this.getEntityClass());
            if (fields != null) {
                _entitySQL.field(fields);
            }
            if (dbLocker != null) {
                _entitySQL.forUpdate(dbLocker);
            }
            return _session.find(_entitySQL, where, page, this.getShardingable());
        } finally {
            _session.close();
        }
    }

    public IResultSet<Entity> findAll() throws Exception {
        return find(null, null, null, null);
    }

    public IResultSet<Entity> findAll(Fields fields, Page page) throws Exception {
        return find(null, fields, page, null);
    }

    public IResultSet<Entity> findAll(Fields fields) throws Exception {
        return find(null, fields, null, null);
    }

    public IResultSet<Entity> findAll(Page page) throws Exception {
        return find(null, null, page, null);
    }

    public Entity findFirst() throws Exception {
        return findFirst(Where.create(buildEntityCond(this)), null, null);
    }

    public Entity findFirst(IDBLocker dbLocker) throws Exception {
        return findFirst(Where.create(buildEntityCond(this)), null, dbLocker);
    }

    public Entity findFirst(Fields fields) throws Exception {
        return findFirst(Where.create(buildEntityCond(this)), fields, null);
    }

    public Entity findFirst(Fields fields, IDBLocker dbLocker) throws Exception {
        return findFirst(Where.create(buildEntityCond(this)), fields, dbLocker);
    }

    public Entity findFirst(Where where) throws Exception {
        return findFirst(where, null, null);
    }

    public Entity findFirst(Where where, IDBLocker dbLocker) throws Exception {
        return findFirst(where, null, dbLocker);
    }

    public Entity findFirst(Where where, Fields fields) throws Exception {
        return findFirst(where, fields, null);
    }

    public Entity findFirst(Where where, Fields fields, IDBLocker dbLocker) throws Exception {
        ISession _session = new DefaultSession(__doGetConnectionHolderSafed());
        try {
            EntitySQL<Entity> _entitySQL = EntitySQL.create(this.getEntityClass());
            if (fields != null) {
                _entitySQL.field(fields);
            }
            if (dbLocker != null) {
                _entitySQL.forUpdate(dbLocker);
            }
            return _session.findFirst(_entitySQL, where, this.getShardingable());
        } finally {
            _session.close();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

    public static <T extends IEntity> Cond buildEntityCond(T entity) throws Exception {
        return buildEntityCond(entity, false);
    }

    public static <T extends IEntity> Cond buildEntityCond(T entity, boolean or) throws Exception {
        Cond _cond = Cond.create();
        EntityMeta _meta = EntityMeta.createAndGet(entity.getClass());
        ClassUtils.BeanWrapper<T> _beanWrapper = ClassUtils.wrapper(entity);
        boolean _flag = false;
        for (String _field : _meta.getPropertyNames()) {
            Object _value = null;
            if (_meta.isMultiplePrimaryKey() && _meta.isPrimaryKey(_field)) {
                _value = _meta.getPropertyByName(_field).getField().get(entity.getId());
            } else {
                _value = _beanWrapper.getValue(_meta.getPropertyByName(_field).getField().getName());
            }
            if (_value != null) {
                if (_flag) {
                    if (or) {
                        _cond.or();
                    } else {
                        _cond.and();
                    }
                } else {
                    _flag = true;
                }
                _cond.eq(_field).param(_value);
            }
        }
        return _cond;
    }
}
