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
package net.ymate.platform.persistence.jdbc.impl;

import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.core.util.ExpressionUtils;
import net.ymate.platform.core.util.UUIDUtils;
import net.ymate.platform.persistence.*;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.base.IEntity;
import net.ymate.platform.persistence.base.IEntityPK;
import net.ymate.platform.persistence.base.ShardingList;
import net.ymate.platform.persistence.impl.DefaultResultSet;
import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import net.ymate.platform.persistence.jdbc.ISession;
import net.ymate.platform.persistence.jdbc.base.*;
import net.ymate.platform.persistence.jdbc.base.impl.BatchUpdateOperator;
import net.ymate.platform.persistence.jdbc.base.impl.DefaultQueryOperator;
import net.ymate.platform.persistence.jdbc.base.impl.DefaultUpdateOperator;
import net.ymate.platform.persistence.jdbc.base.impl.EntityResultSetHandler;
import net.ymate.platform.persistence.jdbc.dialect.IDialect;
import net.ymate.platform.persistence.jdbc.dialect.impl.OracleDialect;
import net.ymate.platform.persistence.jdbc.query.BatchSQL;
import net.ymate.platform.persistence.jdbc.query.EntitySQL;
import net.ymate.platform.persistence.jdbc.query.SQL;
import net.ymate.platform.persistence.jdbc.query.Where;
import net.ymate.platform.persistence.jdbc.support.BaseEntity;
import net.ymate.platform.persistence.jdbc.transaction.Transactions;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 默认数据库会话操作接口实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-9-27 下午03:09:46
 * @version 1.0
 */
public class DefaultSession implements ISession {

    private String __id;

    private IConnectionHolder __connectionHolder;
    private IDialect __dialect;
    private String __tablePrefix;
    //
    private ISessionEvent __sessionEvent;

    public DefaultSession(IConnectionHolder connectionHolder) {
        this.__id = UUIDUtils.UUID();
        this.__connectionHolder = connectionHolder;
        //
        __dialect = connectionHolder.getDialect();
        __tablePrefix = connectionHolder.getDataSourceCfgMeta().getTablePrefix();
    }

    public String getId() {
        return __id;
    }

    public IConnectionHolder getConnectionHolder() {
        return __connectionHolder;
    }

    public ISession setSessionEvent(ISessionEvent sessionEvent) {
        this.__sessionEvent = sessionEvent;
        return this;
    }

    public void close() {
        // 同时需要判断当前连接是否参与事务，若存在事务则不进行关闭操作
        if (__connectionHolder != null) {
            if (Transactions.get() == null) {
                __connectionHolder.release();
            }
        }
    }

    public <T> IResultSet<T> find(SQL sql, IResultSetHandler<T> handler) throws Exception {
        IQueryOperator<T> _opt = new DefaultQueryOperator<T>(sql.getSQL(), this.__connectionHolder, handler);
        for (Object _param : sql.params().params()) {
            _opt.addParameter(_param);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onQueryBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onQueryAfter(new SessionEventContext(_opt));
        }
        return new DefaultResultSet<T>(_opt.getResultSet());
    }

    public <T> IResultSet<T> find(SQL sql, IResultSetHandler<T> handler, Page page) throws Exception {
        String _selectSql = sql.getSQL();
        //
        long _count = 0;
        if (page != null) {
            _selectSql = __dialect.buildPagedQuerySQL(sql.getSQL(), page.page(), page.pageSize());
            if (page.isCount()) {
                _count = this.count(sql);
            }
        }
        //
        IQueryOperator<T> _opt = new DefaultQueryOperator<T>(_selectSql, this.__connectionHolder, handler);
        for (Object _param : sql.params().params()) {
            _opt.addParameter(_param);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onQueryBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onQueryAfter(new SessionEventContext(_opt));
        }
        if (page != null) {
            return new DefaultResultSet<T>(_opt.getResultSet(), page.page(), page.pageSize(), _count);
        }
        return new DefaultResultSet<T>(_opt.getResultSet());
    }

    @SuppressWarnings("unchecked")
    public <T extends IEntity> IResultSet<T> find(T entity) throws Exception {
        return find(entity, Fields.create(), null, entity instanceof IShardingable ? (IShardingable) entity : null);
    }

    public <T extends IEntity> IResultSet<T> find(T entity, IShardingable shardingable) throws Exception {
        return find(entity, Fields.create(), null, shardingable);
    }

    public <T extends IEntity> IResultSet<T> find(T entity, Page page) throws Exception {
        return find(entity, Fields.create(), page, entity instanceof IShardingable ? (IShardingable) entity : null);
    }

    public <T extends IEntity> IResultSet<T> find(T entity, Page page, IShardingable shardingable) throws Exception {
        return find(entity, Fields.create(), page, shardingable);
    }

    public <T extends IEntity> IResultSet<T> find(T entity, Fields filter) throws Exception {
        return find(entity, filter, null, entity instanceof IShardingable ? (IShardingable) entity : null);
    }

    public <T extends IEntity> IResultSet<T> find(T entity, Fields filter, IShardingable shardingable) throws Exception {
        return find(entity, filter, null, shardingable);
    }

    public <T extends IEntity> IResultSet<T> find(T entity, Fields filter, Page page) throws Exception {
        return find(entity, filter, page, entity instanceof IShardingable ? (IShardingable) entity : null);
    }

    @SuppressWarnings("unchecked")
    public <T extends IEntity> IResultSet<T> find(T entity, Fields filter, Page page, IShardingable shardingable) throws Exception {
        return (IResultSet<T>) this.find(EntitySQL.create(entity.getClass()).field(filter), Where.create(BaseEntity.buildEntityCond(entity)), page, shardingable);
    }

    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity) throws Exception {
        return find(entity, null, null, null);
    }

    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, IShardingable shardingable) throws Exception {
        return this.find(entity, null, null, shardingable);
    }

    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, Page page) throws Exception {
        return find(entity, null, page, null);
    }

    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, Page page, IShardingable shardingable) throws Exception {
        return this.find(entity, null, page, shardingable);
    }

    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, Where where) throws Exception {
        return find(entity, where, null, null);
    }

    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, Where where, IShardingable shardingable) throws Exception {
        return this.find(entity, where, null, shardingable);
    }

    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, Where where, Page page) throws Exception {
        return find(entity, where, page, null);
    }

    public <T extends IEntity> IResultSet<T> find(EntitySQL<T> entity, Where where, Page page, IShardingable shardingable) throws Exception {
        String _selectSql = __dialect.buildSelectSQL(entity.getEntityClass(), __tablePrefix, shardingable, __doGetNotExcludedFields(EntityMeta.createAndGet(entity.getEntityClass()), entity.fields(), false, true));
        if (where != null) {
            _selectSql = _selectSql.concat(" ").concat(where.toString());
        }
        long _count = 0;
        if (page != null) {
            _selectSql = __dialect.buildPagedQuerySQL(_selectSql, page.page(), page.pageSize());
            if (page.isCount()) {
                _count = this.count(entity.getEntityClass(), where);
            }
        }
        //
        if (entity.forUpdate() != null) {
            _selectSql = _selectSql + " " + entity.forUpdate().toSQL();
        }
        //
        IQueryOperator<T> _opt = new DefaultQueryOperator<T>(_selectSql, this.__connectionHolder, new EntityResultSetHandler<T>(entity.getEntityClass()));
        if (where != null) {
            for (Object _param : where.getParams().params()) {
                _opt.addParameter(_param);
            }
        }
        if (__sessionEvent != null) {
            __sessionEvent.onQueryBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onQueryAfter(new SessionEventContext(_opt));
        }
        //
        if (page != null) {
            return new DefaultResultSet<T>(_opt.getResultSet(), page.page(), page.pageSize(), _count);
        }
        return new DefaultResultSet<T>(_opt.getResultSet());
    }

    public <T extends IEntity> T find(EntitySQL<T> entity, Serializable id) throws Exception {
        return find(entity, id, null);
    }

    public <T extends IEntity> T find(EntitySQL<T> entity, Serializable id, IShardingable shardingable) throws Exception {
        EntityMeta _meta = EntityMeta.createAndGet(entity.getEntityClass());
        PairObject<Fields, Params> _entityPK = __doGetPrimaryKeyFieldAndValues(_meta, id, null);
        String _selectSql = __dialect.buildSelectByPkSQL(entity.getEntityClass(), __tablePrefix, shardingable, _entityPK.getKey(), __doGetNotExcludedFields(_meta, entity.fields(), false, true));
        //
        if (entity.forUpdate() != null) {
            _selectSql = _selectSql + " " + entity.forUpdate().toSQL();
        }
        //
        IQueryOperator<T> _opt = new DefaultQueryOperator<T>(_selectSql, this.__connectionHolder, new EntityResultSetHandler<T>(entity.getEntityClass()));
        if (_meta.isMultiplePrimaryKey()) {
            for (Object _param : _entityPK.getValue().params()) {
                _opt.addParameter(_param);
            }
        } else {
            _opt.addParameter(id);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onQueryBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onQueryAfter(new SessionEventContext(_opt));
        }
        return _opt.getResultSet().isEmpty() ? null : _opt.getResultSet().get(0);
    }

    public <T> T findFirst(SQL sql, IResultSetHandler<T> handler) throws Exception {
        String _selectSql = __dialect.buildPagedQuerySQL(sql.getSQL(), 1, 1);
        IQueryOperator<T> _opt = new DefaultQueryOperator<T>(_selectSql, this.__connectionHolder, handler);
        for (Object _param : sql.params().params()) {
            _opt.addParameter(_param);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onQueryBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onQueryAfter(new SessionEventContext(_opt));
        }
        return _opt.getResultSet().isEmpty() ? null : _opt.getResultSet().get(0);
    }

    public <T extends IEntity> T findFirst(EntitySQL<T> entity) throws Exception {
        return findFirst(entity, null, null);
    }

    public <T extends IEntity> T findFirst(EntitySQL<T> entity, IShardingable shardingable) throws Exception {
        return findFirst(entity, null, shardingable);
    }

    public <T extends IEntity> T findFirst(EntitySQL<T> entity, Where where) throws Exception {
        return findFirst(entity, where, null);
    }

    public <T extends IEntity> T findFirst(EntitySQL<T> entity, Where where, IShardingable shardingable) throws Exception {
        String _selectSql = __dialect.buildSelectSQL(entity.getEntityClass(), __tablePrefix, shardingable, __doGetNotExcludedFields(EntityMeta.createAndGet(entity.getEntityClass()), entity.fields(), false, true));
        if (where != null) {
            _selectSql = _selectSql.concat(" ").concat(where.toString());
        }
        _selectSql = __dialect.buildPagedQuerySQL(_selectSql, 1, 1);
        //
        if (entity.forUpdate() != null) {
            _selectSql = _selectSql + " " + entity.forUpdate().toSQL();
        }
        //
        IQueryOperator<T> _opt = new DefaultQueryOperator<T>(_selectSql, this.__connectionHolder, new EntityResultSetHandler<T>(entity.getEntityClass()));
        if (where != null) {
            for (Object _param : where.getParams().params()) {
                _opt.addParameter(_param);
            }
        }
        if (__sessionEvent != null) {
            __sessionEvent.onQueryBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onQueryAfter(new SessionEventContext(_opt));
        }
        //
        return _opt.getResultSet().isEmpty() ? null : _opt.getResultSet().get(0);
    }

    public int executeForUpdate(SQL sql) throws Exception {
        IUpdateOperator _opt = new DefaultUpdateOperator(sql.getSQL(), this.getConnectionHolder());
        for (Object _param : sql.params().params()) {
            _opt.addParameter(_param);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onUpdateBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onUpdateAfter(new SessionEventContext(_opt));
        }
        return _opt.getEffectCounts();
    }

    public int[] executeForUpdate(BatchSQL sql) throws Exception {
        IBatchUpdateOperator _opt = null;
        if (sql.getSQL() != null) {
            _opt = new BatchUpdateOperator(sql.getSQL(), this.getConnectionHolder());
            for (Params _param : sql.params()) {
                SQLBatchParameter _batchParam = SQLBatchParameter.create();
                for (Object _p : _param.params()) {
                    _batchParam.addParameter(_p);
                }
                _opt.addBatchParameter(_batchParam);
            }
        } else {
            _opt = new BatchUpdateOperator(this.getConnectionHolder());
        }
        for (String _sql : sql.getSQLs()) {
            _opt.addBatchSQL(_sql);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onUpdateBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onUpdateAfter(new SessionEventContext(_opt));
        }
        return _opt.getEffectCounts();
    }

    public <T extends IEntity> T update(T entity, Fields filter) throws Exception {
        return update(entity, filter, entity instanceof IShardingable ? (IShardingable) entity : null);
    }

    public <T extends IEntity> T update(T entity, Fields filter, IShardingable shardingable) throws Exception {
        EntityMeta _meta = EntityMeta.createAndGet(entity.getClass());
        if (_meta.isView()) {
            throw new UnsupportedOperationException("View does not support this operation.");
        }
        PairObject<Fields, Params> _entity = __doGetPrimaryKeyFieldAndValues(_meta, entity, null);
        filter = __doGetNotExcludedFields(_meta, filter, true, false);
        String _updateSql = __dialect.buildUpdateByPkSQL(entity.getClass(), __tablePrefix, shardingable, _entity.getKey(), filter);
        IUpdateOperator _opt = new DefaultUpdateOperator(_updateSql, this.__connectionHolder);
        // 先获取并添加需要更新的字段值
        for (Object _param : __doGetEntityFieldAndValues(_meta, entity, filter, false).getValue().params()) {
            _opt.addParameter(_param);
        }
        // 再获取并添加主键条件字段值
        for (Object _param : _entity.getValue().params()) {
            _opt.addParameter(_param);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onUpdateBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onUpdateAfter(new SessionEventContext(_opt));
        }
        if (_opt.getEffectCounts() > 0) {
            return entity;
        }
        return null;
    }

    public <T extends IEntity> List<T> update(List<T> entities, Fields filter) throws Exception {
        T _element = entities.get(0);
        EntityMeta _meta = EntityMeta.createAndGet(_element.getClass());
        if (_meta.isView()) {
            throw new UnsupportedOperationException("View does not support this operation.");
        }
        PairObject<Fields, Params> _entity = __doGetPrimaryKeyFieldAndValues(_meta, _element, null);
        filter = __doGetNotExcludedFields(_meta, filter, true, false);
        String _updateSql = __dialect.buildUpdateByPkSQL(_element.getClass(), __tablePrefix, null, _entity.getKey(), filter);
        IBatchUpdateOperator _opt = new BatchUpdateOperator(_updateSql, this.__connectionHolder);
        for (T entity : entities) {
            SQLBatchParameter _batchParam = SQLBatchParameter.create();
            _entity = __doGetEntityFieldAndValues(_meta, entity, filter, false);
            // 先获取并添加需要更新的字段值
            for (Object _param : _entity.getValue().params()) {
                _batchParam.addParameter(_param);
            }
            // 再获取并添加主键条件字段值
            _entity = __doGetPrimaryKeyFieldAndValues(_meta, entity, null);
            for (Object _param : _entity.getValue().params()) {
                _batchParam.addParameter(_param);
            }
            _opt.addBatchParameter(_batchParam);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onUpdateBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onUpdateAfter(new SessionEventContext(_opt));
        }
        return entities;
    }

    public <T extends IEntity> List<T> update(ShardingList<T> entities, Fields filter) throws Exception {
        List<T> _results = new ArrayList<T>();
        for (ShardingList.ShardingElement<T> _element : entities) {
            T _entity = this.update(_element.getElement(), filter, _element);
            if (_entity != null) {
                _results.add(_entity);
            }
        }
        return _results;
    }

    public <T extends IEntity> T insert(T entity) throws Exception {
        return insert(entity, null, (entity instanceof IShardingable ? (IShardingable) entity : null));
    }

    public <T extends IEntity> T insert(T entity, IShardingable shardingable) throws Exception {
        return insert(entity, null, shardingable);
    }

    public <T extends IEntity> T insert(T entity, Fields filter) throws Exception {
        return insert(entity, filter, (entity instanceof IShardingable ? (IShardingable) entity : null));
    }

    public <T extends IEntity> T insert(T entity, Fields filter, IShardingable shardingable) throws Exception {
        EntityMeta _meta = EntityMeta.createAndGet(entity.getClass());
        if (_meta.isView()) {
            throw new UnsupportedOperationException("View does not support this operation.");
        }
        PairObject<Fields, Params> _entity = __doGetEntityFieldAndValues(_meta, entity, filter, true);
        String _insertSql = __dialect.buildInsertSQL(entity.getClass(), __tablePrefix, shardingable, _entity.getKey());
        IUpdateOperator _opt = new DefaultUpdateOperator(_insertSql, this.__connectionHolder);
        if (_meta.hasAutoincrement()) {
            // 兼容Oracle无法直接获取生成的主键问题
            if (__connectionHolder.getDialect() instanceof OracleDialect) {
                final String[] _ids = _meta.getAutoincrementKeys().toArray(new String[_meta.getAutoincrementKeys().size()]);
                _opt.setAccessorConfig(new EntityAccessorConfig(_meta, __connectionHolder, entity) {
                    @Override
                    public PreparedStatement getPreparedStatement(Connection conn, String sql) throws SQLException {
                        if (conn != null && !conn.isClosed()) {
                            return conn.prepareStatement(sql, _ids);
                        }
                        return __conn.getConnection().prepareStatement(sql, _ids);
                    }
                });
            } else {
                _opt.setAccessorConfig(new EntityAccessorConfig(_meta, __connectionHolder, entity));
            }
        }
        // 获取并添加字段值
        for (Object _param : _entity.getValue().params()) {
            _opt.addParameter(_param);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onInsertBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onInsertAfter(new SessionEventContext(_opt));
        }
        if (_opt.getEffectCounts() > 0) {
            return entity;
        }
        return null;
    }

    public <T extends IEntity> List<T> insert(List<T> entities) throws Exception {
        return insert(entities, null);
    }

    public <T extends IEntity> List<T> insert(ShardingList<T> entities) throws Exception {
        return this.insert(entities, null);
    }

    @SuppressWarnings("unchecked")
    public <T extends IEntity> List<T> insert(List<T> entities, Fields filter) throws Exception {
        T _element = entities.get(0);
        EntityMeta _meta = EntityMeta.createAndGet(_element.getClass());
        if (_meta.isView()) {
            throw new UnsupportedOperationException("View does not support this operation.");
        }
        PairObject<Fields, Params> _entity = __doGetEntityFieldAndValues(_meta, _element, filter, true);
        String _insertSql = __dialect.buildInsertSQL(_element.getClass(), __tablePrefix, null, _entity.getKey());
        IBatchUpdateOperator _opt = new BatchUpdateOperator(_insertSql, this.__connectionHolder);
        if (_meta.hasAutoincrement()) {
            // 兼容Oracle无法直接获取生成的主键问题
            if (__connectionHolder.getDialect() instanceof OracleDialect) {
                final String[] _ids = _meta.getAutoincrementKeys().toArray(new String[_meta.getAutoincrementKeys().size()]);
                _opt.setAccessorConfig(new EntityAccessorConfig(_meta, __connectionHolder, (List<IEntity<?>>) entities) {
                    @Override
                    public PreparedStatement getPreparedStatement(Connection conn, String sql) throws SQLException {
                        if (conn != null && !conn.isClosed()) {
                            return conn.prepareStatement(sql, _ids);
                        }
                        return __conn.getConnection().prepareStatement(sql, _ids);
                    }
                });
            } else {
                _opt.setAccessorConfig(new EntityAccessorConfig(_meta, __connectionHolder, (List<IEntity<?>>) entities));
            }
        }
        for (T entity : entities) {
            SQLBatchParameter _batchParam = SQLBatchParameter.create();
            for (Object _param : __doGetEntityFieldAndValues(_meta, entity, filter, true).getValue().params()) {
                _batchParam.addParameter(_param);
            }
            _opt.addBatchParameter(_batchParam);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onInsertBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onInsertAfter(new SessionEventContext(_opt));
        }
        return entities;
    }

    public <T extends IEntity> List<T> insert(ShardingList<T> entities, Fields filter) throws Exception {
        List<T> _results = new ArrayList<T>();
        for (ShardingList.ShardingElement<T> _element : entities) {
            T _entity = this.insert(_element.getElement(), filter, _element);
            if (_entity != null) {
                _results.add(_entity);
            }
        }
        return _results;
    }

    public <T extends IEntity> T delete(T entity) throws Exception {
        return delete(entity, (entity instanceof IShardingable ? (IShardingable) entity : null));
    }

    public <T extends IEntity> T delete(T entity, IShardingable shardingable) throws Exception {
        if (this.delete(entity.getClass(), entity.getId(), shardingable) > 0) {
            return entity;
        }
        return null;
    }

    public <T extends IEntity> int delete(Class<T> entityClass, Serializable id) throws Exception {
        return delete(entityClass, id, null);
    }

    public <T extends IEntity> int delete(Class<T> entityClass, Serializable id, IShardingable shardingable) throws Exception {
        EntityMeta _meta = EntityMeta.createAndGet(entityClass);
        if (_meta.isView()) {
            throw new UnsupportedOperationException("View does not support this operation.");
        }
        PairObject<Fields, Params> _entity = __doGetPrimaryKeyFieldAndValues(_meta, id, null);
        String _deleteSql = __dialect.buildDeleteByPkSQL(entityClass, __tablePrefix, shardingable, _entity.getKey());
        IUpdateOperator _opt = new DefaultUpdateOperator(_deleteSql, this.__connectionHolder);
        // 获取并添加主键条件字段值
        for (Object _param : _entity.getValue().params()) {
            _opt.addParameter(_param);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onRemoveBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onRemoveAfter(new SessionEventContext(_opt));
        }
        return _opt.getEffectCounts();
    }

    public <T extends IEntity> List<T> delete(List<T> entities) throws Exception {
        EntityMeta _meta = EntityMeta.createAndGet(entities.get(0).getClass());
        if (_meta.isView()) {
            throw new UnsupportedOperationException("View does not support this operation.");
        }
        PairObject<Fields, Params> _entity = __doGetPrimaryKeyFieldAndValues(_meta, entities.get(0), null);
        String _deleteSql = __dialect.buildDeleteByPkSQL(entities.get(0).getClass(), __tablePrefix, null, _entity.getKey());
        IBatchUpdateOperator _opt = new BatchUpdateOperator(_deleteSql, this.__connectionHolder);
        for (T entity : entities) {
            SQLBatchParameter _batchParam = SQLBatchParameter.create();
            // 获取并添加主键条件字段值
            _entity = __doGetPrimaryKeyFieldAndValues(_meta, entity, null);
            for (Object _param : _entity.getValue().params()) {
                _batchParam.addParameter(_param);
            }
            _opt.addBatchParameter(_batchParam);
        }
        _opt.execute();
        return entities;
    }

    public <T extends IEntity> List<T> delete(ShardingList<T> entities) throws Exception {
        List<T> _results = new ArrayList<T>();
        for (ShardingList.ShardingElement<T> _element : entities) {
            T _entity = this.delete(_element.getElement(), _element);
            if (_entity != null) {
                _results.add(_entity);
            }
        }
        return _results;
    }

    public <T extends IEntity> int[] delete(Class<T> entityClass, Serializable[] ids) throws Exception {
        EntityMeta _meta = EntityMeta.createAndGet(entityClass);
        if (_meta.isView()) {
            throw new UnsupportedOperationException("View does not support this operation.");
        }
        PairObject<Fields, Params> _entity = __doGetPrimaryKeyFieldAndValues(_meta, ids[0], null);
        String _deleteSql = __dialect.buildDeleteByPkSQL(entityClass, __tablePrefix, null, _entity.getKey());
        IBatchUpdateOperator _opt = new BatchUpdateOperator(_deleteSql, this.__connectionHolder);
        for (Serializable _id : ids) {
            SQLBatchParameter _batchParam = SQLBatchParameter.create();
            // 获取并添加主键条件字段值
            _entity = __doGetPrimaryKeyFieldAndValues(_meta, _id, null);
            for (Object _param : _entity.getValue().params()) {
                _batchParam.addParameter(_param);
            }
            _opt.addBatchParameter(_batchParam);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onRemoveBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onRemoveAfter(new SessionEventContext(_opt));
        }
        return _opt.getEffectCounts();
    }

    public <T extends IEntity> int[] delete(Class<T> entityClass, ShardingList<Serializable> ids) throws Exception {
        List<Integer> _results = new ArrayList<Integer>();
        for (ShardingList.ShardingElement<Serializable> _element : ids) {
            _results.add(this.delete(entityClass, _element.getElement(), _element));
        }
        return ArrayUtils.toPrimitive(_results.toArray(new Integer[0]));
    }

    public <T extends IEntity> long count(Class<T> entityClass, Where where) throws Exception {
        return count(entityClass, where, null);
    }

    public <T extends IEntity> long count(Class<T> entityClass, Where where, IShardingable shardingable) throws Exception {
        EntityMeta _meta = EntityMeta.createAndGet(entityClass);
        ExpressionUtils _exp = ExpressionUtils.bind("SELECT count(1) FROM ${table_name} ${where}")
                .set("table_name", __dialect.buildTableName(__tablePrefix, _meta, shardingable))
                .set("where", where.toSQL());
        IQueryOperator<Object[]> _opt = new DefaultQueryOperator<Object[]>(_exp.getResult(), this.getConnectionHolder(), IResultSetHandler.ARRAY);
        for (Object _param : where.getParams().params()) {
            _opt.addParameter(_param);
        }
        if (__sessionEvent != null) {
            __sessionEvent.onQueryBefore(new SessionEventContext(_opt));
        }
        _opt.execute();
        if (__sessionEvent != null) {
            __sessionEvent.onQueryAfter(new SessionEventContext(_opt));
        }
        return BlurObject.bind(((Object[]) _opt.getResultSet().get(0)[0])[1]).toLongValue();
    }

    public long count(SQL sql) throws Exception {
        String _sql = ExpressionUtils.bind("SELECT count(1) FROM (${sql}) c_t").set("sql", sql.getSQL()).getResult();
        IQueryOperator<Object[]> _opt = new DefaultQueryOperator<Object[]>(_sql, this.getConnectionHolder(), IResultSetHandler.ARRAY);
        for (Object _param : sql.params().params()) {
            _opt.addParameter(_param);
        }
        _opt.execute();
        return BlurObject.bind(((Object[]) _opt.getResultSet().get(0)[0])[1]).toLongValue();
    }

    /**
     * @param entityMeta 实体元描述对象
     * @param targetObj  目标实体对象
     * @param filter     字段名称过滤集合
     * @return 获取主键对象的所有字段和值
     * @throws Exception 可能产生的异常
     */
    protected PairObject<Fields, Params> __doGetPrimaryKeyFieldAndValues(EntityMeta entityMeta, Object targetObj, Fields filter) throws Exception {
        Fields _fields = Fields.create();
        Params _values = Params.create();
        if (targetObj instanceof IEntityPK) {
            if (entityMeta.isMultiplePrimaryKey()) {
                for (String _pkFieldName : entityMeta.getPrimaryKeys()) {
                    Object _value = entityMeta.getPropertyByName(_pkFieldName).getField().get(targetObj);
                    if (_value != null) {
                        if (__doCheckField(filter, _pkFieldName)) {
                            _fields.add(_pkFieldName);
                            _values.add(_value);
                        }
                    }
                }
            } else {
                String _fieldName = entityMeta.getPrimaryKeys().get(0);
                if (__doCheckField(filter, _fieldName)) {
                    _fields.add(_fieldName);
                    _values.add(targetObj);
                }
            }
        } else if (targetObj instanceof IEntity) {
            if (entityMeta.isMultiplePrimaryKey()) {
                PairObject<Fields, Params> _tmpValues = __doGetPrimaryKeyFieldAndValues(entityMeta, ((IEntity) targetObj).getId(), filter);
                _fields.add(_tmpValues.getKey());
                _values.add(_tmpValues.getValue());
            } else {
                String _fieldName = entityMeta.getPrimaryKeys().get(0);
                if (__doCheckField(filter, _fieldName)) {
                    _fields.add(_fieldName);
                    _values.add(((IEntity) targetObj).getId());
                }
            }
        } else {
            String _fieldName = entityMeta.getPrimaryKeys().get(0);
            if (__doCheckField(filter, _fieldName)) {
                _fields.add(_fieldName);
                _values.add(targetObj);
            }
        }
        return new PairObject<Fields, Params>(_fields, _values);
    }

    /**
     * @param entityMeta 实体元描述对象
     * @param targetObj  目标实体对象
     * @param filter     进滤的字段名称集合
     * @param includePK  是否提取主键对象的值
     * @return 获取实体的所有字段和值
     * @throws Exception 可能产生的异常
     */
    protected PairObject<Fields, Params> __doGetEntityFieldAndValues(EntityMeta entityMeta, IEntity targetObj, Fields filter, boolean includePK) throws Exception {
        Fields _fields = Fields.create();
        Params _values = Params.create();
        for (String _fieldName : entityMeta.getPropertyNames()) {
            if (__doCheckField(filter, _fieldName)) {
                EntityMeta.PropertyMeta _propMeta = entityMeta.getPropertyByName(_fieldName);
                Object _value = null;
                if (entityMeta.isPrimaryKey(_fieldName)) {
                    if (includePK) {
                        // 自增字段将被忽略, 指定序列的除外
                        if (_propMeta.isAutoincrement()) {
                            if (StringUtils.isNotBlank(_propMeta.getSequenceName())) {
                                _fields.add(_fieldName);
                                // 尝试调用序列, 若当前数据库不支持序列将会抛出异常以示警告
                                __dialect.getSequenceNextValSql(_propMeta.getSequenceName());
                            }
                        } else {
                            if (entityMeta.isMultiplePrimaryKey()) {
                                _value = _propMeta.getField().get(targetObj.getId());
                            } else {
                                _value = targetObj.getId();
                            }
                        }
                    }
                } else {
                    _value = _propMeta.getField().get(targetObj);
                }
                // 以下操作是为了使@Default起效果的同时也保证数据库中的字段默认值不被null值替代
                if (_value == null) {
                    // 如果value为空则尝试提取默认值
                    _value = BlurObject.bind(_propMeta.getDefaultValue()).toObjectValue(_propMeta.getField().getType());
                }
                if (_value != null || _propMeta.isNullable()) {
                    if (includePK && entityMeta.isPrimaryKey(_fieldName) && entityMeta.isAutoincrement(_fieldName)) {
                        continue;
                    }
                    // 若value不为空则添加至返回对象中
                    _fields.add(_fieldName);
                    _values.add(_value);
                }
            }
        }
        return new PairObject<Fields, Params>(_fields, _values);
    }

    /**
     * @param filter    字段过滤对象
     * @param fieldName 数据表字段名称
     * @return 返回字段是否被过滤
     */
    protected boolean __doCheckField(Fields filter, String fieldName) {
        if (filter != null) {
            if (filter.isExcluded()) {
                return !filter.fields().contains(fieldName);
            } else {
                return filter.fields().contains(fieldName);
            }
        }
        return true;
    }

    /**
     * @param entityMeta 目标数据实体属性描述对象
     * @param filter     字段过滤对象
     * @param forUpdate  若是更新操作则需要过滤掉声明了@Readonly的字段
     * @param includePK  是否包含主键
     * @return 返回目标实体中所有未被过滤的字段名称集合
     */
    protected Fields __doGetNotExcludedFields(EntityMeta entityMeta, Fields filter, boolean forUpdate, boolean includePK) {
        Fields _returnValue = Fields.create();
        for (String _field : entityMeta.getPropertyNames()) {
            if (__doCheckField(filter, _field)) {
                if (!includePK && entityMeta.isPrimaryKey(_field)) {
                    continue;
                }
                if (forUpdate && entityMeta.isReadonly(_field)) {
                    continue;
                }
                _returnValue.add(_field);
            }
        }
        return _returnValue;
    }

    /**
     * 访问器配置接口私有实现，只为DefaultSession提供扩展服务
     */
    private class EntityAccessorConfig implements IAccessorConfig {

        protected EntityMeta __entityMeta;
        protected final IConnectionHolder __conn;
        protected List<IEntity<?>> __entities;

        public EntityAccessorConfig(EntityMeta entityMeta, IConnectionHolder connectionHolder, IEntity<?>... entity) {
            __entityMeta = entityMeta;
            __conn = connectionHolder;
            __entities = Arrays.asList(entity);
        }

        public EntityAccessorConfig(EntityMeta entityMeta, IConnectionHolder connectionHolder, List<IEntity<?>> entities) {
            __entityMeta = entityMeta;
            __conn = connectionHolder;
            __entities = entities;
        }

        public Statement getStatement(Connection conn) throws Exception {
            if (conn != null && !conn.isClosed()) {
                return conn.createStatement();
            }
            return __conn.getConnection().createStatement();
        }

        public CallableStatement getCallableStatement(Connection conn, String sql) throws Exception {
            if (conn != null && !conn.isClosed()) {
                return conn.prepareCall(sql);
            }
            return __conn.getConnection().prepareCall(sql);
        }

        public PreparedStatement getPreparedStatement(Connection conn, String sql) throws Exception {
            if (conn != null && !conn.isClosed()) {
                return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            }
            return __conn.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }

        public void beforeStatementExecution(AccessorEventContext context) throws Exception {
        }

        public void afterStatementExecution(AccessorEventContext context) throws Exception {
            if (__entities != null && __entityMeta.hasAutoincrement()) {
                // 注: 数据表最多一个自动生成主键
                // 获取返回的自动生成主键集合
                Object[] _genKeyValue = __dialect.getGeneratedKey(context.getStatement());
                if (_genKeyValue != null && _genKeyValue.length > 0) {
                    for (int _idx = 0; _idx < this.__entities.size(); _idx++) {
                        IEntity<?> _entity = __entities.get(_idx);
                        for (String _autoFieldName : __entityMeta.getAutoincrementKeys()) {
                            Field _field = __entityMeta.getPropertyByField(_autoFieldName).getField();
                            // 为自生成主键赋值, 自动填充
                            if (__entityMeta.isMultiplePrimaryKey()) {
                                _field.set(_entity.getId(), BlurObject.bind(_genKeyValue[_idx]).toObjectValue(_field.getType()));
                            } else {
                                _field.set(_entity, BlurObject.bind(_genKeyValue[_idx]).toObjectValue(_field.getType()));
                            }
                        }
                    }
                }
            }
        }

        public int getFetchDirection() {
            return 0;
        }

        public int getFetchSize() {
            return 10000;
        }

        public int getMaxFieldSize() {
            return 0;
        }

        public int getMaxRows() {
            return 1000;
        }

        public int getQueryTimeout() {
            return 0;
        }
    }
}
